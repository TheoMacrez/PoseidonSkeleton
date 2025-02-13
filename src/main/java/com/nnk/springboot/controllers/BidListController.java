package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
public class BidListController {
    @Autowired
    private BidListService bidListService;


    @RequestMapping("/bidList/list")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails)
    {
        model.addAttribute("bidLists", bidListService.getAllBidLists());
        if (userDetails != null) {
            model.addAttribute("loggedInUser", userDetails.getUsername());
        }
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {

        model.addAttribute("bidList",new BidList());
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "bidList/add"; // Retourner au formulaire en cas d'erreur
        }
        bidListService.createBidList(bid);

        model.addAttribute("bidLists", bidListService.getAllBidLists());

        return "bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<BidList> bidList = bidListService.getBidListById(id);
        if (bidList.isPresent()) {
            model.addAttribute("bidList", bidList.get());
            return "bidList/update"; // Afficher le formulaire de mise à jour
        }
        return "redirect:/bidList/list"; // Rediriger si le BidList n'existe pas
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            bidList.setBidListId(id); // Assurez-vous que l'ID est correct
            return "bidList/update"; // Retourner au formulaire en cas d'erreur
        }
        bidListService.updateBidList(id, bidList); // Mettre à jour le BidList
        return "redirect:/bidList/list"; // Rediriger vers la liste après mise à jour
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteBidList(id);
        return "redirect:/bidList/list";
    }
}
