package com.lec.foodmarket.domain;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass // 상속 받는 쪽에 포함시켜 줌
@EntityListeners(value=AuditingEntityListener.class)
public class BaseEntity {
	// spring에서 기본으로 제공해주는 CreatedDate, LastModifiedDate 사용
	
	// 등록일
	@NotNull
	@CreatedDate
	private LocalDateTime createdAt;
	
	// 수정일
	@NotNull
	@LastModifiedDate
	private LocalDateTime updatedAt;
}
