package com.saga.choreography.pattern.repository;

import com.saga.choreography.pattern.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<PurchaseItem,Integer> {
}
