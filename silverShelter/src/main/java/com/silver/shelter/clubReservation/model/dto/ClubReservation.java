package com.silver.shelter.clubReservation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ClubReservation {

	private int clubCode; // 클럽 코드 
	private int clubResvNo; // 
	private int memberNo; 
	private String clubResvTime;  
	private String clubLocation;
	private String clubName;
	
}
