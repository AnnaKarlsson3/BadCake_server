package com.dkserver.danielServer.utils;


import com.dkserver.danielServer.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetLoggedInUser {

    public String getLoggedInUserId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            return userDetails.getId();
        }
        return null;
    }
}
