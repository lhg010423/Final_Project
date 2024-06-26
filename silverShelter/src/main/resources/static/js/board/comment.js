// 댓글 목록 조회


const selectCommentList = () => {


// document.querySelectorAll(".boardTitleBtn").forEach(button => {
//     button.addEventListener("click", e => {

    // const boardNo = e.target.dataset.boardNo;

    console.log(boardNo);

    fetch("/board/select?boardNo=" + boardNo)
    .then(resp => resp.json())
    .then(commentMap => {

        console.log(commentMap);

        const commentList = commentMap.commentList;
        const commentCount = commentMap.commentCount;
        const pagination = commentMap.pagination;


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
                const name = document.createElement("span");
                name.innerText = comment.memberName;
                
                // 날짜(작성일)
                const commentDate = document.createElement("span");
                commentDate.classList.add("comment-date");
                commentDate.innerText = comment.commentWriteDate;

                // 작성자 영역(commentWriter)에 프로필, 닉네임, 날짜 추가
                commentWriter.append(name, commentDate);
            
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
selectCommentList();

// 댓글 작성하기
const addComment = document.getElementById("addComment");
const commentContent = document.getElementById("commentContent");

addComment.addEventListener("click", e => {

    if(loginMemberNo == null) {
        alert("로그인 후 이용해 주세요");
        return;
    }

    // 아무내용 안썻을 때
    if(commentContent.value.trim().length == 0) {
        alert("내용 입력후 버튼을 눌러주세요");
        commentContent.focus();
        return;
    }

    const data = {
        "commentContent" : commentContent.value,
        "boardNo"        : boardNo,
        "memberNo"       : loginMemberNo
    };

    fetch("/comment/insert", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(data)
    })
    .then(resp => resp.text())
    .then(result => {


        console.log(result);
        if(result > 0) {
            alert("댓글 등록 완료");
            commentContent.value = "";
            location.href=location.href;
            selectCommentList();
        } else {
            alert("댓글 등록 실패");
        }

    })
    .catch(err => console.log(err));


})


// 답글 작성
const showInsertComment = (parentCommentNo, btn) => {

    // commentInsertContent 이라는 이름의 요소 전부 가져오기
    const temp = document.getElementsByClassName("commentInsertContent");
    
    if(temp.length > 0) {

        if(confirm("다른 답글을 작성 중입니다. 현재 댓글에 답글을 작성 하기겠습니까?")) {
            temp[0].nextElementSibling.remove();
            temp[0].remove();
        } else {
            return;
        }
    }

    // 답글 요소 생성
    const textarea = document.createElement("textarea");
    textarea.classList.add("commentInsertContent");

    // 답글 버튼 뒤에 textarea 추가
    btn.parentElement.after(textarea);


    // 답글 버튼 영역 + 등록/취소 버튼 생성 및 추가
    const commentBtnArea = document.createElement("div");
    commentBtnArea.classList.add("comment-btn-area");

    const insertBtn = document.createElement("button");
    insertBtn.innerText = "등록";
    insertBtn.setAttribute("onclick", "insertChildComment("+parentCommentNo+", this)");

    const cancelBtn = document.createElement("button");
    cancelBtn.innerText = "취소";
    cancelBtn.setAttribute("onclick", "insertCancel(this)");


    commentBtnArea.append(insertBtn, cancelBtn);

    textarea.after(commentBtnArea);

}

// 답글 작성 취소 버튼
const insertCancel = (cancelBtn) => {

    // 취소버튼 앞 textarea 삭제
    cancelBtn.parentElement.previousElementSibling.remove();

    // 취소 버튼이 있는 버튼 영역 삭제
    cancelBtn.parentElement.remove();

}


// 답글 등록
const insertChildComment = (parentCommentNo, btn) => {

    // btn 요소의 부모 요소의 이전 형제 요소를 선택하는 코드
    const textarea = btn.parentElement.previousElementSibling;

    // 유효성 검사
    if(textarea.value.trim().length == 0) {
        alert("내용을 작성한 후 등록 버튼을 눌러주세요");
        textarea.focus();
        return;
    }


    const obj = {
        "commentContent"    : textarea.value,
        "boardNo"           : boardNo,
        "memberNo"          : loginMemberNo,
        "parentCommentNo"   : parentCommentNo
    };

    fetch("/comment/insert", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {

        if(result > 0) {
            alert("답글이 등록 되었습니다");
            selectCommentList();
            location.href=location.href;

        } else {
            alert("답글 등록 실패");
        }
    })
    .catch(err => console.log(err));


}



// 댓글 삭제
const deleteComment = commentNo => {

    // 취소 선택 시
    if(!confirm("삭제 하시겠습니까?")) return;

    fetch("/comment/delete", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : commentNo
    })
    .then(resp => resp.text())
    .then(result => {

        if(result > 0) {
            alert("삭제 되었습니다");
            selectCommentList(); // 삭제한 후 댓글 다시 조회
            location.href=location.href;

        } else {
            alert("삭제 실패");
            //return;
        }
    })
    .catch(err => console.log(err));

}



// 댓글 수정

// 수정 취소 시 원래 댓글 형태로 돌아가기 위한 백업 변수
let beforeCommentRow;

const showUpdateComment = (commentNo, btn) => {

    const temp = document.querySelector(".update-textarea");

    if(temp != null) {

        if(confirm("수정 중인 댓글이 있습니다. 현재 댓글을 수정하시겠습니까?")) {

            const commentRow = temp.parentElement;
            commentRow.after(beforeCommentRow);
            commentRow.remove();

        } else { // 수정 취소
            return;
        }
    }

    // 1. 댓글 수정이 클릭된 행 (.comment-row) 선택
    const commentRow = btn.closest("li"); 

    // 2. 행 전체를 백업(복제)
    // 요소.cloneNode(true) : 요소 복제, 
    //           매개변수 true == 하위 요소도 복제
    beforeCommentRow = commentRow.cloneNode(true);
    // console.log(beforeCommentRow);

    // 3. 기존 댓글에 작성되어 있던 내용만 얻어오기
    let beforeContent = commentRow.children[1].innerText;

    // 4. 댓글 행 내부를 모두 삭제
    commentRow.innerHTML = "";

    // 5. textarea 생성 + 클래스 추가 + 내용 추가
    const textarea = document.createElement("textarea");
    textarea.classList.add("update-textarea");
    textarea.value = beforeContent;

    // 6. 댓글 행에 textarea 추가
    commentRow.append(textarea);

    // 7. 버튼 영역 생성
    const commentBtnArea = document.createElement("div");
    commentBtnArea.classList.add("comment-btn-area");

    // 8. 수정 버튼 생성
    const updateBtn = document.createElement("button");
    updateBtn.innerText = "수정";
    updateBtn.setAttribute("onclick", `updateComment(${commentNo}, this)`);

    // 9. 취소 버튼 생성
    const cancelBtn = document.createElement("button");
    cancelBtn.innerText = "취소";
    cancelBtn.setAttribute("onclick", "updateCancel(this)");

    // 10. 버튼 영역에 수정/취소 버튼 추가 후
    //     댓글 행에 버튼 영역 추가
    commentBtnArea.append(updateBtn, cancelBtn);
    commentRow.append(commentBtnArea);

}


// 댓글 수정 취소
const updateCancel = (btn) => {

    if(confirm("취소 하시겠습니까?")) {
        const commentRow = btn.closest("li");
        commentRow.after(beforeCommentRow);
        commentRow.remove();
    }

}



// 댓글 수정
const updateComment = (commentNo, btn) => {

    // 수정된 내용이 작성된 textarea 얻어오기
    const textarea = btn.parentElement.previousElementSibling;
  
    // 유효성 검사
    if(textarea.value.trim().length == 0){
      alert("댓글 작성 후 수정 버튼을 클릭해 주세요");
      textarea.focus();
      return;
    }
  
    // 댓글 수정 (ajax)
    const data = {
      "commentNo" : commentNo,
      "commentContent" : textarea.value
    }
  
    fetch("/comment/update", {
      method : "POST",
      headers : {"Content-Type" : "application/json"},
      body : JSON.stringify(data)
    })
    .then(resp => resp.text())
    .then(result => {
      if(result > 0){
        alert("댓글이 수정 되었습니다");
        selectCommentList();
        location.href=location.href;

      } else {
        alert("댓글 수정 실패");
        //return;
      }
  
    })
    .catch(err => console.log(err));
}























