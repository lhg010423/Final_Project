document.addEventListener("DOMContentLoaded", function() {
    let isReservationPageInitialized = false;
    let lastSelectedDepartment = ''; // 전역 변수로 선택된 진료과를 저장할 변수를 선언합니다.
    let lastSelectedDoctor = '';
    let lastSelectedDoctorImage = '';
    let lastSelectedDoctorName = '';
    let lastSelectedDay = '';
    let drRoom = '';
    let lastSelectedDep = '';
    let instance = '';

    function initializeReservationButtons() {
        const reservationPage = document.getElementById("reservationPage");
        if (reservationPage && !isReservationPageInitialized) {
            console.log("initializeReservationButtons: reservationPage 존재 확인");
            isReservationPageInitialized = true;

            updateReservationView('2');

            // resImg 클릭 이벤트 핸들러 등록
            const resImgs = document.querySelectorAll('.resImg');
            const toggleButtonImage = document.getElementById('toggleButtonImage');
            const toggleButtonImageIntro = document.getElementById('toggleButtonImageIntro');
            const selectedDepartmentInput = document.getElementById('selectedDepartment');

            resImgs.forEach(resImg => {
                resImg.addEventListener('click', function () {
                    const newImageSrc = this.getAttribute('data-image');
                    if (newImageSrc) {
                        const koreanText = newImageSrc.match(/[\u3131-\uD79D]+/g).join("");
                        toggleButtonImage.src = newImageSrc;
                        console.log("토글 버튼 이미지는 ", koreanText);
                        lastSelectedDepartment = koreanText;
                        toggleButtonImageIntro.innerText = koreanText;
                        const department = this.getAttribute('data-image');
                        selectedDepartmentInput.value = department; // 선택된 진료과 값을 hidden input에 저장

                    } else {
                        console.error("Image source attribute 'data-image' not found.");
                    }
                });
            });

        } else {
            console.log("initializeReservationButtons: reservationPage 요소가 존재하지 않거나 이미 초기화됨.");
            isReservationPageInitialized = false; // 페이지가 사라졌을 때 초기화 상태 리셋
        }
    }

    // 화면을 업데이트하는 함수
    async function updateReservationView(target) {
        try {
            const reservationContents = document.querySelectorAll(".reservation-content");
            const reservationButtons = document.querySelectorAll(".reservation-toggleButton");

            // 모든 콘텐츠를 순회하면서 보이기/숨기기 설정
            console.log("화면 업데이트 함수 시작");
            reservationContents.forEach(content => {
                try {
                    console.log("콘텐츠 순회 시작");
                    if(target == '4'){
                        content.style.display = content.id === `reservation-content${target}` ? "flex" : "none";
                    }else {
                        content.style.display = content.id === `reservation-content${target}` ? "block" : "none";
                    }
                    console.log("콘텐츠 순회 성공: " + content.id);
                } catch (error) {
                    console.error("콘텐츠 순회 오류:", error);
                }
            });

            // 모든 버튼의 활성화 상태 초기화
            reservationButtons.forEach(button => {
                try {
                    if (button.getAttribute("data-target") === target) {
                        button.classList.add("reservation-active");
                        console.log("버튼 활성화 설정 성공: " + button.getAttribute("data-target"));
                    } else {
                        button.classList.remove("reservation-active");
                        console.log("나머지 다지움");
                    }
                } catch (error) {
                    console.error("버튼 활성화 상태 초기화 오류:", error);
                }
            });

            console.log("화면 업데이트 함수 성공: " + target);

        } catch (error) {
            console.error("화면 업데이트 오류:", error);
        }

        if (target === '3') {
            try {
                const response = await fetch(`/medicalCenter/reservation/doctorChoice?departmentName=${lastSelectedDepartment}`);
                const html = await response.text();
                console.log("html값", html);
                document.querySelector("#reservation-content3").innerHTML = html;

                if(document.querySelectorAll('.doc-choice') != null){
                    document.querySelectorAll('.doc-choice').forEach(function(element) {
                        element.addEventListener('click', function() {
                            // 모든 .doc-choice 요소에서 .resized 클래스를 제거
                            document.querySelectorAll('.doc-choice').forEach(function(el) {
                                el.classList.remove('resized');
                            });
                            // 클릭된 요소에만 .resized 클래스 추가
                            this.classList.add('resized');
                            lastSelectedDoctor = this.textContent;
                            lastSelectedDoctorImage = this.querySelector('.doc-choice-profile').getAttribute('src');
                            const toggleButtonImage2 = document.getElementById('toggleButtonImage2');
                            const toggleButtonImageIntro2 = document.getElementById('toggleButtonImageIntro2');
                            const cleanedText = lastSelectedDoctor.trim();

                            // 줄바꿈을 기준으로 텍스트를 분할합니다.
                            const lines = cleanedText.split('\n');
                            
                            // 첫 번째 줄의 텍스트를 추출합니다.
                            const doctorName = lines[0].trim();
                            lastSelectedDep = lines.slice(1).join('\n').trim();
                            console.log('dsfsd', doctorName);

                            toggleButtonImageIntro2.innerText = doctorName;
                            lastSelectedDoctorName = doctorName;
                            toggleButtonImage2.src = lastSelectedDoctorImage;
                            
                    });
                })}
                if(document.querySelectorAll('.resized') != null){
                    document.querySelectorAll('.resized').forEach(function(element) {
                        element.addEventListener('click', function() {
                            this.classList.remove('resized');
                        });
                    });
                }



                // doc-choice-next 클릭 이벤트 핸들러 등록
                const docChoiceNextButton = document.getElementById('doc-choice-next');
                if (docChoiceNextButton != null) {
                    console.log("doc-choice-next 존재 확인");
                    docChoiceNextButton.addEventListener('click', function () {
                        console.log("doc-choice-next 클릭 이벤트 시작");
                        console.log("lastSelected:", lastSelectedDoctor);
                        console.log("lastSelected:", lastSelectedDoctorImage);
                        updateReservationView('4');
                    });
                }
                
            } catch (error) {
                console.error("Error fetching doctor info:", error);
            }
        }

        if (target === '4') {
            try {
                const response = await fetch(`/medicalCenter/reservation/date?departmentName=${lastSelectedDepartment}&resDoctorName=${lastSelectedDoctorName}`);
                const html = await response.text();
                console.log("html값", html);
                document.querySelector("#reservation-content4").innerHTML = html;

                // 캘린더 요소를 가져와서 변수에 저장
                const calendar = document.getElementById('calendar');
                const submitButton = document.getElementById('reserve-button');

                // 오늘 날짜를 YYYY-MM-DD 형식으로 구하기
                const today = new Date();
                const todayString = `${today.getFullYear()}-${String(today.getMonth() + 1).padStart(2, '0')}-${String(today.getDate()).padStart(2, '0')}`;

                // 전송할 데이터를 저장할 객체
                const obj = {
                    docResvTime: '',
                    doctorName: lastSelectedDoctorName
                };

                // jSuites 캘린더를 초기화
                jSuites.calendar(calendar, {
                    format: 'DD/MM/YYYY',  // 날짜 형식을 '일/월/년'으로 설정
                    validRange: [todayString, null],  // 오늘 날짜 이후로만 선택 가능하게 설정
                    onupdate: function(instance, value) {  // 날짜가 업데이트될 때 호출되는 콜백 함수
                        
                        // 클래스명이 'dateInfo'인 요소들을 가져옵니다.
                        const dateInfoElements1 = document.querySelectorAll('.dateInfo');
                        // 가져온 모든 요소를 순회하면서 값을 가져옵니다.
                        dateInfoElements1.forEach(dateInfoElement => {
                            // 각 요소의 텍스트 값을 가져옵니다.
                            const dateInfo = dateInfoElement.textContent.trim();

                            // 가져온 시간 옵션들을 저장할 배열을 선언합니다.
                            const timeOptions = Array.from(document.querySelectorAll('.time-option'));

                            // 시간 옵션들을 순회하면서 조건에 맞는지 확인합니다.
                            timeOptions.forEach(timeOption => {
                                    // 조건에 해당하는 시간 옵션을 화면에 다시 보이도록 설정합니다.
                                    timeOption.style.display = '';

                            });
                        });
                        
                        // value를 Date 객체로 변환
                        const date = new Date(value);

                        // 년도를 추출
                        const year = date.getFullYear();

                        // 월을 추출하고 두 자리 문자열로 변환 (1월은 '01', 12월은 '12')
                        const month = String(date.getMonth() + 1).padStart(2, '0');  // getMonth()는 0부터 시작하므로 +1 필요

                        // 일을 추출하고 두 자리 문자열로 변환 (1일은 '01', 31일은 '31')
                        const day = String(date.getDate()).padStart(2, '0');

                        // 'YYYY년MM월DD일' 형식으로 날짜를 조합
                        const selectdate = `${year}년${month}월${day}일`;

                        // 조합된 날짜를 콘솔에 출력
                        console.log(selectdate); // 예시 출력: "2024년06월04일"

                        // 날짜를 임시 저장
                        window.selectedDate = selectdate;


                        // 클래스가 "dateInfo"인 모든 <li> 요소를 가져옵니다.
                        const dateInfoElements = document.querySelectorAll('.dateInfo');

                        // 가져온 모든 요소를 순회하면서 값을 가져옵니다.
                        dateInfoElements.forEach(dateInfoElement => {
                            // 각 요소의 텍스트 값을 가져옵니다.
                            const dateInfo = dateInfoElement.textContent.trim();

                            // 가져온 시간 옵션들을 저장할 배열을 선언합니다.
                            const timeOptions = Array.from(document.querySelectorAll('.time-option'));

                            // 시간 옵션들을 순회하면서 조건에 맞는지 확인합니다.
                            timeOptions.forEach(timeOption => {
                                // 각 시간 옵션의 값을 가져옵니다.
                                const timeValue = timeOption.querySelector('input[name="time"]').value;

                                // dateInfo와 window.selectedDate + timeValue가 같은지 확인합니다.
                                if (dateInfo === window.selectedDate + timeValue) {
                                    // 조건에 해당하는 시간 옵션을 화면에서 숨깁니다.
                                    timeOption.style.display = 'none';
                                }
                            });
                        });

                        
                    }
                });


                // 시간 클릭 될때 값 얻어오기
                var timeSelect = document.getElementsByName('time');
                timeSelect.forEach(function(time) {
                    time.addEventListener('click', function(e) {
                        const selectedTime = e.target.value;
                        console.log(selectedTime);

                        // 날짜와 시간을 합쳐서 객체에 저장
                        if (window.selectedDate) {
                            obj.docResvTime = window.selectedDate + selectedTime;
                        }
                        console.log(obj.docResvTime);
                        lastSelectedDay = obj.docResvTime;
                    });
                });

                // Fetch 요청을 보내는 함수 정의
                function sendReservation() {
                    if (obj.docResvTime) {
                        fetch(`/medicalCenter/reservation/doctorReservation?resDoctorName=${lastSelectedDoctorName}&drApptTime=${obj.docResvTime}`, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(obj) // 객체를 JSON 문자열로 변환하여 전송
                        })
                        .then(response => response.json())
                        .then(data => {
                            if (data.result > 0) {
                                alert(data.message);  // 성공 메시지 알림
                                updateReservationView('5');
                                drRoom = data.drRoom;
                            } else {
                                alert(data.message);  // 실패 메시지 알림
                                window.location.href = data.redirectUrl;  // 실패 시 URL로 이동
                            }
                        })
                        .catch((error) => {
                            console.error('예약 중 에러 발생:', error);
                        });
                    }
                }

                // 예약 버튼 클릭 이벤트 리스너
                submitButton.addEventListener('click', function(e) {
                    e.preventDefault();  // 기본 동작(폼 제출)을 막습니다.
                    sendReservation();   // 예약 정보를 서버로 전송하는 함수를 호출합니다.
                });
                
            } catch (error) {
                console.error("Error fetching doctor info:", error);
            }        }

            if (target === '5'){
                try {
                    const response = await fetch(`/medicalCenter/reservationSuccess`);
                    const html = await response.text();
                    console.log("html값", html);
                    document.querySelector("#reservationForm").innerHTML = html;
                    
                    const btnimg = document.querySelector("#btnimg");
                    const day = document.querySelector("#doctor-day");
                    const name = document.querySelector("#doctor-name");
                    const dep = document.querySelector("#doctor-dep");
                    const room = document.querySelector("#doctor-room");
                    
                    btnimg.src = lastSelectedDoctorImage;
                    name.innerText = lastSelectedDepartment + " " + lastSelectedDoctorName;
                    dep.innerText = lastSelectedDep;
                    day.innerText = "날짜 : " + lastSelectedDay;
                    room.innerText = "장소 : " + drRoom;
                    
                } catch (error) {
                    console.error("Error fetching doctor info:", error);
                }
            }

    }

    // Mutation Observer 설정
    const observer = new MutationObserver((mutationsList) => {
        for (const mutation of mutationsList) {
            if (mutation.type === 'childList' || mutation.type === 'attributes') {
                const reservationPage = document.getElementById("reservationPage");
                if (reservationPage && !isReservationPageInitialized) {
                    console.log("MutationObserver: reservationPage가 보입니다.");
                    initializeReservationButtons();
                    // resNextButton 클릭 이벤트 핸들러 등록
                    const resNextButton = document.getElementById('resNextButton');
                    if(resNextButton != null){
                        console.log("resNextButton 존재 확인");
                        resNextButton.addEventListener('click', function () {
                            console.log("resNextButton 클릭 이벤트 시작");
                            console.log("lastSelectedDepartment:", lastSelectedDepartment);
                            updateReservationView('3');
                        });
                    } else {
                        console.error("resNextButton 요소를 찾을 수 없습니다.");
                    }
                } else if (!reservationPage && isReservationPageInitialized) {
                    console.log("MutationObserver: reservationPage가 보이지 않습니다.");
                    isReservationPageInitialized = false;
                }
            }
        }
    });

    // DOM에 추가되거나 속성이 변경될 때 감지
    observer.observe(document.body, { 
        childList: true, 
        attributes: true, 
        subtree: true 
    });

    // 초기 로드 시 reservationPage 확인 및 초기화
    if (document.getElementById("reservationPage")) {
        initializeReservationButtons();
    }

    
});

