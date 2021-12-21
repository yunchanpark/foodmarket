package com.lec.foodmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lec.foodmarket.service.BoardService;
import com.lec.foodmarket.service.TotalService;

@Controller
@RequestMapping("/layout/admin/board")
public class AdminTotalController {

	private BoardService boardService;

	@Autowired
	public AdminTotalController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	public AdminTotalController() {;}
	
	

	@RequestMapping("/total")
	public void total(Model model) {
		model.addAttribute("total", TotalService.total());
	}
	
	@RequestMapping("/total2")
	public void total2(Model model) {
		model.addAttribute("total2", TotalService.total());
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










