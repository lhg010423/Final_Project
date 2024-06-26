package com.silver.shelter.common.sst.auth;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@PropertySource("classpath:/config.properties") // config.properties 파일로부터 프로퍼티를 로드합니다.
@Slf4j
public class AuthService {

    // config.properties 파일로부터 clientId 값을 주입받습니다.
    @Value("${my.stt.client.id}")
    private String clientId;

    // config.properties 파일로부터 clientSecret 값을 주입받습니다.
    @Value("${my.stt.client.secret}")
    private String clientSecret;    
    
    // Access Token을 가져오는 메서드입니다.
    public String getAccessToken() throws IOException, InterruptedException {
    	
    	try {
            // HttpClient 객체를 생성합니다.
            HttpClient client = HttpClient.newHttpClient();

            // POST 요청에 포함될 폼 데이터를 작성합니다. 
            String formBody = "client_id={YOUR_CLIENT_ID}&client_secret={YOUR_CLIENT_SECRET}";
            
            log.info("STT가 문제임1");

            // HttpRequest 객체를 생성합니다. URI와 헤더, 그리고 폼 데이터를 설정합니다.
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://openapi.vito.ai/v1/authenticate")) // 인증 URL
                    .header("Content-Type", "application/x-www-form-urlencoded") // Content-Type 헤더를 설정합니다.
                    .POST(HttpRequest.BodyPublishers.ofString(formBody)) // POST 요청으로 폼 데이터를 전송합니다.
                    .build();
            
            log.info("STT가 문제임2");

            // HttpRequest를 보내고, HttpResponse 객체를 받습니다.
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 응답 본문을 JSON으로 파싱합니다.
            ObjectMapper objectMapper = new ObjectMapper();
            
            @SuppressWarnings("unchecked")
			HashMap<String, String> map = objectMapper.readValue(response.body(), HashMap.class);
            // JSON에서 access_token 값을 추출하여 반환합니다.
            return map.get("access_token");
    		
    	} catch(Exception e) {
    		
    		e.printStackTrace();
    		
    		return null; 
    	}

    }
}