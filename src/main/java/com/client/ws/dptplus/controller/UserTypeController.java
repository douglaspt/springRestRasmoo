package com.client.ws.dptplus.controller;

import com.client.ws.dptplus.model.jpa.UserType;
import com.client.ws.dptplus.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-type")
public class UserTypeController {

    @Autowired
    private UserTypeService userTypeService;

    @GetMapping
    public ResponseEntity<List<UserType>> findAll() {
        return ResponseEntity.ok().body(userTypeService.findAll());
    }
}