document.getElementById('infoTab').addEventListener('click', function () {
    showTab('infoTab');
    loadTabContent('medicalCenterIntro', 'section1');
});

document.getElementById('doctorTab').addEventListener('click', function () {
    showTab('doctorTab');
    loadTabContent('doctorMatching', 'section2-2');
});

document.getElementById('careGiverTab').addEventListener('click', function () {
    showTab('careGiverTab');
    loadTabContent('careGiverMatching', 'section3-1', initializeSurveyForm);
});

document.getElementById('checkTab').addEventListener('click', function () {
    console.log('Tab clicked: checkTab');
    showTab('checkTab');
    loadTabContent('ReservationCheck', 'section4-1');
});


document.getElementById('funeralTab').addEventListener('click', function () {
    showTab('funeralTab');
    loadTabContent('funeralService', 'section5-1');
});

var tabSelected = document.getElementsByClassName('selected-tab');
if (tabSelected.length > 0) {
    loadTabContent('medicalCenterIntro', 'section1');
}

function showTab(tabId) {
    var tabs = document.getElementsByClassName('tab');
    var selectedTab = document.querySelector('.selected-tab');

    // 현재 선택된 탭을 다시 클릭한 경우 함수 종료
    if (selectedTab && selectedTab.id === tabId) {
        console.log(`Tab ${tabId} is already selected. No action taken.`);
        return;
    }

    // 기존 선택된 탭의 'selected-tab' 클래스 제거
    if (selectedTab) {
        selectedTab.classList.remove('selected-tab');
    }

    // 새로운 탭 선택
    var newSelectedTab = document.getElementById(tabId);
    if (newSelectedTab) {
        newSelectedTab.classList.add('selected-tab');
    }
}
// 전역 변수 선언 
window.selectedDateValue = null; // 전역 변수로 선언
window.reservedDates = []; // 예약된 날짜들을 저장하는 배열

