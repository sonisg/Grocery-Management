package com.g.grocery.restImp;
import com.g.grocery.constants.GroceryConstants;
import com.g.grocery.dto.OrderCreateRequestDTO;
import com.g.grocery.model.Order;
import com.g.grocery.rest.OrderRest;
import com.g.grocery.service.OrderService;
import com.g.grocery.utils.GroceryUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderRestImp implements OrderRest {

    @Autowired
    OrderService orderService;

    @Override
    public ResponseEntity<String> createOrder(OrderCreateRequestDTO orderRequest) {
        try{
            log.info("Received request: {}", orderRequest);
            return orderService.createOrder(orderRequest);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return GroceryUtils.getResponseEntity(GroceryConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
    }
}
