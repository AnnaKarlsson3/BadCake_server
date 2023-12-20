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

    private int start = 0;

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
            DataSourceBuilder factory = DataSourceBuilder
                    .create().driverClassName(config.getDriverClassName())
                    .username(aes.decrypt(config.getUsername()))
                    .password(aes.decrypt(config.getPassword()))
                    .url(config.getUrl());
            DataSource ds = factory.build();
            return ds;
        }
        return null;
    }
}
