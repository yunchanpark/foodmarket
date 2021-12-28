package com.lec.foodmarket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.Inquiry;
import com.lec.foodmarket.domain.Notice;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

}
