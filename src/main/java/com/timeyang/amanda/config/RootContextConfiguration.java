package com.timeyang.amanda.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.timeyang.amanda.AmandaApplication;
import com.timeyang.amanda.core.fileserver.StorageProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * 根应用上下文配置
 *
 * @author chaokunyang
 * @create 2017-04-15
 */
@Configuration
@EnableScheduling
@EnableAsync(
        mode = AdviceMode.PROXY, proxyTargetClass = false,
        order = Ordered.HIGHEST_PRECEDENCE
)
@EnableLoadTimeWeaving // 附件、md需要懒加载以加快速度
@EnableTransactionManagement(
        mode = AdviceMode.PROXY, proxyTargetClass = false,
        order = Ordered.LOWEST_PRECEDENCE
)
@EnableJpaRepositories(
        basePackages = "com.timeyang.amanda",
        entityManagerFactoryRef = "entityManagerFactoryBean", // 必须指定，不然创建仓库会失败
        transactionManagerRef = "jpaTransactionManager"
)
@EnableConfigurationProperties(StorageProperties.class)
@EnableJpaAuditing
public class RootContextConfiguration implements
        AsyncConfigurer, SchedulingConfigurer {

    private static final Logger logger = LogManager.getLogger();
    private static final Logger schedulingLogger =
            LogManager.getLogger(logger.getName() + ".[scheduling]");

    private final DataSource dataSource;

    private final JpaProperties properties;

    @Autowired
    public RootContextConfiguration(DataSource dataSource, JpaProperties properties) {
        this.dataSource = dataSource;
        this.properties = properties;
    }

    @Bean
    public InstrumentationLoadTimeWeaver loadTimeWeaver() {
        InstrumentationLoadTimeWeaver loadTimeWeaver = new InstrumentationLoadTimeWeaver();
        return loadTimeWeaver;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        Map<String, Object> properties = new HashMap<>();
        // properties.put("javax.persistence.schema-generation.database.action", "none"); // 没有update选项，只有：none、create、drop-and-create、drop，不满足开发需求
        // properties.put("hibernate.hbm2ddl.auto", "update"); // 使用adapter.setGenerateDdl(true)，避免拼写错误;
        properties.put("hibernate.ejb.use_class_enhancer", "true");
        properties.put("hibernate.search.default.directory_provider", "filesystem");
        properties.put("hibernate.search.lucene_version", "5.3.1"); // 避免控制台警告，默认使用LUCENE_CURRENT
        properties.put("hibernate.search.default.indexBase", "../amanda/searchIndexes");

        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.use_sql_comments", "true");
        properties.put("hibernate.physical_naming_strategy", "com.timeyang.amanda.core.jpa.naming.PhysicalNamingStrategyImpl");

        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(this.dataSource);
        factory.setJpaVendorAdapter(jpaVendorAdapter());
        factory.setPackagesToScan(AmandaApplication.class.getPackage().getName());
        factory.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        factory.setValidationMode(ValidationMode.NONE);
        factory.setLoadTimeWeaver(this.loadTimeWeaver()); // TODO: remove when SPR-10856 fixed
        factory.setJpaPropertyMap(properties);
        return factory;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
        adapter.setGenerateDdl(true);
        return adapter;
    }

    @Bean
    public PlatformTransactionManager jpaTransactionManager()
    {
        return new JpaTransactionManager(
                this.entityManagerFactoryBean().getObject()
        );
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public JpaProperties getProperties() {
        return properties;
    }

    @Primary
    @Bean
    public ObjectMapper objectMapper()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE,
                false);
        return mapper;
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler()
    {
        logger.info("Setting up thread pool task scheduler with 20 threads.");
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(20);
        scheduler.setThreadNamePrefix("task-");
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setErrorHandler(t -> schedulingLogger.error(
                "Unknown error occurred while executing task.", t
        ));
        scheduler.setRejectedExecutionHandler(
                (r, e) -> schedulingLogger.error(
                        "Execution of task {} was rejected for unknown reasons.", r
                )
        );
        return scheduler;
    }

    @Override
    public Executor getAsyncExecutor() {
        Executor executor = this.taskScheduler();
        logger.info("Configuring asynchronous method executor {}.", executor);
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> schedulingLogger.error("the async method {} with parameters {} throw exception {}", method, params, ex);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        TaskScheduler scheduler = this.taskScheduler();
        logger.info("Configuring scheduled method executor {}.", scheduler);
        taskRegistrar.setTaskScheduler(scheduler);
    }
}
