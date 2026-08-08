package com.flightbookingapp.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer paymentId;
	
	@CreationTimestamp
	private LocalDateTime paymentDateTime;
	
	private BigDecimal amount;
	
	@Enumerated(EnumType.STRING)
	private ModeOfPayment modeOfPayment;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
	
	@JsonIgnore
	@OneToOne
	@ToString.Exclude
	@JoinColumn(name = "booking_id", unique = true)
	private Booking booking;
}
