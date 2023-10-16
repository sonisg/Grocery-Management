package com.g.grocery.dao;

import com.g.grocery.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDao extends JpaRepository<Item, Integer> {

    @Query("select i from Item i")
    List<Item> getAllItem();

    @Query("select i from Item i where item_id =:itemId")
    Item getItemById (Integer itemId);

    @Modifying
    @Query("update Item set totalQuantityAvailable =:totalItem where itemId =:itemId")
    void updateTotalItemQuantity (Integer itemId, Long totalItem);
}
