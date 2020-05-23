package com.steven.hicks.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

//@RestControllerAdvice
public class ExceptionController {

//    @ExceptionHandler(NotLoggedInException.class)
    public String error(HttpServletRequest request) {
        return "";
    }

}
