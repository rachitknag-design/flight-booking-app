package com.flightbookingapp.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightbookingapp.dto.AirLine;
import com.flightbookingapp.dto.ResponseStructure;
import com.flightbookingapp.dto.SearchFlightBySourceAndDestinationDto;
import com.flightbookingapp.entity.Flight;
import com.flightbookingapp.service.FlightService;

@RestController
@RequestMapping("/flight")
public class FlightController {
	
	@Autowired
	private FlightService flightService;
	
	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<Flight>> createFlight(@RequestBody Flight flight) {
		return new ResponseEntity<ResponseStructure<Flight>>(flightService.createFlight(flight),HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Flight>>> getAllFlights() {
		return new ResponseEntity<>(flightService.getAllFlights(), HttpStatus.OK);
	}
	
	@GetMapping("/{flightId}")
	public ResponseEntity<ResponseStructure<Flight>> getFlightById(@PathVariable Integer flightId) {
		return new ResponseEntity<ResponseStructure<Flight>>(flightService.getFlightById(flightId),HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<ResponseStructure<List<Flight>>> searchFlightBySourceAndDestination(@RequestBody SearchFlightBySourceAndDestinationDto body) {
		return new ResponseEntity<ResponseStructure<List<Flight>>>(flightService.searchFlightBySourceAndDestination(body),HttpStatus.OK);
	}
	
	@GetMapping("/airline/{airLine}")
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightByAirline(@PathVariable AirLine airLine) {
		return new ResponseEntity<ResponseStructure<List<Flight>>>(flightService.getFlightByAirline(airLine), HttpStatus.OK);
	}
	
	@GetMapping("/range")
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightBetweenRange(@RequestParam BigDecimal startPrice, @RequestParam BigDecimal endPrice) {
		return new ResponseEntity<ResponseStructure<List<Flight>>>(flightService.getFlightBetweenRange(startPrice, endPrice), HttpStatus.OK);
	}

}
