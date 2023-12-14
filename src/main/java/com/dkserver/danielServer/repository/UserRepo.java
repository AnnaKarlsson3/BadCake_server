package com.dkserver.danielServer.repository;


import com.dkserver.danielServer.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByUsername(String username);

    @Query("SELECT u FROM UserEntity u WHERE u.id = :userId")
    Optional<UserEntity> findUserById(@Param("userId") String userId);
}
