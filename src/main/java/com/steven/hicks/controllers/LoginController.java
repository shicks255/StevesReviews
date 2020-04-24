package com.steven.hicks.controllers;

import com.steven.hicks.models.User;
import com.steven.hicks.models.dtos.UserLogin;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody UserLogin user) {
        System.out.println(user);

        return ResponseEntity.of(Optional.of("Cool beans"));
    }
}
