package com.main.prevoyancehrm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.prevoyancehrm.entities.User;
import com.main.prevoyancehrm.service.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class PracticeController {


    @Autowired
    private UserServiceImpl userServiceImpl;
    
    @GetMapping("/ok")
    public List<User> getEmployees(){
        return this.userServiceImpl.getAllEmployees();
    }
}
