package com.saga.choreography.pattern.service;

import com.saga.choreography.pattern.dto.OrderRequestDto;
import com.saga.choreography.pattern.dto.PaymentRequestDto;
import com.saga.choreography.pattern.entity.UserBalance;
import com.saga.choreography.pattern.entity.UserTransaction;
import com.saga.choreography.pattern.event.OrderEvent;
import com.saga.choreography.pattern.event.PaymentEvent;
import com.saga.choreography.pattern.event.PaymentStatus;
import com.saga.choreography.pattern.repository.UserBalanceRepository;
import com.saga.choreography.pattern.repository.UserTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentService {
    @Autowired
    private UserBalanceRepository userBalanceRepository;
    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @PostConstruct
    public void initUserBalanceInDb(){
        userBalanceRepository.saveAll(Stream.of(new UserBalance(101,5000),
                new UserBalance(102,4000),new UserBalance(102,2000),
        new UserBalance(103,3000),new UserBalance(104,5000)).collect(Collectors.toList()));
    }/**
     //get the userid
     //check balance
     //if balance sufficient, payment completed and deduct amount from DB
     //if payment failed, cancel the order event and update the amount in DB
     **/
    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDto orderDto=orderEvent.getOrderRequestDto();
        PaymentRequestDto paymentDto=new PaymentRequestDto(orderDto.getOrderId(),
                orderDto.getUserId(),orderDto.getAmount());
        return userBalanceRepository.findById(orderDto.getUserId())
                .filter(userBalance -> userBalance.getAmount()>orderDto.getAmount())
                .map(ub -> {
                    ub.setAmount(ub.getAmount()-orderDto.getAmount());
                    userTransactionRepository.save(new UserTransaction(orderDto.getOrderId(),orderDto.getUserId(),orderDto.getAmount()));
                    return new PaymentEvent(paymentDto, PaymentStatus.PAYMENT_COMPLETED);
                }).orElse(new PaymentEvent(paymentDto,PaymentStatus.PAYMENT_FAILED));

    }
    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId())
                .ifPresent(ut ->{
                    userTransactionRepository.delete(ut);
                    userTransactionRepository.findById(ut.getUserId())
                            .ifPresent(ub->ub.setAmount(ub.getAmount()+ ut.getAmount()));
                } );
    }
}
