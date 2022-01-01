package com.lec.foodmarket.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lec.foodmarket.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	boolean existsById(String id);
	
	boolean existsByEmail(String email);
	
	Optional<Member> findById(String username);
	
	@Query(value ="SELECT role FROM member WHERE id = ?1", nativeQuery = true)
	List<String> selectRoleById(String id);

	@Modifying
	@Query(value ="UPDATE member SET updatedAt =?1 WHERE id = ?2", nativeQuery = true)
	Member updatedAtById(LocalDateTime updatedAt, String id);

	@Query(value ="SELECT id FROM member WHERE name = ?1 AND email = ?2", nativeQuery = true)
	String findIdByNameAndEmail(String find_id_name, String find_id_email);
	
	@Query(value ="SELECT pw FROM member WHERE id = ?1 AND name = ?2 AND email = ?3", nativeQuery = true)
	String findPwByIdAndNameAndEmail(String find_pw_id, String find_pw_name, String find_pw_email);
	
	@Modifying
	@Query(value ="UPDATE member SET pw= ?1 WHERE id = ?2", nativeQuery = true)
	void updatePwById(String find_pw_new, String find_id);

	// 키워드로만 검색
	List<Member> findByIdContainsIgnoreCase(String id);
	List<Member> findByNameContainsIgnoreCase(String name);
	List<Member> findByPhoneNoContainsIgnoreCase(String PhoneNo);
	List<Member> findByEmailContainsIgnoreCase(String email);

	// 날짜로만 검색
	List<Member> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

	// 날짜와 키워드 검색
	List<Member> findByIdContainsIgnoreCaseAndCreatedAtBetween(String id, LocalDateTime from, LocalDateTime to);
	List<Member> findByNameContainsIgnoreCaseAndCreatedAtBetween(String name, LocalDateTime from, LocalDateTime to);
	List<Member> findByPhoneNoContainsIgnoreCaseAndCreatedAtBetween(String phoneNo, LocalDateTime from, LocalDateTime to);
	List<Member> findByEmailContainsIgnoreCaseAndCreatedAtBetween(String email, LocalDateTime from, LocalDateTime to);

	
}
