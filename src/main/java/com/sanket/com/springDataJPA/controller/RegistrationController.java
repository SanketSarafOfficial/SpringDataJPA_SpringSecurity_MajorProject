package com.sanket.com.springDataJPA.controller;

import com.sanket.com.springDataJPA.entity.User;
import com.sanket.com.springDataJPA.event.RegistrationCompleteEvent;
import com.sanket.com.springDataJPA.model.UserModel;
import com.sanket.com.springDataJPA.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.registerUser(userModel);
        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(
                user, applicationUrl(request)
        ));
        return "Success";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")){
            return "User Verification Successful";
        }else{
            return "Bad User :: User Verification Failed !!! ";
        }
    }

    @DeleteMapping("/deleteUserAndToken")
    public String deleteUserInfo() {
        userService.deleteAllUserInfo();
        return " All Information Deleted ";
    }


    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName()
                + ":"
                + request.getServerPort()
                + request.getContextPath();
    }
}

