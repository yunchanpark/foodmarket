package com.lec.foodmarket.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.Point;

public interface PointRepository extends JpaRepository<Point, Long> {

	List<Point> findByUid(Member member);
	List<Point> findByNameContainsIgnoreCase(String reason);
	
	List<Point> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
	List<Point> findByUidAndCreatedAtBetween(Member member, LocalDateTime from, LocalDateTime to);
	List<Point> findByNameContainsIgnoreCaseAndCreatedAtBetween(String reanson, LocalDateTime from, LocalDateTime to);
	List<Point> findByStatus(int division);
	
	List<Point> findByStatusAndUid(int status, Member member);
	List<Point> findByStatusAndNameContainsIgnoreCase(int status, String reason);
	
	List<Point> findByStatusAndCreatedAtBetween(int status, LocalDateTime from, LocalDateTime to);
	List<Point> findByStatusAndUidAndCreatedAtBetween(int status, Member member, LocalDateTime from, LocalDateTime to);
	List<Point> findByStatusAndNameContainsIgnoreCaseAndCreatedAtBetween(int status, String reason, LocalDateTime from,	LocalDateTime to);
	
	void deleteByUid(Member member);



}
