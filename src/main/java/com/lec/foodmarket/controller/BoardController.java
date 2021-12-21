package com.lec.foodmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping("/user_notice_list")
	public void user_notice_list(Model model) {
		model.addAttribute("user_notice_list", boardService.user_notice_list());
	}

//	/******************************************
//	 * 사용자
//	 *   1대1문의 등록(이미지 포함), 조회(자신 글만)
//	 *   공지사항 조회
//	 * 추가사항이나 수정사항 알아서   
//	 ******************************************/
//	
//	/******************************************
//	 * GET 방식
//	 ******************************************/
//	// TODO
//
//	
//	
//	
//	/******************************************
//	 * POST 방식
//	 ******************************************/
//	// TODO

}
