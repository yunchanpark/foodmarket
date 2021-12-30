package com.lec.foodmarket.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.dto.MemberDTO;
import com.lec.foodmarket.service.MemberService;

@Controller
@RequestMapping("/layout/user/member")
public class MemberController {

	private MemberService memberService;

	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	public MemberController() {
		;
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	/******************************************
	 * 사용자 회원가입, 로그인 회원가입시 포인트 지급 이메일 인증, 휴대폰 인증 추가사항이나 수정사항 알아서
	 ******************************************/

	@RequestMapping("/register")
	public void register() {;}

	@RequestMapping("/login")
	public void login() {;}

	@GetMapping("/find_id")
	public void find_id() {;}

	@GetMapping("/find_pw")
	public void find_pw() {;}
	
	@RequestMapping("/find_pw_change")
	public void find_pw_change() {;}
	
	@GetMapping("/update")
	public void update() {;}

	// 아이디 중복체크
	@GetMapping("/checkId/{id}")
	public ResponseEntity<Boolean> checkIdDuplicate(@PathVariable("id") String id) {
		return ResponseEntity.ok(memberService.checkIdDuplicate(id));
	}

	// 이메일 중복체크
	@GetMapping("/checkEmail/{email}")
	public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable("email") String email) {
		return ResponseEntity.ok(memberService.checkEmailDuplicate(email));
	}
	
	// 회원정보 수정
	@PostMapping("/updateOk")
	public String updateOk(Member member, MemberDTO memberDTO, HttpSession session) {
		member = memberService.findById(memberDTO.getUpdate_id());
		memberDTO.setUpdate_pw(passwordEncoder.encode(memberDTO.getUpdate_pw()));
		
		memberService.memberUpdate(member, memberDTO);
		session.invalidate();
		return "redirect:/layout/user/index";
	}
	
	// 회원 탈퇴
	@RequestMapping("/deleteOk")
	public String deleteOk(HttpSession session) {
		Member member = (Member)session.getAttribute("member");
		long uid = member.getUid();
		
		memberService.memberDeleteByid(uid);
		session.invalidate();
		
		return "redirect:/layout/user/index";
	}

	// 회원가입
	@PostMapping("/registerOk")
	public String registerOk(Member member) {
		String encPassword = passwordEncoder.encode(member.getPw());
		member = Member.builder()
				.uid(member.getUid())
				.id(member.getId())
				.pw(encPassword)
				.name(member.getName())
				.addr(member.getAddr())
				.detailAddr(member.getDetailAddr())
				.email(member.getEmail())
				.phoneNo(member.getPhoneNo())
				.role("MEMBER")
				.originProfile("123")
				.saveProfile("123")
				.build();
		memberService.memberSave(member);
		return "redirect:/layout/user/member/login";
	}

	// 아이디 찾기
	@RequestMapping("/find_id_ck")
	public String find_id_ck(@RequestParam(value="find_id_name", required = false) String find_id_name,
			@RequestParam(value="find_id_email", required = false) String find_id_email, RedirectAttributes redirectAttributes) {
		
		String result = null;
		result = memberService.findIdByNameAndEmail(find_id_name, find_id_email);

		if(result !=null) {
			redirectAttributes.addFlashAttribute("findId", result);
			return "redirect:/layout/user/member/find_id_ck";
		}
		return "layout/user/member/find_id_ck";
	}

	// 비밀번호 찾기
	@RequestMapping("/find_pw_ck")
	public String find_pw_ck(@RequestParam(value = "find_pw_id", required = false) String find_pw_id,
			@RequestParam(value = "find_pw_name", required = false) String find_pw_name,
			@RequestParam(value = "find_pw_email", required = false) String find_pw_email, 
			RedirectAttributes redirectAttributes, HttpSession session) {
		
		String result = null;
		result = memberService.findPwByIdAndNameAndEmail(find_pw_id, find_pw_name, find_pw_email);
		
		if(result != null) {
			redirectAttributes.addFlashAttribute("checkFindPwEmail", find_pw_email);
			session.setAttribute("checkFindPWId", find_pw_id);
			redirectAttributes.addFlashAttribute("findPw", result);
			return "redirect:/layout/user/member/find_pw_ck";
		}
		return "layout/user/member/find_pw_ck";
	}

	@RequestMapping("/changeOk")
	public String find_pw_change(@RequestParam(value="find_pw_new", required = false) String find_pw_new, 
			HttpSession session) {
		String find_id = (String) session.getAttribute("checkFindPWId");
		String encPassword = passwordEncoder.encode(find_pw_new);
		memberService.updatePwById(encPassword, find_id);
		
		return "redirect:/layout/user/index";
	}

}
