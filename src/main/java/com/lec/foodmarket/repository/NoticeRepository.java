package com.lec.foodmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
