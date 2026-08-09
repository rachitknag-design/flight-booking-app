package com.flightbookingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightbookingapp.entity.Passenger;

public interface PassangerRepository extends JpaRepository<Passenger, Integer> {

}
