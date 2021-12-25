package com.lec.foodmarket.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Entity(name="orders_detail")
public class OrderDetail {
	
	// 기본키 주문 상세번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_detail_no")
	private Long orderDetailNo;
	
	// 외래키 Order의 주문번호
	@ManyToOne
	@NonNull
	@NotNull
	@JoinColumn(name="order_no")
	private Order orderNo;
	
	// 외래키 Product의 상품번호
	@ManyToOne
	@NonNull
	@NotNull
	@JoinColumn(name="product_no")
	private Product productNo;
	
	// 주문한 상품개수
	@NotNull
	@Column(name="product_cnt", columnDefinition = "integer default 1")
	private int cnt;
	
	// 주문한 상품가격
	@NotNull
	@Column(name="product_price")
	private int price;
}



























