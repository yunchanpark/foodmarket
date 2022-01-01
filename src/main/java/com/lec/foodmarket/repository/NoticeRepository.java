package com.lec.foodmarket.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

	// 공지사항 제목 검색
	List<Notice> findByTitleContainsIgnoreCase(String title);
	
	// 공지사항 이름 검색
	List<Notice> findById(Member member); 
	
	// 작성자와 제목 선택 안하고 등록날짜로 검색
	List<Notice> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
	
	// 공지사항 제목과 등록날짜로 검색
	List<Notice> findByTitleContainsIgnoreCaseAndCreatedAtBetween(String title, LocalDateTime from, LocalDateTime to);

	// 공지사항 이름과 등록날짜로 검색
	List<Notice> findByIdAndCreatedAtBetween(Member member, LocalDateTime from, LocalDateTime to);
}
