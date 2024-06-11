package com.silver.shelter.medicalCenter.model.service;

import java.util.Date;
import java.util.List;

import com.silver.shelter.clubReservation.model.dto.ClubReservation;
import com.silver.shelter.medicalCenter.model.dto.Doctor;
import com.silver.shelter.medicalCenter.model.dto.DoctorAppointment;

public interface DoctorService {

	List<Doctor> getDoctorsByMajorName(String majorName);

	List<Date> getDateByDoctorName(String resDoctorName);

	int getNoByDoctorName(String string);

	int doctorReservation(DoctorAppointment reservation);

	String getDrRoomByDoctorNo(int docNo);

}
