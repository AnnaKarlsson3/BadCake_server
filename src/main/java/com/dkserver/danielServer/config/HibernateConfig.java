package com.dkserver.danielServer.config;



import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class HibernateConfig {
    @Autowired
    private JpaProperties jpaProperties;

    @Bean
    JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource,
            MultiTenantConnectionProvider multiTenantConnectionProviderImpl,
            CurrentTenantIdentifierResolver currentTenantIdentifierResolverImpl
    ) {

        Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());
        //TODO: set string to constants.class
        jpaPropertiesMap.put("hibernate.multiTenancy", "DATABASE");
        jpaPropertiesMap.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProviderImpl);
        jpaPropertiesMap.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolverImpl);
       // jpaPropertiesMap.put(Environment.FORMAT_SQL, true);
       // jpaPropertiesMap.put(Environment.SHOW_SQL, true);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        //TODO: set string to constants.class
        em.setPackagesToScan("com.dkserver*");
        em.setJpaVendorAdapter(this.jpaVendorAdapter());
        em.setJpaPropertyMap(jpaPropertiesMap);
        return em;
    }
}