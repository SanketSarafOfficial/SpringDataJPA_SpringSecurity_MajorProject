package com.sanket.com.springDataJPA.service;

import com.sanket.com.springDataJPA.entity.User;
import com.sanket.com.springDataJPA.model.UserModel;

public interface UserService {
     User registerUser(UserModel userModel);

     void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    void deleteAllUserInfo();
}
