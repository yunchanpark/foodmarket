package com.lec.foodmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lec.foodmarket.domain.Inquiry;
import com.lec.foodmarket.service.BoardService;

@Controller
@RequestMapping("/layout/user/board")
public class BoardController {

	private BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	public BoardController() {
		;
	}
	
	
//	/***********user_notice***********/
	
	@RequestMapping("/user_notice_list")
	public void user_notice_list(Model model) {
		model.addAttribute("user_notice_list", boardService.user_notice_list());
	}
	
	@GetMapping("/user_notice_view")
	public void view(int noticeNo, Model model) {
		model.addAttribute("list", boardService.notice_viewByUid(noticeNo));
	}
	
	


//	/******************************************
//	 * 사용자
//	 *   1대1문의 등록(이미지 포함), 조회(자신 글만)
//	 *   공지사항 조회
//	 * 추가사항이나 수정사항 알아서   
//	 ******************************************/
	
	
	
//	/***********user_inquiry***********/
	
	@GetMapping("/user_inquiry_view")
	public void user_inquiry_view(int inquiryNo, Model model) {
		model.addAttribute("user_inquiry_view", boardService.inquiry_viewByUid(inquiryNo));
	}
	
	@RequestMapping("/user_inquiry_list")
	public void user_inquiry_list(Model model) {
		model.addAttribute("user_inquiry_list", boardService.user_inquiry_list());
	}
	
	@RequestMapping("/user_inquiry_write")
	public void inquiry_write() {}   //   /board/write.html
	
	@PostMapping("/user_inquiry_writeOk")
	public void writeOk(Inquiry dto, Model model) {
		int cnt = boardService.inquiry_write(dto);
		model.addAttribute("result", cnt);
		model.addAttribute("dto", dto);
	}
	
	@GetMapping("/deleteOk")
	public void deleteOk(int inquiryNo, Model model) {
		model.addAttribute("result", boardService.inquiry_deleteByUid(inquiryNo));
	}
	
}
