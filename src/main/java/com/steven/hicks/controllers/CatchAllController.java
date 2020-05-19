package com.steven.hicks.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatchAllController {

    @GetMapping(value = {
            "/artist/**",
            "/album/**",
            "/topRated",
            "/user/**",
            "/search"
    })
    public String catchAll() {
        return "forward:/";
    }

}
