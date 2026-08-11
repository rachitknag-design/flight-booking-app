package com.flightbookingapp.service;

import java.math.BigDecimal;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.flightbookingapp.dto.AirLine;
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

	public ResponseStructure<List<Flight>> getFlightByAirline(AirLine airLine) {
		List<Flight> fetchedFlights = flightRepository.findByAirLine(airLine);
		ResponseStructure<List<Flight>> res = new ResponseStructure<>();
		
		if(fetchedFlights.isEmpty()) {
			throw new ResourceNotFoundException("No flights present for airline "+airLine+".");
		} else {
			res.setData(fetchedFlights);
			res.setMessage("All flights from "+airLine+" fetched successfully.");
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}
	}

	public ResponseStructure<List<Flight>> getFlightBetweenRange(BigDecimal start,BigDecimal end) {
		
		
		if(start.compareTo(end)>0) {
			throw new InvalidDataException("THe start range value should be less than end range.");
		}
		
		List<Flight> fetchedFlights = flightRepository.findByPriceBetween(start, end);
		ResponseStructure<List<Flight>> res = new ResponseStructure<List<Flight>>();
		if(fetchedFlights.isEmpty()) {
			throw new ResourceNotFoundException("No flight availiable between the range "+start+" to "+end+".");
		} else {
			res.setData(fetchedFlights);
			res.setMessage("All flights fetched between the range "+start+" to "+end+".");
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}
	}
}
