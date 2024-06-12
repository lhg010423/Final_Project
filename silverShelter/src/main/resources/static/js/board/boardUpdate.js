const updateBtn = document.getElementById("updateBtn");
const cancelBtn = document.getElementById("cancelBtn");
const boardTitle = document.getElementById("boardTitle");
const boardContent = document.getElementById("boardContent");

// 게시글 수정하기
if(updateBtn != null) {

    updateBtn.addEventListener("click", () => {
        
        const obj = {
            "boardNo"   : boardNo,
            "boardTitle" : boardTitle.value,
            "boardContent" : boardContent.value
        };

        fetch(`/board/${boardCode}/${boardNo}/boardUpdate`, {
            method : "PUT",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        })
        .then(resp => resp.text())
        .then(result => {

            if(result > 0) {
                alert("수정되었습니다.")
                location.href="/board/" + boardCode + "/" + boardNo;
            } else {
                alert("수정 실패");
            }

        })


    })

}


// 게시글 수정 취소
if(cancelBtn != null) {

    cancelBtn.addEventListener("click", () => {

        location.href="/board/" + boardCode + "/" + boardNo;

    })

}


const deleteBtn = document.getElementById("deleteBtn");

// 게시글 삭제하기
if(deleteBtn != null) {

    deleteBtn.addEventListener("click", () => {

        const answer = confirm("게시글을 삭제하시겠습니까?");

        if(!answer) {
            alert("삭제 취소");
            return;
        } else {
            
            fetch(`/board/${boardCode}/${boardNo}/boardDelete`, {
                method : "DELETE",
                headers : {"Content-Type" : "application/json"},
                body : boardNo
            })
            .then(resp => resp.text())
            .then(result => {
                if(result == 0 ) {
                    console.log("삭제 중 오류가 발생했습니다.");
                }
            })
            alert("게시글이 삭제되었습니다.");


        }

        location.href = `/board/${boardCode}`;

    })


}


// 댓글 ----------------------------------------------------------------

function loadComments(boardNo) {

    fetch(`/board/commentSelect?boardNo=${boardNo}`)
    .then(resp => resp.json())
    .then(result => {

        const commentsContainer = document.getElementById("comment-container");
        commentsContainer.innerHTML = "";
        result.forEach(comment => {

            const commentElement = document.createElement("div");
            commentElement.innerHTML = `
                <div>작성자 : ${comment.memberName}</div>
                <div>${comment.commentContent}</div>
                <div>작성일 : ${comment.commentWriteDate}</div>
            `;

        })


    })



}