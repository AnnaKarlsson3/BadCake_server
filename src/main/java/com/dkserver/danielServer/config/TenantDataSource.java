package com.dkserver.danielServer.config;

import com.dkserver.danielServer.models.DataSourceConfigEntity;
import com.dkserver.danielServer.repository.DataSourceConfigRepo;
import com.dkserver.danielServer.security.AES;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TenantDataSource {

    private HashMap<String, DataSource> dataSources = new HashMap<>();

    @Autowired
    private DataSourceConfigRepo configRepo;

    @Autowired
    AES aes;


    public DataSource getDataSource(String name) throws Exception {
        if (dataSources.get(name) != null) {
            return dataSources.get(name);
        }
        DataSource dataSource = createDataSource(name);
        if (dataSource != null) {
            dataSources.put(name, dataSource);
        }
        return dataSource;
    }

  /*  public DataSource getDataSource(String name) throws Exception {
        if (dataSources.containsKey(name)) {
            return dataSources.get(name);
        }
        DataSource dataSource = createDataSource(name);
        if (dataSource != null) {
            dataSources.put(name, dataSource);
        }
        return dataSource;
    }*/


    public DataSource addTenant(String tenantName) throws Exception {
        DataSource dataSource = createDataSource(tenantName);
        if (dataSource != null) {
            dataSources.put(tenantName, dataSource);
        }
        return dataSource;
    }



    @PostConstruct
    public Map<String, DataSource> getAll() throws Exception {
        List<DataSourceConfigEntity> configList = configRepo.findAll();
        Map<String, DataSource> result = new HashMap<>();
        for (DataSourceConfigEntity config : configList) {
            DataSource dataSource = getDataSource(config.getName());
            result.put(config.getName(), dataSource);
        }
        return result;
    }

    private DataSource createDataSource(String name) throws Exception {
        DataSourceConfigEntity config = configRepo.findByName(name);
        if (config != null) {
            String decryptedUsername = aes.decrypt(config.getUsername());
            String decryptedPassword = aes.decrypt(config.getPassword());

            DataSourceBuilder factory = DataSourceBuilder
                    .create().driverClassName(config.getDriverClassName())
                    .username(decryptedUsername)
                    .password(decryptedPassword)
                    .url(config.getUrl());
            DataSource ds = factory.build();

            return ds;
        }
        return null;
    }
}
