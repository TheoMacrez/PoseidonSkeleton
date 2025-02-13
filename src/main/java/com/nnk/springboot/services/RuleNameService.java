package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling business logic related to RuleName entities.
 */
@Service
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Creates a new RuleName.
     *
     * @param ruleName the RuleName object to create.
     * @return the created RuleName object.
     */
    public RuleName createRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Retrieves all RuleNames.
     *
     * @return a list of all RuleNames.
     */
    public List<RuleName> getAllRuleNames() {
        return ruleNameRepository.findAll();
    }

    /**
     * Retrieves a RuleName by its ID.
     *
     * @param id the ID of the RuleName to retrieve.
     * @return an Optional containing the RuleName if found, otherwise empty.
     */
    public Optional<RuleName> getRuleNameById(Integer id) {
        return ruleNameRepository.findById(id);
    }

    /**
     * Updates an existing RuleName.
     *
     * @param id the ID of the RuleName to update.
     * @param ruleName the RuleName object with updated data.
     * @return the updated RuleName object if successful, otherwise null.
     */
    public RuleName updateRuleName(Integer id, RuleName ruleName) {
        if (ruleNameRepository.existsById(id)) {
            ruleName.setId(id);
            return ruleNameRepository.save(ruleName);
        }
        return null;
    }

    /**
     * Deletes a RuleName by its ID.
     *
     * @param id the ID of the RuleName to delete.
     */
    public void deleteRuleName(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
