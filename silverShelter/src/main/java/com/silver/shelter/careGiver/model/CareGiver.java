package com.silver.shelter.careGiver.model;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CareGiver {
	private int caregiversNo;            // 요양사 번호 
	private String caregiversName;		 // 이름
    private String caregiversAge;        // 나이
    private String caregiversGender;     // 성별
    private String caregiversTel;        // 전화번호
    private String caregiversExperience; // 경력
    private String caregiversWorkHours;  // 근무가능시간
    private String caregiversRole;       // 담당업무
}
