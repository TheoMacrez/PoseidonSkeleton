package com.poseidonskeleton.PoseidonSkeleton.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for CurvePointService.
 */
public class CurvePointServiceTest {

    @InjectMocks
    private CurvePointService curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;

    private CurvePoint curvePoint;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setTerm(BigDecimal.valueOf(5.0));
        curvePoint.setValue(BigDecimal.valueOf(100.0));
        curvePoint.setCreationDate(new Timestamp(System.currentTimeMillis()));
    }

    /**
     * Tests the createCurvePoint method of CurvePointService.
     */
    @Test
    public void testCreateCurvePoint() {
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint createdCurvePoint = curvePointService.createCurvePoint(curvePoint);

        assertThat(createdCurvePoint).isEqualTo(curvePoint);
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    /**
     * Tests the getAllCurvePoints method of CurvePointService.
     */
    @Test
    public void testGetAllCurvePoints() {
        List<CurvePoint> curvePoints = new ArrayList<>();
        curvePoints.add(curvePoint);

        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        List<CurvePoint> result = curvePointService.getAllCurvePoints();

        assertThat(result).hasSize(1);
        assertThat(result).contains(curvePoint);
    }

    /**
     * Tests the getCurvePointById method of CurvePointService.
     */
    @Test
    public void testGetCurvePointById() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        Optional<CurvePoint> result = curvePointService.getCurvePointById(1);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(curvePoint);
    }

    /**
     * Tests the updateCurvePoint method of CurvePointService.
     */
    @Test
    public void testUpdateCurvePoint() {
        when(curvePointRepository.existsById(1)).thenReturn(true);
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint updatedCurvePoint = curvePointService.updateCurvePoint(1, curvePoint);

        assertThat(updatedCurvePoint).isEqualTo(curvePoint);
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    /**
     * Tests the deleteCurvePoint method of CurvePointService.
     */
    @Test
    public void testDeleteCurvePoint() {
        doNothing().when(curvePointRepository).deleteById(1);

        curvePointService.deleteCurvePoint(1);

        verify(curvePointRepository, times(1)).deleteById(1);
    }
}
