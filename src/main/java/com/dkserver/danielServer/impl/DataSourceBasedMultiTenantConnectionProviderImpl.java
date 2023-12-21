package com.dkserver.danielServer.impl;

import com.dkserver.danielServer.config.TenantDataSource;
import com.dkserver.danielServer.security.AES;
import com.dkserver.danielServer.services.TenantService;
import jakarta.annotation.PostConstruct;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static com.dkserver.danielServer.utils.Constants.DEFAULT_TENANT;
import static com.dkserver.danielServer.utils.Constants.DEFAULT_TENANT_ID;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    @Autowired
    private DataSource defaultDS;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TenantService tenantService;

    @Autowired
    AES aes;

    private Map<String, DataSource> map = new HashMap<>();

    public boolean init = false;
    boolean first = false;

    @PostConstruct
    public void load() {
        map.put(DEFAULT_TENANT_ID, defaultDS);
    }

    public boolean setInit(){
        return init = false;
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return map.get(DEFAULT_TENANT_ID);
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        if (!init) {
            init = true;
            TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
            try {
                map.putAll(tenantDataSource.getAll());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return map.get(tenantIdentifier) != null ? map.get(tenantIdentifier) : map.get(DEFAULT_TENANT_ID);
    }

/*    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        if (!tenantIdentifier.equals("PUBLIC") && !map.containsKey(tenantIdentifier)) {
            try {
                TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
                DataSource dataSource = tenantDataSource.getDataSource(tenantIdentifier);
                if (dataSource != null) {
                    map.put(tenantIdentifier, dataSource);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return map.getOrDefault(tenantIdentifier, map.get(DEFAULT_TENANT_ID));
    }*/

}
