package com.project.shopapp.services;

import com.project.shopapp.Dto.OrderDetailDto;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDto orderDetailDto) throws Exception;
    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;
    OrderDetail updateOrderDetail(Long id, OrderDetailDto orderDetailDto) throws DataNotFoundException;
    void deleteOrderDetails(Long Id);
    List<OrderDetail> findByOrderId(Long orderId);
}
