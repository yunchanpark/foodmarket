package com.lec.foodmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.Notice;
import com.lec.foodmarket.service.BoardService;

@Controller
@RequestMapping("/layout/admin/board")
public class AdminBoardController {

	private BoardService boardService;

	@Autowired
	public AdminBoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	public AdminBoardController() {;}
	
	
	//inquiry
	
	@RequestMapping("/inquiry_list")
	public void inquiry_list(Model model) {
		model.addAttribute("inquiry_list", boardService.inquiry_list());
	}
	
	
	
	//notice
	
	@RequestMapping("/notice_list")
	public void notice_list(Model model) {
		model.addAttribute("notice_list", boardService.notice_list());
	}
	
	@RequestMapping("/notice_write")
	public void notice_write(Model model) {
		Member member = Member.builder()
				.uid(1L)
				.id("admin")
				.addr("모충동")
				.detailAddr("105호")
				.name("운영자")
				.email("kjh80441@naver.com")
				.originProfile("안녕")
				.phoneNo("010-5103-1570")
				.pw("admin")
				.recommender("안녕")
				.saveProfile("안녕")
				.saveUpPoint(12)
				.build();
		model.addAttribute("member", member);
		System.out.println(member.getName());
	}
	
	@PostMapping("/notice_writeOk")
	public void notice_write(Notice dto, Model model) {
		int cnt = boardService.notice_write(dto);
		System.out.println(dto);
		model.addAttribute("result", cnt);
		model.addAttribute("dto", dto);
		
	}
	
	
	@GetMapping("/notice_update")
	public void update(int noticeNo, Model model) {
		model.addAttribute("list", boardService.selectByUid(noticeNo));
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/******************************************
	 * 관리자 
	 *   1대1문의 (조회, 삭제, 답변, 수정???)
	 *   공지사항 (조회, 삭제, 수정, 등록)
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










