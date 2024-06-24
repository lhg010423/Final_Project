document.addEventListener("DOMContentLoaded", function() {

    // jSuites 캘린더 인스턴스 생성
    let instance = jSuites.calendar(document.getElementById('calendar'), {
        format: 'DD/MM/YYYY HH:MM',
        onupdate: function() {
            fetchReservedDates();
        }
    })


    // 서버에서 예약된 날짜를 가져오는 함수
    function fetchReservedDates() {
        fetch('/admin/getReservedDates')
            .then(response => response.json())
            .then(data => {

                const reservedDates = data.map(item => {
                    const date = new Date(item.reservationDate);
                    date.setHours(0, 0, 0, 0); // 시간을 0으로 설정하고 날짜만 비교
                    return date;
                });

                console.log('data @@@@ :', data);
                console.log('date @@@@ :', reservedDates);

                // 예약된 날짜를 강조하는 함수 호출
                highlightReservedDates(reservedDates);

            })
            .catch(error => console.error('예약된 날짜를 가져오는 중 에러 발생', error));
    }


    // 전역 변수 선언
    window.selectedDateValue = null;
    window.reservedDates = []; // 예약된 날짜들을 저장하는 배열

    // 예약된 날짜를 강조하는 함수
    function highlightReservedDates(reservedDates) {

        // 예약된 날짜들을 전역 변수에 저장
        window.reservedDates = reservedDates;

        // 모든 jcalendar-set-day 요소를 선택
        const days = document.querySelectorAll('.jcalendar-set-day');

        // 모든 날짜 요소에서 클래스 제거 및 span 제거 (초기화 단계)
        days.forEach(function(day) {
            const span = day.querySelector('.reservColor');
            if(span) {
                day.removeChild(span);
            }
        });

        // 예약된 날짜와 일치하는 요소에 클래스 추가 및 클릭 이벤트 추가
        days.forEach(function(day) {
            const dayNumber = parseInt(day.innerText, 10);
            if(!isNaN(dayNumber)) {
                const selectedDate = new Date(instance.getValue());
                const currentMonth = selectedDate.getMonth();
                const currentYear = selectedDate.getFullYear();

                // 현재 날짜 객체 생성
                const dayDate = new Date(currentYear, currentMonth, dayNumber);

                // 예약된 날짜와 일치하는 요소에 클래스 추가
                if (reservedDates.some(date => date.getTime() === dayDate.getTime())) {
                    const span = document.createElement('span');
                    span.classList.add('reservColor');
                    day.appendChild(span);

                    // 예약된 날짜 라벨 추가
                    const label = document.createElement('label');
                    label.id = `label${dayNumber}`;
                    label.innerText = 'Y';
                    label.hidden = true;
                    day.appendChild(label);

                    // 클릭 이벤트 추가
                    day.addEventListener('click', function() {
                        fetchReservationsForDate(dayNumber);
                        window.selectedDateValue = `${currentYear}${currentMonth + 1 < 10 ? '0' + (currentMonth + 1) : currentMonth + 1}${dayNumber < 10 ? '0' + dayNumber : dayNumber}`;
                    });
                } else {
                    // 예약이 없는 날짜에도 클릭 이벤트 추가
                    day.addEventListener('click', function() {
                        displayNoReservationsMessage(dayNumber);
                        window.selectedDateValue = `${currentYear}${currentMonth + 1 < 10 ? '0' + (currentMonth + 1) : currentMonth + 1}${dayNumber < 10 ? '0' + dayNumber : dayNumber}`;
                    });
                }
            }
        })
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




    // 특정 날짜의 예약 목록을 가져오는 함수
    function fetchReservationsForDate(day) {
        const selectedDate = new Date(instance.getValue());
        const year = selectedDate.getFullYear();
        const month = selectedDate.getMonth() + 1; // months are 0-indexed
        const clubResvTime = `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}`;

        console.log(clubResvTime);

        fetch('/admin/getReservationsForDate', {
            method : 'POST',
            headers : {"Content-Type" : "application/json"},
            body : clubResvTime
        })
            .then(resp => resp.json())
            .then(data => {

                console.log(data);

                // 예약 목록을 표시할 HTML 생성
                const reservationList = document.getElementById("reservationList");
                reservationList.innerHTML = ''; // 기존 내용을 초기화

                if(data.length === 0) {
                    console.log("예약 목록을 가져오는 중 오류 발생");
                } else {

                    // 데이터가 있을 경우 예약 목록 표시
                    data.forEach(reservation => {

                        const div = document.createElement('div');
                        div.classList.add('reservation');
                        div.innerHTML = `
                            <div class="reservation-item">
                                <span class="reservation-time">${reservation.reservationTime}</span>
                            </div>
                        `;

                    })


                }
                




            })
        


    }



    fetchReservedDates();
});







