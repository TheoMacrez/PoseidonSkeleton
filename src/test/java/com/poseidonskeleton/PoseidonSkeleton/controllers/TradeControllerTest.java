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

/**
 * Test class for TradeController.
 */
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

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the home method of TradeController.
     */
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

    /**
     * Tests the addTradeForm method of TradeController.
     */
    @Test
    public void testAddTradeForm() {
        String viewName = tradeController.addTradeForm(model);

        assertEquals("trade/add", viewName);
        verify(model).addAttribute(eq("trade"), any(Trade.class));
    }

    /**
     * Tests the validate method of TradeController when validation is successful.
     */
    @Test
    public void testValidateTradeSuccess() {
        Trade trade = new Trade();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = tradeController.validate(trade, bindingResult, model);

        assertEquals("redirect:/trade/list", viewName);
        verify(tradeService).createTrade(trade);
    }

    /**
     * Tests the validate method of TradeController when validation fails.
     */
    @Test
    public void testValidateTradeFailure() {
        Trade trade = new Trade();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = tradeController.validate(trade, bindingResult, model);

        assertEquals("trade/add", viewName);
        verify(tradeService, never()).createTrade(any(Trade.class));
    }

    /**
     * Tests the showUpdateForm method of TradeController when the Trade exists.
     */
    @Test
    public void testShowUpdateForm() {
        Integer id = 1;
        Trade trade = new Trade();
        when(tradeService.getTradeById(id)).thenReturn(Optional.of(trade));

        String viewName = tradeController.showUpdateForm(id, model);

        assertEquals("trade/update", viewName);
        verify(model).addAttribute("trade", trade);
    }

    /**
     * Tests the showUpdateForm method of TradeController when the Trade does not exist.
     */
    @Test
    public void testShowUpdateFormNotFound() {
        Integer id = 1;
        when(tradeService.getTradeById(id)).thenReturn(Optional.empty());

        String viewName = tradeController.showUpdateForm(id, model);

        assertEquals("redirect:/trade/list", viewName);
    }

    /**
     * Tests the updateTrade method of TradeController when validation is successful.
     */
    @Test
    public void testUpdateTradeSuccess() {
        Integer id = 1;
        Trade trade = new Trade();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = tradeController.updateTrade(id, trade, bindingResult, model);

        assertEquals("redirect:/trade/list", viewName);
        verify(tradeService).updateTrade(id, trade);
    }

    /**
     * Tests the updateTrade method of TradeController when validation fails.
     */
    @Test
    public void testUpdateTradeFailure() {
        Integer id = 1;
        Trade trade = new Trade();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = tradeController.updateTrade(id, trade, bindingResult, model);

        assertEquals("trade/update", viewName);
        verify(tradeService, never()).updateTrade(anyInt(), any(Trade.class));
    }

    /**
     * Tests the deleteTrade method of TradeController.
     */
    @Test
    public void testDeleteTrade() {
        Integer id = 1;

        String viewName = tradeController.deleteTrade(id);

        assertEquals("redirect:/trade/list", viewName);
        verify(tradeService).deleteTrade(id);
    }
}
