package com.lec.foodmarket.repository;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.Inquiry;
import com.lec.foodmarket.domain.Member;


public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

	
	// 1:1문의 제목 검색
	List<Inquiry> findByTitleContainsIgnoreCase(String title);
	
	// 1:1문의 이름 검색
	List<Inquiry> findById(Member member);
	
	// 작성자와 제목 선택 안하고 등록날짜로 검색
	List<Inquiry> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
	
	// 1:1문의 제목과 등록날짜로 검색
	List<Inquiry> findByTitleContainsIgnoreCaseAndCreatedAtBetween(String title, LocalDateTime from, LocalDateTime to);
	
	// 1:1문의 이름과 등록날짜로 검색
	List<Inquiry> findByIdAndCreatedAtBetween(Member member, LocalDateTime from, LocalDateTime to);
	
	
	// 1:1문의 답변상태와 제목 검색
	List<Inquiry> findByStatusAndTitleContainsIgnoreCase(int status, String title);
	
	// 1:1문의 답변상태와 이름 검색
	List<Inquiry> findByIdAndStatus(Member member, int status); 
	
	// 작성자와 제목 선택 안하고 답변상태와 등록날짜로 검색
	List<Inquiry> findByStatusAndCreatedAtBetween(int status, LocalDateTime from, LocalDateTime to);
	
	// 1:1문의 답변상태와 제목과 등록날짜로 검색
	List<Inquiry> findByStatusAndTitleContainsIgnoreCaseAndCreatedAtBetween(int status, String title, LocalDateTime from, LocalDateTime to);

	// 1:1문의 답변상태와 이름과 등록날짜로 검색
	List<Inquiry> findByStatusAndIdAndCreatedAtBetween(int Status,Member member, LocalDateTime from, LocalDateTime to);
	
	
	// 미답변 개수
	int countByStatus(int status);
	
	// 제목과 미답변 개수
	int countByStatusAndTitleContainsIgnoreCase(int status,String title);
	
	// 제목 날짜 미답변 개수
	int countByStatusAndTitleContainsIgnoreCaseAndCreatedAtBetween(int status,String title, LocalDateTime from, LocalDateTime to);
	
	// 이름과 미답변 개수
	int countByStatusAndId(int status, Member member);
	
	// 이름 날짜 미답변 개수
	int countByIdAndStatusAndCreatedAtBetween(Member member, int status, LocalDateTime from, LocalDateTime to);
}