async function loadTabContent(htmlFile, sectionId = null, callback = null) {
    try {
        const response = await fetch(htmlFile);
        if (!response.ok) {
            throw new Error(`Failed to fetch ${htmlFile}`);
        }

        const html = await response.text();
        const tabContentContainer = document.getElementById('tabContentContainer');
        if (!tabContentContainer) {
            console.error('tabContentContainer element not found.');
            return;
        }

        tabContentContainer.innerHTML = html;

        if (sectionId) {
            handleSectionDisplay(sectionId);
        }

        if (callback) {
            callback();
        }
    } catch (error) {
        console.error('Error fetching HTML file:', error);
    }
}

function handleSectionDisplay(sectionId) {
    switch (sectionId) {
        case 'section1':
            showSection(sectionId, "sectionBtn1");
            break;
        case 'section2-2':
            break;
        case 'section4-1':
            initializeCalendar(); // 기존의 initializeCalendar 함수 호출
            break;
        default:
            console.info(`Section ${sectionId} not handled.`);
    }
}

function initializeCalendar() {
    const calendarElement = document.getElementById('calendar1'); // calendar1로 수정
    if (!calendarElement) {
        console.log('Calendar element not found.');
        return;
    }

    const instance = jSuites.calendar(calendarElement, {
        format: 'DD/MM/YYYY HH:MM',
        onupdate: fetchReservedDates
    });

    fetchReservedDates();

    async function fetchReservedDates() {
        try {
            const response = await fetch('/medicalCenter/getReservedDates', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({})
            });
            if (!response.ok) {
                throw new Error('Failed to fetch reserved dates');
            }

            const data = await response.json();
            const reservedDates = data.map(item => {
                const date = new Date(item.drApptTime);
                date.setHours(0, 0, 0, 0);
                return date;
            });

            highlightReservedDates(reservedDates);
        } catch (error) {
            console.error('Error fetching reserved dates:', error);
        }
    }

    function highlightReservedDates(reservedDates) {
        window.reservedDates = reservedDates;
        const days = document.querySelectorAll('.jcalendar-set-day');

        days.forEach(day => {
            const span = day.querySelector('.resevColor');
            if (span) {
                day.removeChild(span);
            }
        });

        days.forEach(day => {
            const dayNumber = parseInt(day.innerText, 10);
            if (!isNaN(dayNumber)) {
                const selectedDate = new Date(instance.getValue());
                const dayDate = new Date(selectedDate.getFullYear(), selectedDate.getMonth(), dayNumber);
                const currentMonth = selectedDate.getMonth();
                const currentYear = selectedDate.getFullYear();
                if (reservedDates.some(date => date.getTime() === dayDate.getTime())) {
                    const span = document.createElement('span');
                    span.classList.add('resevColor');
                    day.appendChild(span);

                    const label = document.createElement('label');
                    label.id = `label${dayNumber}`;
                    label.innerText = 'Y';
                    label.hidden = true;
                    day.appendChild(label);
                    // 클릭 이벤트 추가 (이벤트 리스너는 한 번만 등록)
                    if (!day.hasAttribute('data-event-listener')) {
                        day.setAttribute('data-event-listener', 'true'); // 중복 등록 방지용 속성 추가
                        day.addEventListener('click', function() {
                            if (reservedDates.some(date => date.getTime() === dayDate.getTime())) {
                                fetchReservationsForDate(dayNumber);
                                window.selectedDateValue = `${currentYear}${currentMonth + 1 < 10 ? '0' + (currentMonth + 1) : currentMonth + 1}${dayNumber < 10 ? '0' + dayNumber : dayNumber}`;
                            } else {
                                displayNoReservationsMessage(dayNumber);
                                window.selectedDateValue = `${currentYear}${currentMonth + 1 < 10 ? '0' + (currentMonth + 1) : currentMonth + 1}${dayNumber < 10 ? '0' + dayNumber : dayNumber}`;
                            }
                        });
                    }
                } else {
                    // 예약이 없는 날짜에도 클릭 이벤트 추가 (이벤트 리스너는 한 번만 등록)
                    if (!day.hasAttribute('data-event-listener')) {
                        day.setAttribute('data-event-listener', 'true'); // 중복 등록 방지용 속성 추가
                        day.addEventListener('click', function() {
                            displayNoReservationsMessage(dayNumber);
                            window.selectedDateValue = `${currentYear}${currentMonth + 1 < 10 ? '0' + (currentMonth + 1) : currentMonth + 1}${dayNumber < 10 ? '0' + dayNumber : dayNumber}`;
                        });
                    }
                }
            }
        });
    }

    async function fetchReservationDoctorName(doctorNo) {
        try {
            const response = await fetch('/medicalCenter/reservation/result', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ doctorNo })
            });
            if (!response.ok) {
                throw new Error(`Failed to fetch doctor name for doctorNo: ${doctorNo}`);
            }
            const doctorName = await response.text(); // 서버에서 반환된 의사 이름을 읽음
            return doctorName; // 의사 이름을 반환
        } catch (error) {
            console.error('Error fetching doctor name:', error);
            return 'Unknown Doctor'; // 에러 발생 시 기본값으로 'Unknown Doctor'를 반환
        }
    }

    // 예약이 없는 날짜를 클릭했을 때 메시지를 표시하는 함수
    function displayNoReservationsMessage(day) {
        const label = document.querySelector(`#label${day}`);
        const reservationList = document.getElementById('reservationList');
        reservationList.innerHTML = ''; // 기존 내용을 초기화

        // 라벨이 없거나 비어 있는 경우 메시지 표시
        if (!label || label.innerText === '') {
            reservationList.innerHTML = '<p class="reservation-none">예약 일정이 없습니다.</p>';
        }
    }

    // 예약 목록을 생성하고 의사 이름을 포함하여 추가하는 함수
    async function fetchReservationsForDate(day) {
        const selectedDate = new Date(instance.getValue());
        const year = selectedDate.getFullYear();
        const month = selectedDate.getMonth() + 1; // months are 0-indexed
        const clubResvTime = `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}`;

        try {
            const response = await fetch('/medicalCenter/getReservationsForDate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: clubResvTime
            });
            if (!response.ok) {
                throw new Error('Failed to fetch reservations for date');
            }
            const data = await response.json(); // 서버에서 반환된 예약 데이터를 읽음

            // 예약 목록을 표시할 HTML 생성
            const reservationList = document.getElementById('reservationList');
            reservationList.innerHTML = ''; // 기존 내용을 초기화

            if (data.length === 0) {
                // 데이터가 없을 경우 예약 없음 메시지를 표시
                displayNoReservationsMessage(day);
            } else {
                // 데이터가 있을 경우 각 예약 데이터를 처리하여 목록에 추가
                for (const reservation of data) {
                    const reservationDoctorName = await fetchReservationDoctorName(reservation.doctorNo);
                    const div = document.createElement('div');
                    div.classList.add('reservation');
                    div.innerHTML = `
                        <div class="reservation-item">
                            <div class="stapper-div"></div>
                            <div class="reservation-fontList">
                                <p class="reservation-clubName">${reservationDoctorName}</p>
                                <p class="reservation-clubResvTime">${reservation.drApptTime}</p>
                            </div>
                        </div>
                    `;
                    reservationList.appendChild(div);
                }
            }
        } catch (error) {
            console.error('Error fetching reservations for date:', error);
        }
    }
}


