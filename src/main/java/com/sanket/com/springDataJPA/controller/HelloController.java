package com.sanket.com.springDataJPA.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String helloController(){
        return "Hi There , I am the first API created";
    }
}
