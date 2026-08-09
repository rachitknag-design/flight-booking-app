package com.flightbookingapp.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flightbookingapp.dto.AirLine;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer flightId;
	
	@Enumerated(EnumType.STRING)
	private AirLine airLine;
	
	private String source;
	private String destination;
	
	private LocalDateTime departureDateTime;
	private LocalDateTime arrivalDateTime;
	
	private Integer totalSeats;
	private Integer availableSeats;
	private BigDecimal price;
	
	@JsonIgnore
	@ToString.Exclude
	@OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
	private List<Booking> bookings;
}
