package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    // Create
    public CurvePoint createCurvePoint(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    // Read all
    public List<CurvePoint> getAllCurvePoints() {
        return curvePointRepository.findAll();
    }

    // Read by ID
    public Optional<CurvePoint> getCurvePointById(Integer id) {
        return curvePointRepository.findById(id);
    }

    // Update
    public CurvePoint updateCurvePoint(Integer id, CurvePoint curvePoint) {
        if (curvePointRepository.existsById(id)) {
            curvePoint.setId(id); // Set the ID for the entity to update
            return curvePointRepository.save(curvePoint);
        }
        return null; // Or throw an exception
    }

    // Delete
    public void deleteCurvePoint(Integer id) {
        curvePointRepository.deleteById(id);
    }
}
