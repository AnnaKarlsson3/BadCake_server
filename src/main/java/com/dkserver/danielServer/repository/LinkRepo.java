package com.dkserver.danielServer.repository;


import com.dkserver.danielServer.models.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LinkRepo extends JpaRepository<LinkEntity, Integer> {

    @Query("SELECT l FROM LinkEntity l WHERE l.userId = :userId")
    List<LinkEntity> findAllByUserId(@Param("userId") String userId);

    @Query("SELECT l FROM LinkEntity l WHERE l.id = :id AND l.userId = :userId")
    LinkEntity findByIdAndUserId(@Param("id") Integer id, @Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM LinkEntity l WHERE l.id = :id AND l.userId = :userId")
    void deleteByIdAndUserId(@Param("id") Integer id,@Param("userId") String userId);
}
