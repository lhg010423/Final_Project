// 댓글 목록 조회
const selectCommentList = () => {

    fetch("/comment?boardNo=" + boardNo)
    .then(resp => resp.json())
    .then(commentList => {

        console.log(commentList);


        // 기존 댓글 목록 삭제
        const ul = document.getElementById("commentList");
        ul.innerHTML = "";

        // 댓글 출력하기
        for(let comment of commentList) {

            // 행(li) 생성 + 클래스 추가
            const commentRow = document.createElement("li");
            commentRow.classList.add("comment-row");

            // 대댓글(자식 댓글)인 경우 "child-comment" 클래스 추가
            if(comment.parentCommentNo != 0) 
                commentRow.classList.add("child-comment");

            // 만약 삭제된 댓글이지만 자식 댓글이 존재하는 경우
            if(comment.commentDelFl == 'Y') 
                commentRow.innerText = "삭제된 댓글 입니다";

            else{ // 삭제되지 않은 댓글

                // 프로필 이미지, 닉네임, 날짜 감싸는 요소
                const commentWriter = document.createElement("p");
                commentWriter.classList.add("comment-writer");

                
                // 닉네임
                const nickname = document.createElement("span");
                nickname.innerText = comment.memberNickname;
                
                // 날짜(작성일)
                const commentDate = document.createElement("span");
                commentDate.classList.add("comment-date");
                commentDate.innerText = comment.commentWriteDate;

                // 작성자 영역(commentWriter)에 프로필, 닉네임, 날짜 추가
                commentWriter.append(nickname, commentDate);
            
                // 댓글 행에 작성자 영역 추가
                commentRow.append(commentWriter);
            


                // ----------------------------------------------------


                // 댓글 내용 
                const content = document.createElement("p");
                content.classList.add("comment-content");
                content.innerText = comment.commentContent;

                commentRow.append(content); // 행에 내용 추가
            

                // ----------------------------------------------------

                // 버튼 영역
                const commentBtnArea = document.createElement("div");
                commentBtnArea.classList.add("comment-btn-area");


                // 답글 버튼
                const childCommentBtn = document.createElement("button");
                childCommentBtn.innerText = "답글";

                // 답글 버튼에 onclick 이벤트 리스너 추가 
                childCommentBtn.setAttribute("onclick", 
                `showInsertComment(${comment.commentNo}, this)`);     
                
                // 버튼 영역에 답글 추가
                commentBtnArea.append(childCommentBtn);


                // 로그인한 회원 번호가 댓글 작성자 번호와 같을 때
                // 댓글 수정/삭제 버튼 출력

                if(loginMemberNo != null && loginMemberNo == comment.memberNo){

                // 수정 버튼
                const updateBtn = document.createElement("button");
                updateBtn.innerText = "수정";

                // 수정 버튼에 onclick 이벤트 리스너 추가 
                updateBtn.setAttribute("onclick", 
                    `showUpdateComment(${comment.commentNo}, this)`); 


                // 삭제 버튼
                const deleteBtn = document.createElement("button");
                deleteBtn.innerText = "삭제";

                // 삭제 버튼에 onclick 이벤트 리스너 추가 
                deleteBtn.setAttribute("onclick", 
                    `deleteComment(${comment.commentNo})`); 


                // 버튼 영역에 수정, 삭제 버튼 추가
                commentBtnArea.append(updateBtn, deleteBtn);
                }

                // 행에 버튼 영역 추가
                commentRow.append(commentBtnArea);

            } // else 끝

            // 댓글 목록(ul)에 행(li) 추가
            ul.append(commentRow);


        }



    })

}