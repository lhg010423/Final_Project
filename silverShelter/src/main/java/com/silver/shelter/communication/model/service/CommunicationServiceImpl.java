package com.silver.shelter.communication.model.service;

import org.springframework.stereotype.Service;

import com.silver.shelter.clubReservation.model.dto.ClubReservation;
import com.silver.shelter.communication.model.mapper.CommunicationMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunicationServiceImpl implements CommunicationService{

	private final CommunicationMapper mapper;
	
	/**
	 * 게스트룸 예약 하기 
	 */
	@Override
	public int guestRoomReservation(ClubReservation reservation) {

		return mapper.guestRoomReservation(reservation);
	}
	
	@Override
	public int selectGuestRoomReservation(ClubReservation reservation) {

		return mapper.selectGuestRoomReservation(reservation);
	}
}
