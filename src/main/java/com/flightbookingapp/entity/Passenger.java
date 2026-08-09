package com.flightbookingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flightbookingapp.dto.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
public class Passenger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer passengerId;
	
	private String name;
	private Integer age;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private String seatNumber;
	
	@Column(length = 10)
	private String contactNumber;
	
	@JsonIgnore
	@JoinColumn(name = "booking_Id")
	@ToString.Exclude
	@ManyToOne
	private Booking booking;
}


