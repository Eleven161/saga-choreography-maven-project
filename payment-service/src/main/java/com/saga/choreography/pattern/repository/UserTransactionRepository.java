package com.saga.choreography.pattern.repository;

import com.saga.choreography.pattern.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTransactionRepository extends JpaRepository<UserTransaction,Integer> {
}
