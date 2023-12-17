package com.dkserver.danielServer.repository;


import com.dkserver.danielServer.models.DataSourceConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSourceConfigRepo extends JpaRepository<DataSourceConfigEntity, Integer> {

    DataSourceConfigEntity findByName(String name);
}
