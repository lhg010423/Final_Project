package com.silver.shelter.medicalCenter.model.service;

import java.util.List;

import com.silver.shelter.medicalCenter.model.dto.Doctor;

public interface DoctorService {

	List<Doctor> getDoctorsByMajorName(String majorName);

}
