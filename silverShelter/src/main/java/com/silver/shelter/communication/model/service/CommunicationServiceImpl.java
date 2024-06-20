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
	public int reservation(ClubReservation reservation) {

		return mapper.reservation(reservation);
	}
	
	@Override
	public int selectReservation(ClubReservation reservation) {

		return mapper.selectReservation(reservation);
	}
}
