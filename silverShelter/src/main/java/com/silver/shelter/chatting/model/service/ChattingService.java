package com.silver.shelter.chatting.model.service;

import java.util.List;
import java.util.Map;

import com.silver.shelter.chatting.model.dto.ChattingRoom;
import com.silver.shelter.chatting.model.dto.Message;
import com.silver.shelter.member.model.dto.Member;

public interface ChattingService {
	
	/** 채팅방 목록 조회
	 * @param memberNo
	 * @return
	 */
	List<ChattingRoom> selectRoomList(int memberNo);

	/** 채팅 상대 검색
	 * @param map
	 * @return
	 */
	List<Member> selectTarget(Map<String, Object> map);

	/** 채팅방번호 체크
	 * @param map
	 * @return
	 */
	int checkChattingNo(Map<String, Integer> map);

	/** 새 채팅방 생성
	 * @param map
	 * @return
	 */
	int createChattingRoom(Map<String, Integer> map);

	/** 읽음 표시 업데이트
	 * @param paramMap
	 * @return
	 */
	int updateReadFlag(Map<String, Integer> paramMap);

    /** 메세지 조회
     * @param paramMap
     * @return
     */
    List<Message> selectMessageList( Map<String, Integer> paramMap );

	/** 채팅 입력
     * @param msg
     * @return
     */
    int insertMessage(Message msg);

}
