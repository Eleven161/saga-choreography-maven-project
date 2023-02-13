package com.saga.choreography.pattern.event;

import com.saga.choreography.pattern.dto.OrderRequestDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;
@NoArgsConstructor
@Data
public class OrderEvent implements Event {
    private OrderRequestDto orderRequestDto;
    private OrderStatus orderStatus;
    private Date eventDate=new Date();
    private UUID eventId=UUID.randomUUID();

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getDate() {
        return eventDate;
    }

    public OrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        this.orderRequestDto = orderRequestDto;
        this.orderStatus = orderStatus;
    }
}
