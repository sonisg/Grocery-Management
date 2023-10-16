package com.g.grocery.serviceImp;

import com.g.grocery.constants.GroceryConstants;
import com.g.grocery.dao.ItemDao;
import com.g.grocery.dao.OrderDao;
import com.g.grocery.dto.OrderCreateRequestDTO;
import com.g.grocery.dto.OrderItemCreateRequestDTO;
import com.g.grocery.model.Item;
import com.g.grocery.model.Order;
import com.g.grocery.model.OrderItem;
import com.g.grocery.service.OrderService;
import com.g.grocery.utils.GroceryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    ItemDao itemDao;

        @Transactional
        public ResponseEntity<String> createOrder(OrderCreateRequestDTO request) {
            // Create a new Order
            Order order = new Order();
            order.setUserName(request.getUserName());
            order.setContactNumber(request.getContactNumber());
            order.setPaymentMethod(request.getPaymentMethod());

            // Calculate the total amount and add items to the order
            long totalAmount = 0;
            List<OrderItem> orderItems = new ArrayList<>();
            for (OrderItemCreateRequestDTO itemRequest : request.getOrderItems()) {
                Item item = itemDao.getItemById(itemRequest.getItemId());
                if(item.getTotalQuantityAvailable() == 0){
                    return GroceryUtils.getResponseEntity(GroceryConstants.ITEM_NOT_AVAILABLE, HttpStatus.BAD_REQUEST);
                }
                int quantity = itemRequest.getQuantity();
                totalAmount += item.getItemPrice() * quantity;

                OrderItem orderItem = new OrderItem();
                orderItem.setItem(item);
                orderItem.setQuantity(quantity);
                orderItem.setOrder(order);
                orderItems.add(orderItem);

                // Dec total quantity of item to maintain inventory
                Long itemQuantityLeft = item.getTotalQuantityAvailable() - quantity;
                itemDao.updateTotalItemQuantity(item.getItemId(), itemQuantityLeft);
            }

            order.setTotalAmount(totalAmount);
            order.setOrderItems(orderItems);

            // Save the order
            orderDao.save(order);
            return GroceryUtils.getResponseEntity("Order Processed Successfully", HttpStatus.OK);
        }
    }

