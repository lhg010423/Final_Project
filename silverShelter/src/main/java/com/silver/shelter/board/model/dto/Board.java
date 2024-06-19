package com.silver.shelter.board.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {

	private int boardNo;            // 게시글 번호
	private String boardTitle;      // 게시글 제목
	private String boardContent;    // 게시글 내용
	private String boardWriteDate;  // 게시글 작성일
	private String boardUpdateDate; // 게시글 수정일
	
	private int readCount;
	private String boardDelFl;
	private int likeCount;
	private String boardYn;
	private int boardCode;
	private int memberNo;
	private String memberName;     // 작성자
	private int memberRole;        //
	private int cp; // 현제페이지
	
	
	// 특정 게시글의 댓글 목록
	private List<Comment> commentList;
}
