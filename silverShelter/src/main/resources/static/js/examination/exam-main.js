const obj = {
    step1: {
        fullAgreement: false
    },
    step2: {
        contactName: '',
        contactPhone: '',
        contactEmail: ''
    },
    step3: {
        selectedRoom: 'classic',
        selectedOccupants: ''
    },
    step4: {
        healthCheckup: null,
        familyRelationship: null,
        residentRegistration: null,
        idCardCopy: null
    }
};

let currentStep = 0;
const steps = document.querySelectorAll('.step');
const lines = document.querySelectorAll('.line');
const contents = document.querySelectorAll('.step-content');
const titles = ["약관동의", "신청자 정보", "객실 선택", "서류 제출"];

function showStep(stepIndex) {
    steps.forEach((step, index) => {
        step.classList.toggle('active', index <= stepIndex);
    });
    lines.forEach((line, index) => {
        line.classList.toggle('active', index < stepIndex);
    });
    contents.forEach((content, index) => {
        content.classList.toggle('active', index === stepIndex);
    });
    document.getElementById('prevBtn').style.display = stepIndex === 0 ? 'none' : 'inline';
    document.getElementById('nextBtn').textContent = stepIndex === steps.length - 1 ? '제출' : '다음';
    document.getElementById('step-title').textContent = titles[stepIndex];
}

// 스텝 변경 시 동작 이벤트 
function changeStep(stepChange) {
    console.log(obj);

    // 현재 단계에서 다음 단계로 넘어가기 전에 유효성 검사 수행
    if (stepChange === 1 && !validateStep(currentStep)) {
        alert(getValidationMessage(currentStep));
        return; // 유효하지 않으면 다음 단계로 이동하지 않음
    }

    const newStep = currentStep + stepChange;

    if (newStep === 2 && stepChange === 1) {
        // step2로 이동할 때 신청자 정보 확인 및 비동기 요청
        fetch('/examination/checkApplicantInfo', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                contactName: obj.step2.contactName,
                contactPhone: obj.step2.contactPhone,
                contactEmail: obj.step2.contactEmail
            })
        })
        .then(response => response.json())
        .then(data => {
            
            console.log("data: ", data);
            if (data > 0) {
                alert('이미 신청한 회원입니다. 메인 페이지로 이동합니다.');
                location.href = "/";
            } else {
                // 스텝 변경 및 UI 업데이트
                currentStep = newStep;
                showStep(currentStep);
                window.scrollTo({ top: 0, behavior: 'smooth' });
            }
        })
        .catch(error => {
            console.error('요청 중 오류 발생:', error);
            alert('요청 중 오류가 발생했습니다. 다시 시도해주세요.');
        });
    } else if (newStep >= 0 && newStep < steps.length) {
        // 스텝 변경 및 UI 업데이트
        currentStep = newStep;
        showStep(currentStep);
        window.scrollTo({ top: 0, behavior: 'smooth' });

    } else if (newStep === steps.length) {

        // 모든 스텝이 유효한 경우 제출 처리
        if (validateStep(currentStep)) {
            submitExamination(); // 서버로 제출 이벤트

        } else {
            alert(getValidationMessage(currentStep));
        }
    }

    // if (newStep >= 0 && newStep < steps.length) {
    //     currentStep = newStep;
    //     showStep(currentStep);

    //     window.scrollTo({ top: 0, behavior: 'smooth' }); // 스크롤을 페이지 상단으로 이동

    // } else if (newStep === steps.length) {

    //     // 모든 스텝이 유효한 경우 제출 처리
    //     if (validateStep(currentStep)) {
            
    //         submitExamination(); // 서버로 제출 이벤트

    //     } else {
    //         alert(getValidationMessage(currentStep));
    //     }
    // }
}

// step별 유효성 검사 함수
function validateStep(stepIndex) {
    if (stepIndex === 0) {
        // 약관 동의 스텝
        const individualAgreementCheckboxes = document.querySelectorAll(".individual-agreement");
        return Array.from(individualAgreementCheckboxes).every(cb => cb.checked);

    } else if (stepIndex === 1) {
        // 신청자 정보 스텝
        const contactName = document.getElementById('contact-name').value;
        const contactPhone = document.getElementById('contact-phone').value;
        const contactEmail = document.getElementById('contact-email').value;
        obj.step2.contactName = contactName;
        obj.step2.contactPhone = contactPhone;
        obj.step2.contactEmail = contactEmail;
        return contactName !== '' && contactPhone !== '' && contactEmail !== '';
        
    } else if (stepIndex === 2) {
        // 객실 선택 스텝
        return obj.step3.selectedRoom !== '' && obj.step3.selectedOccupants !== '';

    } else if (stepIndex === 3) {
        // 서류 제출 스텝
        return obj.step4.healthCheckup !== null &&
               obj.step4.familyRelationship !== null &&
               obj.step4.residentRegistration !== null &&
               obj.step4.idCardCopy !== null;
    } 

    return true;
}

// 스텝별 유효성검사 메시지 함수 실행
function getValidationMessage(stepIndex) {
    if (stepIndex === 0) {
        return '모든 약관을 동의해주세요';
    } else if (stepIndex === 1) {
        return '신청자 정보를 모두 입력해주세요';
    } else if (stepIndex === 2) {
        return '객실 정보를 모두 입력해주세요';
    } else if (stepIndex === 3) {
        return '모든 서류를 제출해주세요';
    }
    return '';
}

