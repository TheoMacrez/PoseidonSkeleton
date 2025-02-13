package com.poseidonskeleton.PoseidonSkeleton.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TradeServiceTest {

    @InjectMocks
    private TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;

    private Trade trade;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Test Account");
        trade.setType("Test Type");
        trade.setBuyQuantity(BigDecimal.valueOf(100.0));
        trade.setSellQuantity(BigDecimal.valueOf(50.0));
        trade.setBuyPrice(BigDecimal.valueOf(10.0));
        trade.setSellPrice(BigDecimal.valueOf(15.0));
        trade.setBenchmark("Test Benchmark");
        trade.setTradeDate(new Timestamp(System.currentTimeMillis()));
        trade.setSecurity("Test Security");
        trade.setStatus("Test Status");
        trade.setTrader("Test Trader");
        trade.setBook("Test Book");
        trade.setCreationName("Test Creator");
        trade.setCreationDate(new Timestamp(System.currentTimeMillis()));
        trade.setRevisionName("Test Revisor");
        trade.setRevisionDate(new Timestamp(System.currentTimeMillis()));
        trade.setDealName("Test Deal");
        trade.setDealType("Test Deal Type");
        trade.setSourceListId("Test Source");
        trade.setSide("Test Side");
    }

    @Test
    public void testCreateTrade() {
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade createdTrade = tradeService.createTrade(trade);

        assertThat(createdTrade).isEqualTo(trade);
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    public void testGetAllTrades() {
        List<Trade> trades = new ArrayList<>();
        trades.add(trade);

        when(tradeRepository.findAll()).thenReturn(trades);

        List<Trade> result = tradeService.getAllTrades();

        assertThat(result).hasSize(1);
        assertThat(result).contains(trade);
    }

    @Test
    public void testGetTradeById() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Optional<Trade> result = tradeService.getTradeById(1);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(trade);
    }

    @Test
    public void testUpdateTrade() {
        when(tradeRepository.existsById(1)).thenReturn(true);
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade updatedTrade = tradeService.updateTrade(1, trade);

        assertThat(updatedTrade).isEqualTo(trade);
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    public void testDeleteTrade() {
        doNothing().when(tradeRepository).deleteById(1);

        tradeService.deleteTrade(1);

        verify(tradeRepository, times(1)).deleteById(1);
    }
}
