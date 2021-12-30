package com.lec.foodmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

}
