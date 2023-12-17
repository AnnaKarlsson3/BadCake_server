package com.dkserver.danielServer.controllers;


import com.dkserver.danielServer.dto.AuthResponseDto;
import com.dkserver.danielServer.dto.LoginDto;
import com.dkserver.danielServer.dto.RegisterDto;
import com.dkserver.danielServer.models.UserEntity;
import com.dkserver.danielServer.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/rest/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    //TODO: set string to constants.class

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto){
        String token = authService.loginUser(loginDto);
        if(!token.isEmpty()) {
            Optional<UserEntity> user = authService.returnLoggedInUser(token, loginDto);
            return ResponseEntity.status(HttpStatus.OK).body(new AuthResponseDto(token, user));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong username or password!");
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String username = authService.saveRegistration(registerDto);
        if(username == null){
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User registered success!", HttpStatus.CREATED);
    }


    @PostMapping("/resetpasswordemail")
    public ResponseEntity<String> resetPassword(@RequestParam String email) {
        String response = authService.setResetPasswordToken(email);
        if(response == null){
            return new ResponseEntity<>("Something is wrong!", HttpStatus.BAD_REQUEST);
        }
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/resetpassword")
        public ResponseEntity<String> updatePassword(@RequestParam String token, @RequestParam String newPassword) {
        // Validate the token and reset the password
        if (authService.resetPassword(token, newPassword)) {
            return new ResponseEntity<>("Password reset successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid or expired token.", HttpStatus.BAD_REQUEST);
        }
    }




}
