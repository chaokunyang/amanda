package com.timeyang.amanda.config;

import com.timeyang.amanda.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

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
        entityManagerFactoryRef = "entityManagerFactoryBean"
)
public class RootContextConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public InstrumentationLoadTimeWeaver loadTimeWeaver()  throws Throwable {
        InstrumentationLoadTimeWeaver loadTimeWeaver = new InstrumentationLoadTimeWeaver();
        return loadTimeWeaver;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws Throwable {
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.schema-generation.database.action",
                "none");
        properties.put("hibernate.ejb.use_class_enhancer", "true");
        properties.put("hibernate.search.default.directory_provider",
                "filesystem");
        properties.put("hibernate.search.default.indexBase", "../searchIndexes");

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");

        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(adapter);
        factory.setDataSource(this.dataSource);
        factory.setPackagesToScan(Application.class.getPackage().getName());
        factory.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        factory.setValidationMode(ValidationMode.NONE);
        factory.setLoadTimeWeaver(this.loadTimeWeaver()); // TODO: remove when SPR-10856 fixed
        factory.setJpaPropertyMap(properties);
        return factory;

    }

}
