package com.lec.foodmarket.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="point_condition")
public class PointCondition {
	
	// 기본키 포인트 기본설정 번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="point_condition_no")
	private Long pointConditionNo;
	
	// 상품 결제시 포인트 퍼센트
	@NotNull
	@Column(name="product_point", columnDefinition="integer default 0")
	private int productPoint;
	
	// 추천인 쓴 사람 포인트 지급
	@NotNull
	@Column(name="recommender_give_point", columnDefinition="integer default 0")
	private int recommenderGive;
	
	// 추천인 받는 사람 포인트 지급
	@NotNull
	@Column(name="recommender_receive_point", columnDefinition="integer default 0")
	private int recommenderReceive;
	
	// 포인트 사용할 수 있는 최소 금액
	@NotNull
	@Column(name="point_use_condition", columnDefinition="integer default 0")
	private int useCondition;
	
	// 회원가입 지급 포인트
	@NotNull
	@Column(name="join_point", columnDefinition="integer default 0")
	private int joinPoint;
}















