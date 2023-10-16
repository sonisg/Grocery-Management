package com.g.grocery.rest;

import com.g.grocery.dto.ItemRequestDTO;
import com.g.grocery.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/item")
public interface ItemRest {

    @PostMapping(path ="/add")
    ResponseEntity<String> addNewItem(@RequestBody(required = true) ItemRequestDTO itemRequestDTO);

    @GetMapping(path = "/get")
    ResponseEntity<List<Item>> getAllItems();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateItem(@RequestBody(required = true) ItemRequestDTO requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteItem(@PathVariable Integer id);

}
