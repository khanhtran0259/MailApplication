package com.mvn.springMvcServlet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CatchController {

    @GetMapping("")
    public String index(){

        return "redirect:/login";
    }
}
