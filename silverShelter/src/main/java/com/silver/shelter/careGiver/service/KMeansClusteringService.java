package com.silver.shelter.careGiver.service;
import com.silver.shelter.careGiver.mapper.CaregiverMapper;
import com.silver.shelter.careGiver.model.CareGiver;
import com.silver.shelter.careGiver.model.SurveyForm;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KMeansClusteringService {
    
	 private final CaregiverMapper careGiverMapper;

	    public KMeansClusteringService(CaregiverMapper careGiverMapper) {
	        this.careGiverMapper = careGiverMapper;
	    }

	    @Transactional(readOnly = true)
	    public List<CareGiver> getAllCareGiversFromDatabase() {
	        return careGiverMapper.getAllCareGivers();
	    }
	    
	public int getMatchingCareGivers(List<CareGiver> clusterCentroids, SurveyForm surveyForm) {
	    String gender = surveyForm.getGender();
	    String age = surveyForm.getAge();
	    String experience = surveyForm.getExperience();
	    String workTime = surveyForm.getWorkTime();
	    String role = surveyForm.getRole();
	    
	    // 각 클러스터에서 속성 일치 개수를 저장할 맵
	    Map<Integer, Integer> matchCountMap = new HashMap<>();
	    // 각 클러스터에서 SurveyForm과 일치하는 CareGiver 수를 계산
	    int i = 0;
	    for (CareGiver centroid : clusterCentroids) {
	        int count = 0;

	        if (centroid.getCaregiversGender().equals(gender)) {
	            count += 100000;
	        }

	        if (centroid.getCaregiversAge().equals(age)) {
	            count += 10000;
	        }

	        if (centroid.getCaregiversExperience().equals(experience)) {
	            count += 100;
	        }

	        if (centroid.getCaregiversWorkHours().equals(workTime)) {
	            count += 1000;
	        }

	        if (centroid.getCaregiversRole().equals(role)) {
	            count += 10;
	        }

	        matchCountMap.put(i, count);
	        i++;
	    }
	    
	    // 일치 개수가 가장 많은 클러스터 인덱스 찾기
	    int maxMatchIndex = -1;
	    int maxMatchCount = 0;
	    for (Map.Entry<Integer, Integer> entry : matchCountMap.entrySet()) {
	        if (entry.getValue() > maxMatchCount) {
	            maxMatchCount = entry.getValue();
	            maxMatchIndex = entry.getKey();
	        }
	    }
	    
	    // 결과 출력
	    System.out.println("Matching CareGivers:");
	    if (maxMatchIndex != -1) {
	        for (CareGiver caregiver : clusterCentroids) {
	            System.out.println(caregiver);
	        }
	        // 가장 많은 일치를 가진 클러스터의 CareGiver들을 반환
	        return maxMatchIndex;
	    } else {
	        System.out.println("No matching caregivers found.");
	        return -1; // 일치하는 클러스터가 없는 경우 빈 리스트 반환
	    }
	}

    








}


