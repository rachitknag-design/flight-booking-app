package com.flightbookingapp.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.flightbookingapp.dto.ResponseStructure;
import com.flightbookingapp.dto.SearchFlightBySourceAndDestinationDto;
import com.flightbookingapp.entity.Flight;
import com.flightbookingapp.exception.InvalidDataException;
import com.flightbookingapp.exception.ResourceNotFoundException;
import com.flightbookingapp.repository.FlightRepository;

@Service
public class FlightService {
	
	@Autowired
	private FlightRepository flightRepository;

	public ResponseStructure<Flight> saveFlight(Flight flight) {
		if(flight.getFlightId()!=null) {
			throw new InvalidDataException("Id must not be provide to create a new flight record.");
		}
		
		ResponseStructure<Flight> res = new ResponseStructure<Flight>();
		res.setData(flightRepository.save(flight));
		res.setMessage("FLight record saved successfully.");
		res.setStatusCode(HttpStatus.OK.value());
		
		return res;
	}

	public ResponseStructure<List<Flight>> getAllFlights() {
		List<Flight> flights = flightRepository.findAll();
		ResponseStructure<List<Flight>> res = new ResponseStructure<List<Flight>>();
		
		if(flights.isEmpty()) {
			throw new ResourceNotFoundException("No flight record exist in DB."); 
		} else {
			res.setData(flights);
			res.setMessage("All flight record fetched successfully.");
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}
	}

	public  ResponseStructure<Flight> getFlightById(Integer flightId) {
		
		if(flightId==null) {
			throw new InvalidDataException("Id can't be null to search by Id.");
		}
		
		Flight fetchedFlight = flightRepository.findById(flightId)
				.orElseThrow(()-> new ResourceNotFoundException("Flight record with id "+flightId+" doesn't exists."));
		
		ResponseStructure<Flight> res = new ResponseStructure<Flight>();
		res.setData(fetchedFlight);
		res.setMessage("Flight record with id "+flightId+" fetched successfully.");
		res.setStatusCode(HttpStatus.OK.value());
		
		return res;
	}

	public ResponseStructure<List<Flight>> searchFlightBySourceAndDestination(SearchFlightBySourceAndDestinationDto body) {
		
		
		if(body==null) {
			throw new InvalidDataException("Source and destination must be passed to search flights.");
		}
		
		if(body.getSource()==null) {
			throw new InvalidDataException("Source must be provided to search the flights.");
		}
		
		if(body.getDestination()==null) {
			throw new InvalidDataException("Destination must be provided to search the flights.");
		}
		
		List<Flight> fetchedFlights = flightRepository.findBySourceAndDestination(body.getSource(), body.getDestination());
		ResponseStructure<List<Flight>> res = new ResponseStructure<List<Flight>>();
		if(fetchedFlights.isEmpty()) {
			throw new ResourceNotFoundException("No Flight found between "+body.getSource()+" and "+body.getDestination()+".");
		} else {
			res.setData(fetchedFlights);
			res.setMessage("All fetched between "+body.getSource()+" and "+body.getDestination()+".");
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}
	}

}
