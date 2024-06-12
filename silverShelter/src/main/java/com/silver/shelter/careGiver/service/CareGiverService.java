package com.silver.shelter.careGiver.service;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.stereotype.Service;
import com.silver.shelter.careGiver.model.CareGiver;
import com.silver.shelter.careGiver.model.CaregiverCluster;

@Service
public class CareGiverService {
	public String getRecommendation(String gender, int age, int experience, String workingHours, String roles) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:5000/predict";

        JSONObject requestJson = new JSONObject();
        requestJson.put("gender", gender);
        requestJson.put("age", age);
        requestJson.put("experience", experience);
        requestJson.put("workingHours", workingHours);
        requestJson.put("roles", roles);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    private List<CaregiverCluster> clusters;

    public CareGiverService(List<CaregiverCluster> clusters) {
        this.clusters = clusters;
    }

    public List<CareGiver> recommendCaregivers(CareGiver userData) {
        // 사용자 정보를 기반으로 요양사 추천
        return null;
    }
}
