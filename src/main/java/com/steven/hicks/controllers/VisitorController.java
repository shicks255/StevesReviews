package com.steven.hicks.controllers;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/visitor")
public class VisitorController {

    @Autowired
    MeterRegistry m_meterRegistry;

    @GetMapping
    public void markVisitor(HttpServletRequest request) {

        System.out.println(request.getHeader("x-forwarded-for"));
        m_meterRegistry.counter("visitor", "ip", request.getHeader("x-forwarded-for"))
                .increment();
    }

}
