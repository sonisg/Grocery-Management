package com.g.grocery.dao;

import com.g.grocery.model.Order;
import com.g.grocery.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDao extends JpaRepository<OrderItem, Integer> {
}
