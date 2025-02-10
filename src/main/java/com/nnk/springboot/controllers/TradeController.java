package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeController {
    @Autowired
    private TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.getAllTrades()); // Récupérer toutes les trades
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(Model model) {
        model.addAttribute("trade", new Trade()); // Ajouter un nouvel objet Trade au modèle
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/add"; // Retourner au formulaire en cas d'erreur
        }
        tradeService.createTrade(trade); // Sauvegarder la trade
        return "redirect:/trade/list"; // Rediriger vers la liste après ajout
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Trade> trade = tradeService.getTradeById(id);
        if (trade.isPresent()) {
            model.addAttribute("trade", trade.get()); // Ajouter la trade au modèle
            return "trade/update"; // Afficher le formulaire de mise à jour
        }
        return "redirect:/trade/list"; // Rediriger si la trade n'existe pas
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            trade.setTradeId(id); // Assurez-vous que l'ID est correct
            return "trade/update"; // Retourner au formulaire en cas d'erreur
        }
        tradeService.updateTrade(id, trade); // Mettre à jour la trade
        return "redirect:/trade/list"; // Rediriger vers la liste après mise à jour
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {
        tradeService.deleteTrade(id); // Supprimer la trade
        return "redirect:/trade/list"; // Rediriger vers la liste après suppression
    }
}
