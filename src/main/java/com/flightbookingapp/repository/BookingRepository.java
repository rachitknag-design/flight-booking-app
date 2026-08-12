package com.flightbookingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightbookingapp.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
