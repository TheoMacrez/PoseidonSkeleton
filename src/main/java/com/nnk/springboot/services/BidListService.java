package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    // Create
    public BidList createBidList(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    // Read all
    public List<BidList> getAllBidLists() {
        return bidListRepository.findAll();
    }

    // Read by ID
    public Optional<BidList> getBidListById(Integer id) {
        return bidListRepository.findById(id);
    }

    // Update
    public BidList updateBidList(Integer id, BidList bidList) {
        // Check if the BidList exists
        if (bidListRepository.existsById(id)) {
            bidList.setBidListId(id); // Set the ID for the entity to update
            return bidListRepository.save(bidList);
        }
        return null; // Or throw an exception
    }

    // Delete
    public void deleteBidList(Integer id) {
        bidListRepository.deleteById(id);
    }
}
