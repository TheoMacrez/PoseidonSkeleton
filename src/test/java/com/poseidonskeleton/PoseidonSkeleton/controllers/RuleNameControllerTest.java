package com.poseidonskeleton.PoseidonSkeleton.controllers;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
 * Test class for RuleNameController.
 */
public class RuleNameControllerTest {

    @Mock
    private RuleNameService ruleNameService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private RuleNameController ruleNameController;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the home method of RuleNameController.
     */
    @Test
    public void testHome() {
        List<RuleName> ruleNames = Arrays.asList(new RuleName(), new RuleName());
        when(ruleNameService.getAllRuleNames()).thenReturn(ruleNames);
        when(userDetails.getUsername()).thenReturn("testUser");

        String viewName = ruleNameController.home(model, userDetails);

        assertEquals("ruleName/list", viewName);
        verify(model).addAttribute("ruleNames", ruleNames);
        verify(model).addAttribute("loggedInUser", "testUser");
    }

    /**
     * Tests the addRuleForm method of RuleNameController.
     */
    @Test
    public void testAddRuleForm() {
        String viewName = ruleNameController.addRuleForm(model);

        assertEquals("ruleName/add", viewName);
        verify(model).addAttribute(eq("ruleName"), any(RuleName.class));
    }

    /**
     * Tests the validate method of RuleNameController when validation is successful.
     */
    @Test
    public void testValidateRuleNameSuccess() {
        RuleName ruleName = new RuleName();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = ruleNameController.validate(ruleName, bindingResult, model);

        assertEquals("redirect:/ruleName/list", viewName);
        verify(ruleNameService).createRuleName(ruleName);
    }

    /**
     * Tests the validate method of RuleNameController when validation fails.
     */
    @Test
    public void testValidateRuleNameFailure() {
        RuleName ruleName = new RuleName();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = ruleNameController.validate(ruleName, bindingResult, model);

        assertEquals("ruleName/add", viewName);
        verify(ruleNameService, never()).createRuleName(any(RuleName.class));
    }

    /**
     * Tests the showUpdateForm method of RuleNameController when the RuleName exists.
     */
    @Test
    public void testShowUpdateForm() {
        Integer id = 1;
        RuleName ruleName = new RuleName();
        when(ruleNameService.getRuleNameById(id)).thenReturn(Optional.of(ruleName));

        String viewName = ruleNameController.showUpdateForm(id, model);

        assertEquals("ruleName/update", viewName);
        verify(model).addAttribute("ruleName", ruleName);
    }

    /**
     * Tests the showUpdateForm method of RuleNameController when the RuleName does not exist.
     */
    @Test
    public void testShowUpdateFormNotFound() {
        Integer id = 1;
        when(ruleNameService.getRuleNameById(id)).thenReturn(Optional.empty());

        String viewName = ruleNameController.showUpdateForm(id, model);

        assertEquals("redirect:/ruleName/list", viewName);
    }

    /**
     * Tests the updateRuleName method of RuleNameController when validation is successful.
     */
    @Test
    public void testUpdateRuleNameSuccess() {
        Integer id = 1;
        RuleName ruleName = new RuleName();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = ruleNameController.updateRuleName(id, ruleName, bindingResult, model);

        assertEquals("redirect:/ruleName/list", viewName);
        verify(ruleNameService).updateRuleName(id, ruleName);
    }

    /**
     * Tests the updateRuleName method of RuleNameController when validation fails.
     */
    @Test
    public void testUpdateRuleNameFailure() {
        Integer id = 1;
        RuleName ruleName = new RuleName();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = ruleNameController.updateRuleName(id, ruleName, bindingResult, model);

        assertEquals("ruleName/update", viewName);
        verify(ruleNameService, never()).updateRuleName(anyInt(), any(RuleName.class));
    }

    /**
     * Tests the deleteRuleName method of RuleNameController.
     */
    @Test
    public void testDeleteRuleName() {
        Integer id = 1;

        String viewName = ruleNameController.deleteRuleName(id);

        assertEquals("redirect:/ruleName/list", viewName);
        verify(ruleNameService).deleteRuleName(id);
    }
}
