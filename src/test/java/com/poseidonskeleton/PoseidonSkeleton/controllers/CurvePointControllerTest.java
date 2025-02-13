package com.poseidonskeleton.PoseidonSkeleton.controllers;

import com.nnk.springboot.controllers.CurvePointController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for CurvePointController.
 */
public class CurvePointControllerTest {

    @Mock
    private CurvePointService curvePointService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private CurvePointController curvePointController;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the home method of CurvePointController.
     */
    @Test
    public void testHome() {
        List<CurvePoint> curvePoints = Arrays.asList(new CurvePoint(), new CurvePoint());
        when(curvePointService.getAllCurvePoints()).thenReturn(curvePoints);
        when(userDetails.getUsername()).thenReturn("testUser");

        String viewName = curvePointController.home(model, userDetails);

        assertEquals("curvePoint/list", viewName);
        verify(model).addAttribute("curvePoints", curvePoints);
        verify(model).addAttribute("loggedInUser", "testUser");
    }

    /**
     * Tests the addCurvePointForm method of CurvePointController.
     */
    @Test
    public void testAddCurvePointForm() {
        String viewName = curvePointController.addCurvePointForm(model);

        assertEquals("curvePoint/add", viewName);
        verify(model).addAttribute(eq("curvePoint"), any(CurvePoint.class));
    }

    /**
     * Tests the validate method of CurvePointController when validation is successful.
     */
    @Test
    public void testValidateCurvePointSuccess() {
        CurvePoint curvePoint = new CurvePoint();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = curvePointController.validate(curvePoint, bindingResult, model);

        assertEquals("redirect:/curvePoint/list", viewName);
        verify(curvePointService).createCurvePoint(curvePoint);
    }

    /**
     * Tests the validate method of CurvePointController when validation fails.
     */
    @Test
    public void testValidateCurvePointFailure() {
        CurvePoint curvePoint = new CurvePoint();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = curvePointController.validate(curvePoint, bindingResult, model);

        assertEquals("curvePoint/add", viewName);
        verify(curvePointService, never()).createCurvePoint(any(CurvePoint.class));
    }

    /**
     * Tests the showUpdateForm method of CurvePointController when the CurvePoint exists.
     */
    @Test
    public void testShowUpdateForm() {
        Integer id = 1;
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointService.getCurvePointById(id)).thenReturn(Optional.of(curvePoint));

        String viewName = curvePointController.showUpdateForm(id, model);

        assertEquals("curvePoint/update", viewName);
        verify(model).addAttribute("curvePoint", curvePoint);
    }

    /**
     * Tests the showUpdateForm method of CurvePointController when the CurvePoint does not exist.
     */
    @Test
    public void testShowUpdateFormNotFound() {
        Integer id = 1;
        when(curvePointService.getCurvePointById(id)).thenReturn(Optional.empty());

        String viewName = curvePointController.showUpdateForm(id, model);

        assertEquals("redirect:/curvePoint/list", viewName);
    }

    /**
     * Tests the updateCurvePoint method of CurvePointController when validation is successful.
     */
    @Test
    public void testUpdateCurvePointSuccess() {
        Integer id = 1;
        CurvePoint curvePoint = new CurvePoint();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = curvePointController.updateCurvePoint(id, curvePoint, bindingResult, model);

        assertEquals("redirect:/curvePoint/list", viewName);
        verify(curvePointService).updateCurvePoint(id, curvePoint);
    }

    /**
     * Tests the updateCurvePoint method of CurvePointController when validation fails.
     */
    @Test
    public void testUpdateCurvePointFailure() {
        Integer id = 1;
        CurvePoint curvePoint = new CurvePoint();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = curvePointController.updateCurvePoint(id, curvePoint, bindingResult, model);

        assertEquals("curvePoint/update", viewName);
        verify(curvePointService, never()).updateCurvePoint(anyInt(), any(CurvePoint.class));
    }

    /**
     * Tests the deleteCurvePoint method of CurvePointController.
     */
    @Test
    public void testDeleteCurvePoint() {
        Integer id = 1;

        String viewName = curvePointController.deleteCurvePoint(id);

        assertEquals("redirect:/curvePoint/list", viewName);
        verify(curvePointService).deleteCurvePoint(id);
    }
}
