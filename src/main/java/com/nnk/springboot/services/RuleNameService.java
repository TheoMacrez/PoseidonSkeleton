package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    // Create
    public RuleName createRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    // Read all
    public List<RuleName> getAllRuleNames() {
        return ruleNameRepository.findAll();
    }

    // Read by ID
    public Optional<RuleName> getRuleNameById(Integer id) {
        return ruleNameRepository.findById(id);
    }

    // Update
    public RuleName updateRuleName(Integer id, RuleName ruleName) {
        if (ruleNameRepository.existsById(id)) {
            ruleName.setId(id); // Set the ID for the entity to update
            return ruleNameRepository.save(ruleName);
        }
        return null; // Or throw an exception
    }

    // Delete
    public void deleteRuleName(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
