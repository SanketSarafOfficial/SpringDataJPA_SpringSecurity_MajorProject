package com.sanket.com.springDataJPA.listener;

import com.sanket.com.springDataJPA.entity.User;
import com.sanket.com.springDataJPA.event.RegistrationCompleteEvent;
import com.sanket.com.springDataJPA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import java.util.UUID;

public class RegistrationCompleteListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;


    // This is the implemented method present in the ApplicationListener
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // Create verification token for the user with link

        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        userService.saveVerificationTokenForUser(token , user);

        // once link is created , send mail to user

    }
}
