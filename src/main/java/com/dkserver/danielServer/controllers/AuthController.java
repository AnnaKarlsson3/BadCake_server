package com.dkserver.danielServer.controllers;


import com.dkserver.danielServer.dto.AuthResponseDto;
import com.dkserver.danielServer.dto.LoginDto;
import com.dkserver.danielServer.dto.RegisterDto;
import com.dkserver.danielServer.impl.DataSourceBasedMultiTenantConnectionProviderImpl;
import com.dkserver.danielServer.models.UserEntity;
import com.dkserver.danielServer.services.AuthService;
import com.dkserver.danielServer.services.TenantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import static com.dkserver.danielServer.utils.Constants.*;

@RestController
@CrossOrigin(origins = CORS_URL)
@RequestMapping(REST_AUTH)
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    DataSourceBasedMultiTenantConnectionProviderImpl dataSourceBasedMultiTenantConnectionProvider;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // Rensa säkerhetskontext, session, etc.
        SecurityContextHolder.getContext().setAuthentication(null);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Byt till standardtenanten
        tenantService.switchToDefaultTenant();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto){
        String token = authService.loginUser(loginDto);
        if(!token.isEmpty()) {
            Optional<UserEntity> user = authService.returnLoggedInUser(token, loginDto);

            //put in TenantMap if new registered!
            dataSourceBasedMultiTenantConnectionProvider.setInit();

            return ResponseEntity.status(HttpStatus.OK).body(new AuthResponseDto(token, user));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AUTH_RESPONSE_NO_FOUND);
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) throws Exception {
        String username = authService.saveRegistration(registerDto);
        if(username == null){
            return new ResponseEntity<>(AUTH_RESPONSE_TAKEN, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(AUTH_RESPONSE_UPLOAD_SUCCESS, HttpStatus.CREATED);
    }


    @PostMapping("/resetpasswordemail")
    public ResponseEntity<String> resetPassword(@RequestParam String email) {
        String response = authService.setResetPasswordToken(email);
        if(response == null){
            return new ResponseEntity<>(ERROR_RESPONSE_SOMETHING_WRONG, HttpStatus.BAD_REQUEST);
        }
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/resetpassword")
        public ResponseEntity<String> updatePassword(@RequestParam String token, @RequestParam String newPassword) {
        // Validate the token and reset the password
        if (authService.resetPassword(token, newPassword)) {
            return new ResponseEntity<>(AUTH_RESPONSE_PASSWORD_CHANGE_SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(AUTH_RESPONSE_PASSWORD_INVALID_TOKEN, HttpStatus.BAD_REQUEST);
        }
    }




}
