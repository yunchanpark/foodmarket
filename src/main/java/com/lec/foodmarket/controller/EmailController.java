package com.lec.foodmarket.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.lec.foodmarket.service.EmailService;

@Controller
@RequestMapping("/email")
public class EmailController {
	
	private EmailService emailService;
	
	@Autowired
	public EmailController(EmailService emailService) {
		this.emailService = emailService;
	}
	
	public EmailController() {;}
	
	// 이메일 전송
	@ResponseBody
	@GetMapping("/auth/{email}")
	public void emailConfirm(@PathVariable("email") String email, HttpSession session) throws Exception {
		// 이전 "email_auth" 세션만 삭제
		session.removeAttribute("email_auth");
		
		String key = emailService.createKey();
		emailService.sendSimpleMessage(email, key);
		session.setAttribute("email_auth", key);
	}
	
	// 이메일 인증 버튼
	@ResponseBody
	@PostMapping(value = "/authCk", produces="application/json")
	public JsonObject emailCk(String emailhash, HttpSession session) {
		String key = (String) session.getAttribute("email_auth");
		JsonObject json = new JsonObject();
		int result = 0;
		
		if(key.equals(emailhash)) result = 1;
		json.addProperty("ck", result);
		return json;
	}
	
	// 비밀번호 찾기 이메일 인증 버튼
	@ResponseBody
	@PostMapping(value = "/findAuthCk", produces="application/json")
	public JsonObject findauthCk(@RequestParam("find_pw_auth") String find_pw_auth, HttpSession session) {
		String key = (String) session.getAttribute("email_auth");
		System.out.println(key);
		System.out.println(find_pw_auth);
		JsonObject json = new JsonObject();
		int result = 0;
		
		if(key.equals(find_pw_auth)) result = 1;
		json.addProperty("ck", result);
		return json;
	}
}
