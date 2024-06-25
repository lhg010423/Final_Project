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
document.addEventListener("DOMContentLoaded", function() {
    const links = document.querySelectorAll('.boardHeader a');
    const defaultBoardCode = '1';

    // 모든 링크에서 'active' 클래스 제거
    function removeActiveClasses() {
        links.forEach(link => link.classList.remove('active'));
    }

    links.forEach(link => {

        // 기본 활성화할 링크에 'active' 클래스 추가
        if(link.href.inclues(defaultBoardCode)) {
            link.classList.add('active');
        }

        link.addEventListener('click', function(e) {
            // 기본 링크 동작을 방지
            e.preventDefault();

            // 모든 링크에서 'active' 클래스 제거
            removeActiveClasses();
            // links.forEach(link => link.classList.remove('active'));

            // 클릭된 링크에 'active' 클래스 추가
            this.classList.add('active');

        })
    })
})