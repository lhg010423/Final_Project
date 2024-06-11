package com.silver.shelter.chatting.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.silver.shelter.chatting.model.dto.ChattingRoom;
import com.silver.shelter.chatting.model.dto.Message;
import com.silver.shelter.member.model.dto.Member;

@Mapper
public interface ChattingMapper {

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

	/** 채팅방 번호 체크(기존에 있는지)
	 * @param map
	 * @return
	 */
	int checkChattingNo(Map<String, Integer> map);

	/** 새로운 채팅방 생성
	 * @param map
	 * @return
	 */
	int createChattingRoom(Map<String, Integer> map);

	/** 읽음 표시 업데이트
	 * @param paramMap
	 * @return
	 */
	int updateReadFlag(Map<String, Integer> paramMap);

    /** 메시지 조회
     * @param object
     * @return
     */
    public List<Message> selectMessageList(Object object);
 
    
    /** 채팅 입력
     * @param msg
     * @return
     */
    public int insertMessage(Message msg);

}
