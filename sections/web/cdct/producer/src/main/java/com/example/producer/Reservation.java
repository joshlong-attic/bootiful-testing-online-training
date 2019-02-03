package com.example.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

	private String id;
	private String reservationName;
}
