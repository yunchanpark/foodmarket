package com.lec.foodmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lec.foodmarket.service.MemberService;

@Controller
@RequestMapping("/layout/admin/member")
public class AdminMemberController {

	private MemberService memberService;

	@Autowired
	public AdminMemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	public AdminMemberController() {;}
	
	/******************************************
	 * 관리자
	 *   일반회원(조회, 수정, 삭제, 메시지보내기)
	 *   운영자(등록, 조회, 수정, 삭제, 권한 등록 및 수정 및 삭제)
	 * 추가사항이나 수정사항 알아서   
	 ******************************************/
	
	/******************************************
	 * GET 방식
	 ******************************************/
	// TODO
	
	
	
	
	/******************************************
	 * POST 방식
	 ******************************************/
	// TODO
	
	
	
	
	
}











