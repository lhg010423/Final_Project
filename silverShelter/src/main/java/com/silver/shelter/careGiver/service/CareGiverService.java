package com.silver.shelter.careGiver.service;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.silver.shelter.careGiver.model.CareGiver;

@Service
public class CareGiverService {
//	public String getRecommendation(String gender, int age, int experience, String workingHours, String roles) {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://localhost:5000/predict";
//
//        JSONObject requestJson = new JSONObject();
//        requestJson.put("gender", gender);
//        requestJson.put("age", age);
//        requestJson.put("experience", experience);
//        requestJson.put("workingHours", workingHours);
//        requestJson.put("roles", roles);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> entity = new HttpEntity<>(requestJson.toString(), headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
//
//        return response.getBody();
//    }
//
//	public String getRecommendation(String formData) {
//		// 요양사 추천 로직 구현
//        // AI 모델 호출 및 결과 처리 등
//        return "Best Caregiver recommendation based on the survey data";
//	}
}
