package com.flightbookingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbookingapp.dto.ResponseStructure;
import com.flightbookingapp.entity.Booking;
import com.flightbookingapp.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<Booking>> createBooking(@RequestBody Booking booking) {
		return new ResponseEntity<ResponseStructure<Booking>>(bookingService.createBooking(booking), HttpStatus.CREATED);
	}
}
