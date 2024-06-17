document.querySelectorAll('.reservation-update').forEach(button => {

    button.addEventListener('click', e => {

        e.preventDefault(); 

        const currentReservation = e.target.closest('.reservation-item');
        const selectedTime = currentReservation.querySelector('#reservationTime').value;
        const currentTimes = Array.from(document.querySelectorAll('.reservation-item')).map(item => item.querySelector('.reservation-clubResvTime').innerText);

        if (currentTimes.includes(selectedTime)) {
            alert('예약 시간이 겹칩니다.');

        } else {
            
            currentReservation.closest('form').submit(); 

        }
    });
});

