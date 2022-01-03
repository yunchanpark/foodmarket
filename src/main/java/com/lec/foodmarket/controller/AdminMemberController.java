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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonObject;
import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.Point;
import com.lec.foodmarket.domain.PointCondition;
import com.lec.foodmarket.domain.dto.MemberSerchDTO;
import com.lec.foodmarket.domain.dto.OperatorDTO;
import com.lec.foodmarket.domain.dto.PointDTO;
import com.lec.foodmarket.service.MemberService;
import com.lec.foodmarket.validator.MemberValidator;
import com.lec.foodmarket.validator.PointValidator;

@Controller
@RequestMapping("/layout/admin/member")
public class AdminMemberController {

	private MemberService memberService;
	
	// 회원관리 유효성
	private MemberValidator memberValidator;
	
	private PointValidator pointValidator;

	@Autowired
	public AdminMemberController(MemberService memberService, MemberValidator memberValidator, PointValidator pointValidator) {
		this.memberService = memberService;
		this.memberValidator = memberValidator;
		this.pointValidator = pointValidator;
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

	// 적립금 내역
	@GetMapping("/pointList")
	public String pointList(@Valid PointDTO pointDTO, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		pointValidator.validate(pointDTO, result);
		
		if(result.hasErrors()) {
			if (result.getFieldError("selectStartDate") != null)
				redirectAttributes.addFlashAttribute("errSelectStartDate", result.getFieldError("selectStartDate").getCode());
			if (result.getFieldError("selectEndDate") != null)
				redirectAttributes.addFlashAttribute("errSelectEndDate", result.getFieldError("selectEndDate").getCode());
			redirectAttributes.addFlashAttribute("search", pointDTO);
			return "redirect:/layout/admin/member/pointList";
		}
		
		int division = pointDTO.getDivision();
		String keyword = pointDTO.getKeyword();
		String searchKeyword = pointDTO.getSearchKeyword();
		LocalDate selectStartDate = pointDTO.getSelectStartDate();
		LocalDate selectEndDate = pointDTO.getSelectEndDate();
		LocalDateTime start;
		LocalDateTime end;
		
		List<Point> list = new ArrayList<Point>();
		
		if(division == 2) {
			if(keyword != null && (searchKeyword == null || searchKeyword.trim().length() == 0) && selectStartDate == null && selectEndDate == null) {
				list = memberService.pointAllSelect();
			}
			else if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() != 0) && selectStartDate == null && selectEndDate == null) {
				// 키워드가 아이디일 때
				if(keyword.equals("pointListId")) list = memberService.pointIdSelect(searchKeyword);
				// 키워드가 회원명일 때
				if(keyword.equals("pointListName")) list = memberService.pointNameSelect(searchKeyword);
				// 키워드가 사유일 때
				if(keyword.equals("pointListReason")) list = memberService.pointReasonSelect(searchKeyword);
			} 
			// 검색 카테고리와 날짜만 같이 입력했을 때
			else if (keyword != null && (searchKeyword == null || searchKeyword.trim().length() == 0) && selectStartDate != null && selectEndDate != null) {
				start = selectStartDate.atTime(0, 0, 0);
				end = selectEndDate.atTime(23, 59, 59);
				list = memberService.pointCreateAtSelect(start, end);
			}
			// 검색 카테고리와 날짜를 같이 입력했을 때
			else if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() != 0) && selectStartDate != null && selectEndDate != null) {
				start = selectStartDate.atTime(0, 0, 0);
				end = selectEndDate.atTime(23, 59, 59);
				if(keyword.equals("pointListId")) list = memberService.pointCreateAtAndMemberId(searchKeyword, start, end);
				if(keyword.equals("pointListName")) list = memberService.pointCreateAtAndMemberName(searchKeyword, start, end);
				if(keyword.equals("pointListReason")) list = memberService.pointCreateAtAndReason(searchKeyword, start, end);
			}
		} else if (division != 2) {
			if(keyword != null && (searchKeyword == null || searchKeyword.trim().length() == 0) && selectStartDate == null && selectEndDate == null) {
				list = memberService.pointStatusSelect(division);
			}
			else if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() != 0) && selectStartDate == null && selectEndDate == null) {
				// 키워드가 아이디일 때
				if(keyword.equals("pointListId")) list = memberService.pointStatusAndIdSelect(division, searchKeyword);
				// 키워드가 회원명일 때
				if(keyword.equals("pointListName")) list = memberService.pointStatusAndNameSelect(division, searchKeyword);
				// 키워드가 사유일 때
				if(keyword.equals("pointListReason")) list = memberService.pointStatusAndReasonSelect(division, searchKeyword);
			} 
			// 검색 카테고리와 날짜만 같이 입력했을 때
			else if (keyword != null && (searchKeyword == null || searchKeyword.trim().length() == 0) && selectStartDate != null && selectEndDate != null) {
				start = selectStartDate.atTime(0, 0, 0);
				end = selectEndDate.atTime(23, 59, 59);
				list = memberService.pointStatusAndCreateAtSelect(division, start, end);
			}
			// 검색 카테고리와 날짜를 같이 입력했을 때
			else if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() != 0) && selectStartDate != null && selectEndDate != null) {
				start = selectStartDate.atTime(0, 0, 0);
				end = selectEndDate.atTime(23, 59, 59);
				if(keyword.equals("pointListId")) list = memberService.pointStatusAndCreateAtAndMemberId(division, searchKeyword, start, end);
				if(keyword.equals("pointListName")) list = memberService.pointStatusAndCreateAtAndMemberName(division, searchKeyword, start, end);
				if(keyword.equals("pointListReason")) list = memberService.pointStatusAndCreateAtAndReason(division, searchKeyword, start, end);
			}
		}
		
		model.addAttribute("search", pointDTO);
		model.addAttribute("list",list);
		
		return "layout/admin/member/pointList";
	}
	
	// 적립금 설정 
	@GetMapping("/point")
	public void point(PointCondition pointCondition, Model model) {
		List<PointCondition> p =  memberService.pointConditionAllSelect();
		model.addAttribute("point", p);
	}

	@PostMapping("/point")
	public String updatePoint(PointCondition pointCondition) {
		memberService.updatepointCondition(pointCondition);
		return "redirect:/layout/admin/member/point";
	}
	
	@PostMapping("/pointSave")
	public String pointSave(@RequestParam(value="pointStatus", required = false) int Status, @RequestParam(value="pointName", required = false) String Name, 
			@RequestParam(value="pointPrice", required = false) int price, @RequestParam(value="pointUid", required = false) Member uid, Point point, Member member) {
		if(Status == 1) {
			Name = "관리자 수기 차감";
		}
		point = Point.builder()
				.name(Name)
				.point(price)
				.uid(uid)
				.status(Status)
				.build();
		memberService.pointSave(point);
		System.out.println(point.getUid());
		
		member = point.getUid();
		int a = member.getSaveUpPoint();
		
		if(Status == 0) {
			price = price + a;
		} else {
			price = a - price;
			price = price < 0 ? 0 : price;
		}
		memberService.memberSaveUpdate(member, price);
		return "redirect:/layout/admin/member/list";
	}
	
	@GetMapping("/operator")
	public void operator(Model model) {
		String ADMIN = "ADMIN";
		List<Member> list = new ArrayList<Member>();
		list = memberService.findByRole(ADMIN);
		model.addAttribute("list", list);
	}
	
	@PostMapping("/operatorIns")
	public String operatorIns(OperatorDTO operatorDTO, Member member, Model model) {
		System.out.println(1);
		System.out.println(operatorDTO);
	
		return "redirect:/layout/admin/member/operator";
	}
}











