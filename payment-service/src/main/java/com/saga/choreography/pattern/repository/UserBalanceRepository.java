package com.saga.choreography.pattern.repository;

import com.saga.choreography.pattern.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBalanceRepository extends JpaRepository<UserBalance,Integer> {
}
