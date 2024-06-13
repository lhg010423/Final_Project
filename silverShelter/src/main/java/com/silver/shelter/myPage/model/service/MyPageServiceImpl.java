package com.silver.shelter.myPage.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.silver.shelter.clubReservation.model.dto.ClubReservation;
import com.silver.shelter.myPage.model.mapper.MyPageMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackFor=Exception.class)
public class MyPageServiceImpl implements MyPageService{
	
	private final MyPageMapper mapper;
	
	@Override
	public List<ClubReservation> selectReserv(int memberNo) {

		return mapper.selectReserv(memberNo);
	}
	
	@Override
	public List<ClubReservation> getReservedDates(int memberNo) {

		return mapper.getReservedDates(memberNo);
	}
	
	@Override
	public List<ClubReservation> getReservationsForDate(ClubReservation reservation) {
		
		return mapper.getReservationsForDate(reservation);
	}
}
