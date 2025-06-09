package com.example.pasir_nizio_patryk.repository;

import com.example.pasir_nizio_patryk.model.Transaction;
import com.example.pasir_nizio_patryk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}
