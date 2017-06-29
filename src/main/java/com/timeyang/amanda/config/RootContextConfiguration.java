package com.timeyang.amanda.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.timeyang.amanda.AmandaApplication;
import com.timeyang.amanda.core.fileserver.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 根应用上下文配置
 *
 * @author chaokunyang
 * @create 2017-04-15
 */
@Configuration
@EnableLoadTimeWeaving // 附件、md需要懒加载以加快速度
@EnableJpaRepositories(
        basePackages = "com.timeyang.amanda",
        entityManagerFactoryRef = "entityManagerFactoryBean" // 必须指定，不然创建仓库会失败
)
@EnableConfigurationProperties(StorageProperties.class)
@EnableJpaAuditing
public class RootContextConfiguration {

    private final DataSource dataSource;

    private final JpaProperties properties;

    @Autowired
    public RootContextConfiguration(DataSource dataSource, JpaProperties properties) {
        this.dataSource = dataSource;
        this.properties = properties;
    }

    @Bean
    public InstrumentationLoadTimeWeaver loadTimeWeaver()  throws Throwable {
        InstrumentationLoadTimeWeaver loadTimeWeaver = new InstrumentationLoadTimeWeaver();
        return loadTimeWeaver;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder) throws Throwable {
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

}
