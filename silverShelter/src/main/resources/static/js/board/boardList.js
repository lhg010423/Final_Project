/* 글쓰기 버튼 클릭 시 */
const insertBtn = document.querySelector("#insertBtn");

// 글쓰기 버튼이 존재할 때 (로그인 상태인 경우
if(insertBtn != null) {
    insertBtn.addEventListener("click", () => {

        console.log(boardCode);

        // get 방식 요청
        // /editBoard/1/insert
        location.href = `/board/${boardCode}/insert`;

    });
}

// 상단 게시판이동하는 a태그 클릭시 밑줄이 유지되는 코드
// document.addEventListener("DOMContentLoaded", function() {
//     const links = document.querySelectorAll('.boardHeader a');
//     const defaultBoardCode = '1';
//     let storedBoardCode = localStorage.getItem('activeBoardCode') || defaultBoardCode;

//     // 모든 링크에서 'active' 클래스 제거
//     function removeActiveClasses() {
//         links.forEach(link => link.classList.remove('active'));
//     }

//     // 로컬 저장소에 저장된 값에 맞는 링크에 'active' 클래스 추가
//     function setActiveLink(boardCode) {
//         removeActiveClasses();
//         links.forEach(link => {
//             if (link.href.includes(`boardCode=${boardCode}`)) {
//                 link.classList.add('active');
//             }
//         });
//     }

//     // 초기 로드 시 활성 링크 설정
//     setActiveLink(storedBoardCode);

//     links.forEach(link => {
//         link.addEventListener('click', function(event) {
//             // 클릭된 링크의 boardCode 값을 로컬 저장소에 저장
//             const urlParams = new URLSearchParams(this.href.split('?')[1]);
//             const boardCode = urlParams.get('boardCode');
//             localStorage.setItem('activeBoardCode', boardCode);

//             // 페이지 이동이 일어나기 전에 active 클래스 설정
//             setActiveLink(boardCode);
//         });
//     });
// });