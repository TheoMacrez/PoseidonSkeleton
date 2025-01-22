package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
