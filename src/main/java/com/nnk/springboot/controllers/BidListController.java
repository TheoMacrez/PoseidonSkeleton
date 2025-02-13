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

/**
 * Controller class for handling HTTP requests related to BidList entities.
 */
@Controller
public class BidListController {

    @Autowired
    private BidListService bidListService;

    /**
     * Handles the request to display the list of BidLists.
     *
     * @param model the Model object to add attributes.
     * @param userDetails the details of the authenticated user.
     * @return the view name for the BidList list page.
     */
    @RequestMapping("/bidList/list")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("bidLists", bidListService.getAllBidLists());
        if (userDetails != null) {
            model.addAttribute("loggedInUser", userDetails.getUsername());
        }
        return "bidList/list";
    }

    /**
     * Handles the request to display the form for adding a new BidList.
     *
     * @param model the Model object to add attributes.
     * @return the view name for the add BidList form.
     */
    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("bidList", new BidList());
        return "bidList/add";
    }

    /**
     * Handles the request to validate and save a new BidList.
     *
     * @param bid the BidList object to validate and save.
     * @param result the BindingResult object to handle validation errors.
     * @param model the Model object to add attributes.
     * @return the view name for the BidList list page if successful, otherwise the add form.
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/add";
        }
        bidListService.createBidList(bid);
        model.addAttribute("bidLists", bidListService.getAllBidLists());
        return "bidList/list";
    }

    /**
     * Handles the request to display the form for updating an existing BidList.
     *
     * @param id the ID of the BidList to update.
     * @param model the Model object to add attributes.
     * @return the view name for the update BidList form if the BidList exists, otherwise redirect to the list page.
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<BidList> bidList = bidListService.getBidListById(id);
        if (bidList.isPresent()) {
            model.addAttribute("bidList", bidList.get());
            return "bidList/update";
        }
        return "redirect:/bidList/list";
    }

    /**
     * Handles the request to update an existing BidList.
     *
     * @param id the ID of the BidList to update.
     * @param bidList the BidList object with updated data.
     * @param result the BindingResult object to handle validation errors.
     * @param model the Model object to add attributes.
     * @return the view name for the update BidList form if there are errors, otherwise redirect to the list page.
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            bidList.setBidListId(id);
            return "bidList/update";
        }
        bidListService.updateBidList(id, bidList);
        return "redirect:/bidList/list";
    }

    /**
     * Handles the request to delete a BidList.
     *
     * @param id the ID of the BidList to delete.
     * @param model the Model object to add attributes.
     * @return the view name to redirect to the BidList list page.
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteBidList(id);
        return "redirect:/bidList/list";
    }
}
