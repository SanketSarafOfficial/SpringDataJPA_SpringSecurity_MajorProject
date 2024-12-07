package com.sanket.com.springDataJPA.service;

import com.sanket.com.springDataJPA.entity.User;
import com.sanket.com.springDataJPA.entity.VerificationToken;
import com.sanket.com.springDataJPA.model.UserModel;
import com.sanket.com.springDataJPA.repository.UserRepository;
import com.sanket.com.springDataJPA.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
