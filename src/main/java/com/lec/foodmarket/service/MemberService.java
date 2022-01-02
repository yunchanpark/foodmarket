package com.lec.foodmarket.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.Point;
import com.lec.foodmarket.domain.PointCondition;
import com.lec.foodmarket.domain.dto.MemberDTO;
import com.lec.foodmarket.repository.MemberRepository;
import com.lec.foodmarket.repository.PointConditionRepository;
import com.lec.foodmarket.repository.PointRepository;

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

	public MemberService() {
		;
	}

	/******************************************
	 * 관리자 일반회원(조회, 수정, 삭제, 메시지보내기) 운영자(등록, 조회, 수정, 삭제, 권한 등록 및 수정 및 삭제)
	 * 
	 * 사용자 회원가입, 로그인 회원가입시 포인트 지급 이메일 인증, 휴대폰 인증
	 * 
	 * 추가사항이나 수정사항 알아서
	 ******************************************/
	/******************************************
	 * 사용자
	 ******************************************/
	public int addMember(Member member) {
		member.setCreatedAt(LocalDateTime.now());
		memberRepository.saveAndFlush(member);
		return 1;
	}

	public Member updateTime(LocalDateTime updatedAt, String id) {
		return memberRepository.updatedAtById(updatedAt, id);
	}

	// 아아디 중복체크
	public boolean checkIdDuplicate(String id) {
		return memberRepository.existsById(id);
	}

	// 이메일 중복체크
	public boolean checkEmailDuplicate(String email) {
		return memberRepository.existsByEmail(email);
	}

	// 회원 조회
	public Member findById(String username) {
		return memberRepository.findById(username).orElse(null);
	}

	// 회원 가입
	public void memberSave(Member member) {
		memberRepository.save(member);
	}

	// 권한 조회
	public List<String> selectRoleById(String id) {
		return memberRepository.selectRoleById(id);
	}

	// 아이디 찾기
	public String findIdByNameAndEmail(String find_id_name, String find_id_email) {
		return memberRepository.findIdByNameAndEmail(find_id_name, find_id_email);
	}

	// 비밀번호 찾기
	public String findPwByIdAndNameAndEmail(String find_pw_id, String find_pw_name, String find_pw_email) {
		return memberRepository.findPwByIdAndNameAndEmail(find_pw_id, find_pw_name, find_pw_email);
	}

	// 비밀번호 변경
	@Transactional
	public void updatePwById(String find_pw_new, String find_id) {
		memberRepository.updatePwById(find_pw_new, find_id);
	}

	// 회원정보 수정
	public void memberUpdate(Member member, MemberDTO memberDTO) {
		member.setPw(memberDTO.getUpdate_pw());
		member.setName(memberDTO.getUpdate_name());
		member.setPhoneNo(memberDTO.getUpdate_phoneNo());
		member.setEmail(memberDTO.getUpdate_email());
		member.setAddr(memberDTO.getUpdate_addr());
		member.setDetailAddr(memberDTO.getUpdate_addr_detail());
		memberRepository.saveAndFlush(member);
	}
	
	// 포인트 누적
	public void memberSaveUpdate(Member member, int price) {
		member.setSaveUpPoint(price);
		memberRepository.saveAndFlush(member);
	}
	
	@Transactional
	public void pointDeleteByMember(Member member) {
		pointRepository.deleteByUid(member);
	}

	// 회원 탈퇴
	public void memberDeleteByid(long uid) {
		memberRepository.deleteById(uid);
	}

	/******************************************
	 * 관리자
	 ******************************************/
	// 모든 회원 검색
	public List<Member> memberAllSelect() {
		return memberRepository.findAll();
	}

	// 키워드 아이디 검색
	public List<Member> memberIdSelect(String id) {
		return memberRepository.findByIdContainsIgnoreCase(id);
	}

	// 키워드 이름 검색
	public List<Member> memberNameSelect(String name) {
		return memberRepository.findByNameContainsIgnoreCase(name);
	}

	// 키워드 휴대폰번호 검색
	public List<Member> memberPhoneNoSelect(String PhoneNo) {
		return memberRepository.findByPhoneNoContainsIgnoreCase(PhoneNo);
	}

	// 키워드 이메일 검색
	public List<Member> memberEmailSelect(String Email) {
		return memberRepository.findByEmailContainsIgnoreCase(Email);
	}

	// 가입일로 회원 검색
	public List<Member> memberCreateAtSelect(LocalDateTime from, LocalDateTime to) {
		return memberRepository.findByCreatedAtBetween(from, to);
	}

	// 가입일과 아이디로 회원 검색
	public List<Member> memberCreateAtAndMemberId(String id, LocalDateTime from, LocalDateTime to) {
		return memberRepository.findByIdContainsIgnoreCaseAndCreatedAtBetween(id, from, to);
	}

	// 가입일과 이름로 회원 검색
	public List<Member> memberCreateAtAndMemberName(String name, LocalDateTime from, LocalDateTime to) {
		return memberRepository.findByNameContainsIgnoreCaseAndCreatedAtBetween(name, from, to);
	}

	// 가입일과 휴대폰번호로 회원 검색
	public List<Member> memberCreateAtAndMemberPhoneNo(String phoneNo, LocalDateTime from, LocalDateTime to) {
		return memberRepository.findByPhoneNoContainsIgnoreCaseAndCreatedAtBetween(phoneNo, from, to);
	}

	// 가입일과 이메일로 회원 검색
	public List<Member> memberCreateAtAndMemberEmail(String email, LocalDateTime from, LocalDateTime to) {
		return memberRepository.findByEmailContainsIgnoreCaseAndCreatedAtBetween(email, from, to);
	}

	// 회원 상세보기
	public Optional<Member> findById(long uid) {
		return memberRepository.findById(uid);
	}

	// 적립금 설정
	public List<PointCondition> pointConditionAllSelect() {
		return pointConditionRepository.findAll();
	}

	// 적립금 변경
	public void updatepointCondition(PointCondition pointCondition) {
		pointConditionRepository.saveAndFlush(pointCondition);
	}

	// 회원가입시 포인트 적립 내용 저장
	public void pointSave(Point point) {
		pointRepository.save(point);
	}

	// 포인트 적립 전체 검색
	public List<Point> pointAllSelect() {
		return pointRepository.findAll();
	}
	
	// 키워드가 아이디 검색
	public List<Point> pointIdSelect(String id) {
		List<Member> member = memberRepository.findByIdContainsIgnoreCase(id);
		
		List<Point> list = new ArrayList<>();
		for (int i = 0; i < member.size(); i++) {
			list.addAll(pointRepository.findByUid(member.get(i)));
		}
		return list;
	}

	// 키워드 이름 검색
	public List<Point> pointNameSelect(String name) {
		List<Member> member = memberRepository.findByNameContainsIgnoreCase(name);
		
		List<Point> list = new ArrayList<>();
		for (int i = 0; i < member.size(); i++) {
			list.addAll(pointRepository.findByUid(member.get(i)));
		}
		return list;
	}

	// 키워드 사유 검색
	public List<Point> pointReasonSelect(String reason) {
		return pointRepository.findByNameContainsIgnoreCase(reason);
	}

	// 기간으로만 검색
	public List<Point> pointCreateAtSelect(LocalDateTime from, LocalDateTime to) {
		return pointRepository.findByCreatedAtBetween(from, to);
	}

	// 키워드 아이디와 날짜 검색
	public List<Point> pointCreateAtAndMemberId(String id, LocalDateTime from, LocalDateTime to) {
		List<Member> member = memberRepository.findByIdContainsIgnoreCase(id);
		List<Point> list = new ArrayList<>();
		for (int i = 0; i < member.size(); i++) {
			list.addAll(pointRepository.findByUidAndCreatedAtBetween(member.get(i), from, to));
		}
		return list;
	}

	// 키워드 이름과 날짜 검색
	public List<Point> pointCreateAtAndMemberName(String name, LocalDateTime from, LocalDateTime to) {
		List<Member> member = memberRepository.findByNameContainsIgnoreCase(name);
		List<Point> list = new ArrayList<>();
		for (int i = 0; i < member.size(); i++) {
			list.addAll(pointRepository.findByUidAndCreatedAtBetween(member.get(i), from, to));
		}
		return list;
	}

	// 키워드 사유와 날짜 검색
	public List<Point> pointCreateAtAndReason(String reanson, LocalDateTime from, LocalDateTime to) {
		return pointRepository.findByNameContainsIgnoreCaseAndCreatedAtBetween(reanson, from, to);
	}

	// 지급내역 or 사용내역 전체 조회
	public List<Point> pointStatusSelect(int division) {
		return pointRepository.findByStatus(division);
	}
	
	// 지급내역 or 사용내역과 아이디로 검색
	public List<Point> pointStatusAndIdSelect(int status, String id) {
		List<Member> member = memberRepository.findByIdContainsIgnoreCase(id);
		List<Point> list = new ArrayList<>();
		for (int i = 0; i < member.size(); i++) {
			list.addAll(pointRepository.findByStatusAndUid(status, member.get(i)));
		}
		return list;
	}

	// 지급내역 or 사용내역과 이름으로 검색
	public List<Point> pointStatusAndNameSelect(int status, String name) {
		List<Member> member = memberRepository.findByNameContainsIgnoreCase(name);
		System.out.println(member);
		List<Point> list = new ArrayList<>();
		for (int i = 0; i < member.size(); i++) {
			list.addAll(pointRepository.findByStatusAndUid(status, member.get(i)));
		}
		return list;
	}

	// 지급내역 or 사용내역과 사유로 검색
	public List<Point> pointStatusAndReasonSelect(int status, String reason) {
		return pointRepository.findByStatusAndNameContainsIgnoreCase(status, reason);
	}

	// 지급내역 or 사용내역 날짜만 검색
	public List<Point> pointStatusAndCreateAtSelect(int status, LocalDateTime from, LocalDateTime to) {
		return pointRepository.findByStatusAndCreatedAtBetween(status, from, to);
	}
	
	// 지급내역 or 사용내역과 아이디와 날짜로 검색
	public List<Point> pointStatusAndCreateAtAndMemberId(int status, String id, LocalDateTime from, LocalDateTime to) {
		List<Member> member = memberRepository.findByIdContainsIgnoreCase(id);
		List<Point> list = new ArrayList<>();
		for (int i = 0; i < member.size(); i++) {
			list.addAll(pointRepository.findByStatusAndUidAndCreatedAtBetween(status, member.get(i), from, to));
		}
		return list;
	}

	// 지급내역 or 사용내역과 이름과 날짜로 검색
	public List<Point> pointStatusAndCreateAtAndMemberName(int status, String name, LocalDateTime from, LocalDateTime to) {
		List<Member> member = memberRepository.findByIdContainsIgnoreCase(name);
		List<Point> list = new ArrayList<>();
		for (int i = 0; i < member.size(); i++) {
			list.addAll(pointRepository.findByStatusAndUidAndCreatedAtBetween(status, member.get(i), from, to));
		}
		return list;
	}

	// 지급내역 or 사용내역과 사유와 날짜로 검색
	public List<Point> pointStatusAndCreateAtAndReason(int status, String reason, LocalDateTime from, LocalDateTime to) {
		return pointRepository.findByStatusAndNameContainsIgnoreCaseAndCreatedAtBetween(status, reason, from, to);
	}

	

}
