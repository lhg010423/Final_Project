package com.silver.shelter.myPage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.silver.shelter.clubReservation.model.dto.ClubReservation;

@Mapper
public interface MyPageMapper {

	List<ClubReservation> selectReserv(int memberNo);

	List<ClubReservation> getReservedDates(int memberNo);

	List<ClubReservation> getReservationsForDate(ClubReservation reservation);

	int myReserUpdate(ClubReservation clubresv);

}
