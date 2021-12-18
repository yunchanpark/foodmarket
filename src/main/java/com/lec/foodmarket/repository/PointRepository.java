package com.lec.foodmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.foodmarket.domain.Point;

public interface PointRepository extends JpaRepository<Point, Long> {

}
