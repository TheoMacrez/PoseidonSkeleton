package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling business logic related to BidList entities.
 */
@Service
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    /**
     * Creates a new BidList.
     *
     * @param bidList the BidList object to create.
     * @return the created BidList object.
     */
    public BidList createBidList(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    /**
     * Retrieves all BidLists.
     *
     * @return a list of all BidLists.
     */
    public List<BidList> getAllBidLists() {
        return bidListRepository.findAll();
    }

    /**
     * Retrieves a BidList by its ID.
     *
     * @param id the ID of the BidList to retrieve.
     * @return an Optional containing the BidList if found, otherwise empty.
     */
    public Optional<BidList> getBidListById(Integer id) {
        return bidListRepository.findById(id);
    }

    /**
     * Updates an existing BidList.
     *
     * @param id the ID of the BidList to update.
     * @param bidList the BidList object with updated data.
     * @return the updated BidList object if successful, otherwise null.
     */
    public BidList updateBidList(Integer id, BidList bidList) {
        if (bidListRepository.existsById(id)) {
            bidList.setBidListId(id);
            return bidListRepository.save(bidList);
        }
        return null;
    }

    /**
     * Deletes a BidList by its ID.
     *
     * @param id the ID of the BidList to delete.
     */
    public void deleteBidList(Integer id) {
        bidListRepository.deleteById(id);
    }
}
