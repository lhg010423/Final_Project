package com.silver.shelter.board.model.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

	private long commentNo;            // 댓글 번호
	private String commentContent;    // 댓글 내용
	private String commentWriteDate;  // 댓글 작성일
	private String commentDelFl;      // 댓글 삭제 여부
	private int boardNo;              // 게시글 번호
	private int memberNo;             // 작성자 회원 번호
	private long parentCommentNo;      // 부모댓글 번호
	private int ref;                  // 댓글 그룹
	private int refLevel;             // 대댓글의 깊이
	
	
	
	// JOIN 으로 가져온 값들
	private String memberName;        // 작성자 이름

	
	
	
	private List<Comment> childComments = new ArrayList<>();
	
	
	public void addChildComment(Comment childComment) {
		this.childComments.add(childComment);
	}
	
	
	
}
