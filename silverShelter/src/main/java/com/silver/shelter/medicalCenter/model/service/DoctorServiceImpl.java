package com.silver.shelter.medicalCenter.model.service;

import java.util.Date;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.silver.shelter.clubReservation.model.dto.ClubReservation;
import com.silver.shelter.medicalCenter.model.dto.Doctor;
import com.silver.shelter.medicalCenter.model.mapper.DoctorMapper;
import com.silver.shelter.member.model.mapper.MemberMapper;
import com.silver.shelter.member.model.service.MemberServiceImpl;
import com.silver.shelter.signUp.model.mapper.signUpFormMapper;

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
	public int selectDoctorReservation(ClubReservation reservation) {
		return mapper.selectDoctorReservation;
	}

	@Override
	public int doctorReservation(ClubReservation reservation) {
		return 0;
	}
}
