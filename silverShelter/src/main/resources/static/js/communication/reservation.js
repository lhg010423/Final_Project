document.addEventListener('DOMContentLoaded', function() {
    // 캘린더 요소를 가져와서 변수에 저장
    const calendar = document.getElementById('calendar-2');
    const submitButton = document.getElementById('reserve-button');
    const clubCode = document.getElementById('clubCode');
    const timeList = document.getElementsByName('time');

    // 전송할 데이터를 저장할 객체
    const obj = {
        clubResvTime: '',
        clubCode: clubCode.value
    };

    // 오늘 날짜를 YYYY-MM-DD 형식으로 구하기
    const today = new Date();
    const todayString = `${today.getFullYear()}-${String(today.getMonth() + 1).padStart(2, '0')}-${String(today.getDate()).padStart(2, '0')}`;

    // jSuites 캘린더를 초기화
    jSuites.calendar(calendar, {
        format: 'DD/MM/YYYY',  // 날짜 형식을 '일/월/년'으로 설정
        validRange: [todayString, null],  // 오늘 날짜 이후로만 선택 가능하게 설정
        onupdate: function(instance, value) {  // 날짜가 업데이트될 때 호출되는 콜백 함수
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

            // 현재 날짜와 시간과 비교하여 시간 옵션을 비활성화
            const currentTime = new Date();
            timeList.forEach(function(time) {
                const selectedTime = time.value;
                const selectedDateTime = new Date(`${year}-${month}-${day}T${selectedTime}`);

                if (selectedDateTime < currentTime) {
                    time.disabled = true;
                    // 수정해야함 
                    time.classList.add('name');
                } else {
                    time.disabled = false;
                    time.style.backgroundColor = '';
                }
            });
        }
    });

    // 시간 클릭 될때 값 얻어오기
    timeList.forEach(function(time) {
        time.addEventListener('click', function(e) {
            const selectedTime = e.target.value;
            console.log(selectedTime);
            console.log(clubCode.value); // clubCode 값 확인

            // 날짜와 시간을 합쳐서 객체에 저장
            if (window.selectedDate) {
                obj.clubResvTime = window.selectedDate + ' ' + selectedTime;
            }
            console.log(obj.clubResvTime);
        });
    });

    // Fetch 요청을 보내는 함수 정의
    function sendReservation() {
        if (obj.clubResvTime) {
            fetch('/communication/guestReservation', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(obj) // 객체를 JSON 문자열로 변환하여 전송
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
        }
    }

    // 예약 버튼 클릭 이벤트 리스너
    submitButton.addEventListener('click', function(e) {
        e.preventDefault();  // 기본 동작(폼 제출)을 막습니다.
        sendReservation();   // 예약 정보를 서버로 전송하는 함수를 호출합니다.
    });
});
