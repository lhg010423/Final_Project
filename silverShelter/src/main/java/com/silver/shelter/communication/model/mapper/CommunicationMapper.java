package com.silver.shelter.communication.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.silver.shelter.clubReservation.model.dto.ClubReservation;

@Mapper
public interface CommunicationMapper {
	
	// 게스트룸 예약하기 
	int reservation(ClubReservation reservation);

	// 예약 겹치는지 확인
	int selectReservation(ClubReservation reservation);

}
