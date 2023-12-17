package com.dkserver.danielServer.services;

import com.dkserver.danielServer.models.DataSourceConfigEntity;
import com.dkserver.danielServer.repository.DataSourceConfigRepo;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;

import static com.dkserver.danielServer.utils.Constants.*;

@Service
public class DatabaseCreationService {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DataSourceConfigRepo dataSourceConfigRepo;

    @Value("${spring.datasource.username}")
    private String databaseUsername;

    @Value("${spring.datasource.password}")
    private String databasePassword;

    public void createDatabaseForUser(String userUuid) {

        //TODO: set string to constants.class

        String newDbName = "badcake_" + userUuid;
        saveDataSourceConfigToUserDb(newDbName);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("CREATE DATABASE IF NOT EXISTS `" + newDbName + "`");
        // Switch to the new database
        jdbcTemplate.execute("USE `" + newDbName + "`");
        // Create table
        jdbcTemplate.execute(CreateShortNotesTableSQL());
        jdbcTemplate.execute(CreateLinksTableSQL());
        jdbcTemplate.execute(CreateCustomerTableSQL());
    }

    public String CreateShortNotesTableSQL(){
        return "CREATE TABLE IF NOT EXISTS shortnotes (" +
                "id int(11) NOT NULL AUTO_INCREMENT, " +
                "title varchar(500) NOT NULL, " +
                "short_description varchar(500) NOT NULL, " +
                "description varchar(5000) NOT NULL, " +
                "user_id varchar(50) NOT NULL, " +
                "PRIMARY KEY (id)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
    }

    public String CreateLinksTableSQL(){
        return "CREATE TABLE IF NOT EXISTS links (" +
                "id int(11) NOT NULL AUTO_INCREMENT, " +
                "name varchar(500) NOT NULL, " +
                "prefix varchar(500) NOT NULL, " +
                "link varchar(5000) NOT NULL, " +
                "user_id varchar(50) NOT NULL, " +
                "PRIMARY KEY (id)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
    }

    public String CreateCustomerTableSQL(){
        return "CREATE TABLE IF NOT EXISTS customers (" +
                "id int(11) NOT NULL AUTO_INCREMENT, " +
                "name varchar(500) NOT NULL, " +
                "org_nr varchar(500) NOT NULL, " +
                "contact varchar(500) NOT NULL, " +
                "contact_phone varchar(500) NOT NULL, " +
                "admin_name varchar(500) NOT NULL, " +
                "admin_password varchar(500) NOT NULL, " +
                "address varchar(500) NOT NULL, " +
                "post_no varchar(500) NOT NULL, " +
                "city varchar(500) NOT NULL, " +
                "user_id varchar(50) NOT NULL, " +
                "PRIMARY KEY (id)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
    }


    public void saveDataSourceConfigToUserDb(String dbName){
        DataSourceConfigEntity dataSourceConfigEntity = new DataSourceConfigEntity();
        dataSourceConfigEntity.setName(dbName);
        dataSourceConfigEntity.setDriverClassName(DRIVERCLASS_NAME);
        dataSourceConfigEntity.setUrl(NEW_JDBC_URL + dbName + USE_SSL_IN_JDBC_URL);
        dataSourceConfigEntity.setUsername(databaseUsername);
        dataSourceConfigEntity.setPassword(databasePassword);
        dataSourceConfigEntity.setInitialize(true);

        dataSourceConfigRepo.save(dataSourceConfigEntity);
    }
}