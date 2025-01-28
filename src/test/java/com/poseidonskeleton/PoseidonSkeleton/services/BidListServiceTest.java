package com.poseidonskeleton.PoseidonSkeleton.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BidListServiceTest {

    @InjectMocks
    private BidListService bidListService;

    @Mock
    private BidListRepository bidListRepository;

    private BidList bidList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("Test Account");
        bidList.setBidQuantity(100.0);
        // Initialisez d'autres attributs si nécessaire
    }

    @Test
    public void testCreateBidList() {
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        BidList createdBidList = bidListService.createBidList(bidList);

        assertThat(createdBidList).isEqualTo(bidList);
        verify(bidListRepository, times(1)).save(bidList);
    }

    @Test
    public void testGetAllBidLists() {
        List<BidList> bidLists = new ArrayList<>();
        bidLists.add(bidList);

        when(bidListRepository.findAll()).thenReturn(bidLists);

        List<BidList> result = bidListService.getAllBidLists();

        assertThat(result).hasSize(1);
        assertThat(result).contains(bidList);
    }

    @Test
    public void testGetBidListById() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

        Optional<BidList> result = bidListService.getBidListById(1);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bidList);
    }

    @Test
    public void testUpdateBidList() {
        when(bidListRepository.existsById(1)).thenReturn(true);
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        BidList updatedBidList = bidListService.updateBidList(1, bidList);

        assertThat(updatedBidList).isEqualTo(bidList);
        verify(bidListRepository, times(1)).save(bidList);
    }

    @Test
    public void testDeleteBidList() {
        doNothing().when(bidListRepository).deleteById(1);

        bidListService.deleteBidList(1);

        verify(bidListRepository, times(1)).deleteById(1);
    }
}
