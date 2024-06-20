package com.silver.shelter.medicalCenter.model.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.silver.shelter.medicalCenter.model.dto.Doctor;
import com.silver.shelter.medicalCenter.model.dto.DoctorAppointment;

@Mapper
public interface DoctorMapper {

	List<Doctor> findDoctorsByMajorName(String majorName);

	List<Date> getDateByDoctorName(String resDoctorName);

	int getNoByDoctorName(String string);

	int doctorReservation(DoctorAppointment reservation);

	String getDrRoomByDoctorNo(int docNo);

	List<DoctorAppointment> getReservationsForDate(DoctorAppointment reservation);

	List<DoctorAppointment> getReservedDates(int memberNo);

	String getdrnamebydrno(String doctorNo);

	String getDepByDrNo(String doctorNo);

}
