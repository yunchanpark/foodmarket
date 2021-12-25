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
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity(name="refunds_image")
public class RefundImage {
	
	// 기본키 환불 사진번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="refund_image_no")
	private int imageNo;
	
	// 외래키 환불번호
	@ManyToOne
	@NonNull
	@NotNull
	@JoinColumn(name="refund_no")
	private Refund refundNo;
	
	// 환불 원본사진(Null 허용x)
	@NonNull
	@NotNull
	@Column(name="refund_orgin")
	private String orgin;

	// 서버에 저장될 환불 사진(Null 허용x)
	@NonNull
	@NotNull
	@Column(name="refund_save")
	private String save;
}



















