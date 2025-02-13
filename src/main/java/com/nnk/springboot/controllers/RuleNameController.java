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

/**
 * Controller class for handling HTTP requests related to RuleName entities.
 */
@Controller
public class RuleNameController {

    @Autowired
    private RuleNameService ruleNameService;

    /**
     * Handles the request to display the list of RuleNames.
     *
     * @param model the Model object to add attributes.
     * @param userDetails the details of the authenticated user.
     * @return the view name for the RuleName list page.
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("ruleNames", ruleNameService.getAllRuleNames());
        if (userDetails != null) {
            model.addAttribute("loggedInUser", userDetails.getUsername());
        }
        return "ruleName/list";
    }

    /**
     * Handles the request to display the form for adding a new RuleName.
     *
     * @param model the Model object to add attributes.
     * @return the view name for the add RuleName form.
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute("ruleName", new RuleName());
        return "ruleName/add";
    }

    /**
     * Handles the request to validate and save a new RuleName.
     *
     * @param ruleName the RuleName object to validate and save.
     * @param result the BindingResult object to handle validation errors.
     * @param model the Model object to add attributes.
     * @return the view name for the RuleName list page if successful, otherwise the add form.
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        ruleNameService.createRuleName(ruleName);
        return "redirect:/ruleName/list";
    }

    /**
     * Handles the request to display the form for updating an existing RuleName.
     *
     * @param id the ID of the RuleName to update.
     * @param model the Model object to add attributes.
     * @return the view name for the update RuleName form if the RuleName exists, otherwise redirect to the list page.
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<RuleName> ruleName = ruleNameService.getRuleNameById(id);
        if (ruleName.isPresent()) {
            model.addAttribute("ruleName", ruleName.get());
            return "ruleName/update";
        }
        return "redirect:/ruleName/list";
    }

    /**
     * Handles the request to update an existing RuleName.
     *
     * @param id the ID of the RuleName to update.
     * @param ruleName the RuleName object with updated data.
     * @param result the BindingResult object to handle validation errors.
     * @param model the Model object to add attributes.
     * @return the view name for the update RuleName form if there are errors, otherwise redirect to the list page.
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            ruleName.setId(id);
            return "ruleName/update";
        }
        ruleNameService.updateRuleName(id, ruleName);
        return "redirect:/ruleName/list";
    }

    /**
     * Handles the request to delete a RuleName.
     *
     * @param id the ID of the RuleName to delete.
     * @return the view name to redirect to the RuleName list page.
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
        ruleNameService.deleteRuleName(id);
        return "redirect:/ruleName/list";
    }
}
