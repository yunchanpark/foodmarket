package com.lec.foodmarket.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.lec.foodmarket.domain.listener.Auditable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "product")
public class Product extends BaseEntity implements Auditable {

	// 기본키 상품번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_no")
	private Long productNo;

	// 외래키 category의 카테고리번호
	@ManyToOne
	@JoinColumn(name = "category_no")
	private ProductCategory categoryNo;

	// 상품이름(Null 허용x)
	@NotNull
	@Column(name = "product_name")
	private String name;

	// 상품추가설명(Null 허용)
	@Column(name = "product_description")
	private String description;

	// 상품가격(Null 허용x)
	@NotNull
	@Column(name = "product_price")
	private int price;

	// 상품매입가격(Null 허용x)
	@NotNull
	@Column(name = "product_purchase_price")
	private int purchasePrice;

	// 상품재고(Null 허용x)
	@NotNull
	@Column(name = "product_stock")
	private int stock;

	// 상품조회수(Null 허용, 기본값 0)
	@Column(name = "product_viewcnt", columnDefinition = "integer default 0")
	private int viewCnt;

	// 상품 원본 사진이름(Null 허용x)
	@NotNull
	@Column(name = "product_image_orgin")
	private String imageOrgin;

	// 상품 서버에 저장될 사진이름(Null 허용x)
	@NotNull
	@Column(name = "product_image_save")
	private String imageSave;

	// 상품할인(Null 허용, 기본값 0)
	@Column(name = "product_discount", columnDefinition = "integer default 0")
	private int discount;
	
	@Column(name = "product_exchange_rate")
	private String exchangeRate;

	// 상품상세설명(Null 허용x, Text타입)
	@Lob
	@NotNull
	@Column(name = "product_detail_content", length = 1024)
	private String detailContent;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = "product_discount_start")
	private LocalDateTime disStart;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = "product_discount_end")
	private LocalDateTime disEnd;
}
