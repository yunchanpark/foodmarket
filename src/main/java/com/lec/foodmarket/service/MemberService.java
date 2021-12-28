package com.lec.foodmarket.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.repository.MemberRepository;
import com.lec.foodmarket.repository.PointConditionRepository;
import com.lec.foodmarket.repository.PointRepository;

import lombok.NonNull;

@Service
public class MemberService {
	
	private MemberRepository memberRepository; // 회원
	private PointRepository pointRepository; // 포인트
	private PointConditionRepository pointConditionRepository; // 포인트관련 설정
	
	@Autowired
	public MemberService(MemberRepository memberRepository, PointRepository pointRepository,
			PointConditionRepository conditionRepository) {
		this.memberRepository = memberRepository;
		this.pointRepository = pointRepository;
		this.pointConditionRepository = conditionRepository;
	}
	
	public MemberService() {;}
	
	/******************************************
	 * 관리자
	 *   일반회원(조회, 수정, 삭제, 메시지보내기)
	 *   운영자(등록, 조회, 수정, 삭제, 권한 등록 및 수정 및 삭제)
	 *   
	 * 사용자
	 *   회원가입, 로그인
	 *   회원가입시 포인트 지급
	 *   이메일 인증, 휴대폰 인증
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
	public int addMember(Member member) {
		member.setCreatedAt(LocalDateTime.now());
		memberRepository.saveAndFlush(member);
		return 1;
	}
	
	// 아아디 중복체크
	public boolean checkIdDuplicate(String id) {
		return memberRepository.existsById(id);
	}

	// 이메일 중복체크
	public boolean checkEmailDuplicate(String email) {
		return memberRepository.existsByEmail(email);
	}

	public Member findById(String username) {
		return memberRepository.findById(username);
	}

	public void memberSave(Member member) {
		memberRepository.save(member);
	}

	public List<String> selectRoleById(String id) {
		return memberRepository.selectRoleById(id);
	}

	public Member updateTime(LocalDateTime updatedAt, String id) {
		return memberRepository.updatedAtById(updatedAt, id);
	}

	public String findIdByNameAndEmail(String find_id_name, String find_id_email) {
		return memberRepository.findIdByNameAndEmail(find_id_name, find_id_email);
	}

	public String findPwByIdAndNameAndEmail(String find_pw_id, String find_pw_name, String find_pw_email) {
		return memberRepository.findPwByIdAndNameAndEmail(find_pw_id, find_pw_name, find_pw_email);
	}
	
}

























