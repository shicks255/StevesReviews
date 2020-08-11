package com.steven.hicks.controllers;

import com.steven.hicks.services.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/visitor")
public class VisitorController {

    @Autowired
    VisitorService m_visitorService;

    @GetMapping
    public void markVisitor(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        m_visitorService.upsertVisitor(ip);
    }

}
