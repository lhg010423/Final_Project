const loginArea = document.querySelector("#loginArea");
const memberId = document.querySelector("#memberId");
const memberPw = document.querySelector("#memberPw");
const loginBtn = document.querySelector("#loginBtn");
loginArea.addEventListener("submit", e => {
	
	// 아이디 미작성시
	if(memberId.value.trim().length === 0){
		alert("아이디를 작성해주세요!");
		
		e.preventDefault();
		memberId.focus();
		return;
	}
	
	if(memberPw.value.trim().length === 0){
		
		alert("비밀번호를 입력해주세요!");
		
		e.preventDefault();
		memberPw.focus();
		return;
	}
	
});

// 쿠키 가져오기 함수
const getCookie = (key) => {
    const cookies = document.cookie.split("; ");
    for (let cookie of cookies) {
        const [k, v] = cookie.split("=");
        if (k === key) return decodeURIComponent(v); // 쿠키 값을 디코딩. 못알아듣지만 써봄
    }
    return null;
}

// 저장된 쿠기가 있다면 멤버 아이디 저장할거심
const autoFillMemberId = () => {
    const saveId = getCookie("saveId");
    if (saveId) {
        memberId.value = saveId;
        document.querySelector("input[name='saveId']").checked = true;
    }
}


document.addEventListener("DOMContentLoaded", autoFillMemberId);

