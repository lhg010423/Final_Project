package com.silver.shelter.careGiver.service;

import com.silver.shelter.careGiver.mapper.CaregiverMapper;
import com.silver.shelter.careGiver.model.CareGiver;
import com.silver.shelter.careGiver.model.SurveyForm;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 클러스터링 서비스 클래스.
 */
@Service
public class ClusteringService {

    private final CaregiverMapper careGiverMapper;

    /**
     * CaregiverMapper를 주입받는 생성자.
     * 
     * @param careGiverMapper CaregiverMapper 객체.
     */
    public ClusteringService(CaregiverMapper careGiverMapper) {
        this.careGiverMapper = careGiverMapper;
    }

    /**
     * 데이터베이스에서 모든 간병인 목록을 조회합니다.
     * 
     * @return 데이터베이스에서 조회된 모든 간병인 목록.
     */
    @Transactional(readOnly = true)
    public List<CareGiver> getAllCareGiversFromDatabase() {
        return careGiverMapper.getAllCareGivers();
    }

    /**
     * 주어진 SurveyForm과 가장 일치하는 클러스터의 간병인을 찾습니다.
     * 
     * @param clusterCentroids 클러스터 중심점 목록.
     * @param surveyForm 조사 양식(SurveyForm) 객체.
     * @return 가장 일치하는 클러스터의 인덱스.
     */
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
