package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling business logic related to Trade entities.
 */
@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Creates a new Trade.
     *
     * @param trade the Trade object to create.
     * @return the created Trade object.
     */
    public Trade createTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    /**
     * Retrieves all Trades.
     *
     * @return a list of all Trades.
     */
    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    /**
     * Retrieves a Trade by its ID.
     *
     * @param id the ID of the Trade to retrieve.
     * @return an Optional containing the Trade if found, otherwise empty.
     */
    public Optional<Trade> getTradeById(Integer id) {
        return tradeRepository.findById(id);
    }

    /**
     * Updates an existing Trade.
     *
     * @param id the ID of the Trade to update.
     * @param trade the Trade object with updated data.
     * @return the updated Trade object if successful, otherwise null.
     */
    public Trade updateTrade(Integer id, Trade trade) {
        if (tradeRepository.existsById(id)) {
            trade.setTradeId(id);
            return tradeRepository.save(trade);
        }
        return null;
    }

    /**
     * Deletes a Trade by its ID.
     *
     * @param id the ID of the Trade to delete.
     */
    public void deleteTrade(Integer id) {
        tradeRepository.deleteById(id);
    }
}
