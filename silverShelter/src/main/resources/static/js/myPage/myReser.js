document.addEventListener('DOMContentLoaded', function() {
    // jSuites 캘린더 인스턴스 생성
    let instance = jSuites.calendar(document.getElementById('calendar'), {
        format: 'DD/MM/YYYY HH:MM',
        onupdate: function() {
            fetchReservedDates();
        }
    });

    // 예약된 날짜를 강조하는 함수
    function highlightReservedDates(reservedDates) {
        // 모든 jcalendar-set-day 요소를 선택
        const days = document.querySelectorAll('.jcalendar-set-day');

        // 모든 날짜 요소에서 클래스 제거 및 span 제거 (초기화 단계)
        days.forEach(function(day) {
            const span = day.querySelector('.resevColor');
            if (span) {
                day.removeChild(span);
            }
        });

        // 예약된 날짜와 일치하는 요소에 클래스 추가 및 클릭 이벤트 추가
        days.forEach(function(day) {
            const dayNumber = parseInt(day.innerText, 10);
            if (!isNaN(dayNumber)) {
                const selectedDate = new Date(instance.getValue());
                const currentMonth = selectedDate.getMonth();
                const currentYear = selectedDate.getFullYear();


                // 현재 날짜 객체 생성
                const dayDate = new Date(currentYear, currentMonth, dayNumber);

                // 예약된 날짜와 일치하는 요소에 클래스 추가
                if (reservedDates.some(date => date.getTime() === dayDate.getTime())) {
                    const span = document.createElement('span');
                    span.classList.add('resevColor');
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
                    });
                } else {
                    // 예약이 없는 날짜에도 클릭 이벤트 추가
                    day.addEventListener('click', function() {
                        displayNoReservationsMessage(dayNumber);
                    });
                }
            }
        });
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

    // 서버에서 예약된 날짜를 가져오는 함수
    function fetchReservedDates() {
        fetch('/myPage/getReservedDates')
            .then(response => response.json())
            .then(data => {
                // 데이터가 객체 배열로 반환되면, 각 객체의 clubResvTime 속성만 추출
                const reservedDates = data.map(item => {
                    const date = new Date(item.clubResvTime);
                    date.setHours(0, 0, 0, 0); // 시간을 0으로 설정하여 날짜만 비교
                    return date;
                });

                // 데이터 확인을 위한 로그 출력
                console.log('Fetched data:', data);
                console.log('Reserved dates:', reservedDates);

                highlightReservedDates(reservedDates);
            })
            .catch(error => console.error('Error fetching reserved dates:', error));
    }


    // 특정 날짜의 예약 목록을 가져오는 함수
    function fetchReservationsForDate(day) {
        const selectedDate = new Date(instance.getValue());
        const year = selectedDate.getFullYear();
        const month = selectedDate.getMonth() + 1; // months are 0-indexed
        const clubResvTime = `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}`;
        
        console.log(clubResvTime);

        fetch('/myPage/getReservationsForDate', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: clubResvTime
        })
            .then(response => response.json())
            .then(data => {
                // 예약 목록을 표시할 HTML 생성
                const reservationList = document.getElementById('reservationList');
                reservationList.innerHTML = ''; // 기존 내용을 초기화
                
                if (data.length === 0) {
                    
                } else {
                    // 데이터가 있을 경우 예약 목록 표시
                    data.forEach(reservation => {
                        const div = document.createElement('div');
                        div.classList.add('reservation');
                        div.innerHTML = `
                            <div class="divTest"></div>
                            <p class="reservation-clubName">${reservation.clubName}</p>
                            <p class="reservation-clubResvTime">${reservation.clubResvTime}</p>
                        `;
                        reservationList.appendChild(div);
                    });
                }
            })
            .catch(error => console.error('Error fetching reservations for date:', error));
    }

    // 예약된 날짜를 초기화하고 예약된 날짜를 강조하는 함수 호출
    fetchReservedDates();
});