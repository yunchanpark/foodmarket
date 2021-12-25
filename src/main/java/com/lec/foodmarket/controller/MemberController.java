package com.lec.foodmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lec.foodmarket.service.MemberService;

@Controller
@RequestMapping("/layout/user/member")
public class MemberController {
	
	private MemberService memberService;

	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	public MemberController() {;}
	
	/******************************************
	 * 사용자
	 *   회원가입, 로그인
	 *   회원가입시 포인트 지급
	 *   이메일 인증, 휴대폰 인증
	 * 추가사항이나 수정사항 알아서   
	 ******************************************/
	
	/******************************************
	 * GET 방식
	 ******************************************/
	@RequestMapping("/register")
	public void register() {
		
	}
	
	@GetMapping("/login")
	public void login() {
		
	}
	
	@GetMapping("/find_id")
	public void find_id() {
		
	}
	
	@GetMapping("/find_id_ck")
	public void find_id_ck() {
		
	}
	
	@GetMapping("/find_pw")
	public void find_pw() {
		
	}
	
	@GetMapping("/find_pw_ck")
	public void find_pw_ck() {
		
	}
	
	@GetMapping("/find_pw_change")
	public void find_pw_change() {
		
	}
	
	@GetMapping("/update")
	public void update() {
		
	}

	
	
	
	
	
	/******************************************
	 * POST 방식
	 ******************************************/
	// TODO
	
	
	
	
	
}










