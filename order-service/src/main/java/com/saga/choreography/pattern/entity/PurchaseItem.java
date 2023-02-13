package com.saga.choreography.pattern.entity;

import com.saga.choreography.pattern.event.OrderStatus;
import com.saga.choreography.pattern.event.PaymentStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItem {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer price;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
