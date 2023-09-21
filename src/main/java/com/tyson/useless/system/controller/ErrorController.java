package com.tyson.useless.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
    @RequestMapping("/customError")
    public String handleCustomError() {
        return "general_error"; // Use the general error HTML template
    }

    @RequestMapping("/error-429")
    public String error429() {
        return "custom_error_429";
    }
}
