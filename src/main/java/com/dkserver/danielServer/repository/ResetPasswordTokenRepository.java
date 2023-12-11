package com.dkserver.danielServer.repository;

import com.dkserver.danielServer.models.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Integer> {

    ResetPasswordToken findByToken(String token);

}
