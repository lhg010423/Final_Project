package com.silver.shelter.medicalCenter.model.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.silver.shelter.medicalCenter.model.dto.Doctor;

@Mapper
public interface DoctorMapper {

	List<Doctor> findDoctorsByMajorName(String majorName);

	List<Date> getDateByDoctorName(String resDoctorName);
	

}