// 서류 제출 시 파일 이벤트 핸들러
function handleFileChange(event, containerId) {
    let file = event.target.files[0];
    const container = document.getElementById(containerId);
    const fileNameSpan = container.querySelector('.file-name');
    if (file) {
        fileNameSpan.textContent = file.name;
        container.style.display = 'flex';
    } else {
        fileNameSpan.textContent = '';
        container.style.display = 'none';
    }
    
    const inputId = event.target.id;
    
    if(file == undefined) {
        file = null;
    }

    obj.step4[inputId] = file;
}

// 제출 파일 삭제 시 핸들러
function removeFile(inputId) {
    const input = document.getElementById(inputId);
    input.value = '';
    handleFileChange({ target: input }, `${inputId}-file-name`);
}


showStep(currentStep);

document.addEventListener("DOMContentLoaded", function() {
    const fullAgreementCheckbox = document.getElementById("fullAgreement");
    const individualAgreementCheckboxes = document.querySelectorAll(".individual-agreement");

    fullAgreementCheckbox.addEventListener("change", function() {
        const isChecked = fullAgreementCheckbox.checked;
        individualAgreementCheckboxes.forEach(checkbox => {
            checkbox.checked = isChecked;
        });
        obj.step1.fullAgreement = isChecked;
    });

    individualAgreementCheckboxes.forEach((checkbox, index) => {
        checkbox.addEventListener("change", function() {
            if (!checkbox.checked) {
                fullAgreementCheckbox.checked = false;
            } else {
                const allChecked = Array.from(individualAgreementCheckboxes).every(cb => cb.checked);
                fullAgreementCheckbox.checked = allChecked;
            }
            obj.step1.fullAgreement = fullAgreementCheckbox.checked;
        });
    });

    const tabButtons = document.querySelectorAll(".tab-button");
    const roomContents = document.querySelectorAll(".room-content");
    const occupantsSelect = document.querySelector('.occupants-select');

    tabButtons.forEach(button => {
        button.addEventListener("click", function() {
            const room = button.getAttribute("data-room");

            tabButtons.forEach(btn => btn.classList.remove("active"));
            button.classList.add("active");

            roomContents.forEach(content => {
                if (content.id === room) {
                    content.classList.add("active");
                } else {
                    content.classList.remove("active");
                }
            });

            obj.step3.selectedRoom = room;
        });
    });

    occupantsSelect.addEventListener("change", function() {
        console.log(occupantsSelect.value);
        obj.step3.selectedOccupants = occupantsSelect.value;
    });

    document.querySelector(`.tab-button[data-room="${obj.step3.selectedRoom}"]`).click();

    // 건강검진 기록부 파일이 변경되었을 때 파일명을 표시하고 객체에 파일을 저장
    document.getElementById("healthCheckup").addEventListener("change", function(event) {
        handleFileChange(event, 'healthCheckup-file-name');
    });

    // 가족 관계 증명서 파일이 변경되었을 때 파일명을 표시하고 객체에 파일을 저장
    document.getElementById("familyRelationship").addEventListener("change", function(event) {
        handleFileChange(event, 'familyRelationship-file-name');
    });

    // 주민등록본 파일이 변경되었을 때 파일명을 표시하고 객체에 파일을 저장
    document.getElementById("residentRegistration").addEventListener("change", function(event) {
        handleFileChange(event, 'residentRegistration-file-name');
    });

    // 신분증 사본 파일이 변경되었을 때 파일명을 표시하고 객체에 파일을 저장
    document.getElementById("idCardCopy").addEventListener("change", function(event) {
        handleFileChange(event, 'idCardCopy-file-name');
    });
});

// submit 이벤트 함수
function submitExamination() {
    const formData = new FormData();
    
    // step1 데이터는 동의여부 논리값일 뿐이므로 제외함

    // step2 연락처 데이터 추가
    formData.append('contactName', obj.step2.contactName);
    formData.append('contactPhone', obj.step2.contactPhone);
    formData.append('contactEmail', obj.step2.contactEmail);

    // step3 방 데이터 추가
    formData.append('selectedRoom', obj.step3.selectedRoom);
    formData.append('selectedOccupants', obj.step3.selectedOccupants);

    // step4 파일 데이터 추가
    formData.append('healthCheckup', obj.step4.healthCheckup);
    formData.append('familyRelationship', obj.step4.familyRelationship);
    formData.append('residentRegistration', obj.step4.residentRegistration);
    formData.append('idCardCopy', obj.step4.idCardCopy);



    fetch("/examination/submit", {
        method: "POST",
        body: formData,
        headers: {
            // FormData 객체를 사용할 때는 Content-Type 헤더를 설정하지 않음.
            // -> 브라우저가 자동으로 설정함
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('네트워크 응답에 문제가 있습니다.');
        }
        return response.json(); // 필요한 경우, 응답을 JSON으로 파싱
    })
    .then(data => {
        alert('제출 완료. 심사 후 개별적으로 이메일 드리겠습니다');
        location.href = "/";
    })
    .catch(error => {
        console.error('제출 중 에러 발생:', error);
        alert('제출 중 오류가 발생했습니다. 다시 시도해주세요.');
    });

}