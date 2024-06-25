//console.log("HI");
/* ----- 다음 주소  api 활용하기 ----- */ 
function execDaumPostcode() {
        
    new daum.Postcode({
        
        oncomplete: function(data) {
        // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                
                addr = data.roadAddress;

            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                
                addr = data.jibunAddress;

            }
           
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("roadAddress").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}

if (document.querySelector("#searchAddress")) {
    // 주소 검색 버튼 (searchAddress) 클릭시
document.querySelector("#searchAddress").addEventListener("click",execDaumPostcode);
}


/* ------------------------ 회원가입 유효성 검사 ---------------------------- */

const checkObj ={
    "memberId"          :   false,
    "memberPw"          :   false,
    "memberPwConfirm"   :   false,
    "guardianTel"       :   false
    // 만약 examId 를 쓸거면 examId.value
    
}

const memberId = document.querySelector("#memberId");
const examId = document.querySelector("#examId");
const idMessage = document.querySelector("#idMessage");

// 아이디가 입력될 '때'마다 유효성검사
memberId.addEventListener("input", e => {

    // 나중에 할건데, 입력 후 변경된 경우

    //작성된 값 얻어오기
    const inputId = e.target.value;
        console.log(inputId);

    // 입력값이없을경우
    if(inputId.trim().length === 0){

        idMessage.innerText ="* 6~20자 이내의 소문자 알파벳과 숫자로 입력하세요";

        // idMessage 에 색상추가
        idMessage.classList.remove('confirm','error');
        
        // 유효성 검사 여부 false 로 변경
        checkObj.memberId = false;

        // 잘못 입력한 띄어쓰기 없애기
        memberId.value ="";

        return;
    }

    // if를 빠져나온 경우는 뭔가 값이 있다는 뜻?

    const regExp = /^[a-z0-9]{6,20}$/;
    // 입력받은 아이디가 정규식과 일치하지 않는 경우

    if( !regExp.test(inputId) ){
        idMessage.innerText = "* 알맞은 아이디 형식으로 작성해주세요.";
        idMessage.classList.add('error');
        idMessage.classList.remove('confirm');

        checkObj.memberId =false;

        return;
    }

    // 유효한경우(ajax)
    fetch("/member/checkId?memberId=" + inputId)
    .then(resp=> resp.text() )
    .then( count=> {

        if(count == 1 ){

            idMessage.innerText = "* 이미 사용중인 아이디 입니다.";
            idMessage.classList.add('error');
            idMessage.classList.remove('confirm');

            checkObj.memberId = false; // 중복 ㄴㄴ
            return;
        } 

        // 중복이 아닌경우
        console.log(examId.value);
        idMessage.innerText = "* 사용 가능한 아이디 입니다.";
        idMessage.classList.add('confirm');
        idMessage.classList.remove('error');

        checkObj.memberId = true;
    })
    .catch(error => {
        console.log(error);
    })
    ;

});

/*
const checkObj ={
    "memberId"          :   false,
    "memberPw"          :   false,
    "memberPwConfirm"   :   false,
    "guardianTel"       :   false
    
}
*/

/* ----- 비밀번호 확인 유효성 검사하기 -----*/

const memberPw = document.querySelector("#memberPw");
const memberPwConfirm = document.querySelector("#memberPwConfirm");
const pwMessage = document.querySelector("#pwMessage");

// 비밀번호, 비밀번호 확인이 같은지 검사하는 함수를 먼저 만들어주면 좋다
const checkPw = () => {

    // 같을 경우
    if(memberPw.value === memberPwConfirm.value){
        pwMessage.innerText = "* 입력한 비밀번호가 일치합니다";
        pwMessage.classList.add('confirm');
        pwMessage.classList.remove('error');

        checkObj.memberPwConfirm = true;
        return;
    }

    pwMessage.innerText = "* 입력한 비밀번호가 일치하지 않습니다.";
    pwMessage.classList.add('error');
    pwMessage.classList.remove('confirm');
    checkObj.memberPwConfirm = false;

}

// 비밀번호 유효성검사
memberPw.addEventListener("input", e => {


    const inputPw = e.target.value;

    // 먼저 따질건 입력되지 않은 경우
    if(inputPw.trim().length === 0){

        pwMessage.innerText = "* 6~20자 이내의 영문, 숫자, 특수문자로 입력하세요";
        pwMessage.classList.remove('confirm', 'error');
        checkObj.memberPw = false;

        memberPw.value = "";

        return;
    }

    // 뭐라도 입력됐다면
    const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;
    if (!regExp .test(inputPw)){
        pwMessage.innerText = "* 사용할 수 없는 비밀번호입니다.";
        pwMessage.classList.add("error");
        pwMessage.classList.remove("confirm");
        checkObj.memberPw = false;
        return;
    }

    // 유효한경우
    pwMessage.innerText = "* 사용 가능한 비밀번호입니다.";
    pwMessage.classList.add("confirm");
    pwMessage.classList.remove("error");
    checkObj.memberPw = true;

    // 비번 입력시 확인란의 값과 비교하는 코드 추가

    // 비밀번호 확인란에 값이 작성되어 있을 때!!!
    if(memberPwConfirm.value.length > 0){
        checkPw();

        
    }
    
});


// 비번 확인란의 유효성 검사, 비밀번호 입력 인풋이 유효할때만 검사함
memberPwConfirm.addEventListener("input", () => {

    if(checkObj.memberPw){
        checkPw();

        return;
    }

    checkObj.memberPwConfirm = false;

});

/* 보호자 전화번호 검사 (정규식 검사)*/



const guardianTel = document.querySelector("#guardianTel");
const guardianTelMessage = document.querySelector("#guardianTelMessage");

guardianTel.addEventListener("input", e => {

    const guardianTel = e.target.value;

    if(guardianTel.trim().length === 0 ){
        guardianTelMessage.innerText = "* 전화번호를 입력해주세요.(- 포함)";
        guardianTelMessage.classList.remove("confirm", "error");
        checkObj.guardianTel = false;

        return;
    }

    const regExp = /^01[0-9]{1}-[0-9]{3,4}-[0-9]{4}$/;


    if(! regExp.test(guardianTel) ){
        guardianTelMessage.innerText = "* 유효하지 않은 전화번호 형식입니다";
        guardianTelMessage.classList.add("error");
        guardianTelMessage.classList.remove("confirm");
        checkObj.guardianTel = false;

        return;
    }

        guardianTelMessage.innerText = "* 사용할 수 있는 전화번호 형식입니다.";
        guardianTelMessage.classList.add("confirm");
        guardianTelMessage.classList.remove("error");
        checkObj.guardianTel = true;
});


/* 버튼 클릭 시 전체 유효성 검사 여부 확인하기*/
/*
const checkObj ={
    "memberId"          :   false,
    "memberPw"          :   false,
    "memberPwConfirm"   :   false,
    "guardianTel"       :   false
    
}
*/


/* ---------------------------- */

document.getElementById('yesBtn').addEventListener('click', function() {
    isItOkay();
});

document.getElementById('noBtn').addEventListener('click', function() {
    isItOkay();
});


function isItOkay() {

	for(let key in checkObj){
		
		if( !checkObj[key]){
			
			let str;
			
			switch(key){
				
				case "memberId" : str = "아이디가 유효하지 않습니다"; break;
				case "memberPw" : str = "비밀번호가 유효하지 않습니다"; break;
				case "memberPwConfirm" : str = "비밀번호가 유효하지 않습니다"; break;
				case "guardianTel" : str = "보호자 연락처가 유효하지 않습니다"; break;
			}
			
			alert(str);
			
			document.getElementById(key).focus();
			
			e.preventDefault();
			
			return;
		}	
		
	}
	
	
};

function initializeSurveyForm() {
    const form = document.getElementById("surveyForm");
    if (form) {
        form.addEventListener("submit", function(event) {
            event.preventDefault();
            console.log("Form submitted");

            const formData = {
                gender: document.querySelector('input[name="gender"]:checked').value,
                age: document.querySelector('input[name="age"]:checked').value,
                experience: document.querySelector('input[name="experience"]:checked').value,
                workTime: document.querySelector('input[name="workTime"]:checked').value,
                role: document.querySelector('input[name="role"]:checked').value
            };
            console.log(formData);

            fetch('/medicalCenter/careGivers', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Server response:', data);
                renderCaregiversList(data);
                hideSurveyForm(); // 설문조사 폼 숨기기
            })
            .catch(error => {
                console.error('Error:', error);
            });
        });
    }
}

function renderCaregiversList(data) {
    let table = `
    <h1 id="surveyFormTit1">요양사 매칭 결과</h1>
    <table class="caregiver-table">
        <thead>
            <tr>
                <th>번호</th>
                <th>이름</th>
                <th>나이</th>
                <th>성별</th>
                <th>전화번호</th>
                <th>경력</th>
                <th>근무 시간</th>
                <th>역할</th>
                <th>선택</th>
            </tr>
        </thead>
        <tbody>
    `;

    data.forEach(caregiver => {
        table += `
        <tr>
            <td>${caregiver.caregiversNo}</td>
            <td>${caregiver.caregiversName}</td>
            <td>${caregiver.caregiversAge}</td>
            <td>${translateGender(caregiver.caregiversGender)}</td>
            <td>${caregiver.caregiversTel}</td>
            <td>${translateExperience(caregiver.caregiversExperience)}</td>
            <td>${translateWorkHours(caregiver.caregiversWorkHours)}</td>
            <td>${translateRole(caregiver.caregiversRole)}</td>
            <td><button class="select-button" data-caregiver-id="${caregiver.caregiversNo}">선택</button></td>
        </tr>
        `;
    });

    table += `
        </tbody>
    </table>
    `;

    document.getElementById("caregiversList").innerHTML = table;

    // 선택 버튼에 클릭 이벤트 리스너 추가
    const selectButtons = document.querySelectorAll(".select-button");
    selectButtons.forEach(button => {
        button.addEventListener("click", function() {
            const caregiverId = this.getAttribute("data-caregiver-id");
            sendSelectedCaregiverId(caregiverId);
        });
    });
}

function sendSelectedCaregiverId(caregiverId) {
    console.log(`Sending selected caregiver ID: ${caregiverId}`);
    
    const formData = new FormData();
    formData.append('caregiverId', caregiverId);

    fetch('/medicalCenter/selectCaregiver', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        console.log(`Received response with status: ${response.status}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        console.log('Server response:', data);
        renderCaregiverInfo(data);
    })
    .catch(error => {
        console.error('Error sending selected caregiver ID:', error);
    });
}

function renderCaregiverInfo(caregiverInfo) {
    console.log('Rendering caregiver info:', caregiverInfo);

    const caregiverTable = document.getElementById("caregiversList");
    caregiverTable.innerHTML = '';

    if (!caregiverInfo || Object.keys(caregiverInfo).length === 0) {
        caregiverTable.innerHTML = "<p>선택된 요양사 정보가 없습니다.</p>";
        return;
    }

    let table = `
        <h1 id="surveyFormTit1">요양사 매칭이 성공적으로 완료되었습니다</h1>
        <table class="caregiver-table">
            <thead>
                <tr>
                    <th>번호</th>
                    <th>이름</th>
                    <th>나이</th>
                    <th>성별</th>
                    <th>전화번호</th>
                    <th>경력</th>
                    <th>근무 시간</th>
                    <th>역할</th>
                </tr>
            </thead>
            <tbody>
        <tr>
            <td>${caregiverInfo.caregiversNo}</td>
            <td>${caregiverInfo.caregiversName}</td>
            <td>${caregiverInfo.caregiversAge}</td>
            <td>${translateGender(caregiverInfo.caregiversGender)}</td>
            <td>${caregiverInfo.caregiversTel}</td>
            <td>${translateExperience(caregiverInfo.caregiversExperience)}</td>
            <td>${translateWorkHours(caregiverInfo.caregiversWorkHours)}</td>
            <td>${translateRole(caregiverInfo.caregiversRole)}</td>
        </tr>
            </tbody>
        </table>
    `;

    caregiverTable.innerHTML = table;

    console.log('Caregiver info rendered successfully');
}

function translateGender(gender) {
    switch (gender.toLowerCase()) {
        case 'male': return '남성';
        case 'female': return '여성';
        default: return gender;
    }
}

function translateExperience(experience) {
    switch (experience.toLowerCase()) {
        case 'novice': return '3년 미만';
        case 'intermediate': return '3-7년';
        case 'experienced': return '8년 이상';
        default: return experience;
    }
}

function translateWorkHours(workHours) {
    switch (workHours.toLowerCase()) {
        case 'morning': return '오전 (09:00 - 13:00)';
        case 'afternoon': return '오후 (13:00 - 18:00)';
        case 'evening': return '저녁 (18:00 - 22:00)';
        default: return workHours;
    }
}

function translateRole(role) {
    switch (role.toLowerCase()) {
        case 'companionship': return '정서적 지원(대화, 동반 등)';
        case 'personalcare': return '의료적 지원(투약, 간호 등)';
        case 'housekeeping': return '일상생활 지원(세탁, 청소 등)';
        default: return role;
    }
}


function hideSurveyForm() {
    const surveyForm = document.getElementById("surveyForm");
    const surveyFormTit = document.getElementsByClassName("surveyFormTit");

    if (surveyForm) {
        surveyForm.style.display = "none"; // 설문조사 폼 숨기기
        for (var i = 0; i < surveyFormTit.length; i++) {
            surveyFormTit[i].style.display = 'none';
        }
    }
}