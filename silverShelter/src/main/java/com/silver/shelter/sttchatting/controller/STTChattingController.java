package com.silver.shelter.sttchatting.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.silver.shelter.chatting.model.dto.Message;
import com.silver.shelter.chatting.model.service.ChattingService;
import com.silver.shelter.sttchatting.model.service.SpeachToTextService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@PropertySource("classpath:/config.properties")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("sttChatting")
public class STTChattingController {
	
	@Value("${my.stt.client.id}")
	private String clientId;

	@Value("${my.stt.client.secret}")
	private String clientSecret;
	
	@Value("${my.stt.upload.path}")
	private String uploadPath;
	
	@Value("${my.tts.download.path}")
	private String downloadPath;
	
	private final ChattingService service;
	
	private final SpeachToTextService speachToTextService;
	
	@ResponseBody
	@PostMapping("stt")
	public String fileUpload(@RequestParam("upload")MultipartFile upload,
							 HttpServletRequest req) {
        // 업로드된 파일의 원본명 저장
		String filename = upload.getOriginalFilename();
		
		// 파일이 저장될 경로를 설정
		String filepath = uploadPath + filename; 
		
		try {
			// 출력 스트림 향상 후 업로드된 파일 쓰기
			BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
			os.write(upload.getBytes());
			os.close(); // 스트림 닫기
			
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		
		String resp = speachToTextService.stt(filepath, clientId, clientSecret);
		
		log.info("resp {}",resp);
		
		return resp;
	}

	@ResponseBody
	@PostMapping("sendMessage")
	public int sendMessage(@RequestBody Map<String, Object>parmaMap)  {
		
		Message message = new Message();
		
		message.setChattingNo(Integer.parseInt(parmaMap.get("chattingNo").toString()));
        message.setSenderNo(Integer.parseInt(parmaMap.get("senderNo").toString()));
        message.setMessageContent((String) parmaMap.get("messageContent"));
		
		log.info("어떻게 넘어 오는 중이니? "+message);
		int result = speachToTextService.insertMessage(message);
		
		if(result > 0) {
			return 1;
		
		} else {
			
			return 0;
		}

	}
}
