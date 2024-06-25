function initializeSurveyForm() {
    const form = document.getElementById("surveyForm");
    if (form) {
        form.addEventListener("submit", function(event) {
            event.preventDefault();
            console.log("Form submitted");

            // Fetch form data
            const genderElement = document.querySelector('input[name="gender"]:checked');
            const ageElement = document.querySelector('input[name="age"]:checked');
            const experienceElement = document.querySelector('input[name="experience"]:checked');
            const workTimeElement = document.querySelector('input[name="workTime"]:checked');
            const roleElement = document.querySelector('input[name="role"]:checked');

            if (!genderElement || !ageElement || !experienceElement || !workTimeElement || !roleElement) {
                console.error('One or more form elements not found.');
                return;
            }

            const formData = {
                gender: genderElement.value,
                age: ageElement.value,
                experience: experienceElement.value,
                workTime: workTimeElement.value,
                role: roleElement.value
            };

            console.log('Form Data:', formData);

            fetch('/medicalCenter/careGivers', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            })
            .then(response => {
                console.log('Fetch response received:', response);
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
    } else {
        console.error('Form element not found.');
    }
}

function renderCaregiversList(data) {
    console.log('Rendering caregivers list:', data);

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

    const caregiversList = document.getElementById("caregiversList");
    if (caregiversList) {
        caregiversList.innerHTML = table;
    } else {
        console.error('Caregivers list element not found.');
    }

    // 선택 버튼에 클릭 이벤트 리스너 추가
    const selectButtons = document.querySelectorAll(".select-button");
    selectButtons.forEach(button => {
        button.addEventListener("click", function() {
            const caregiverId = this.getAttribute("data-caregiver-id");
            console.log(`Select button clicked: Caregiver ID ${caregiverId}`);
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
    if (caregiverTable) {
        caregiverTable.innerHTML = '';
    } else {
        console.error('Caregiver table element not found.');
        return;
    }

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
        <a onclick="location.href='member/success'" id="careBtn">회원가입</a>
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
    const surveyFormTit = document.getElementById("surveyFormTit");

    if (surveyForm) {
        surveyForm.style.display = "none"; // 설문조사 폼 숨기기
        if (surveyFormTit) {
            surveyFormTit.style.display = 'none';
        }
    } else {
        console.error('Survey form element not found.');
    }
}

// DOM 로드 후 initializeSurveyForm 함수 실행
document.addEventListener("DOMContentLoaded", initializeSurveyForm);
