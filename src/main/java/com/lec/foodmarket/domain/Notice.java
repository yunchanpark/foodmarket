package com.lec.foodmarket.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Entity(name="notice")

public class Notice extends BaseEntity implements Auditable {
	
	// 기본키 공지사항 번호
	@Id
	@GeneratedValue
	@Column(name="notice_no")
	private Long noticeNo;
	
	// 외래키 Member의 아이디
	@ManyToOne
	@NonNull
	@NotNull
	@JoinColumn(name="uid")
	private Member id;
	
	// 공지사항 제목(Null 허용x)
	@NonNull
	@NotNull
	@Column(name="notice_title")
	private String title;
	
	// 공지사항 내용(Null 허용x)
	@NonNull
	@NotNull
	@Column(name="notice_content")
	private String content;
	
	// 공지사항 조회수(기본값 0)
	@Column(name="notice_viewcnt", columnDefinition="integer default 0")
	private int viewCnt;


}



























