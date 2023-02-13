package com.saga.choreography.pattern.config;

import com.saga.choreography.pattern.event.PaymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EventConsumerConfig {
    @Autowired
    private OrderStatusEventHandler handler;
    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer(){
        //listen to paymentevent topic
        //will check payment status
        //if payment status -completed then complete the order
        //if payment status -failed them cancel the order
      return (payment) -> handler.updateOrder(payment.getPaymentRequestDto().getOrderId(),
              po->{
          po.setPaymentStatus(payment.getPaymentStatus());
              });

    }
}
