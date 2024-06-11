const updateBtn = document.getElementById("updateBtn");
const deleteBtn = document.getElementById("cancelBtn");
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