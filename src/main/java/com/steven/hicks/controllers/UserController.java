package com.steven.hicks.controllers;

import com.steven.hicks.models.User;
import com.steven.hicks.models.dtos.UserLogin;
import com.steven.hicks.models.dtos.UserStats;
import com.steven.hicks.services.JwtTokenService;
import com.steven.hicks.services.StatsService;
import com.steven.hicks.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService m_userService;
    @Autowired
    StatsService m_statsService;
    @Autowired
    JwtTokenService m_jwtTokenService;

    @GetMapping()
    public User getUser(HttpServletRequest request) {
        User user = m_jwtTokenService.getUserFromToken(request);
        return user;
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable int userId) {
        User user = m_userService.findById(userId);
        return user;
    }

    @GetMapping("/stats/{userId}")
    public UserStats getUserStats(@PathVariable int userId) {
        UserStats stats = m_statsService.getUserStats(userId);
        return stats;
    }

    @GetMapping("/stats")
    public UserStats getUserStats(HttpServletRequest request) {
        User user = m_jwtTokenService.getUserFromToken(request);
        UserStats userStats = m_statsService.getUserStats(user.getId());
        return userStats;
    }

    @PostMapping("/register")
    public void register(@RequestBody UserLogin user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        m_userService.registerUser(newUser);
    }
}
