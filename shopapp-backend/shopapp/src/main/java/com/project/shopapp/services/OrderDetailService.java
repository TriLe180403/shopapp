package com.project.shopapp.services;

import com.project.shopapp.Dto.OrderDetailDto;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.OrderDetailRepository;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService{
    public final OrderRepository orderRepository;
    public final ProductRepository productRepository;
    public final OrderDetailRepository orderDetailRepository;
    @Override
    public OrderDetail createOrderDetail(OrderDetailDto orderDetailDto) throws Exception {
        //tim xem orderId co ton tai hay ko
        Order order = orderRepository.findById(orderDetailDto.getOrderId()).orElseThrow(()->
                new DataNotFoundException(
                        "cannot find order with id "+orderDetailDto.getOrderId()));
        //tim product theo id
        Product product = productRepository.findById(orderDetailDto.getProductId()).orElseThrow(()->
                new DataNotFoundException(
                        "cannot find order with id "+orderDetailDto.getProductId()));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDto.getNumberOfProducts())
                .totalMoney(orderDetailDto.getTotalMoney())
                .price(orderDetailDto.getPrice())
                .color(orderDetailDto.getColor())
                .build();
        //luu vao db
        return orderDetailRepository.save(orderDetail);

    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {

        return orderDetailRepository.findById(id).orElseThrow(()->
                new DataNotFoundException("cannot find orderDetail with id"+id));
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDto orderDetailDto) throws DataNotFoundException {
        //Tìm xem orderDetail có tồn tại hay ko
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("cannot find order detail with id"+id));
        Order existingOrder = orderRepository.findById(orderDetailDto.getOrderId())
                .orElseThrow(()-> new DataNotFoundException("cannot find order with id"+id));
        Product existingProduct = productRepository.findById(orderDetailDto.getProductId()).orElseThrow(()->
                new DataNotFoundException(
                        "cannot find order with id "+orderDetailDto.getProductId()));
        existingOrderDetail.setPrice(orderDetailDto.getPrice());
        existingOrderDetail.setNumberOfProducts(orderDetailDto.getNumberOfProducts());
        existingOrderDetail.setTotalMoney(orderDetailDto.getTotalMoney());
        existingOrderDetail.setColor(orderDetailDto.getColor());
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public void deleteOrderDetails(Long id) {

        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {

        return orderDetailRepository.findByOrderId(orderId);
    }
}
