package com.silver.shelter.careGiver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.silver.shelter.careGiver.service.CareGiverService;

@RestController
public class CareGiverController {
	
//    @Autowired
//    private CareGiverService careGiverService;
//    
//    @PostMapping("/medicalCenter/careGivers")
//    public String handleCareGiversRequest(@RequestBody String formData) {
//        // 전송된 JSON 데이터를 파싱하여 필요한 작업을 수행합니다.
//        // 이 부분에 요양사 매칭 알고리즘을 구현합니다.
//    	String recommendation = careGiverService.getRecommendation(formData);
//        System.out.println("Received JSON data: " + formData);
//        
//        
//        // 클라이언트로 응답을 보냅니다. (예: 처리 결과를 JSON 형식으로 응답)
//        return "{\"message\": \"Data received successfully\"}";
//    }
}