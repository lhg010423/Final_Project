
document.addEventListener("DOMContentLoaded", function () {

})
document.querySelectorAll(".boardTitleBtn").forEach(button => {
    button.addEventListener("click", e => {

        const boardNo = e.target.dataset.boardNo;
        const boardCode = e.target.dataset.boardCode;

        console.log(boardNo);
        console.log(boardCode);




        fetch("/admin/boardList", {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : boardNo
            // body : JSON.stringify(obj)
            // body : boardNo
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
        })
    })
})





































// 댓글 ----------------------------------------------------------------
// console.log(boardNo);
// console.log(1);
// const loadComments = (boardNo) => {

//     console.log(boardNo);

//     fetch(`/board/commentSelect?boardNo=${boardNo}`)
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
