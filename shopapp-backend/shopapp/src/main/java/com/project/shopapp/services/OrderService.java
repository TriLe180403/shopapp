package com.project.shopapp.services;

import com.project.shopapp.Dto.OrderDto;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderStatus;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public Order createOrder(OrderDto orderDto) throws Exception {
        //tìm xem user_id có tồn tại ko
        User user = userRepository
                .findById(orderDto.getUserId())
                .orElseThrow(()-> new DataNotFoundException("cannot find  user with id"+orderDto.getUserId()));
        //convert orderDto => order
        //use lib Model Mapper
        //Tạo một luồng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(OrderDto.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDto, order);
        order.setUser(user);
        order.setOrderDate(new Date());//Lấy thời gian hiện tại
        order.setStatus(OrderStatus.PENDING);
        //Kểm tra shipping date phải >= ngày hôm nay
        LocalDate shipppingdate = orderDto.getShippingDate() ==null
                ? LocalDate.now() :orderDto.getShippingDate();
        if (shipppingdate.isBefore(LocalDate.now())){
            throw new DataNotFoundException("date must be at least today");
        }
        order.setShippingDate(shipppingdate);
        order.setActive(true);
        orderRepository.save(order);

        return order;
    }

    @Override
    public Order getOrder(Long id) {
        return null;
    }

    @Override
    public Order updateOrder(Long id, OrderDto orderDto) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }

    @Override
    public List<Order> getAllOrder(Long userId) {
        return null;
    }
}
