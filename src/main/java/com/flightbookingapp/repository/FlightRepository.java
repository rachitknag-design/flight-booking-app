package com.flightbookingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightbookingapp.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
	public List<Flight> findBySourceAndDestination(String source, String destination);
}
