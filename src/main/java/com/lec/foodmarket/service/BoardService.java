package com.lec.foodmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lec.foodmarket.repository.InquiryImageRepository;
import com.lec.foodmarket.repository.InquiryRepository;
import com.lec.foodmarket.repository.NoticeRepository;

@Service
public class BoardService {

	private InquiryRepository inquiryRepository; // 1대1문의
	private InquiryImageRepository inquiryImageRepository; // 1대1문의 이미지
	private NoticeRepository noticeRepository; // 공지사항

	@Autowired
	public BoardService(InquiryRepository inquiryRepository, InquiryImageRepository inquiryImageRepository,
			NoticeRepository noticeRepository) {
		super();
		this.inquiryRepository = inquiryRepository;
		this.inquiryImageRepository = inquiryImageRepository;
		this.noticeRepository = noticeRepository;
	}

	public BoardService() {;}
	
	/******************************************
	 * 관리자 
	 *   1대1문의 (조회, 삭제, 답변, 수정???)
	 *   공지사항 (조회, 삭제, 수정, 등록)
	 * 
	 * 사용자
	 *   1대1문의 등록(이미지 포함), 조회(자신 글만)
	 *   공지사항 조회
	 *   
	 * 추가사항이나 수정사항 알아서
	 ******************************************/

	
	
	/******************************************
	 * 관리자
	 ******************************************/
	// TODO
	
	
	
	
	
	
	
	
	
	
	/******************************************
	 * 사용자
	 ******************************************/
	// TODO
	
	
	
	
	
	
	
}






























