package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.Optional;

/**
 * Controller class for handling HTTP requests related to CurvePoint entities.
 */
@Controller
public class CurvePointController {

    @Autowired
    private CurvePointService curvePointService;

    /**
     * Handles the request to display the list of CurvePoints.
     *
     * @param model the Model object to add attributes.
     * @param userDetails the details of the authenticated user.
     * @return the view name for the CurvePoint list page.
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("curvePoints", curvePointService.getAllCurvePoints());
        if (userDetails != null) {
            model.addAttribute("loggedInUser", userDetails.getUsername());
        }
        return "curvePoint/list";
    }

    /**
     * Handles the request to display the form for adding a new CurvePoint.
     *
     * @param model the Model object to add attributes.
     * @return the view name for the add CurvePoint form.
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(Model model) {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(0); // Initialize curveId with a default value
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/add";
    }

    /**
     * Handles the request to validate and save a new CurvePoint.
     *
     * @param curvePoint the CurvePoint object to validate and save.
     * @param result the BindingResult object to handle validation errors.
     * @param model the Model object to add attributes.
     * @return the view name for the CurvePoint list page if successful, otherwise the add form.
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        model.addAttribute("curvePoints", curvePointService.getAllCurvePoints());
        curvePointService.createCurvePoint(curvePoint);
        return "redirect:/curvePoint/list";
    }

    /**
     * Handles the request to display the form for updating an existing CurvePoint.
     *
     * @param id the ID of the CurvePoint to update.
     * @param model the Model object to add attributes.
     * @return the view name for the update CurvePoint form if the CurvePoint exists, otherwise redirect to the list page.
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<CurvePoint> curvePoint = curvePointService.getCurvePointById(id);
        if (curvePoint.isPresent()) {
            model.addAttribute("curvePoint", curvePoint.get());
            return "curvePoint/update";
        }
        return "redirect:/curvePoint/list";
    }

    /**
     * Handles the request to update an existing CurvePoint.
     *
     * @param id the ID of the CurvePoint to update.
     * @param curvePoint the CurvePoint object with updated data.
     * @param result the BindingResult object to handle validation errors.
     * @param model the Model object to add attributes.
     * @return the view name for the update CurvePoint form if there are errors, otherwise redirect to the list page.
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            curvePoint.setId(id);
            return "curvePoint/update";
        }
        curvePointService.updateCurvePoint(id, curvePoint);
        return "redirect:/curvePoint/list";
    }

    /**
     * Handles the request to delete a CurvePoint.
     *
     * @param id the ID of the CurvePoint to delete.
     * @return the view name to redirect to the CurvePoint list page.
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id) {
        curvePointService.deleteCurvePoint(id);
        return "redirect:/curvePoint/list";
    }
}
