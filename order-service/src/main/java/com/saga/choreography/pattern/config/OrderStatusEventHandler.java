package com.saga.choreography.pattern.config;

import com.saga.choreography.pattern.dto.OrderRequestDto;
import com.saga.choreography.pattern.entity.PurchaseItem;
import com.saga.choreography.pattern.event.OrderStatus;
import com.saga.choreography.pattern.event.PaymentStatus;
import com.saga.choreography.pattern.repository.OrderRepository;
import com.saga.choreography.pattern.service.OrderStatusPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Configuration
public class OrderStatusEventHandler {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderStatusPublisher publisher;
    @Transactional
    public void updateOrder(int id, Consumer<PurchaseItem> consumer){
         orderRepository.findById(id).ifPresent(consumer.andThen(this::updateOrder));
    }

    private void updateOrder(PurchaseItem purchaseItem) {
        boolean isCompleted=PaymentStatus.PAYMENT_COMPLETED.equals(purchaseItem.getPaymentStatus());
        OrderStatus status=isCompleted? OrderStatus.ORDER_COMPLETED:OrderStatus.ORDER_FAILED;
        purchaseItem.setOrderStatus(status);
        if (!isCompleted){
            publisher.publishOrderEvent(convertEntityToDto(purchaseItem),status);
        }
    }
    private OrderRequestDto convertEntityToDto(PurchaseItem item){
        OrderRequestDto dto=new OrderRequestDto();
        dto.setOrderId(item.getId());
        dto.setUserId(item.getUserId());
        dto.setAmount(item.getPrice());
        dto.setProductId(item.getProductId());
        return dto;
    }
}





