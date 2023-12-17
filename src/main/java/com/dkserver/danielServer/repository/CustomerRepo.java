package com.dkserver.danielServer.repository;



import com.dkserver.danielServer.models.CustomerEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, Integer> {

    @Query("SELECT c FROM CustomerEntity c WHERE c.userId = :userId")
    List<CustomerEntity> findAllByUserId(@Param("userId") String userId);

    @Query("SELECT c FROM CustomerEntity c WHERE c.id = :id AND c.userId = :userId")
    CustomerEntity findByIdAndUserId(@Param("id") Integer id, @Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CustomerEntity c WHERE c.id = :id AND c.userId = :userId")
    void deleteByIdAndUserId(@Param("id") Integer id,@Param("userId") String userId);
}
