package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.Optional;

@Controller
public class CurveController {
    @Autowired
    private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curvePointService.getAllCurvePoints()); // Récupérer tous les Curve Points
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(Model model) {
        model.addAttribute("curvePoint", new CurvePoint()); // Ajouter un nouvel objet CurvePoint au modèle
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/add"; // Retourner au formulaire en cas d'erreur
        }
        curvePointService.createCurvePoint(curvePoint); // Sauvegarder le CurvePoint
        return "redirect:/curvePoint/list"; // Rediriger vers la liste après ajout
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<CurvePoint> curvePoint = curvePointService.getCurvePointById(id);
        if (curvePoint.isPresent()) {
            model.addAttribute("curvePoint", curvePoint.get()); // Ajouter le CurvePoint au modèle
            return "curvePoint/update"; // Afficher le formulaire de mise à jour
        }
        return "redirect:/curvePoint/list"; // Rediriger si le CurvePoint n'existe pas
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            curvePoint.setId(id); // Assurez-vous que l'ID est correct
            return "curvePoint/update"; // Retourner au formulaire en cas d'erreur
        }
        curvePointService.updateCurvePoint(id, curvePoint); // Mettre à jour le CurvePoint
        return "redirect:/curvePoint/list"; // Rediriger vers la liste après mise à jour
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id) {
        curvePointService.deleteCurvePoint(id); // Supprimer le CurvePoint
        return "redirect:/curvePoint/list"; // Rediriger vers la liste après suppression
    }
}
