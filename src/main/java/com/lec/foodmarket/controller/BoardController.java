package com.lec.foodmarket.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lec.foodmarket.commond.FileUpload;
import com.lec.foodmarket.domain.Inquiry;
import com.lec.foodmarket.domain.InquiryImage;
import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.dto.FileUploadDTO;
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
		model.addAttribute("user_inquiry_imageview", boardService.inquiryImagefind(inquiryNo));
	}

	@RequestMapping("/user_inquiry_list")
	public void user_inquiry_list(Model model) {
		model.addAttribute("user_inquiry_list", boardService.user_inquiry_list());
	}
	
	@RequestMapping("/user_inquiry_write")
	public void inquiry_write(Model model) {;}
	
	@PostMapping("/user_inquiry_writeOk")
	public void writeOk(Member member, Inquiry inquiry, BindingResult result, @RequestParam("image") MultipartFile upload, HttpSession session, RedirectAttributes redirectAttrs) throws Exception {
		member = (Member)session.getAttribute("member");
		inquiry.setId(member);
		int cnt = boardService.inquiry_write(inquiry);
		session.setAttribute("result", cnt);
		session.setAttribute("inquiry", inquiry);
		
		FileUpload file = new FileUpload();
		FileUploadDTO dto = file.ckUpload("/inquiryImages/inquiry/", "inquiryImages\\inquiry", upload);
		
		InquiryImage inquiryimage = InquiryImage.builder()
				.inquiryNo(inquiry)
				.inquiryOrgin(dto.getOrginName())
				.inquirySave(dto.getSaveName())
				.build();
		boardService.inquiryImageSave(inquiryimage);
	}
	

	
	@GetMapping("/user_inquiry_deleteOk")
	public void deleteOk(int inquiryNo, Model model) {
		model.addAttribute("result1", boardService.inquiryImageDelete(inquiryNo, "inquiryImages\\inquiry"));
		model.addAttribute("result", boardService.inquiry_deleteByUid(inquiryNo));
	}
	
	
	// road
	@RequestMapping("/road")
	public void road(Model model) {
		model.addAttribute("road", boardService.user_road());}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
