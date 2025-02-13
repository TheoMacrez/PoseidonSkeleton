package com.poseidonskeleton.PoseidonSkeleton.controllers;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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

public class BidListControllerTest {

    @Mock
    private BidListService bidListService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private BidListController bidListController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHome() {
        List<BidList> bidLists = Arrays.asList(new BidList(), new BidList());
        when(bidListService.getAllBidLists()).thenReturn(bidLists);
        when(userDetails.getUsername()).thenReturn("testUser");

        String viewName = bidListController.home(model, userDetails);

        assertEquals("bidList/list", viewName);
        verify(model).addAttribute("bidlists", bidLists);
        verify(model).addAttribute("loggedInUser", "testUser");
    }

    @Test
    public void testAddBidForm() {
        String viewName = bidListController.addBidForm(model);

        assertEquals("bidList/add", viewName);
        verify(model).addAttribute(eq("bidlist"), any(BidList.class));
    }

    @Test
    public void testValidateBidSuccess() {
        BidList bid = new BidList();
        when(bindingResult.hasErrors()).thenReturn(false);
        List<BidList> bidLists = Arrays.asList(bid);
        when(bidListService.getAllBidLists()).thenReturn(bidLists);

        String viewName = bidListController.validate(bid, bindingResult, model);

        assertEquals("bidList/list", viewName);
        verify(bidListService).createBidList(bid);
        verify(model).addAttribute("bidlists", bidLists);
    }

    @Test
    public void testValidateBidFailure() {
        BidList bid = new BidList();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = bidListController.validate(bid, bindingResult, model);

        assertEquals("bidList/add", viewName);
        verify(bidListService, never()).createBidList(any(BidList.class));
    }

    @Test
    public void testShowUpdateForm() {
        Integer id = 1;
        BidList bidList = new BidList();
        when(bidListService.getBidListById(id)).thenReturn(Optional.of(bidList));

        String viewName = bidListController.showUpdateForm(id, model);

        assertEquals("bidList/update", viewName);
        verify(model).addAttribute("bidlist", bidList);
    }

    @Test
    public void testShowUpdateFormNotFound() {
        Integer id = 1;
        when(bidListService.getBidListById(id)).thenReturn(Optional.empty());

        String viewName = bidListController.showUpdateForm(id, model);

        assertEquals("redirect:/bidList/list", viewName);
    }

    @Test
    public void testUpdateBidSuccess() {
        Integer id = 1;
        BidList bidList = new BidList();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = bidListController.updateBid(id, bidList, bindingResult, model);

        assertEquals("redirect:/bidList/list", viewName);
        verify(bidListService).updateBidList(id, bidList);
    }

    @Test
    public void testUpdateBidFailure() {
        Integer id = 1;
        BidList bidList = new BidList();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = bidListController.updateBid(id, bidList, bindingResult, model);

        assertEquals("bidList/update", viewName);
        verify(bidListService, never()).updateBidList(anyInt(), any(BidList.class));
    }

    @Test
    public void testDeleteBid() {
        Integer id = 1;

        String viewName = bidListController.deleteBid(id, model);

        assertEquals("redirect:/bidList/list", viewName);
        verify(bidListService).deleteBidList(id);
    }
}
