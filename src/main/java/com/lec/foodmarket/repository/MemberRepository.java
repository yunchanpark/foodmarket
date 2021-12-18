package com.lec.foodmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
