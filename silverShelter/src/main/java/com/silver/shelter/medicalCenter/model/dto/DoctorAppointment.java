package com.silver.shelter.medicalCenter.model.dto;

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
public class DoctorAppointment {

	private int drApptNo;
	private int memberNo;
	private int doctorNo;
	private String drApptTime;
	private String drApptStatus;
	
}
