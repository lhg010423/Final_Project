document.addEventListener("DOMContentLoaded", function () {

    // 게시글 제목 클릭 시 게시글 정보를 불러오는 이벤트 핸들러
    document.querySelectorAll(".boardTitleBtn").forEach(button => {
        button.addEventListener("click", e => {
            const boardNo = e.target.dataset.boardNo;
            const boardCode = e.target.dataset.boardCode;

            console.log(boardNo);
            console.log(boardCode);

            fetch("/admin/boardList", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({ boardNo: boardNo })
            })
            .then(resp => resp.json())
            .then(result => {
                console.log(result);

                if(result == null) {
                    alert("값 없음");
                    e.preventDefault();
                } else {
                    document.getElementById("titleSpan").innerText = result.boardTitle;
                    document.getElementById("writerSpan").innerText = result.memberName;
                    document.getElementById("writeDateSpan").innerText = result.boardWriteDate;
                    document.getElementById("readCountSpan").innerText = "조회수  " + result.readCount;
                    document.getElementById("contentSpan").innerText = result.boardContent;
                    // loadComments(); // 댓글 조회 함수 호출
                }
            })
            .catch(error => {
                console.log("게시글을 불러오는 중 오류 발생", error);
            });
        });
    });

    // 댓글 목록 조회 함수
    const selectCommentList = () => {
        document.querySelectorAll(".boardTitleBtn").forEach(button => {
            button.addEventListener("click", e => {
                const boardNo = e.target.dataset.boardNo;

                console.log(boardNo);

                fetch("/comment?boardNo=" + boardNo)
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
                        const commentRow = document.createElement("li");
                        commentRow.classList.add("comment-row");

                        if(comment.parentCommentNo != 0) 
                            commentRow.classList.add("child-comment");

                        if(comment.commentDelFl == 'Y') 
                            commentRow.innerText = "삭제된 댓글 입니다";
                        else {
                            const commentWriter = document.createElement("p");
                            commentWriter.classList.add("comment-writer");

                            const name = document.createElement("span");
                            name.innerText = comment.memberName;

                            const commentDate = document.createElement("span");
                            commentDate.classList.add("comment-date");
                            commentDate.innerText = comment.commentWriteDate;

                            commentWriter.append(name, commentDate);
                            commentRow.append(commentWriter);

                            const content = document.createElement("p");
                            content.classList.add("comment-content");
                            content.innerText = comment.commentContent;

                            commentRow.append(content);

                            const commentBtnArea = document.createElement("div");
                            commentBtnArea.classList.add("comment-btn-area");

                            const childCommentBtn = document.createElement("button");
                            childCommentBtn.innerText = "답글";
                            childCommentBtn.setAttribute("onclick", `showInsertComment(${comment.commentNo}, this)`);

                            commentBtnArea.append(childCommentBtn);

                            if(loginMemberNo != null && loginMemberNo == comment.memberNo){
                                const updateBtn = document.createElement("button");
                                updateBtn.innerText = "수정";
                                updateBtn.setAttribute("onclick", `showUpdateComment(${comment.commentNo}, this)`);

                                const deleteBtn = document.createElement("button");
                                deleteBtn.innerText = "삭제";
                                deleteBtn.setAttribute("onclick", `deleteComment(${comment.commentNo})`);

                                commentBtnArea.append(updateBtn, deleteBtn);
                            }

                            commentRow.append(commentBtnArea);
                        }

                        ul.append(commentRow);
                    }
                });
            });
        });
    };

    selectCommentList();

    // 댓글 작성하기
    const addComment = document.getElementById("addComment");
    const commentContent = document.getElementById("commentContent");

    addComment.addEventListener("click", e => {
        if(loginMemberNo == null) {
            alert("로그인 후 이용해 주세요");
            return;
        }

        if(commentContent.value.trim().length == 0) {
            alert("내용 입력 후 버튼을 눌러주세요");
            commentContent.focus();
            return;
        }

        const data = {
            "commentContent": commentContent.value,
            "boardNo": boardNo,
            "memberNo": loginMemberNo
        };

        fetch("/comment", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        })
        .then(resp => resp.text())
        .then(result => {
            console.log(result);
            if(result > 0) {
                alert("댓글 등록 완료");
                commentContent.value = "";
                selectCommentList();
            } else {
                alert("댓글 등록 실패");
            }
        })
        .catch(err => console.log(err));
    });

    // 답글 작성
    const showInsertComment = (parentCommentNo, btn) => {
        const temp = document.getElementsByClassName("commentInsertContent");
        
        if(temp.length > 0) {
            if(confirm("다른 답글을 작성 중입니다. 현재 댓글에 답글을 작성 하시겠습니까?")) {
                temp[0].nextElementSibling.remove();
                temp[0].remove();
            } else {
                return;
            }
        }

        const textarea = document.createElement("textarea");
        textarea.classList.add("commentInsertContent");
        btn.parentElement.after(textarea);

        const commentBtnArea = document.createElement("div");
        commentBtnArea.classList.add("comment-btn-area");

        const insertBtn = document.createElement("button");
        insertBtn.innerText = "등록";
        insertBtn.setAttribute("onclick", `insertChildComment(${parentCommentNo}, this)`);

        const cancelBtn = document.createElement("button");
        cancelBtn.innerText = "취소";
        cancelBtn.setAttribute("onclick", "insertCancel(this)");

        commentBtnArea.append(insertBtn, cancelBtn);
        textarea.after(commentBtnArea);
    };

    // 답글 작성 취소 버튼
    const insertCancel = (cancelBtn) => {
        cancelBtn.parentElement.previousElementSibling.remove();
        cancelBtn.parentElement.remove();
    };

    // 답글 등록
    const insertChildComment = (parentCommentNo, btn) => {
        const textarea = btn.parentElement.previousElementSibling;

        if(textarea.value.trim().length == 0) {
            alert("내용을 작성한 후 등록 버튼을 눌러주세요");
            textarea.focus();
            return;
        }

        const obj = {
            "commentContent": textarea.value,
            "boardNo": boardNo,
            "memberNo": loginMemberNo,
            "parentCommentNo": parentCommentNo
        };

        fetch("/comment", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(obj)
        })
        .then(resp => resp.json())
        .then(result => {
            if(result > 0) {
                alert("답글이 등록 되었습니다");
                selectCommentList();
            } else {
                alert("답글 등록 실패");
            }
        })
        .catch(err => console.log(err));
    };

    // 댓글 삭제
    const deleteComment = commentNo => {
        if(!confirm("삭제 하시겠습니까?")) return;

        fetch("/comment", {
            method: "DELETE",
            headers: {"Content-Type": "application/json"},
            body: commentNo
        })
        .then(resp => resp.text())
        .then(result => {
            if(result > 0) {
                alert("삭제 되었습니다");
                selectCommentList();
            } else {
                alert("삭제 실패");
            }
        })
        .catch(err => console.log(err));
    };

    // 댓글 수정
    let beforeCommentRow;

    const showUpdateComment = (commentNo, btn) => {
        const temp = document.querySelector(".update-textarea");

        if(temp != null) {
            if(confirm("수정 중인 댓글이 있습니다. 현재 댓글을 수정하시겠습니까?")) {
                const commentRow = temp.parentElement;
                commentRow.after(beforeCommentRow);
                commentRow.remove();
            } else {
                return;
            }
        }

        const commentRow = btn.closest("li");
        beforeCommentRow = commentRow.cloneNode(true);
        let beforeContent = commentRow.children[1].innerText;

        commentRow.innerHTML = "";

        const textarea = document.createElement("textarea");
        textarea.classList.add("update-textarea");
        textarea.value = beforeContent;

        commentRow.append(textarea);

        const commentBtnArea = document.createElement("div");
        commentBtnArea.classList.add("comment-btn-area");

        const updateBtn = document.createElement("button");
        updateBtn.innerText = "수정";
        updateBtn.setAttribute("onclick", `updateComment(${commentNo}, this)`);

        const cancelBtn = document.createElement("button");
        cancelBtn.innerText = "취소";
        cancelBtn.setAttribute("onclick", "updateCancel(this)");

        commentBtnArea.append(updateBtn, cancelBtn);
        commentRow.append(commentBtnArea);
    };

    // 댓글 수정 취소
    const updateCancel = (btn) => {
        if(confirm("취소 하시겠습니까?")) {
            const commentRow = btn.closest("li");
            commentRow.after(beforeCommentRow);
            commentRow.remove();
        }
    };

    // 댓글 수정
    const updateComment = (commentNo, btn) => {
        const textarea = btn.parentElement.previousElementSibling;

        if(textarea.value.trim().length == 0) {
            alert("댓글 작성 후 수정 버튼을 클릭해 주세요");
            textarea.focus();
            return;
        }

        const data = {
            "commentNo": commentNo,
            "commentContent": textarea.value
        };

        fetch("/comment", {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        })
        .then(resp => resp.text())
        .then(result => {
            if(result > 0) {
                alert("댓글이 수정 되었습니다");
                selectCommentList();
            } else {
                alert("댓글 수정 실패");
            }
        })
        .catch(err => console.log(err));
    };

});
