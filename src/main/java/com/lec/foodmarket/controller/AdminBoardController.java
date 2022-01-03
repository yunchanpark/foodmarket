package com.lec.foodmarket.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lec.foodmarket.domain.Inquiry;
import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.Notice;
import com.lec.foodmarket.domain.dto.InquirySearchDTO;
import com.lec.foodmarket.domain.dto.NoticeSearchDTO;
import com.lec.foodmarket.service.BoardService;
import com.lec.foodmarket.validator.InquirySearchValidator;
import com.lec.foodmarket.validator.NoticeSearchValidator;

@Controller
@RequestMapping("/layout/admin/board")
public class AdminBoardController {

	private BoardService boardService;

	// 공지사항 유효성
	private NoticeSearchValidator noticesearchValidator;

	// 1:1문의 유효성
	private InquirySearchValidator inquirysearchValidator;

	@Autowired
	public AdminBoardController(BoardService boardService, NoticeSearchValidator noticesearchValidator,
			InquirySearchValidator inquirysearchValidator) {
		this.boardService = boardService;
		this.noticesearchValidator = noticesearchValidator;
		this.inquirysearchValidator = inquirysearchValidator;
	}

	public AdminBoardController() {
		;
	}

	// inquiry

	@RequestMapping("/inquiry_list")
	public void inquiry_list(Model model) {
		model.addAttribute("inquiry_list", boardService.inquiry_list());
	}

	@GetMapping("/inquiry_update")
	public void inquiry_update(int inquiryNo, Model model) {
		model.addAttribute("list", boardService.inquiry_viewByUid(inquiryNo));
		model.addAttribute("user_inquiry_imageview", boardService.inquiryImagefind(inquiryNo));
	}

	@PostMapping("/inquiry_updateOk")
	public void inquiry_updateOk(@ModelAttribute("dto") Inquiry dto, Model model) {
		model.addAttribute("result", boardService.inquiry_update(dto));
	}

	// -------------------------------------------------------------

