package com.g.grocery.service;

import com.g.grocery.dto.ItemRequestDTO;
import com.g.grocery.model.Item;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ItemService {

    ResponseEntity<String> addNewItem(ItemRequestDTO requestMap);

    ResponseEntity<List<Item>> getAllItems();

    ResponseEntity<String> updateItem(ItemRequestDTO requestMap);

    ResponseEntity<String> deleteItem(Integer id);
}
