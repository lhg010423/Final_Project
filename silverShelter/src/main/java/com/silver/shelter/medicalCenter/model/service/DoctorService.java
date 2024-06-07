package com.silver.shelter.medicalCenter.model.service;

import java.util.Date;
import java.util.List;

import com.silver.shelter.medicalCenter.model.dto.Doctor;

public interface DoctorService {

	List<Doctor> getDoctorsByMajorName(String majorName);

	List<Date> getDateByDoctorName(String resDoctorName);

}
