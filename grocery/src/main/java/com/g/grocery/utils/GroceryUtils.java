package com.g.grocery.utils;

import com.g.grocery.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GroceryUtils {

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}", httpStatus);
    }
}
