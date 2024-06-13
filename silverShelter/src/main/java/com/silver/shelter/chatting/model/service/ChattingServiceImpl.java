package com.silver.shelter.chatting.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.silver.shelter.chatting.model.dto.ChattingRoom;
import com.silver.shelter.chatting.model.dto.Message;
import com.silver.shelter.chatting.model.mapper.ChattingMapper;
import com.silver.shelter.member.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChattingServiceImpl implements ChattingService{
	
	private final ChattingMapper mapper; 

	// 채팅방 목록 조회
	@Override
	public List<ChattingRoom> selectRoomList(int memberNo) {

		return mapper.selectRoomList(memberNo);
	}
	
	// 채팅 상대 검색
	@Override
	public List<Member> selectTarget(Map<String, Object> map) {
		return mapper.selectTarget(map);
	}

	@Override
	public int checkChattingNo(Map<String, Integer> map) {
		
		return mapper.checkChattingNo(map);
	}

	@Override
	public int createChattingRoom(Map<String, Integer> map) {
		int result = mapper.createChattingRoom(map);
		
		if(result > 0) {
			return (int)map.get("chattingNo");
		}
		
		return 0;
	}

	// 읽음 표시 업데이트
	@Override
	public int updateReadFlag(Map<String, Integer> paramMap) {
		return mapper.updateReadFlag(paramMap);
	}

    // 채팅 메세지 조회
    @Override
    public List<Message> selectMessageList( Map<String, Integer> paramMap) {
        
        List<Message> messageList = mapper.selectMessageList(  paramMap.get("chattingNo") );
        
        if(!messageList.isEmpty()) { // 메시지 목록이 있다면
            int result = mapper.updateReadFlag(paramMap);
        }
        return messageList;
    }
    
	// 채팅 입력
	@Override
	public int insertMessage(Message msg) {
		return mapper.insertMessage(msg);
	}

}
