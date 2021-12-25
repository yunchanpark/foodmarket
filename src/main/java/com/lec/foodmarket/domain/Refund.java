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
@RequiredArgsConstructor
@Builder
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@Entity(name="refunds")
public class Refund extends BaseEntity implements Auditable {
	
	// 기본키 환불번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="refund_no")
	private Long refundNo;
	
	// 외래키 Order의 주문번호
	@ManyToOne
	@NotNull
	@NonNull
	@JoinColumn(name="order_no", referencedColumnName="order_no")
	private Order orderNo;
	
	// 환불 이유(Null 허용x)
	@NonNull
	@NotNull
	@Column(name="refund_reason")
	private String reason;
	
	// 환불 상태
	@Column(name="refund_status", columnDefinition="integer default 0")
	private int status;
}
























