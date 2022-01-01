package com.lec.foodmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lec.foodmarket.repository.OrderDetailRepository;
import com.lec.foodmarket.repository.OrderRepository;
import com.lec.foodmarket.repository.RefundImageRepository;
import com.lec.foodmarket.repository.RefundRepository;

@Service
public class OrderService {
	
	private OrderRepository orderRepository; // 주문
	private OrderDetailRepository orderDetailRepository; // 주문상세
	private RefundRepository refundRepository; // 환불
	private RefundImageRepository refundImageRepository; // 환불이미지
	
	@Autowired
	public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
			RefundRepository refundRepository, RefundImageRepository refundImageRepository) {
		this.orderRepository = orderRepository;
		this.orderDetailRepository = orderDetailRepository;
		this.refundRepository = refundRepository;
		this.refundImageRepository = refundImageRepository;
	}
	
	
	public OrderService() {;}



	
	/******************************************
	 * 관리자 
	 *   주문(조회, 수정, 삭제)
	 *   환불(조회, 수정, 삭제)
	 *   환불 승인/거부
	 * 
	 * 사용자
	 *   주문 목록, 상세 조회
	 *   환불 신청
	 *   결제 정보 입력
	 *   주문한 상품 리뷰 작성, 수정, 삭제
	 *   
	 * 추가사항이나 수정사항 알아서
	 ******************************************/
	
	
	/******************************************
	 * 관리자
	 ******************************************/
	// TODO
	
	
	
	
	
	/******************************************
	 * 사용자
	 ******************************************/

	public Object order_list() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
}


















