package com.silver.shelter.medicalCenter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
	private int doctorNo;
	private int majorCode;
	private String doctorName;
	private String doctorTel;
	private String doctorProfile;
}
