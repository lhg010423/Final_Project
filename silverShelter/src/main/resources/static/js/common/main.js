/* 내정보 드랍다운 */
document.addEventListener("DOMContentLoaded", function() {
    
    var memberName = document.getElementById("memberName");
    var logonDropdown = document.getElementById("logonDropdown");

    memberName.onmouseover = function() {
        logonDropdown.style.display = "block";
    }

    memberName.onmouseout = function() {
        setTimeout(function() {
            if (!logonDropdown.matches(':hover')) {
                logonDropdown.style.display = "none";
            }
        }, 250);
    }

    logonDropdown.onmouseover = function() {
        logonDropdown.style.display = "block";
    }

    logonDropdown.onmouseout = function() {
        logonDropdown.style.display = "none";
    }
});
/* ------------------------------------ */


/* 캐러셀 1*/
document.addEventListener('DOMContentLoaded', function() {

	const indicators = document.querySelectorAll('.indicator');
    const carouselInner = document.querySelector('.carousel-inner');

    let currentIndex = 0;
    const totalSlides = indicators.length;

    indicators.forEach((indicator, index) => {
        indicator.addEventListener('click', () => {
            currentIndex = index;
            updateCarousel();
        });
    });


    function updateCarousel() {
        const offset = -currentIndex * 100;

        carouselInner.style.transform = `translateX(${offset}%)`;
        indicators.forEach(indicator => indicator.classList.remove('active'));
        indicators[currentIndex].classList.add('active');

    }

    function autoSlide() {

        currentIndex = (currentIndex + 1) % totalSlides;
        updateCarousel();

    }

    setInterval(autoSlide, 4000); // 4초마다 슬라이드 변경

});


/* 캐러샐2 */
document.addEventListener('DOMContentLoaded', function() {

    const carouselInner = document.querySelector('.my-carousel-inner');
    const totalSlides = document.querySelectorAll('.my-carousel-item').length;
    const slideWidth = 50; // 두 개의 이미지가 보이므로 각 슬라이드의 너비는 50%
    let currentIndex = 0;
    let offset = 0;
    let speed = 0.009; // 이동 속도 조절 (값을 조절하여 속도를 변경)

    window.prevSlide = function() {
        currentIndex = (currentIndex > 0) ? currentIndex - 1 : totalSlides - 2;
        updateCarousel();
    }

    window.nextSlide = function() {
        currentIndex = (currentIndex < totalSlides - 2) ? currentIndex + 1 : 0;
        updateCarousel();
    }
    
    function updateCarousel() {
        offset = -currentIndex * slideWidth;
        carouselInner.style.transform = `translateX(${offset}%)`;
    }
    
    function autoSlide() {
        offset -= speed;
        if (offset <= -slideWidth * totalSlides) {
            offset = 0;
            currentIndex = 0;
        }
        carouselInner.style.transform = `translateX(${offset}%)`;
        requestAnimationFrame(autoSlide);
    }
    
	// 자동 슬라이드
    autoSlide();
}); /* 캐러샐2 end */
    
 
/* --------------------- 날씨 API -------------------------- */ 

function getCurrentDate(){
    const today = new Date();
    const year = today.getFullYear();
    const month = ('0' + (today.getMonth() + 1)).slice(-2);
    const day = ('0'+ (today.getDate())).slice(-2);
    
    return `${year}${month}${day}`;
}

console.log(getCurrentDate());


async function getServiceKey() {
    
    try{

        const response = await fetch("/getServiceKey");
        
        return response.text();
        

    } catch(err){
        console.log("getServiceKey의 에러" + err)
    }
}

// 공공데이터 날씨 API 정보를 얻어올 함수 
async function fetchData(){
    
    // 날씨 이미지 
    const weatherIcon = document.getElementById("weatherIcon")

    const skyInformation = document.getElementById("skyInformation")
    
    const temperature = document.getElementById("temperature")
    
    const precipitation = document.getElementById("precipitation")

    const CurrentDate = getCurrentDate();

    const servicekey = await getServiceKey(); // 비동기 요청 1번째의 응답이 올대까지 기다림 

    console.log("serviceKey : ", servicekey);

    const url = 'http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst';

    //URLSearchParams : URL의 쿼리문자열을 쉽게 다룰 수 있게 해주는 내장 객체 
    // 단, 사용시 decode 서비스키 사용 -> URLSearchParams이 데이터를 인코딩하기 때문 
    const queryParams = new URLSearchParams({
        
        serviceKey : servicekey,
        pageNo : 1, 
        numOfRows : 10,
        dataType : 'JSON',
        base_date : CurrentDate,
        base_time : '0500',
        nx : 60,
        ny: 127 

    });

    console.log(`${url}?${queryParams}`);

    // fetch 요청 

    // 비동기 요청 2번째 
    try{
        const response = await fetch(`${url}?${queryParams}`);
        const result = await response.json();

        console.log(result);


        const obj = result.response.body.items.item.reduce((acc, data) => {
            acc[data.category] = data.fcstValue;
            return acc;
        }, {});
   
        // console.log(obj);
        const sky = {
            "1" : "맑음",
            "3" : "구름많음",
            "4" : "흐림"
        }
        
        if( obj.PTY == 0 ) { // 강수 없음
            switch(obj.SKY) { // SKY 상태에 따라 아이콘 지정
                case "1":
                    weatherIcon.src = '/images/weather/sunny.png'; // 맑음 이미지 파일 경로
                    break;
                case "3":
                    weatherIcon.src = '/images/weather/cloudy.png'; // 구름 많음 이미지 파일 경로
                    break;
                case "4":
                    weatherIcon.src = '/images/weather/overcast.png'; // 흐림 이미지 파일 경로
                    break;
   
            }
        } else if(obj.PTY == 3) { // 눈 올 때
            weatherIcon.src = 'images/weather/snow.png';
        } else { // 그외 비올때
            weatherIcon.src = 'images/weather/rain.png';
        }
        // 하늘 정보
        skyInformation.innerText = sky[obj.SKY];
        // 기온
        temperature.innerText = `${obj.TMP}℃`;
       
        // 강수확률
        precipitation.innerText = `강수 확률 : ${obj.POP}%`;


    } catch(err){

        console.log(err);
    }
}

fetchData();