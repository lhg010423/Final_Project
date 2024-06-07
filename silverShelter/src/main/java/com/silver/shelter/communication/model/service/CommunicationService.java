package com.silver.shelter.communication.model.service;

import com.silver.shelter.clubReservation.model.dto.ClubReservation;

public interface CommunicationService {

	/** 게스트룸 예약하기 
	 * @param reservation
	 * @return
	 */
	int guestRoomReservation(ClubReservation reservation);

	/** 예약 시간 겹치는지 확인
	 * @param reservation
	 * @return
	 */
	int selectGuestRoomReservation(ClubReservation reservation);


}
