package com.flightbookingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightbookingapp.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
