package com.sanket.com.springDataJPA.service;

import com.sanket.com.springDataJPA.entity.PasswordResetToken;
import com.sanket.com.springDataJPA.entity.User;
import com.sanket.com.springDataJPA.entity.VerificationToken;
import com.sanket.com.springDataJPA.model.UserModel;
import com.sanket.com.springDataJPA.repository.PasswordResetTokenRepository;
import com.sanket.com.springDataJPA.repository.UserRepository;
import com.sanket.com.springDataJPA.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

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

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);

        //setting up new Verification Token by generating a new Token

        verificationToken.setToken(UUID.randomUUID().toString());

        // simply save the data , it will replace old token with new one.
        verificationTokenRepository.save(verificationToken);

        return verificationToken;
    }

    // simply returning the user details by searching it through mail details
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    @Transactional
    public void createPasswordResetTokenForUser(User user, String token) {

        // store the password reset token details in the DB

        PasswordResetToken passwordResetToken
                = new PasswordResetToken(user , token);

        // checking if Id is equal and deleting the older id if present , because the old id may cause Duplicate error

        if(Objects.equals(passwordResetToken.getUser().getId(), user.getId())){
            if(passwordResetToken.getToken()!= null){
                deleteByUserId(passwordResetToken.getUser().getId());
            }
            passwordResetTokenRepository.save(passwordResetToken);
        }
    }

    @Override
    public String validatePasswordResetToken(String token) {

        // same code as of validateVerificationToken()

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        if(passwordResetToken == null){
            return "Invalid !";
        }

        User user = passwordResetToken.getUser();
        Calendar cal = Calendar.getInstance();

        if(passwordResetToken.getExpirationTime().getTime() - cal.getTime().getTime() <= 10){
            passwordResetTokenRepository.delete(passwordResetToken);
            return "Token Expired !";
        }

        return "Valid";

    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword , user.getPassword());
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        passwordResetTokenRepository.deleteUserByUserId(userId);
    }
}
