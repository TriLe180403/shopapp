package com.project.shopapp.services;

import com.project.shopapp.Dto.OrderDetailDto;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDto orderDetailDto) throws Exception;
    OrderDetail getOrderDetail(Long id);
    OrderDetail updateOrderDetail(Long id, OrderDetailDto orderDetailDto);
    void deleteOrderDetails(Long orderId);
    List<OrderDetail> getOrderDetails(Long orderId);
}
