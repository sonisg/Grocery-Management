package com.g.grocery.restImp;

import com.g.grocery.constants.GroceryConstants;
import com.g.grocery.dto.ItemRequestDTO;
import com.g.grocery.model.Item;
import com.g.grocery.rest.ItemRest;
import com.g.grocery.service.ItemService;
import com.g.grocery.utils.GroceryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ItemRestImp implements ItemRest {

    @Autowired
    ItemService itemService;

    @Override
    public ResponseEntity<String> addNewItem(ItemRequestDTO request) {
        try{
            return itemService.addNewItem(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return GroceryUtils.getResponseEntity(GroceryConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Item>> getAllItems() {
        try{
            return itemService.getAllItems();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateItem(ItemRequestDTO requestMap) {
        try{
            return itemService.updateItem(requestMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return GroceryUtils.getResponseEntity(GroceryConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> deleteItem(Integer id) {
        try{
            return itemService.deleteItem(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GroceryUtils.getResponseEntity(GroceryConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
