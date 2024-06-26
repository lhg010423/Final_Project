const updateBtn = document.getElementById("updateBtn");
const cancelBtn = document.getElementById("cancelBtn");
const boardTitle = document.getElementById("boardTitle");
const boardContent = document.getElementById("boardContent");


if(updateBtn != null) {

    updateBtn.addEventListener("click", () => {
        
        const obj = {
            "boardNo"   : boardNo,
            "boardTitle" : boardTitle.value,
            "boardContent" : boardContent.value
        };

        // boardController 그대로 쓰기
        fetch(`/board/${boardCode}/${boardNo}/boardUpdate`, {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        })
        .then(resp => resp.text())
        .then(result => {

            if(result > 0) {
                // 현재 주소가 https://goldenprestige.store/admin/1/448/update일 때
                const currentUrl = window.location.href; 
                // '/update' 부분을 제거하여 새로운 주소 생성
                const newUrl = currentUrl.replace('/update', ''); 

                // 수정이 완료되었음을 알리는 메시지
                alert("수정되었습니다.");
                // 새로운 주소로 이동
                location.href = newUrl;
            } else {
                alert("수정 실패");
            }

        })


    })

}


// 게시글 수정 취소
if(cancelBtn != null) {

    cancelBtn.addEventListener("click", () => {

        location.href="/admin/" + boardCode + "/" + boardNo;

    })

}