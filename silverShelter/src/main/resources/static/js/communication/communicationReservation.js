document.addEventListener("DOMContentLoaded", function() {
    const wrappers = document.querySelectorAll(".image-wrapper");
    const calendar = document.getElementById('calendar-2');
    const submitButton = document.getElementById('reserve-button');
    const clubCode = document.getElementById('clubCode');

    // 전송할 데이터를 저장할 객체
    const obj = {
        clubSport: '',
        clubResvTime: '',
        clubCode: clubCode.value
    };

    // 오늘 날짜를 YYYY-MM-DD 형식으로 구하기
    const today = new Date();
    const todayString = `${today.getFullYear()}-${String(today.getMonth() + 1).padStart(2, '0')}-${String(today.getDate()).padStart(2, '0')}`;

    // jSuites 캘린더를 초기화
    jSuites.calendar(calendar, {
        format: 'DD/MM/YYYY',
        validRange: [todayString, null],  // 오늘 날짜 이후로만 선택 가능하게 설정
        onupdate: function(instance, value) {
            const date = new Date(value);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            const selectdate = `${year}년${month}월${day}일`;

            console.log(selectdate); // 예시 출력: "2024년06월04일"

            // 날짜를 임시 저장
            window.selectedDate = selectdate;
        }
    });

    // 시간 클릭 될때 값 얻어오기
    var timeSelect = document.getElementsByName('time');
    timeSelect.forEach(function(time) {
        time.addEventListener('click', function(e) {
            const selectedTime = e.target.value;
            console.log(selectedTime);
            console.log(clubCode.value); // clubCode 값 확인

            // 날짜와 시간을 합쳐서 객체에 저장
            if (window.selectedDate) {
                obj.clubResvTime = window.selectedDate + selectedTime;
            }
            console.log(obj.clubResvTime);
        });
    });

    // 각 "image-wrapper" 요소에 대해 반복
    wrappers.forEach(wrapper => {
        wrapper.addEventListener("click", function() {
            wrappers.forEach(wrap => wrap.classList.remove("selected"));
            wrapper.classList.add("selected");
            const selectedValue = wrapper.querySelector(".selected-sport").value;
            obj.clubSport = selectedValue;
            console.log("선택된 스포츠 타입:", obj.clubSport); // 선택된 스포츠 값 확인
        });
    });

    // Fetch 요청을 보내는 함수 정의
    function sendReservation() {
        console.log("전송할 데이터:", obj); // 전송할 데이터 디버깅
        if (obj.clubResvTime && obj.clubSport) {
            fetch('/communication/communicationReservation', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(obj)
            })
            .then(response => response.json())
            .then(data => {
                if (data == 1) {
                    alert("예약 되었습니다.");
                    location.href = "/";
                } else if (data == 3) {
                    alert("예약 있음 확인 바람");
                } else {
                    alert("예약 실패");
                    location.href = location.pathname;
                }
            })
            .catch((error) => {
                console.error('예약 중 에러 발생:', error);
            });
        } else {
            alert("모든 필드를 채워주세요.");
        }
    }

    // 예약 버튼 클릭 이벤트 리스너
    submitButton.addEventListener('click', function(e) {
        e.preventDefault();
        sendReservation();
    });
});
