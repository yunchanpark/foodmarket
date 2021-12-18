package com.lec.foodmarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/layout")
public class HomeController {
	
	public HomeController() {;}
	
	/******************************************
	 * GET 방식
	 ******************************************/
	
	@GetMapping("/admin/index")
	public void adminHome() {
		//TODO
	}

	@GetMapping("/user/index")
	public void userHome() {
		//TODO
	}
}
