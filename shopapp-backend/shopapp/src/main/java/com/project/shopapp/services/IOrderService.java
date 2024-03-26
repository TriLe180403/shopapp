package com.project.shopapp.services;
import com.project.shopapp.Dto.OrderDto;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;


import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDto orderDto) throws Exception;
    Order getOrder(Long id);
    Order updateOrder(Long id, OrderDto orderDto) throws DataNotFoundException;
    void deleteOrder(Long id);
    List<Order> findByUserId(Long userId);
}
