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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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

    @Test
    public void testAddCurvePointForm() {
        String viewName = curvePointController.addCurvePointForm(model);

        assertEquals("curvePoint/add", viewName);
        verify(model).addAttribute(eq("curvePoint"), any(CurvePoint.class));
    }

    @Test
    public void testValidateCurvePointSuccess() {
        CurvePoint curvePoint = new CurvePoint();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = curvePointController.validate(curvePoint, bindingResult, model);

        assertEquals("redirect:/curvePoint/list", viewName);
        verify(curvePointService).createCurvePoint(curvePoint);
    }

    @Test
    public void testValidateCurvePointFailure() {
        CurvePoint curvePoint = new CurvePoint();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = curvePointController.validate(curvePoint, bindingResult, model);

        assertEquals("curvePoint/add", viewName);
        verify(curvePointService, never()).createCurvePoint(any(CurvePoint.class));
    }

    @Test
    public void testShowUpdateForm() {
        Integer id = 1;
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointService.getCurvePointById(id)).thenReturn(Optional.of(curvePoint));

        String viewName = curvePointController.showUpdateForm(id, model);

        assertEquals("curvePoint/update", viewName);
        verify(model).addAttribute("curvePoint", curvePoint);
    }

    @Test
    public void testShowUpdateFormNotFound() {
        Integer id = 1;
        when(curvePointService.getCurvePointById(id)).thenReturn(Optional.empty());

        String viewName = curvePointController.showUpdateForm(id, model);

        assertEquals("redirect:/curvePoint/list", viewName);
    }

    @Test
    public void testUpdateCurvePointSuccess() {
        Integer id = 1;
        CurvePoint curvePoint = new CurvePoint();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = curvePointController.updateCurvePoint(id, curvePoint, bindingResult, model);

        assertEquals("redirect:/curvePoint/list", viewName);
        verify(curvePointService).updateCurvePoint(id, curvePoint);
    }

    @Test
    public void testUpdateCurvePointFailure() {
        Integer id = 1;
        CurvePoint curvePoint = new CurvePoint();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = curvePointController.updateCurvePoint(id, curvePoint, bindingResult, model);

        assertEquals("curvePoint/update", viewName);
        verify(curvePointService, never()).updateCurvePoint(anyInt(), any(CurvePoint.class));
    }

    @Test
    public void testDeleteCurvePoint() {
        Integer id = 1;

        String viewName = curvePointController.deleteCurvePoint(id);

        assertEquals("redirect:/curvePoint/list", viewName);
        verify(curvePointService).deleteCurvePoint(id);
    }
}