// 예약 수정 버튼 클릭 시 
const updateBtn = document.querySelector("#reservationUpdate");
if(updateBtn){
updateBtn.addEventListener("click", () => {
    // window.selectedDateValue에서 연도, 월, 일을 추출하여 Date 객체 생성
    const selectedDate = new Date(window.selectedDateValue.slice(0, 4), window.selectedDateValue.slice(4, 6) - 1, window.selectedDateValue.slice(6, 8));
    const isReserved = window.reservedDates.some(date => date.getTime() === selectedDate.getTime());

    if (window.selectedDateValue) {
        if (isReserved) {
            location.href = `/medicalCenter/reservationCheck/update/${window.selectedDateValue}`;
        } else {
            alert('예약이 없는 날짜입니다. 수정할 수 없습니다.');
        }
    } else {
        alert('날짜를 선택해 주세요.');
    }
});
}

function showSection(sectionId, sectionBtnId) {
    console.log("Trying to show section with ID:", sectionId);
    var sections = document.getElementsByClassName('section');
    var btns = document.getElementsByClassName('sectionBtn');
    var sectionBtn = document.getElementById(sectionBtnId);

    console.log(sections);
    console.log(btns);
    console.log(sectionBtn);
    for (var i = 0; i < sections.length; i++) {
        sections[i].style.display = 'none';
    }
    for (var j = 0; j < btns.length; j++) {
        btns[j].classList.remove('sectionBtnClicked');
        console.log("답=" + btns[j].classList);
    }

    var selectedSection = document.getElementById(sectionId);
    if (selectedSection != null && sectionBtn != null) {
        selectedSection.style.display = 'flex';
        sectionBtn.classList.add('sectionBtnClicked');
        console.log("Displayed section with ID:", sectionId);
    } else {
        console.log("안됨");
    }
}

