package com.flightbookingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightbookingapp.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
	
}
