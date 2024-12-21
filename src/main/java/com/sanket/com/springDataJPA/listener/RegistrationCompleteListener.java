package com.sanket.com.springDataJPA.listener;

import com.sanket.com.springDataJPA.entity.User;
import com.sanket.com.springDataJPA.event.RegistrationCompleteEvent;
import com.sanket.com.springDataJPA.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;


    // This is the implemented method present in the ApplicationListener
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // Create verification token for the user with link

        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        userService.saveVerificationTokenForUser(token, user);

        // create the application url link & send mail to user

        String applicationUrl = event.getApplicationUrl() + "/verifyRegistration?token=" + token;

        log.info("click the link to verify your account : {}", applicationUrl);
    }
}