function showFloor(sectionId, floorId) {
    console.log("Trying to show floor with ID:", sectionId);
    console.log("Trying to show floor with ID:", floorId);
    var selectedFloor = document.getElementById(floorId);
    var sections = document.getElementsByClassName('floor-sec');
    var floors = document.getElementsByClassName('floor');

    for (var i = 0; i < sections.length; i++) {
        sections[i].style.display = 'none';
    }
    for (var j = 0; j < floors.length; j++) {
        floors[j].classList.remove('floorChecked');
        console.log(floors[j].classList);
    }

    var selectedSection = document.getElementById(sectionId);
    console.log(selectedSection);
    if (selectedSection) {
        selectedSection.style.display = 'flex';
        selectedFloor.classList.add('floorChecked');
        console.log("Displayed floor with ID:", sectionId);
    } else {
        console.error("No floor found with ID:", sectionId);
    }
}

function showDep(sectionId, floorId) {
    console.log("Trying to show floor with ID:", sectionId);
    console.log("Trying to show floor with ID:", floorId);
    var selectedFloor = document.getElementById(floorId);
    var sections = document.getElementsByClassName('dep-sec');
    var floors = document.getElementsByClassName('dep-floor');

    for (var i = 0; i < sections.length; i++) {
        sections[i].style.display = 'none';
        sections[i].classList.remove('floorChecked1');
    }
    for (var j = 0; j < floors.length; j++) {
        floors[j].classList.remove('floorChecked');
        console.log(floors[j].classList);
    }

    var selectedSection = document.getElementById(sectionId);
    console.log(selectedSection);
    if (selectedSection) {
        selectedSection.style.display = 'inline';
        selectedSection.classList.add('floorChecked1');
        selectedFloor.classList.add('floorChecked');
        console.log("Displayed floor with ID:", sectionId);
    } else {
        console.error("No floor found with ID:", sectionId);
    }
}

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

// 캘린더를 클릭해도 크기가 바뀌지 않도록 유지
document.querySelectorAll('.jcalendar-set-day').forEach(day => {
    day.addEventListener('click', (event) => {
        const calendarContainer = document.querySelector('.calendar-container');
        const calendarRect = calendarContainer.getBoundingClientRect();
        
        // 클릭 후에도 크기를 유지
        calendarContainer.style.width = `${calendarRect.width}px`;
        calendarContainer.style.height = `${calendarRect.height}px`;
    });
});
