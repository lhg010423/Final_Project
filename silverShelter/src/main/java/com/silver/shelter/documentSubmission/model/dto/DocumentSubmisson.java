package com.silver.shelter.documentSubmission.model.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentSubmisson {

	// Step 2 fields
	// 방 이름 			
    private String selectedRoom;
    // 방 수 
    private String selectedOccupants;

    // Step 3 fields (files)
    // 제출용 서류 
    
    // 건강검진 기록부 
    private MultipartFile healthCheckup;
    // 가족관계 증명서 
    private MultipartFile familyRelationship;
    // 주미등록본
    private MultipartFile residentRegistration;
    // 신분증 카피본 
    private MultipartFile idCardCopy;

    // Step 4 fields
    // 유저 이름
    private String contactName;
    // 유저 휴대폰 번호
    private String contactPhone;
    // 유저 이메일
    private String contactEmail;
}
