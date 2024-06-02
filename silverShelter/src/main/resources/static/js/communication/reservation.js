
document.addEventListener('DOMContentLoaded',function() {
    var calendarEl = document.getElementById('calendar')

    var calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
            left:'prevYear,prev,next,nextYear today',
            center: 'title',
            right:'dayGridMonth,dayGridWeek,dayGridDay'
        }
    })
})