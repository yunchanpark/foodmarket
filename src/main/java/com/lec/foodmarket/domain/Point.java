package com.lec.foodmarket.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Builder
@RequiredArgsConstructor
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@Entity(name="point")
public class Point extends BaseEntity implements Auditable {
	
	// 기본키 포인트번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="point_no")
	private Long pointNo;
	
	// 외래키 Member의 아이디
	@ManyToOne
	@NotNull
	@NonNull
	@JoinColumn(name="uid")
	private Member uid;
	
	// 포인트 이름
	@NotNull
	@NonNull
	@Column(name="point_name")
	private String name;
	
	// 포인트
	@Column(name="point")
	private int point;
	
	// 포인트가 사용/적립 유무
	@Column(name="point_status")
	private int status;
}


















