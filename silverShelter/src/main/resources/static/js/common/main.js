   
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

    /* 네비창 script */
document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('.hidden-bar').addEventListener('click', function() {

        document.getElementById('sideNav').classList.toggle('active');
    });


        document.querySelector('.closebtn').addEventListener('click', function() {

        document.getElementById('sideNav').classList.remove('active');

    });
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
    });
    
  /* 캐러샐2 end */
