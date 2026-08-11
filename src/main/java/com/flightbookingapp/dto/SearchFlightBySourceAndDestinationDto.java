package com.flightbookingapp.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchFlightBySourceAndDestinationDto {
	private String source;
	private String destination;
}
