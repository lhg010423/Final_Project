package com.silver.shelter.careGiver.service;
import com.silver.shelter.careGiver.model.CareGiver;
import com.silver.shelter.careGiver.model.SurveyForm;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class KMeansClusteringService {
    
    
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

    

    // CSV 파일에서 데이터 읽기 및 전처리
    public List<CareGiver> preprocessData(String csvFile) throws IOException {
        List<CareGiver> caregivers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            // 첫 번째 줄은 헤더일 경우, skip 처리
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                // CSV 파일의 각 줄을 CareGiver 객체로 변환하여 리스트에 추가
                CareGiver caregiver = parseLine(line);
                if (caregiver != null) {
                    caregivers.add(caregiver);
                }
            }
        }

        return caregivers;
    }

    // CSV 한 줄을 CareGiver 객체로 변환하는 메소드
    private CareGiver parseLine(String line) {
        if (line == null || line.isEmpty()) {
            return null;
        }

        String[] fields = line.split(",");
        if (fields.length < 8) {
            return null;
        }

        int caregiversNo = Integer.parseInt(fields[0].trim());
        String caregiversName = fields[1].trim();
        String caregiversAge = fields[2].trim();
        String caregiversGender = fields[3].trim();
        String caregiversTel = fields[4].trim();
        String caregiversExperience = fields[5].trim();
        String caregiversWorkHours = fields[6].trim();
        String caregiversRole = fields[7].trim();

        return new CareGiver(caregiversNo, caregiversName, caregiversAge, caregiversGender,
                             caregiversTel, caregiversExperience, caregiversWorkHours, caregiversRole);
    }







}


