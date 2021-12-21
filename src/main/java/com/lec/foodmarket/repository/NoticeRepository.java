package com.lec.foodmarket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.Notice;

import lombok.NonNull;

public interface NoticeRepository extends JpaRepository<Notice, Long> {




}
