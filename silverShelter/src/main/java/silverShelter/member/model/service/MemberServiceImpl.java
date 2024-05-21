package silverShelter.member.model.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import silverShelter.member.model.mapper.MemberMapper;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl {

	private final MemberMapper mapper;
	
	
}
