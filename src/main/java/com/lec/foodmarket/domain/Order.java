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
@Builder
@RequiredArgsConstructor
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@Entity(name="orders")
public class Order extends BaseEntity implements Auditable {
	
	// 기본키 주문번호
	@Id
	@GeneratedValue
	@Column(name="order_no")
	private Long orderNo;
	
	// 외래키 Member의 아이디
	@ManyToOne
	@NotNull
	@NonNull
	@JoinColumn(name="id")
	private Member id;
	
	// 주문상태 
	@Column(name="order_status", columnDefinition="integer default 0")
	private int status;
}

























