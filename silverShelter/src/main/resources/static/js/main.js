   
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
