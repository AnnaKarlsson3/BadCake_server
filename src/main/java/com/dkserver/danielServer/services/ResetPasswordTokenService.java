package com.dkserver.danielServer.services;

import com.dkserver.danielServer.models.ResetPasswordToken;
import com.dkserver.danielServer.models.UserEntity;
import com.dkserver.danielServer.repository.ResetPasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResetPasswordTokenService {

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    public ResetPasswordToken generateResetToken(Optional<UserEntity> user) {
        String tokenValue = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1); // Token expires in 1 hour

        ResetPasswordToken resetToken = new ResetPasswordToken();
        resetToken.setToken(tokenValue);
        resetToken.setExpiryDate(expiryDate);
        resetToken.setUserId(user.get().getId());

        return resetPasswordTokenRepository.save(resetToken);
    }
}
