package com.poseidonskeleton.PoseidonSkeleton.controllers;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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

public class TradeControllerTest {

    @Mock
    private TradeService tradeService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private TradeController tradeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHome() {
        List<Trade> trades = Arrays.asList(new Trade(), new Trade());
        when(tradeService.getAllTrades()).thenReturn(trades);
        when(userDetails.getUsername()).thenReturn("testUser");

        String viewName = tradeController.home(model, userDetails);

        assertEquals("trade/list", viewName);
        verify(model).addAttribute("trades", trades);
        verify(model).addAttribute("loggedInUser", "testUser");
    }

    @Test
    public void testAddTradeForm() {
        String viewName = tradeController.addTradeForm(model);

        assertEquals("trade/add", viewName);
        verify(model).addAttribute(eq("trade"), any(Trade.class));
    }

    @Test
    public void testValidateTradeSuccess() {
        Trade trade = new Trade();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = tradeController.validate(trade, bindingResult, model);

        assertEquals("redirect:/trade/list", viewName);
        verify(tradeService).createTrade(trade);
    }

    @Test
    public void testValidateTradeFailure() {
        Trade trade = new Trade();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = tradeController.validate(trade, bindingResult, model);

        assertEquals("trade/add", viewName);
        verify(tradeService, never()).createTrade(any(Trade.class));
    }

    @Test
    public void testShowUpdateForm() {
        Integer id = 1;
        Trade trade = new Trade();
        when(tradeService.getTradeById(id)).thenReturn(Optional.of(trade));

        String viewName = tradeController.showUpdateForm(id, model);

        assertEquals("trade/update", viewName);
        verify(model).addAttribute("trade", trade);
    }

    @Test
    public void testShowUpdateFormNotFound() {
        Integer id = 1;
        when(tradeService.getTradeById(id)).thenReturn(Optional.empty());

        String viewName = tradeController.showUpdateForm(id, model);

        assertEquals("redirect:/trade/list", viewName);
    }

    @Test
    public void testUpdateTradeSuccess() {
        Integer id = 1;
        Trade trade = new Trade();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = tradeController.updateTrade(id, trade, bindingResult, model);

        assertEquals("redirect:/trade/list", viewName);
        verify(tradeService).updateTrade(id, trade);
    }

    @Test
    public void testUpdateTradeFailure() {
        Integer id = 1;
        Trade trade = new Trade();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = tradeController.updateTrade(id, trade, bindingResult, model);

        assertEquals("trade/update", viewName);
        verify(tradeService, never()).updateTrade(anyInt(), any(Trade.class));
    }

    @Test
    public void testDeleteTrade() {
        Integer id = 1;

        String viewName = tradeController.deleteTrade(id);

        assertEquals("redirect:/trade/list", viewName);
        verify(tradeService).deleteTrade(id);
    }
}

