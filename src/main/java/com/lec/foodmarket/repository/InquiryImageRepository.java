package com.lec.foodmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.Inquiry;
import com.lec.foodmarket.domain.InquiryImage;

public interface InquiryImageRepository extends JpaRepository<InquiryImage, Long> {

	void deleteByInquiryNo (Inquiry inquiryno);
	
	InquiryImage findByInquiryNo(Inquiry inquiryno);
}
