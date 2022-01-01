package com.lec.foodmarket.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lec.foodmarket.domain.Inquiry;
import com.lec.foodmarket.domain.Member;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

	// 1:1문의 제목 검색
	List<Inquiry> findByTitleContainsIgnoreCase(String title);
	
	// 1:1문의 미답변 검색
//	@Query(value = "SELECT * FROM inqury WHERE status = ?1", nativeQuery = true)
//	List<Inquiry> findByStatus(String status);
	
	// 1:1문의 이름 검색
	List<Inquiry> findById(Member member); 
	
	// 작성자와 제목 선택 안하고 등록날짜로 검색
	List<Inquiry> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
	
	// 1:1문의 제목과 등록날짜로 검색
	List<Inquiry> findByTitleContainsIgnoreCaseAndCreatedAtBetween(String title, LocalDateTime from, LocalDateTime to);

	// 1:1문의 이름과 등록날짜로 검색
	List<Inquiry> findByIdAndCreatedAtBetween(Member member, LocalDateTime from, LocalDateTime to);
	
	
}
