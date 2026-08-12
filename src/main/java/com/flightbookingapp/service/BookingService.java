package com.flightbookingapp.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightbookingapp.dto.ResponseStructure;
import com.flightbookingapp.entity.Booking;
import com.flightbookingapp.entity.Flight;
import com.flightbookingapp.entity.Passenger;
import com.flightbookingapp.entity.Payment;
import com.flightbookingapp.exception.IllegalArgumentException;
import com.flightbookingapp.repository.BookingRepository;
import com.flightbookingapp.repository.FlightRepository;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Transactional
	public ResponseStructure<Booking> createBooking(Booking booking) {
		
		//fetch and validate flight existence
		if(booking.getFlight()==null||booking.getFlight().getFlightId()==null) {
			throw new IllegalArgumentException("Flight Id must be provided for booking.");
		}
		Integer flightId = (booking.getFlight().getFlightId()!=null)?booking.getFlight().getFlightId():null;
		if(flightId==null) {
			throw new IllegalArgumentException("Flight Id must be provided for booking.");
		}
		Flight flight = flightRepository.findById(flightId)
				.orElseThrow(()->new IllegalArgumentException("Flight not found with id: "+flightId+"."));
		
		//Validate passengers(Null check & ID check)
		List<Passenger> passengers = booking.getPassengers();
		if(passengers==null||passengers.isEmpty()) {
			throw new IllegalArgumentException("Booking must include atleast 1 passenger.");
		}
		
		for(Passenger passenger : passengers) {
			if(passenger.getPassengerId()!=null) {
				throw new IllegalArgumentException("New Passenger can't have existing passenger Id");
			}
			passenger.setBooking(booking);
		}
		
		//validate seats availability
		Integer requestedSeats = (passengers!=null)?passengers.size():0;
		if(flight.getAvailableSeats()<requestedSeats) {
			throw new IllegalArgumentException("Not enough seat available. Remaining seats: "+flight.getAvailableSeats()+".");
		}
		
		//Validate Payment (Null Check, ID Check & Dynamic Price Calculation)
		Payment payment = booking.getPayment();
		if(payment==null) {
			throw new IllegalArgumentException("Payment details must be provided.");
		}
		
		if(payment.getPaymentId()!=null) {
			throw new IllegalArgumentException("New payment record can't have existing paymentId.");
		}
		
		//Calculate total price: number of passengers * flight price
		BigDecimal totalPrice = flight.getPrice().multiply(BigDecimal.valueOf(requestedSeats));
		payment.setAmount(totalPrice);
		payment.setBooking(booking);
		
		//decrement available seats on flight
		flight.setAvailableSeats(flight.getAvailableSeats()-requestedSeats);
		
		//Associate Managed Flight Entity
		booking.setFlight(flight);
		
		Booking savedBooking = bookingRepository.save(booking);
		
		//create response
		ResponseStructure<Booking> res = new ResponseStructure<Booking>();
		res.setData(savedBooking);
		res.setMessage("Flight booked successfully.");
		res.setStatusCode(HttpStatus.CREATED.value());
		
		return res;
	}

}
