package com.lec.foodmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lec.foodmarket.repository.MemberRepository;
import com.lec.foodmarket.repository.PointConditionRepository;
import com.lec.foodmarket.repository.PointRepository;

@Service
public class TotalService {
	
	private MemberRepository memberRepository; // 회원
	private PointRepository pointRepository; // 포인트
	private PointConditionRepository pointConditionRepository; // 포인트관련 설정
	
	@Autowired
	public TotalService(MemberRepository memberRepository, PointRepository pointRepository,
			PointConditionRepository conditionRepository) {
		this.memberRepository = memberRepository;
		this.pointRepository = pointRepository;
		this.pointConditionRepository = conditionRepository;
	}
	
	public TotalService() {;}

	public static Object total() {
		return null;
	}
	
	
	
	
	
	
	
	
}

























