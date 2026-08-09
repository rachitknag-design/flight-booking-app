package com.flightbookingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightbookingapp.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
