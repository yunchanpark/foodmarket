package com.lec.foodmarket.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="product_likecnt")
public class ProductLikeCnt {
	
	@Id
	@GeneratedValue
	@Column(name="product_likecnt_no")
	private Long likeCntNo;
	
	// 외래키 member의 아이디
	@ManyToOne
	@NonNull
	@NotNull
	@JoinColumn(name="id")
	private Member id;
	
	// 외래키 product의 상품번호
	@ManyToOne
	@NonNull
	@NotNull
	@JoinColumn(name="product_no")
	private Product productNo;
	
	// 상품좋아요개수(기본값 0)
	@Column(name="product_likecnt", columnDefinition="integer default 0")
	private int product_likecnt;
	
}
