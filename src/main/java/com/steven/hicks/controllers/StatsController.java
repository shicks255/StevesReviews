package com.steven.hicks.controllers;

import com.steven.hicks.models.dtos.SiteStats;
import com.steven.hicks.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    StatsService m_statsService;

    @GetMapping("/siteStats")
    public SiteStats getSiteStats() {
        return m_statsService.getSiteStats();
    }
}
