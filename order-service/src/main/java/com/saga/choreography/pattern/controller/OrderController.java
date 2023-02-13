package com.saga.choreography.pattern.controller;

import com.saga.choreography.pattern.dto.OrderRequestDto;
import com.saga.choreography.pattern.entity.PurchaseItem;
import com.saga.choreography.pattern.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/create")
    public PurchaseItem createOrder(@RequestBody OrderRequestDto orderRequestDto){
      return orderService.createOrder(orderRequestDto);
    }
    @GetMapping
    public List<PurchaseItem> getAllOrders(){
        return orderService.getAllOrders();
    }
}
