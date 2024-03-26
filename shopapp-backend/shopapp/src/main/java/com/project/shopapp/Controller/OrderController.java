package com.project.shopapp.Controller;

import com.project.shopapp.Dto.OrderDto;
import com.project.shopapp.services.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    @PostMapping("")
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody OrderDto orderDto,
            BindingResult result
            ){
        try {
            if (result.hasErrors()){
                List<String> errorMessage =  result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            OrderResponse orderResponse =  orderService.createOrder(orderDto);
            return ResponseEntity.ok(orderResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrder(
            @Valid @PathVariable("user_id" ) Long userId
    ){
        try {
            return ResponseEntity.ok("lay ra danh sach order tu userId");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable long id,
            @Valid @RequestBody OrderDto orderDto
    ){
        return ResponseEntity.ok("cap nhap 1 thong tin moi");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(
            @Valid @PathVariable Long id
    ){
        return ResponseEntity.ok("Order delete successfully");
    }
}
