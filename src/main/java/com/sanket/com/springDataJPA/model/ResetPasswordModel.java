package com.sanket.com.springDataJPA.model;


import lombok.Data;

@Data
public class ResetPasswordModel {
    private String email;
    private String oldPassword;
    private String newPassword;
}
