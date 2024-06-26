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
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        })
        .then(resp => resp.text())
        .then(result => {

            if(result > 0) {
                const currentUrl = window.location.href;
                const newUrl = currentUrl.replace('/boardUpdate', '');
                alert("수정되었습니다.")
                location.href = newUrl;
                          
            } else {

                alert("수정 실패");
                // return;
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
                method : "POST",
                headers : {"Content-Type" : "application/json"},
                body : boardNo
            })
            .then(resp => resp.text())
            .then(result => {

                if(result == 0 ) {
                    console.log("삭제 중 오류가 발생했습니다.");
                
                } else {
                    alert("게시글이 삭제되었습니다.");
                    location.href = `/board/${boardCode}`;
                }
            })

        }

    })


}

// 좋아요 클릭 했을 때
document.querySelector("#boardLike").addEventListener("click", e => {

    // 2. 로그인 상태가 아닌 경우 동작 X
    if(loginMemberNo == null) {
        alert("로그인 후 이용해 주세요");
        return;
    }

    // 3. 준비된 3개의 변수를 객체로 저장 (JSON 변환 예정)
    const obj = {
        "memberNo" : loginMemberNo,
        "boardNo"  : boardNo,
        "likeCheck" : likeCheck
    };

    // 4. 좋아요 INSERT/DELETE 비동기 요청
    fetch("/board/like", {
        method  : "POST",
        headers : {"Content-Type" : "application/json"},
        body    : JSON.stringify(obj)
    })
    .then(resp => resp.text()) // 반환 결과 text 형태로 변환
    .then(count => {
        if(count == -1) {
            console.log("좋아요 처리 실패");
            return;
        }

        // 5. likeCheck 값 0 <-> 1 변환
        // -> 클릭 될 때 마다 INSERT/DELETE 동작을 번갈아 가면서 하게끔..
        likeCheck = likeCheck == 0 ? 1 : 0;

        // 6. 하트를 채웠다/비웠다 바꾸기
        e.target.classList.toggle("fa-regular");
        e.target.classList.toggle("fa-solid");

        // 7. 게시글 좋아요 수 수정
        e.target.nextElementSibling.innerText = count;
    })

})





// 댓글 ----------------------------------------------------------------
// console.log(boardNo);
// console.log(1);
// const loadComments = () => {

//     console.log(boardNo);

//     fetch("/board/commentSelect?boardNo=" + boardNo)
//     .then(resp => resp.json())
//     .then(result => {

//         console.log("받은 댓글", result);

//         const commentsContainer = document.getElementById("comments-container");
//         commentsContainer.innerHTML = "";

//         // 최상위 댓글을 찾기 위한 배열
//         const topLevelComments = [];
        


//         // result에서 최상위 댓글과 자식 댓글을 구분하여 처리
//         result.forEach(comment => {

//             if(comment.refLevel === 0) {

//                 // 최상위 댓글을 HTML 요소로 변환
//                 const commentElement = createCommentElement(comment);
//                 commentsContainer.appendChild(commentElement);
//                 topLevelComments.push(commentElement); // 최상위 댓글을 배열에 추가

//                 // 최상위 댓글에 속하는 모든 자식 댓글 추가
//                 appendChildComments(result, commentElement, comment.commentNo);
//             }

//         });

//     })
//     .catch(error => {
//         console.error("댓글을 불러오는 중 오류 발생 : ", error);

//     });

// };


// // 최상위 댓글에 속하는 모든 자식 댓글 추가
// const appendChildComments = (comments, parentElement, parentCommentNo) => {

//     // 부모 댓글에 속하느 모든 자식 댓글 찾기
//     const childComments = comments.filter(comment => comment.parentCommentNo === parentCommentNo);

//     // 모든 자식 댓글을 처리
//     childComments.forEach(childComment => {

//         // 각 자식 댓글을 HTML 요소로 변환
//         const childElement = createCommentElement(childComment);
//         parentElement.querySelector(".child-comments").appendChild(childElement);


//         // 각 자식 댓글에 속하는 모든 자식 댓글을 재귀적으로 추가
//         appendChildComments(comments, childElement, childComment.commentNo);
//     });
// };




// // 댓글 데이터를 받아 HTML 요소로 변환하는 함수
// const createCommentElement = (comment) => {

//     // 댓글을 담을 div 요소 생성
//     const commentElement = document.createElement("div");
//     commentElement.className = "comment";
//     commentElement.dataset.commentNo = comment.commentNo; // 댓글 번호를 dataset으로 저장

//     // 댓글 내용과 작성자 정보를 추가
//     commentElement.innerHTML = `
//         <p>${comment.commentContent}</p>
//         <small>${comment.memberName} - ${comment.commentWriteDate}</small>
//     `;


//     // 자식 댓글을 담을 div 요소 생성
//     const childContainer = document.createElement("div");
//     childContainer.className = "child-comments";
//     commentElement.appendChild(childContainer); // 자식 댓글을 담을 컨테이너를 댓글 요소에 추가

//     return commentElement; // 생성된 댓글 요소 반환
// }
        
// document.addEventListener("DOMContentLoaded", () => {
//     loadComments();
// })

        
        
        
        


// 대댓글이 있는 경우
// if(comment.childComments && comment.childComments.length > 0) {
//     const childContainer = document.createElement("div");
//     childContainer.className = "child-comments";

//     comment.childComments.forEach(childComment => {
//         const childElement = createCommentElement(childComment);
//         childElement.classList.add("child-comment"); // 대댓글 클래스 추가

//         // 자식 댓글에 대한 들여쓰기 계산
//         const childIndentationLevel = (childComment.lefLevel + 1) * 50;
//         childElement.style.paddingLeft = `${childIndentationLevel}px`;


//         childContainer.appendChild(childElement);
//     });

//     commentElement.appendChild(childContainer);
// }


