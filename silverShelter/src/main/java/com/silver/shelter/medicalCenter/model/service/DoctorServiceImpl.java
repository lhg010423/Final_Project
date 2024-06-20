package com.silver.shelter.medicalCenter.model.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.silver.shelter.clubReservation.model.dto.ClubReservation;
import com.silver.shelter.medicalCenter.model.dto.Doctor;
import com.silver.shelter.medicalCenter.model.dto.DoctorAppointment;
import com.silver.shelter.medicalCenter.model.mapper.DoctorMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DoctorServiceImpl implements DoctorService{
	
	public final DoctorMapper mapper;
	
	@Override
	public List<Doctor> getDoctorsByMajorName(String majorName) {
			return mapper.findDoctorsByMajorName(majorName);
	}

	@Override
	public List<Date> getDateByDoctorName(String resDoctorName) {
		return mapper.getDateByDoctorName(resDoctorName);
	}


	@Override
	public int getNoByDoctorName(String string) {
		return mapper.getNoByDoctorName(string);
	}

	@Override
	public int doctorReservation(DoctorAppointment reservation) {
		return mapper.doctorReservation(reservation);
	}

	@Override
	public String getDrRoomByDoctorNo(int docNo) {
		return mapper.getDrRoomByDoctorNo(docNo);
	}

	@Override
	public List<DoctorAppointment> getReservationsForDate(DoctorAppointment reservation) {
		return mapper.getReservationsForDate(reservation);
	}

	@Override
	public List<DoctorAppointment> getReservedDates(int memberNo) {
		return mapper.getReservedDates(memberNo);
	}

	@Override
	public String getdrnamebydrno(String doctorNo) {
		return mapper.getdrnamebydrno(doctorNo);
	}

	@Override
	public String getDepByDrNo(String doctorNo) {
		return mapper.getDepByDrNo(doctorNo);
	}
}