	// 공지사항 list (검색)
	@GetMapping("/inquiry_list")
	public String inquiryList(@Valid InquirySearchDTO inquirysearchDTO, BindingResult result, Model model,
			RedirectAttributes redirectAttrs) {
		// 상품 검색 유효성 바인딩
		inquirysearchValidator.validate(inquirysearchDTO, result);
		int listt = 0;
		if (result.hasErrors()) {
			System.out.println(inquirysearchDTO);
			if (result.getFieldError("selectStartDate") != null)
				redirectAttrs.addFlashAttribute("errSelectStartDate",
						result.getFieldError("selectStartDate").getCode());
			if (result.getFieldError("selectEndDate") != null)
				redirectAttrs.addFlashAttribute("errSelectEndDate", result.getFieldError("selectEndDate").getCode());
			redirectAttrs.addFlashAttribute("search", inquirysearchDTO);
			return "redirect:/layout/admin/board/inquiry_list";
		}

		int inquirycnt = inquirysearchDTO.getInquirycnt();
		String keyword = inquirysearchDTO.getKeyword();
		int answerkeyword = inquirysearchDTO.getAnswerkeyword();
		String searchKeyword = inquirysearchDTO.getSearchKeyword();
		LocalDate selectStartDate = inquirysearchDTO.getSelectStartDate();
		LocalDate selectEndDate = inquirysearchDTO.getSelectEndDate();
		LocalDateTime start;
		LocalDateTime end;

		List<Inquiry> list = new ArrayList<Inquiry>();

		Inquiry inquiry;

		// 검색 버튼만 눌렀을 때
		if (keyword != null && (searchKeyword == null || searchKeyword.trim().length() == 0) && selectStartDate == null
				&& selectEndDate == null) {
			list = boardService.inquiry_list();
		}

		// 미 답변일 때
		if (answerkeyword == 0) {
			// 검색 카테고리와 검색 키워드만 입력했을 때
			if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() == 0)
					&& selectStartDate == null && selectEndDate == null) {

				// 검색 카테고리가 제목일 때
				if (keyword.equals("inquiry_title")) {
					list = boardService.inquiryStatusAndTitleSelect(answerkeyword, searchKeyword);
					if (inquirycnt == 0)
						listt = boardService.InquiryNoAnswerTitleCnt(inquirycnt, searchKeyword);
				}
				// 검색 카테고리가 이름일 때
				if (keyword.equals("inquiry_id")) {
					list = boardService.inquiryStatusAndNameSelect(searchKeyword, answerkeyword);
					if (inquirycnt == 0)
						listt = boardService.InquiryNoAnswerIdCnt(inquirycnt, searchKeyword);
				}
			}
			// 검색 카테고리와 날짜를 같이 입력했을 때////////////////
			else if (keyword != null && (searchKeyword == null || searchKeyword.trim().length() == 0)
					&& selectStartDate != null && selectEndDate != null) {
				start = selectStartDate.atTime(0, 0, 0);
				end = selectEndDate.atTime(23, 59, 59);
				list = boardService.InquiryStatusAndCreatedAtSelect(answerkeyword, start, end);
				if (inquirycnt == 0)
					listt = boardService.InquiryNoAnswerTitleCreatedAtCnt(inquirycnt, searchKeyword, start, end);
			} else if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() != 0)
					&& selectStartDate != null && selectEndDate != null) {
				start = selectStartDate.atTime(0, 0, 0);
				end = selectEndDate.atTime(23, 59, 59);
				if (keyword.equals("inquiry_title")) {
					list = boardService.InquiryStatusAndTitleAndCreatedAtSelect(inquirycnt,searchKeyword, start, end);
					if (inquirycnt == 0)
						listt = boardService.InquiryNoAnswerTitleCreatedAtCnt(inquirycnt, searchKeyword, start, end);
				}
				if (keyword.equals("inquiry_id")) {
					list = boardService.InquiryStatusAndNameAndCreatedAtSelect(inquirycnt, searchKeyword, start, end);
					if (inquirycnt == 0)
						listt = boardService.InquiryNoAnswerIdCreatedCnt(inquirycnt, searchKeyword, start, end);
				}
			}
		}

		// 답변완료일 때
		if (answerkeyword == 1) {
			// 검색 카테고리와 검색 키워드만 입력했을 때
			if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() == 0)
					&& selectStartDate == null && selectEndDate == null) {

				// 검색 카테고리가 제목일 때
				if (keyword.equals("inquiry_title")) {
					list = boardService.inquiryStatusAndTitleSelect(answerkeyword, searchKeyword);
				}
				// 검색 카테고리가 이름일 때
				if (keyword.equals("inquiry_id"))
					list = boardService.inquiryStatusAndNameSelect(searchKeyword, answerkeyword);
			}
			// 검색 카테고리와 날짜를 같이 입력했을 때////////////////
			else if (keyword != null && (searchKeyword == null || searchKeyword.trim().length() == 0)
					&& selectStartDate != null && selectEndDate != null) {
				start = selectStartDate.atTime(0, 0, 0);
				end = selectEndDate.atTime(23, 59, 59);
				list = boardService.InquiryStatusAndCreatedAtSelect(answerkeyword, start, end);
			} else if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() != 0)
					&& selectStartDate != null && selectEndDate != null) {
				start = selectStartDate.atTime(0, 0, 0);
				end = selectEndDate.atTime(23, 59, 59);
				if (keyword.equals("inquiry_title"))
					list = boardService.InquiryStatusAndTitleAndCreatedAtSelect(answerkeyword, searchKeyword, start,
							end);
				if (keyword.equals("inquiry_id"))
					list = boardService.InquiryStatusAndNameAndCreatedAtSelect(answerkeyword, searchKeyword, start,
							end);
			}
		}

		// 답변 여부 전체일 때
		if (answerkeyword == 2) {
			// 검색 카테고리와 검색 키워드만 입력했을 때
			if (keyword != null && (searchKeyword == null || searchKeyword.trim().length() == 0)
					&& selectStartDate == null && selectEndDate == null) {
				list = boardService.inquiry_list();
				if (inquirycnt == 0)
					listt = boardService.InquiryNoAnswerCnt(inquirycnt);
			}
			// 검색 카테고리와 검색 키워드만 입력했을 때
			else if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() == 0)
					&& selectStartDate == null && selectEndDate == null) {
				// 검색 카테고리가 제목일 때
				if (keyword.equals("inquiry_title")) {
					list = boardService.inquiryTitleSelect(searchKeyword);
					if (inquirycnt == 0)
						listt = boardService.InquiryNoAnswerTitleCnt(inquirycnt, searchKeyword);
				}
				// 검색 카테고리가 이름일 때
				if (keyword.equals("inquiry_id")) {
					list = boardService.inquiryNameSelect(searchKeyword);
					if (inquirycnt == 0)
						listt = boardService.InquiryNoAnswerIdCnt(inquirycnt, searchKeyword);
				}
			}
			// 검색 카테고리와 날짜를 같이 입력했을 때////////////////
			else if (keyword != null && (searchKeyword == null || searchKeyword.trim().length() == 0)
					&& selectStartDate != null && selectEndDate != null) {
				start = selectStartDate.atTime(0, 0, 0);
				end = selectEndDate.atTime(23, 59, 59);
				list = boardService.inquiryCreatedAtSelect(start, end);
				if (inquirycnt == 0)
					listt = boardService.InquiryNoAnswerTitleCreatedAtCnt(inquirycnt, searchKeyword, start, end);
			} else if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() != 0)
					&& selectStartDate != null && selectEndDate != null) {
				start = selectStartDate.atTime(0, 0, 0);
				end = selectEndDate.atTime(23, 59, 59);
				if (keyword.equals("inquiry_title")) {
					list = boardService.inquiryTitleAndCreatedAtSelect(searchKeyword, start, end);
					if (inquirycnt == 0)
						listt = boardService.InquiryNoAnswerTitleCreatedAtCnt(inquirycnt, searchKeyword, start, end);
				}
				if (keyword.equals("inquiry_id")) {
					list = boardService.inquiryNameAndCreatedAtSelect(searchKeyword, start, end);
					if (inquirycnt == 0)
						listt = boardService.InquiryNoAnswerIdCreatedCnt(inquirycnt, searchKeyword, start, end);
				}
			}
		}

		model.addAttribute("search", inquirysearchDTO);
		model.addAttribute("inquiry_list", list);
		model.addAttribute("listt", listt);
		System.out.println(list);
		System.out.println(listt + "?????????");
		return "layout/admin/board/inquiry_list";
	}

	// notice

