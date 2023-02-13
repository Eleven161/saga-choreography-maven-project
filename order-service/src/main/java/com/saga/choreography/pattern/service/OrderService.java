package com.saga.choreography.pattern.service;

import com.saga.choreography.pattern.dto.OrderRequestDto;
import com.saga.choreography.pattern.entity.PurchaseItem;
import com.saga.choreography.pattern.event.OrderStatus;
import com.saga.choreography.pattern.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderStatusPublisher publisher;
    @Transactional
    public PurchaseItem createOrder(OrderRequestDto orderRequestDto) {
        PurchaseItem purchaseItem= orderRepository.save(convertDtoToEntity(orderRequestDto));
        orderRequestDto.setOrderId(purchaseItem.getId());
        //produce kafka event with status order-created
        publisher.publishOrderEvent(orderRequestDto,OrderStatus.ORDER_CREATED);
        return purchaseItem;
    }
    public List<PurchaseItem> getAllOrders(){
        return orderRepository.findAll();
    }

    private PurchaseItem convertDtoToEntity(OrderRequestDto orderRequestDto){
        PurchaseItem item=new PurchaseItem();
        item.setProductId(orderRequestDto.getProductId());
        item.setUserId(orderRequestDto.getUserId());
        item.setPrice(orderRequestDto.getAmount());
        item.setOrderStatus(OrderStatus.ORDER_CREATED);
      return item;
    }
}
