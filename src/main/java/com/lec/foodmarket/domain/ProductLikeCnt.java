package com.lec.foodmarket.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity(name="product_likecnt")
public class ProductLikeCnt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_likecnt_no")
	private Long likeCntNo;
	
	// 외래키 member의 아이디
	@ManyToOne(optional = false)
	@NonNull
	@JoinColumn(name="id")
	private Member member;
	
	// 외래키 product의 상품번호
	@ManyToOne(optional = false)
	@NonNull
	@JoinColumn(name="product_no")
	private Product product;
}
