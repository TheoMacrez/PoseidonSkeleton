package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling business logic related to CurvePoint entities.
 */
@Service
public class CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Creates a new CurvePoint.
     *
     * @param curvePoint the CurvePoint object to create.
     * @return the created CurvePoint object.
     */
    public CurvePoint createCurvePoint(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Retrieves all CurvePoints.
     *
     * @return a list of all CurvePoints.
     */
    public List<CurvePoint> getAllCurvePoints() {
        return curvePointRepository.findAll();
    }

    /**
     * Retrieves a CurvePoint by its ID.
     *
     * @param id the ID of the CurvePoint to retrieve.
     * @return an Optional containing the CurvePoint if found, otherwise empty.
     */
    public Optional<CurvePoint> getCurvePointById(Integer id) {
        return curvePointRepository.findById(id);
    }

    /**
     * Updates an existing CurvePoint.
     *
     * @param id the ID of the CurvePoint to update.
     * @param curvePoint the CurvePoint object with updated data.
     * @return the updated CurvePoint object if successful, otherwise null.
     */
    public CurvePoint updateCurvePoint(Integer id, CurvePoint curvePoint) {
        if (curvePointRepository.existsById(id)) {
            curvePoint.setId(id);
            return curvePointRepository.save(curvePoint);
        }
        return null;
    }

    /**
     * Deletes a CurvePoint by its ID.
     *
     * @param id the ID of the CurvePoint to delete.
     */
    public void deleteCurvePoint(Integer id) {
        curvePointRepository.deleteById(id);
    }
}
