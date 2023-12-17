package com.dkserver.danielServer.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reset_password_token")
public class ResetPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;
    @Column(name="expiry_date")
    private LocalDateTime expiryDate;
    @Column(name="user_id")
    private String userId;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    /*@OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;*/
}
