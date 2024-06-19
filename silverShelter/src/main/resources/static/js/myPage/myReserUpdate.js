// 모든 '.reservation-update' 클래스를 가진 버튼을 선택합니다.
document.querySelectorAll('.reservation-update').forEach(button => {
    // 각 버튼에 클릭 이벤트 리스너를 추가합니다.
    button.addEventListener('click', e => {
        // 기본 폼 제출 동작을 막습니다.
        e.preventDefault(); 

        // 클릭된 버튼과 가장 가까운 '.reservation-item' 요소를 찾습니다.
        const currentReservation = e.target.closest('.reservation-item');
        
        // 현재 예약 항목에서 선택된 시간을 가져옵니다.
        const selectedTime = currentReservation.querySelector('#reservationTime').value;
        
        // 모든 예약 항목의 시간을 배열로 만듭니다.
        const currentTimes = Array.from(document.querySelectorAll('.reservation-item'))
            .map(item => item.querySelector('.reservation-clubResvTime').innerText);

        // 선택된 시간이 현재 예약된 시간 목록에 있는지 확인합니다.
        if (currentTimes.includes(selectedTime)) {
            // 예약 시간이 겹치는 경우 경고 메시지를 표시합니다.
            alert('예약 시간이 겹칩니다.');
        } else {
            // 예약 시간이 겹치지 않는 경우 폼을 제출합니다.
            currentReservation.closest('form').submit(); 
        }
    });
});

// 모든 삭제 버튼 선택 
document.querySelectorAll('.reservation-delete').forEach(button => {

    button.addEventListener('click',e => {

        e.preventDefault();

         // 클릭된 버튼과 가장 가까운 '.reservation-item' 요소를 찾습니다.
        const currentReservation = e.target.closest('.reservation-item');
        const clubCode = currentReservation.querySelector('#clubCode').value;
        const clubResvNo = currentReservation.querySelector('#clubResvNo').value;

        const obj = {
            "clubCode": clubCode,
            "clubResvNo": clubResvNo
        };

        if(confirm('일정을 삭제하시겠습니까?')){

        }
    })
})