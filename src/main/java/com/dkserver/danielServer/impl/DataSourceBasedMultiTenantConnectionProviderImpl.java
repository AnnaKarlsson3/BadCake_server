package com.dkserver.danielServer.impl;

import com.dkserver.danielServer.config.TenantDataSource;
import com.dkserver.danielServer.security.AES;
import jakarta.annotation.PostConstruct;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
    private static final String DEFAULT_TENANT_ID = "badcake";
    @Autowired
    private DataSource defaultDS;

    @Autowired
    private ApplicationContext context;

    @Autowired
    AES aes;

    private Map<String, DataSource> map = new HashMap<>();

    boolean init = false;

    @PostConstruct
    public void load() {
        map.put(DEFAULT_TENANT_ID, defaultDS);
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return map.get(DEFAULT_TENANT_ID);
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        if (!init) {
            init = true;
            try {
                aes.encrypt("Default encrypt");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
            try {
                map.putAll(tenantDataSource.getAll());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return map.get(tenantIdentifier) != null ? map.get(tenantIdentifier) : map.get(DEFAULT_TENANT_ID);
    }
}
