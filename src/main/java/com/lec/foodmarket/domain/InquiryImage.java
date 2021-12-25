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
@Entity(name="inquiry_image")
public class InquiryImage {
	
	// 기본키 1대1문의 사진번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="inquiry_image_no")
	private Long inquiryImageNo;
	
	// 외래키 Inquiry의 1대1문의 번호
	@ManyToOne
	@NonNull
	@NotNull
	@JoinColumn(name="inquiry_no")
	private Inquiry inquiryNo;
	
	// 1대1문의 사진원본이름(Null 허용x)
	@NonNull
	@NotNull
	@Column(name="inuiry_image_orgin")
	private String inuiryOrgin;

	// 서버에 저장될 1대1문의 사진이름(Null 허용x)
	@NonNull
	@NotNull
	@Column(name="inuiry_image_save")
	private String inuirySave;
}
