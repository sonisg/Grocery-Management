package com.g.grocery.service;
import com.g.grocery.dto.OrderCreateRequestDTO;
import com.g.grocery.model.Order;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    ResponseEntity<String> createOrder(OrderCreateRequestDTO orderRequest);
}
