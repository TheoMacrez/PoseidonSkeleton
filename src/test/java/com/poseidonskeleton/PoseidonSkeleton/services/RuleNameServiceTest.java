package com.poseidonskeleton.PoseidonSkeleton.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for RuleNameService.
 */
public class RuleNameServiceTest {

    @InjectMocks
    private RuleNameService ruleNameService;

    @Mock
    private RuleNameRepository ruleNameRepository;

    private RuleName ruleName;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Test Rule");
        ruleName.setDescription("Test Description");
        ruleName.setJson("{\"key\":\"value\"}");
        ruleName.setTemplate("Test Template");
        ruleName.setSqlStr("SELECT * FROM table");
        ruleName.setSqlPart("WHERE id = ?");
    }

    /**
     * Tests the createRuleName method of RuleNameService.
     */
    @Test
    public void testCreateRuleName() {
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        RuleName createdRuleName = ruleNameService.createRuleName(ruleName);

        assertThat(createdRuleName).isEqualTo(ruleName);
        verify(ruleNameRepository, times(1)).save(ruleName);
    }

    /**
     * Tests the getAllRuleNames method of RuleNameService.
     */
    @Test
    public void testGetAllRuleNames() {
        List<RuleName> ruleNames = new ArrayList<>();
        ruleNames.add(ruleName);

        when(ruleNameRepository.findAll()).thenReturn(ruleNames);

        List<RuleName> result = ruleNameService.getAllRuleNames();

        assertThat(result).hasSize(1);
        assertThat(result).contains(ruleName);
    }

    /**
     * Tests the getRuleNameById method of RuleNameService.
     */
    @Test
    public void testGetRuleNameById() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        Optional<RuleName> result = ruleNameService.getRuleNameById(1);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(ruleName);
    }

    /**
     * Tests the updateRuleName method of RuleNameService.
     */
    @Test
    public void testUpdateRuleName() {
        when(ruleNameRepository.existsById(1)).thenReturn(true);
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        RuleName updatedRuleName = ruleNameService.updateRuleName(1, ruleName);

        assertThat(updatedRuleName).isEqualTo(ruleName);
        verify(ruleNameRepository, times(1)).save(ruleName);
    }

    /**
     * Tests the deleteRuleName method of RuleNameService.
     */
    @Test
    public void testDeleteRuleName() {
        doNothing().when(ruleNameRepository).deleteById(1);

        ruleNameService.deleteRuleName(1);

        verify(ruleNameRepository, times(1)).deleteById(1);
    }
}
