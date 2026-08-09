package com.flightbookingapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ResponseStructure<T> {
	private int statusCode;
	private String message;
	private T data;
}
