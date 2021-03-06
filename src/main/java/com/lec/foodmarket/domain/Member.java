package com.lec.foodmarket.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.lang.Nullable;

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
@Entity(name="member")
@DynamicUpdate
public class Member extends BaseEntity implements Auditable {
	// 기본키 아이디(Null 허용x)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;
	
	// 아이디 (Null 허용x)
	@NonNull
	@NotNull
	private String id;
	
	// 비밀번호(Null 허용x)
	@NotNull
	@NonNull
	private String pw;
	
	// 이름(Null 허용x)
	@NotNull
	@NonNull
	private String name;
	
	// 주소(Null 허용x)
	@NotNull
	@NonNull
	private String addr;
	
	// 상세주소(Null 허용x)
	@NonNull
	@NotNull
	@Column(name="detail_addr")
	private String detailAddr;
	
	// 이메일
	@NotNull
	@NonNull
	private String email;
	
	// 핸드폰번호(Null 허용x)
	@NotNull
	@NonNull
	@Column(name="phone_no")
	private String phoneNo;
	
	// 추천인(Null 허용)
	@Nullable
	private String recommender;
	
	// 적립금(Null 허용)
	@Nullable
	@Column(name="save_up_point")
	private int saveUpPoint;
	
	// 권한부분 생각 해야됨
	@NotNull
	@NonNull
	private String role;
	
}



















