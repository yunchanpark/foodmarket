package com.lec.foodmarket.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.lec.foodmarket.domain.listener.Auditable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@Entity(name="inquiry")
public class Inquiry extends BaseEntity implements Auditable{
	
	// 기본키 1대1문의 번호
	@Id
	@GeneratedValue
	@Column(name="inquiry_no")
	private Long inquiryNo;
	
	// 외래키 Member의 아이디
	@ManyToOne
	@NonNull
	@NotNull
	@JoinColumn(name="id")
	private Member id;
	
	// 1대1문의 제목(Null 허용x)
	@NonNull
	@NotNull
	@Column(name="inquiry_title")
	private String title;
	
	// 1대1문의 내용(Null 허용x)
	@NonNull
	@NotNull
	@Column(name="inquiry_content")
	private String content;
	
	// 1대1문의 답변(Null 허용)
	@Lob
	@Column(name="inquiry_answer", length=512)
	private String answer;
	
	// 1대1문의 상태(기본값 0)
	@Column(name="inquiry_status", columnDefinition="integer default 0")
	private int status;
}

























