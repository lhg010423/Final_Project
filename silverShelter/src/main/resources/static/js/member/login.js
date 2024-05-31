const loginArea = document.querySelector("#loginArea");
const memberId = document.querySelector("#memberId");
const memberPw = document.querySelector("#memberPw");

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

// 로그인 아이디 저장 ( 안됨 )
const getCookie = (key) => {
	
	const cookies = document.cookie;
	
	const cookieList = cookies.split(";").map( el => el.split ("=") );
	
	const obj = {};
	
	for(let i = 0; i < cookieList.length; i++ ){
		const k = cookieList[i][0];
		const v = cookieList[i][0];
		obj[k] = v;
		
	}
	
	
	return obj[key];
	
}

const loginId = document.querySelector("#loginAaer input [name ='memberId']");

if (loginId != null){
	
	const saveId = getCookie("saveId");
	
	if(saveId != undefined){
		loginId.value = saveId;
		
		document.querySelector("input[name='saveId']").checked =true;
	}
}