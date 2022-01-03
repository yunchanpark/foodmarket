package com.lec.foodmarket.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lec.foodmarket.domain.Inquiry;
import com.lec.foodmarket.domain.InquiryImage;
import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.Notice;
import com.lec.foodmarket.repository.InquiryImageRepository;
import com.lec.foodmarket.repository.InquiryRepository;
import com.lec.foodmarket.repository.MemberRepository;
import com.lec.foodmarket.repository.NoticeRepository;

@Service
public class BoardService {

	private InquiryRepository inquiryRepository; // 1대1문의
	private InquiryImageRepository inquiryImageRepository; // 1대1문의 이미지
	private NoticeRepository noticeRepository; // 공지사항
	private MemberRepository memberRepository;

	@Autowired
	public BoardService(InquiryRepository inquiryRepository, InquiryImageRepository inquiryImageRepository,
			NoticeRepository noticeRepository, MemberRepository memberRepository) {
		super();
		this.inquiryRepository = inquiryRepository;
		this.inquiryImageRepository = inquiryImageRepository;
		this.noticeRepository = noticeRepository;
		this.memberRepository = memberRepository;
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
	// 공지사항 모두 검색
	public List<Notice> notice_list() {
		return noticeRepository.findAll(Sort.by(Direction.DESC, "noticeNo"));
	}

	// 공지사항 제목 검색
	public List<Notice> noticeTitleSelect(String title) {
		return noticeRepository.findByTitleContainsIgnoreCase(title);
	}

	// 공지사항 이름 검색
	public List<Notice> noticeNameSelect(String name) {
		List<Member> member = memberRepository.findByNameContainsIgnoreCase(name);
		List<Notice> list = new ArrayList<Notice>();
		for(int i = 0; i < member.size(); i++) {
		list.addAll(noticeRepository.findById(member.get(i)));
		}
		return list;
	}

	// 작성자와 제목 선택 안하고 등록날짜로 검색
	public List<Notice> noticeCreatedAtSelect(LocalDateTime from, LocalDateTime to) {
		return noticeRepository.findByCreatedAtBetween(from, to);
	}

	// 공지사항 제목과 등록날짜로 검색
	public List<Notice> noticeTitleAndCreatedAtSelect(String title, LocalDateTime from, LocalDateTime to) {
		return noticeRepository.findByTitleContainsIgnoreCaseAndCreatedAtBetween(title, from, to);
	}

	// 공지사항 이름과 등록날짜로 검색
	public List<Notice> noticeNameAndCreatedAtSelect(String name, LocalDateTime from, LocalDateTime to) {
		List<Member> member = memberRepository.findByNameContainsIgnoreCase(name);
		List<Notice> list = new ArrayList<Notice>();
		for(int i = 0; i < member.size(); i++) {
		list.addAll(noticeRepository.findByIdAndCreatedAtBetween(member.get(i), from, to));
		}
		return list;
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
	public int notice_deleteByUid(long noticeNo) {
		noticeRepository.deleteById(noticeNo);
		return 1;
	}

	// 일괄삭제 부분
	public int noticeDeleteInBatch(List<Long> noticeNo) {
		noticeRepository.deleteAllByIdInBatch(noticeNo);
		return 1;
	}

	/******************************************
	 * 사용자 notice
	 ******************************************/
	public List<Notice> user_notice_list() {
		return noticeRepository.findAll(Sort.by(Direction.DESC, "noticeNo"));
	}

	/******************************************
	 * 관리자 inquiry
	 ******************************************/

	// 1:1 문의 모두 검색
	public List<Inquiry> inquiry_list() {
		return inquiryRepository.findAll(Sort.by(Direction.DESC, "inquiryNo"));
	}

	// 1:1 문의 제목 검색
	public List<Inquiry> inquiryTitleSelect(String title) {
		return inquiryRepository.findByTitleContainsIgnoreCase(title);
	}

	// 1:1 문의 이름 검색
	public List<Inquiry> inquiryNameSelect(String name) {
		List<Member> member = memberRepository.findByNameContainsIgnoreCase(name);
		List<Inquiry> list = new ArrayList<>();
		for (int i = 0; i < member.size(); i++) {
			list.addAll(inquiryRepository.findById(member.get(i)));
		}
		return list;
	}

	// 작성자와 제목 선택 안하고 등록날짜로 검색
	public List<Inquiry> inquiryCreatedAtSelect(LocalDateTime from, LocalDateTime to) {
		return inquiryRepository.findByCreatedAtBetween(from, to);
	}

	// 1:1 문의 제목과 등록날짜로 검색
	public List<Inquiry> inquiryTitleAndCreatedAtSelect(String title, LocalDateTime from, LocalDateTime to) {
		return inquiryRepository.findByTitleContainsIgnoreCaseAndCreatedAtBetween(title, from, to);
	}

	// 1:1 문의 이름과 등록날짜로 검색
	public List<Inquiry> inquiryNameAndCreatedAtSelect(String name, LocalDateTime from, LocalDateTime to) {
		List<Member> member = memberRepository.findByNameContainsIgnoreCase(name);
		List<Inquiry> list = new ArrayList<>();
		for (int i = 0; i < member.size(); i++) {
			list.addAll(inquiryRepository.findByIdAndCreatedAtBetween(member.get(i), from, to));
		}
		return list;
	}

	// 1:1 문의 답변상태와 제목 검색
	public List<Inquiry> inquiryStatusAndTitleSelect(int status, String title) {
		return inquiryRepository.findByStatusAndTitleContainsIgnoreCase(status, title);
	}

	// 1:1 문의 답변상태와 이름 검색
	public List<Inquiry> inquiryStatusAndNameSelect(String name, int status) {
		List<Member> member = memberRepository.findByNameContainsIgnoreCase(name);
		List<Inquiry> list = new ArrayList<>();
		for (int i = 0; i < member.size(); i++) {
		list.addAll(inquiryRepository.findByIdAndStatus(member.get(i), status));
		}
		return list;
	}

	// 작성자와 제목 선택 안하고 답변상태와 등록날짜로 검색
	public List<Inquiry> InquiryStatusAndCreatedAtSelect(int status, LocalDateTime from, LocalDateTime to) {
		return inquiryRepository.findByStatusAndCreatedAtBetween(status, from, to);
	}

	// 1:1 문의 답변상태와 제목과 등록날짜로 검색
	public List<Inquiry> InquiryStatusAndTitleAndCreatedAtSelect(int status, String title, LocalDateTime from,
			LocalDateTime to) {
		return inquiryRepository.findByStatusAndTitleContainsIgnoreCaseAndCreatedAtBetween(status, title, from, to);
	}

	// 1:1 문의 답변상태와 이름과 등록날짜로 검색
	public List<Inquiry> InquiryStatusAndNameAndCreatedAtSelect(int status, String name, LocalDateTime from,
			LocalDateTime to) {
		List<Member> member = memberRepository.findByNameContainsIgnoreCase(name);
		List<Inquiry> list = new ArrayList<>();
		for (int i = 0; i < member.size(); i++) {
		list.addAll(inquiryRepository.findByStatusAndIdAndCreatedAtBetween(status, member.get(i), from, to));
		}
		return list;
	}

	// 미답변 cnt
	public int InquiryNoAnswerCnt(int status) {
		return inquiryRepository.countByStatus(status);
	}

	// 제목과 미답변 개수
	public int InquiryNoAnswerTitleCnt(int status, String title) {
		return inquiryRepository.countByStatusAndTitleContainsIgnoreCase(status, title);
	}

	// 이름과 미답변 개수
	public int InquiryNoAnswerIdCnt(int status, String name) {
		List<Member> member = memberRepository.findByNameContainsIgnoreCase(name);
		return inquiryRepository.countByStatusAndId(status, member.get(0));
	}
	
	// 이름 시간 미답변 개수
	public int InquiryNoAnswerIdCreatedCnt(int status, String name , LocalDateTime from, LocalDateTime to) {
		List<Member> member = memberRepository.findByNameContainsIgnoreCase(name);
		return inquiryRepository.countByIdAndStatusAndCreatedAtBetween(member.get(0), status, from, to);

	}
	

	// 제목 시간 미답변 개수
	public int InquiryNoAnswerTitleCreatedAtCnt(int status, String title, LocalDateTime from, LocalDateTime to) {
		return inquiryRepository.countByStatusAndTitleContainsIgnoreCaseAndCreatedAtBetween(status, title, from, to);
	}

	// 글 수정
	public int inquiry_update(Inquiry dto) {
		int cnt = 0;
		Inquiry data = inquiryRepository.findById(dto.getInquiryNo()).orElse(null);
		if (data != null) {
			data.setContent(dto.getContent());
			data.setTitle(dto.getTitle());
			data.setAnswer(dto.getAnswer());
			data.setStatus(1);
			inquiryRepository.saveAndFlush(data); // UPDATE
			cnt = 1;
		}
		return cnt;
	}

	// 미답변 개수

	/******************************************
	 * 사용자 inquiry
	 ******************************************/

	public List<Inquiry> user_inquiry_list() {
		return inquiryRepository.findAll(Sort.by(Direction.DESC, "inquiryNo"));
	}

	public int inquiry_write(Inquiry inquiry) {
		inquiryRepository.saveAndFlush(inquiry);
		return 1;
	}

	// 이미지
	public void inquiryImageSave(InquiryImage inquiryimage) {
		inquiryImageRepository.saveAndFlush(inquiryimage);
	}

	// 이미지 찾기
	public InquiryImage inquiryImagefind(long inquiryno) {
		return inquiryImageRepository.findByInquiryNo(inquiryRepository.findById(inquiryno).get());
	}

	// 특정 uid 의 글 읽어오기 + 조회수 증가
	@Transactional // 해당 메소드는 하나의 트랜잭션으로 처리함.
	public List<Inquiry> inquiry_viewByUid(long inquiryNo) {

		List<Inquiry> list = new ArrayList<>();

		Inquiry data = inquiryRepository.findById(inquiryNo).orElse(null); // SELECT
		if (data != null) {
			inquiryRepository.saveAndFlush(data); // UPDATE
			list.add(data);
		}
		; // 조회수 증가
		return list; // 읽어오기
	}

	// 특정 uid 의 글 읽어오기 (update 에서 필요)
	public List<Inquiry> inquiry_selectByUid(long inquiryNo) {
		List<Inquiry> list = new ArrayList<>();
		list.add(inquiryRepository.findById(inquiryNo).orElse(null));
		return list;
	}

	// 글 삭제
	@Transactional
	public int inquiry_deleteByUid(long inquiryNo) {
		inquiryImageRepository.deleteByInquiryNo(inquiryRepository.findById(inquiryNo).get());
		inquiryRepository.deleteById(inquiryNo);
		return 1;
	}

	// 이미지 삭제
	public int inquiryImageDelete(long inquiry_Image_no, String realPath) {
		// 물리적인 경로
		int result = 0;
		String ckUploadPath = "C:\\spring_foodmarket\\ckupload\\" + realPath;
		InquiryImage inquiryList = inquiryImageRepository.findById(inquiry_Image_no).get();
		File f = new File(ckUploadPath, inquiryList.getInquirySave());
		if (f.exists()) {
			if (f.delete()) {
				result = 1;
			} else {
				result = 0;
			}
		} else {
			result = -1;
		}

		return result;

	}

	public Object user_road() {
		// TODO Auto-generated method stub
		return null;
	}

}
