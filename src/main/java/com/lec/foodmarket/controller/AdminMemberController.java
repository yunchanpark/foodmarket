package com.lec.foodmarket.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonObject;
import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.dto.MemberSerchDTO;
import com.lec.foodmarket.service.MemberService;
import com.lec.foodmarket.validator.MemberValidator;

@Controller
@RequestMapping("/layout/admin/member")
public class AdminMemberController {

	private MemberService memberService;
	
	private MemberValidator memberValidator;

	@Autowired
	public AdminMemberController(MemberService memberService, MemberValidator memberValidator) {
		this.memberService = memberService;
		this.memberValidator = memberValidator;
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
	@GetMapping("/list")
	public String memberList(@Valid MemberSerchDTO memberSerchDTO, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		memberValidator.validate(memberSerchDTO, result);
		
		if(result.hasErrors()) {
			System.out.println(memberSerchDTO);
			if (result.getFieldError("selectStartDate") != null)
				redirectAttributes.addFlashAttribute("errSelectStartDate", result.getFieldError("selectStartDate").getCode());
			if (result.getFieldError("selectEndDate") != null)
				redirectAttributes.addFlashAttribute("errSelectEndDate", result.getFieldError("selectEndDate").getCode());
			redirectAttributes.addFlashAttribute("search", memberSerchDTO);
			return "redirect:/layout/admin/member/list";
		}
		
		String keyword = memberSerchDTO.getKeyword();
		String searchKeyword = memberSerchDTO.getSearchKeyword();
		LocalDate selectStartDate = memberSerchDTO.getSelectStartDate();
		LocalDate selectEndDate = memberSerchDTO.getSelectEndDate();
		LocalDateTime start;
		LocalDateTime end;
		
		List<Member> list = new ArrayList<Member>();
		
		// 검색 버튼만 눌렀을 때
		if(keyword != null && (searchKeyword == null || searchKeyword.trim().length() == 0) && selectStartDate == null && selectEndDate == null) {
			list = memberService.memberAllSelect();
		}
		// 검색 카테고리와 검색 키워드만 입력했을 때
		else if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() == 0) && selectStartDate == null && selectEndDate == null) {
			// 검색 카테고리가 아이디일 때
			if(keyword.equals("memberId")) list = memberService.memberIdSelect(searchKeyword);
			// 검색 카테고리가 이름일 때
			if(keyword.equals("memberName")) list = memberService.memberNameSelect(searchKeyword);
			// 검색 카테고리가 휴대폰번호일 때
			if(keyword.equals("memberPhoneNo")) list = memberService.memberPhoneNoSelect(searchKeyword);
			// 검색 카테고리가 이메일일 때
			if(keyword.equals("memberEmail")) list = memberService.memberEmailSelect(searchKeyword);
		} 
		// 검색 카테고리와 날짜를 같이 입력했을 때
		else if (keyword != null && (searchKeyword == null || searchKeyword.trim().length() == 0) && selectStartDate != null && selectEndDate != null) {
			start = selectStartDate.atTime(0, 0, 0);
			end = selectEndDate.atTime(23, 59, 59);
			list = memberService.memberCreateAtSelect(start, end);
		}
		else if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() != 0) && selectStartDate != null && selectEndDate != null) {
			start = selectStartDate.atTime(0, 0, 0);
			end = selectEndDate.atTime(23, 59, 59);
			if(keyword.equals("memberId")) list = memberService.memberCreateAtAndMemberId(searchKeyword, start, end);
			if(keyword.equals("memberName")) list = memberService.memberCreateAtAndMemberName(searchKeyword, start, end);
			if(keyword.equals("memberPhoneNo")) list = memberService.memberCreateAtAndMemberPhoneNo(searchKeyword, start, end);
			if(keyword.equals("memberEmail")) list = memberService.memberCreateAtAndMemberEmail(searchKeyword, start, end);
		}
		
		model.addAttribute("search", memberSerchDTO);
		model.addAttribute("list",list);
		
		return "layout/admin/member/list";
	}
	
	// 회원 상세보기
	@ResponseBody
	@RequestMapping(value="/listUid/{uid}")
	public JsonObject listUid(@PathVariable("uid") long uid, RedirectAttributes redirectAttributes ) {
		Optional<Member> list = memberService.findById(uid);
		String memberList = list.get().toString();
		JsonObject json = new JsonObject();
		
		json.addProperty("ck", memberList);
		return json;
	}
	
	@GetMapping("/operator")
	public void operator() {;}
	
	@GetMapping("/point")
	public void point() {;}
	
	
	
	/******************************************
	 * POST 방식
	 ******************************************/
	// TODO
	
	
	
	
	
}











