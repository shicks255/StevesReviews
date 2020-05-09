package com.steven.hicks.controllers;

import com.steven.hicks.models.User;
import com.steven.hicks.models.dtos.UserStats;
import com.steven.hicks.services.StatsService;
import com.steven.hicks.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService m_userService;
    @Autowired
    StatsService m_statsService;

    @GetMapping()
    public User getLoggedInUser() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    @GetMapping("/stats")
    public UserStats getUserStats() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User u = (User)user;

        UserStats stats = m_statsService.getUserStats(u.getId());
        return stats;
    }

}
