package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import jakarta.servlet.http.HttpServletRequest;
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

@Controller
public class RuleNameController {

    @Autowired
    private RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("ruleNames", ruleNameService.getAllRuleNames()); // Récupérer tous les RuleNames
        if (userDetails != null) {
            model.addAttribute("loggedInUser", userDetails.getUsername());
        }
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute("ruleName", new RuleName()); // Ajouter un nouvel objet RuleName au modèle
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/add"; // Retourner au formulaire en cas d'erreur
        }
        ruleNameService.createRuleName(ruleName); // Sauvegarder le RuleName
        return "redirect:/ruleName/list"; // Rediriger vers la liste après ajout
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<RuleName> ruleName = ruleNameService.getRuleNameById(id);
        if (ruleName.isPresent()) {
            model.addAttribute("ruleName", ruleName.get()); // Ajouter le RuleName au modèle
            return "ruleName/update"; // Afficher le formulaire de mise à jour
        }
        return "redirect:/ruleName/list"; // Rediriger si le RuleName n'existe pas
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            ruleName.setId(id); // Assurez-vous que l'ID est correct
            return "ruleName/update"; // Retourner au formulaire en cas d'erreur
        }
        ruleNameService.updateRuleName(id, ruleName); // Mettre à jour le RuleName
        return "redirect:/ruleName/list"; // Rediriger vers la liste après mise à jour
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
        ruleNameService.deleteRuleName(id); // Supprimer le RuleName
        return "redirect:/ruleName/list"; // Rediriger vers la liste après suppression
    }
}
