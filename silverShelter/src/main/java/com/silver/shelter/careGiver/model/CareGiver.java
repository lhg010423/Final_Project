package com.silver.shelter.careGiver.model;


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
	private int caregiversNo;
	private String caregiversName;
    private String caregiversAge;
    private String caregiversGender;
    private String caregiversTel;
    private String caregiversExperience;
    private String caregiversWorkHours;
    private String caregiversRole;
}
