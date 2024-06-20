package com.silver.shelter.admin.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
	
	private String type; // 예약의 유형 (GUESTROOM, SPORTSCENTER, FACILITY, DOCTOR)
    
	private String manager; // 담당자 (의사이름, 그외는 null)
	
    private String resvId; // 접두사가 붙은 예약의 고유 식별자
    
    private Long memberName; // 예약을 한 회원의 고유 식별자
    
    private String reservationDate; // 예약 날짜
    
    private int reservationTime; // 예약 시간
    
//    private Integer reservationCount; // 예약 인원
	
	
	
}
