package com.lec.foodmarket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lec.foodmarket.domain.Inquiry;
import com.lec.foodmarket.domain.Notice;
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

	public BoardService() {
		;
	}

	/******************************************
	 * 관리자 1대1문의 (조회, 삭제, 답변, 수정???) 공지사항 (조회, 삭제, 수정, 등록)
	 * 
	 * 사용자 1대1문의 등록(이미지 포함), 조회(자신 글만) 공지사항 조회
	 * 
	 * 추가사항이나 수정사항 알아서
	 ******************************************/

	/******************************************
	 * 관리자 notice
	 ******************************************/
	public List<Notice> notice_list() {
		return noticeRepository.findAll(Sort.by(Direction.DESC, "noticeNo"));
	}

	public int notice_write(Notice dto) {
		noticeRepository.saveAndFlush(dto);
		return 1;
	}

	// 특정 uid 의 글 읽어오기 + 조회수 증가
	@Transactional // 해당 메소드는 하나의 트랜잭션으로 처리함.
	public List<Notice> notice_viewByUid(long noticeNo) {

		List<Notice> list = new ArrayList<>();

		Notice data = noticeRepository.findById(noticeNo).orElse(null); // SELECT
		if (data != null) {
			data.setViewCnt(data.getViewCnt() + 1);
			noticeRepository.saveAndFlush(data); // UPDATE
			list.add(data);
		}
		; // 조회수 증가
		return list; // 읽어오기
	}

	// 특정 uid 의 글 읽어오기 (update 에서 필요)
	public List<Notice> notice_selectByUid(long noticeNo) {
		List<Notice> list = new ArrayList<>();
		list.add(noticeRepository.findById(noticeNo).orElse(null));
		return list;
	}

	// 글 수정
	public int notice_update(Notice dto) {
		int cnt = 0;
		Notice data = noticeRepository.findById(dto.getNoticeNo()).orElse(null);
		if (data != null) {
			data.setContent(dto.getContent());
			data.setTitle(dto.getTitle());
			noticeRepository.saveAndFlush(data); // UPDATE
			cnt = 1;
		}
		return cnt;
	}

	// 글 삭제
	public int notice_deleteByUid(long uid) {
		noticeRepository.deleteById(uid);
		return 1;
	}
//	public Object inquiry_list() {
//		return null;
//	}
//
//	public Object notice_list() {
//		return null;
//	}

	/******************************************
	 * 사용자 notice
	 ******************************************/
	public List<Notice> user_notice_list() {
		return noticeRepository.findAll(Sort.by(Direction.DESC, "noticeNo"));
	}
	
	
	
	
	
	/******************************************
	 * 관리자 inquiry
	 ******************************************/

	public List<Inquiry> inquiry_list() {
		return inquiryRepository.findAll(Sort.by(Direction.DESC, "inquiryNo"));
	}

	
	
	
	
	/******************************************
	 * 사용자 inquiry
	 ******************************************/

	
	public List<Inquiry> user_inquiry_list() {
		return inquiryRepository.findAll(Sort.by(Direction.DESC, "inquiryNo"));
	}

	public int inquiry_write(Inquiry dto) {
		inquiryRepository.saveAndFlush(dto);
		return 1;
	}
	

	// 특정 uid 의 글 읽어오기 + 조회수 증가
	@Transactional   // 해당 메소드는 하나의 트랜잭션으로 처리함.
	public List<Inquiry> inquiry_viewByUid(long inquiryNo){
		
		List<Inquiry> list = new ArrayList<>();
		
		Inquiry data = inquiryRepository.findById(inquiryNo).orElse(null); // SELECT
		if(data != null) {
			inquiryRepository.saveAndFlush(data);  // UPDATE
			list.add(data);
		}
		;  // 조회수 증가
		return list;  // 읽어오기
	}
	
	// 특정 uid 의 글 읽어오기  (update 에서 필요)
	public List<Inquiry> inquiry_selectByUid(long inquiryNo){
		List<Inquiry> list = new ArrayList<>();
		list.add(inquiryRepository.findById(inquiryNo).orElse(null));
		return list;
	}
	
	// 글 삭제
	public int inquiry_deleteByUid(long inquiryNo) {
		inquiryRepository.deleteById(inquiryNo);
		return 1;
	}

}
