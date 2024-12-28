package com.sanket.com.springDataJPA.service;

import com.sanket.com.springDataJPA.entity.User;
import com.sanket.com.springDataJPA.entity.VerificationToken;
import com.sanket.com.springDataJPA.model.ResetPasswordModel;
import com.sanket.com.springDataJPA.model.UserModel;

import java.util.Optional;

public interface UserService {
     User registerUser(UserModel userModel);

     void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    void deleteAllUserInfo();

    VerificationToken generateNewVerificationToken(String oldToken);

    User findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);
}
