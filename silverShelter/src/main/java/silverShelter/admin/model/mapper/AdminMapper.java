package silverShelter.admin.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import silverShelter.member.model.dto.Member;

@Mapper
public interface AdminMapper {

	/** 회원 목록 조회
	 * @param cp
	 * @return
	 */
	List<Member> memberAllSelect();

	/** 검색한 회원 목록 조회
	 * @param paramMap
	 * @param cp
	 * @return
	 */
	List<Member> memberSearchSelect(Map<String, Object> paramMap);

	/** 회원 상세 조회
	 * @param member
	 * @return
	 */
	Member adminDetailSelect(Member member);

}
