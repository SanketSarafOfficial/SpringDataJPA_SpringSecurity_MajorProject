package com.sanket.com.springDataJPA.service;

import com.sanket.com.springDataJPA.entity.User;
import com.sanket.com.springDataJPA.entity.VerificationToken;
import com.sanket.com.springDataJPA.model.UserModel;
import com.sanket.com.springDataJPA.repository.UserRepository;
import com.sanket.com.springDataJPA.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setRole("USER");
        // we will encode this password using password encoder
        // we will get password details in hashcode
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));

        //save the information to the DB
        userRepository.save(user);

        return user;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken =
                new VerificationToken(user , token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        // verify whether data is present in verification token or not

        if(verificationToken == null){
            return "Invalid !";
        }

        // checking the time difference , if the expiration time has expired (time is 10 minutes what we have set
        // ) , then the token will be deleted from the database

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if(verificationToken.getExpirationTime().getTime() - cal.getTime().getTime() <= 10){
            verificationTokenRepository.delete(verificationToken);
            return "Token Expired !";
        }

        user.setEnabled(true);

        return "Valid";  // "valid" is hardcoded in the verification in controller

    }

    @Override
    public void deleteAllUserInfo() {
        verificationTokenRepository.deleteAll();
        userRepository.deleteAll();
    }
}
