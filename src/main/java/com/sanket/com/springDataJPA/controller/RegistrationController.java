package com.sanket.com.springDataJPA.controller;

import com.sanket.com.springDataJPA.entity.User;
import com.sanket.com.springDataJPA.entity.VerificationToken;
import com.sanket.com.springDataJPA.event.RegistrationCompleteEvent;
import com.sanket.com.springDataJPA.model.ResetPasswordModel;
import com.sanket.com.springDataJPA.model.UserModel;
import com.sanket.com.springDataJPA.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
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

    @PostMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest httpServletRequest) {

        // generation of new token & Saving in DB
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);

        //sending user & token details to get mail again
        User user = verificationToken.getUser();

        resendVerificationTokenMail(user, applicationUrl(httpServletRequest), verificationToken);

        return "Verification Link Sent Successfully !";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody ResetPasswordModel resetPasswordModel, HttpServletRequest request) {

        User user = userService.findUserByEmail(resetPasswordModel.getEmail());

        // so if user has data in it , then we have to generate the password reset token for it

        String url = "";

        if (user != null) {
            String token = UUID.randomUUID().toString();

            // Logic part & storing password reset token in DB
            userService.createPasswordResetTokenForUser(user, token);

            // url generation that will used to save password
            url = passwordResetTokenMail(user, applicationUrl(request), token);
        }
        return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody ResetPasswordModel resetPasswordModel) {

        String result = userService.validatePasswordResetToken(token);

        if (!result.equalsIgnoreCase("valid")) {
            return "Invalid Token";
        }

        // simply fetch the details of the user if the token is present

        Optional<User> user = userService.getUserByPasswordResetToken(token);

        if (user.isPresent()) {

            userService.changePassword(user.get() , resetPasswordModel.getNewPassword());

            return "Password Reset successful";
        } else {
            return "Invalid Token";
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody ResetPasswordModel resetPasswordModel){

        User user = userService.findUserByEmail(resetPasswordModel.getEmail());

        // if the old password given is not correct

        if(!userService.checkIfValidOldPassword(user , resetPasswordModel.getOldPassword())){
            return "Invalid Old Password";
        }

        //if old password validation is over without errors , then save New Password

        userService.changePassword(user , resetPasswordModel.getNewPassword());

        return "Password Saved Successfully";

    }

    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url = applicationUrl + "/savePassword?token=" + token;

        log.info("Click on the link below to reset your password : {}" + url);

        return url;
    }

    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {

        // generation of Url and logging it , so that when user clicks on it , we will see whether is token is generated and verified or not

        String url = applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();

        // logging

        log.info("Click the link to verify your account: {}", url);

    }


    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return "User Verification Successful";
        } else {
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

