package com.silver.shelter.chatting.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.silver.shelter.chatting.model.dto.ChattingRoom;
import com.silver.shelter.chatting.model.dto.Message;
import com.silver.shelter.chatting.model.service.ChattingService;
import com.silver.shelter.member.model.dto.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("chatting")
public class ChattingController {

	private final ChattingService service;
	
	@GetMapping("list")
	public String chatting(@SessionAttribute("loginMember")Member loginMember,
						   Model model) {
		
		List<ChattingRoom> roomList = service.selectRoomList(loginMember.getMemberNo());
		model.addAttribute("roomList", roomList);
		
		return "chatting/chatting";
	}
	
	// 채팅 상대 검색
    @GetMapping("selectTarget")
    @ResponseBody
	public List<Member> selectTarget(@RequestParam("query")String query,
									 @SessionAttribute("loginMember")Member loginMember){
		
		log.info("어떻게 넘어오는 중? : "+loginMember);
		Map<String, Object> map = new HashMap<>();
		
		map.put("memberNo", loginMember.getMemberNo());
		map.put("query", query);
		
		return service.selectTarget(map);
	}
    
    // 채팅방 목록 조회 - 비동기
    @GetMapping("roomList")
    @ResponseBody
    public List<ChattingRoom> selectRoomList(@SessionAttribute("loginMember") Member loginMember) {
    	return service.selectRoomList(loginMember.getMemberNo());
    }
    
    // 채팅방 입장(없으면 생성)
    @GetMapping("enter")
    @ResponseBody
    public int chattingEnter(@RequestParam("targetNo") int targetNo, 
    						@SessionAttribute("loginMember") Member loginMember) {
     
        Map<String, Integer> map = new HashMap<String, Integer>();
        
        map.put("targetNo", targetNo);
        map.put("loginMemberNo", loginMember.getMemberNo());
        
        // 채팅방번호 체크 서비스 호출 및 반환(기존 생성된 방이 있는지)
        int chattingNo = service.checkChattingNo(map);
        
        // 반환받은 채팅방번호가 0(없다)이라면 생성하기
        if(chattingNo == 0) {
        	
            chattingNo = service.createChattingRoom(map);
        }
        
        return chattingNo;
    }
    
    // 채팅 읽음 표시
    @PutMapping("updateReadFlag")
    @ResponseBody
    public int updateReadFlag(@RequestBody Map<String, Integer> paramMap) {
        return service.updateReadFlag(paramMap);
    }
    
    // 메세지 조회 - 비동기
    @GetMapping("selectMessage")
    @ResponseBody
    public List<Message> selectMessageList(@RequestParam Map<String, Integer> paramMap) {
        return service.selectMessageList(paramMap);
    }
}
