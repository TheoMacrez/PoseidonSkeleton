package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
 * Controller class for handling HTTP requests related to Trade entities.
 */
@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;

    /**
     * Handles the request to display the list of Trades.
     *
     * @param model the Model object to add attributes.
     * @param userDetails the details of the authenticated user.
     * @return the view name for the Trade list page.
     */
    @RequestMapping("/trade/list")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("trades", tradeService.getAllTrades());
        if (userDetails != null) {
            model.addAttribute("loggedInUser", userDetails.getUsername());
        }
        return "trade/list";
    }

    /**
     * Handles the request to display the form for adding a new Trade.
     *
     * @param model the Model object to add attributes.
     * @return the view name for the add Trade form.
     */
    @GetMapping("/trade/add")
    public String addTradeForm(Model model) {
        model.addAttribute("trade", new Trade());
        return "trade/add";
    }

    /**
     * Handles the request to validate and save a new Trade.
     *
     * @param trade the Trade object to validate and save.
     * @param result the BindingResult object to handle validation errors.
     * @param model the Model object to add attributes.
     * @return the view name for the Trade list page if successful, otherwise the add form.
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/add";
        }
        tradeService.createTrade(trade);
        return "redirect:/trade/list";
    }

    /**
     * Handles the request to display the form for updating an existing Trade.
     *
     * @param id the ID of the Trade to update.
     * @param model the Model object to add attributes.
     * @return the view name for the update Trade form if the Trade exists, otherwise redirect to the list page.
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Trade> trade = tradeService.getTradeById(id);
        if (trade.isPresent()) {
            model.addAttribute("trade", trade.get());
            return "trade/update";
        }
        return "redirect:/trade/list";
    }

    /**
     * Handles the request to update an existing Trade.
     *
     * @param id the ID of the Trade to update.
     * @param trade the Trade object with updated data.
     * @param result the BindingResult object to handle validation errors.
     * @param model the Model object to add attributes.
     * @return the view name for the update Trade form if there are errors, otherwise redirect to the list page.
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            trade.setTradeId(id);
            return "trade/update";
        }
        tradeService.updateTrade(id, trade);
        return "redirect:/trade/list";
    }

    /**
     * Handles the request to delete a Trade.
     *
     * @param id the ID of the Trade to delete.
     * @return the view name to redirect to the Trade list page.
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {
        tradeService.deleteTrade(id);
        return "redirect:/trade/list";
    }
}