//	@RequestMapping("/notice_list")
//	public void notice_list(Model model) {
//		model.addAttribute("notice_list", boardService.notice_list());
//	}

	@RequestMapping("/notice_write")
	public void notice_write() {
		;
	}

	@PostMapping("/notice_writeOk")
	public void notice_write(Member member, Notice dto, BindingResult result, HttpSession session, Model model) {
		member = (Member) session.getAttribute("member");
		dto.setId(member);
		int cnt = boardService.notice_write(dto);
		model.addAttribute("result", cnt);
		model.addAttribute("dto", dto);
	}

	@GetMapping("/notice_update")
	public void update(int noticeNo, Model model) {
		model.addAttribute("list", boardService.notice_selectByUid(noticeNo));
	}

	@PostMapping("/notice_updateOk")
	public void notice_updateOk(@ModelAttribute("dto") Notice dto, Model model) {
		model.addAttribute("result", boardService.notice_update(dto));
	}

	@GetMapping("/notice_view")
	public void notice_view(int noticeNo, Model model) {
		model.addAttribute("list", boardService.notice_viewByUid(noticeNo));
	}

	// 공지사항 삭제(view에서)
//	@GetMapping("/notice_deleteOk")
//	public void notice_deleteOk(int noticeNo, Model model) {
//		model.addAttribute("result", boardService.notice_deleteByUid(noticeNo));
//	}

	// 공지사항 일괄삭제(list에서)
	@ResponseBody
	@PostMapping(value = "/notice_deleteOk", produces = "application/json")
	public int noticeSelectDelete(@RequestParam("noticeNoArr") List<Long> arrStringNoticeNo) throws Exception {
		int result = 0;
		result = boardService.noticeDeleteInBatch(arrStringNoticeNo);
		return result;
	}

	// 공지사항 list (검색)
	@GetMapping("/notice_list")
	public String noticeList(@Valid NoticeSearchDTO noticesearchDTO, BindingResult result, Model model,
			RedirectAttributes redirectAttrs) {
		// 상품 검색 유효성 바인딩
		noticesearchValidator.validate(noticesearchDTO, result);

		if (result.hasErrors()) {
			System.out.println(noticesearchDTO);
			if (result.getFieldError("selectStartDate") != null)
				redirectAttrs.addFlashAttribute("errSelectStartDate",
						result.getFieldError("selectStartDate").getCode());
			if (result.getFieldError("selectEndDate") != null)
				redirectAttrs.addFlashAttribute("errSelectEndDate", result.getFieldError("selectEndDate").getCode());
			redirectAttrs.addFlashAttribute("search", noticesearchDTO);
			return "redirect:/layout/admin/board/notice_list";
		}

		String keyword = noticesearchDTO.getKeyword();
		String searchKeyword = noticesearchDTO.getSearchKeyword();
		LocalDate selectStartDate = noticesearchDTO.getSelectStartDate();
		LocalDate selectEndDate = noticesearchDTO.getSelectEndDate();
		LocalDateTime start;
		LocalDateTime end;

		List<Notice> list = new ArrayList<Notice>();
		Notice notice;

		// 검색 버튼만 눌렀을 때
		System.out.println(2);
		if (keyword != null && (searchKeyword == null || searchKeyword.trim().length() == 0) && selectStartDate == null
				&& selectEndDate == null) {
			list = boardService.notice_list();
			System.out.println(list + ";;");
		}
		// 검색 카테고리와 검색 키워드만 입력했을 때
		else if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() == 0)
				&& selectStartDate == null && selectEndDate == null) {
			// 검색 카테고리가 제목일 때
			if (keyword.equals("notice_title"))
				list = boardService.noticeTitleSelect(searchKeyword);
			// 검색 카테고리가 이름일 때
			if (keyword.equals("notice_id"))
				list = boardService.noticeNameSelect(searchKeyword);
		}
		// 검색 카테고리와 날짜를 같이 입력했을 때////////////////
		else if (keyword != null && (searchKeyword == null || searchKeyword.trim().length() == 0)
				&& selectStartDate != null && selectEndDate != null) {
			start = selectStartDate.atTime(0, 0, 0);
			end = selectEndDate.atTime(23, 59, 59);
			list = boardService.noticeCreatedAtSelect(start, end);
		} else if (keyword != null && (searchKeyword != null || searchKeyword.trim().length() != 0)
				&& selectStartDate != null && selectEndDate != null) {
			start = selectStartDate.atTime(0, 0, 0);
			end = selectEndDate.atTime(23, 59, 59);
			if (keyword.equals("notice_title"))
				list = boardService.noticeTitleAndCreatedAtSelect(searchKeyword, start, end);
			if (keyword.equals("notice_id"))
				list = boardService.noticeNameAndCreatedAtSelect(searchKeyword, start, end);
		}

		model.addAttribute("search", noticesearchDTO);
		model.addAttribute("list", list);
		System.out.println(list);
		return "layout/admin/board/notice_list";
	}

}
