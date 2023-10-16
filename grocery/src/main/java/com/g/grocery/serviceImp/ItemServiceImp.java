package com.g.grocery.serviceImp;

import com.g.grocery.constants.GroceryConstants;
import com.g.grocery.dao.ItemDao;
import com.g.grocery.dto.ItemRequestDTO;
import com.g.grocery.jwt.JwtFilter;
import com.g.grocery.model.Item;
import com.g.grocery.service.ItemService;
import com.g.grocery.utils.GroceryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItemServiceImp implements ItemService {
    
    @Autowired
    JwtFilter jwtFilter;
    
    @Autowired
    ItemDao itemDao;
    
    @Override
    public ResponseEntity<String> addNewItem(ItemRequestDTO requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                    itemDao.save(getItemFromMap(requestMap, false));
                    return GroceryUtils.getResponseEntity("Item Added Successfully", HttpStatus.OK);
            }
            else{
                return GroceryUtils.getResponseEntity(GroceryConstants.UNAUTHORISED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GroceryUtils.getResponseEntity(GroceryConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private Item getItemFromMap(ItemRequestDTO requestMap, Boolean isAdd){
        Item item = new Item();
        if(isAdd){
            item.setItemId(requestMap.getItemId());
        }
        item.setItemName(requestMap.getItemName());
        item.setItemPrice(Long.valueOf(requestMap.getItemPrice()));
        item.setItemQuantity(requestMap.getItemQuantity());
        item.setTotalQuantityAvailable(Long.valueOf(requestMap.getTotalQuantityAvailable()));
        return item;
    }

    @Override
    public ResponseEntity<List<Item>> getAllItems() {
        try{
            return new ResponseEntity<>(itemDao.getAllItem(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateItem(ItemRequestDTO requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                    Optional<Item> opt= itemDao.findById(Integer.valueOf(requestMap.getItemId()));
                    if(opt.isPresent()) {
                        Item item = getItemFromMap(requestMap, true);
                        itemDao.save(item);
                        return GroceryUtils.getResponseEntity("Item Updated Successfully", HttpStatus.OK);
                    }else{
                        return GroceryUtils.getResponseEntity("Item Id doesn't exist", HttpStatus.OK);
                    }
                }
            else{
                return GroceryUtils.getResponseEntity(GroceryConstants.UNAUTHORISED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return GroceryUtils.getResponseEntity(GroceryConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteItem(Integer id) {
        try{
            if(jwtFilter.isAdmin()){
                Optional op = itemDao.findById(id);
                if(op.isPresent()){
                    itemDao.deleteById(id);
                    return GroceryUtils.getResponseEntity("Item Deleted Successfully", HttpStatus.OK);
                }else{
                    return GroceryUtils.getResponseEntity("Item Id doesn't exist", HttpStatus.OK);
                }
            }else{
                return GroceryUtils.getResponseEntity(GroceryConstants.UNAUTHORISED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return GroceryUtils.getResponseEntity(GroceryConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
