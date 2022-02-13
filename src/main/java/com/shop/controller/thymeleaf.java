package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class thymeleaf {
@GetMapping(value = "/members/login")
public String thymeleaf1() {
	return "/layouts/layout1";
}
}
