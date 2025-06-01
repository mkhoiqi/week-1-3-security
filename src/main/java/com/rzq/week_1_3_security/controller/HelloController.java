package com.rzq.week_1_3_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@GetMapping("/api/public")
	public String publicEndpoint() {
		return "this is public endpoint";
	}
	
	@GetMapping("/api/admin")
	public String adminEndpoint() {
		return "this is admin endpoint";
	}
	
	@GetMapping("/api/user")
	public String userEndpoint() {
		return "this is user endpoint";
	}
}
