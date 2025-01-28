package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    // Create
    public Trade createTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    // Read all
    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    // Read by ID
    public Optional<Trade> getTradeById(Integer id) {
        return tradeRepository.findById(id);
    }

    // Update
    public Trade updateTrade(Integer id, Trade trade) {
        if (tradeRepository.existsById(id)) {
            trade.setTradeId(id); // Set the ID for the entity to update
            return tradeRepository.save(trade);
        }
        return null; // Or throw an exception
    }

    // Delete
    public void deleteTrade(Integer id) {
        tradeRepository.deleteById(id);
    }
}
