package com.silver.shelter.myPage.model.service;

import java.util.List;
import java.util.Map;

import com.silver.shelter.clubReservation.model.dto.ClubReservation;

public interface MyPageService {

	List<ClubReservation> selectReserv(int memberNo);

	List<ClubReservation> getReservedDates(int memberNo);

	List<ClubReservation> getReservationsForDate(ClubReservation reservation);

	int myReserUpdate(ClubReservation clubresv);

	/** 예약 삭제 하기 
	 * @param clubReserv
	 * @return
	 */
	int deleteReservation(ClubReservation clubReserv);

}
