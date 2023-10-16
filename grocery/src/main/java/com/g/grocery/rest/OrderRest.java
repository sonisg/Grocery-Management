package com.g.grocery.rest;
import com.g.grocery.dto.OrderCreateRequestDTO;
import com.g.grocery.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(path = "/orders")
public interface OrderRest {

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderCreateRequestDTO request);
}
