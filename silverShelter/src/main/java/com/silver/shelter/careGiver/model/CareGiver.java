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
    private String gender;
    private String age;
    private String experience;
    private String workTime;
    private String role;
}
