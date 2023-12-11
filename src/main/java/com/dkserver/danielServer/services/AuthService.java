package com.dkserver.danielServer.services;


import com.dkserver.danielServer.dto.LoginDto;
import com.dkserver.danielServer.dto.RegisterDto;
import com.dkserver.danielServer.models.ResetPasswordToken;
import com.dkserver.danielServer.models.Role;
import com.dkserver.danielServer.models.UserEntity;
import com.dkserver.danielServer.repository.RoleRepo;
import com.dkserver.danielServer.repository.UserRepo;
import com.dkserver.danielServer.security.JwtGenerator;
import com.dkserver.danielServer.repository.ResetPasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtGenerator jwtGenerator;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    ResetPasswordTokenService resetPasswordTokenService;

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    public String loginUser(LoginDto loginDto) {
        String token = "";

        try {
            // Försök att autentisera användaren
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtGenerator.generakToken(authentication);
        } catch (AuthenticationException e) {
            //authentication failed!
        }

        return token;
    }

    public Optional<UserEntity> returnLoggedInUser(String token, LoginDto loginDto){
        Optional<UserEntity> user = null;

        if(token != null) {
            user = userRepo.findByUsername(loginDto.getUsername());
            return user;
        }
        return null;
    }


    public String saveRegistration(RegisterDto registerDto) {
        if(userRepo.existsByUsername(registerDto.getUsername())){
            return null;
        }
        UserEntity user = new UserEntity();
      //user.setUuid(UUID.randomUUID());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role roles = roleRepo.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        userRepo.save(user);
        return user.getUsername();
    }

    public String setResetPasswordToken(String email){
        Optional<UserEntity> user = userRepo.findByEmail(email);
        if(user.get().getEmail().equals(email)){
            //generate Token
            ResetPasswordToken resetPasswordToken = resetPasswordTokenService.generateResetToken(user);
            sendResetPasswordEmail(email, resetPasswordToken.getToken());
            return "ResetPassword email sent!";
        }
        return null;
    }

    public void sendResetPasswordEmail(String email, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("dev.badcake@gmail.com");
        message.setSubject("Återställ lösenord");
        message.setText("För att återställa ditt lösenord hos BadCake, klicka på länken: "
                + "http://localhost:8080/rest/auth/resetpassword?token=" + resetToken);
        mailSender.send(message);
    }

    public boolean resetPassword(String token, String newPassword) {
        ResetPasswordToken resetToken = resetPasswordTokenRepository.findByToken(token);

        if (resetToken != null && !resetToken.isExpired()) {
            int userId = resetToken.getUserId();
            Optional<UserEntity> user = userRepo.findById(userId);
            user.get().setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user.get());
            resetPasswordTokenRepository.delete(resetToken);
            return true;
        }

        return false;
    }


}
