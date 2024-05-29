console.log('test')

document.addEventListener("DOMContentLoaded", function() {
    console.log('test');

    // 방 클릭시 이미지 / 글귀 변경 
    const classicBtn = document.getElementById("classic-btn");
    const premiumBtn = document.getElementById("premium-btn");
    const vipBtn = document.getElementById("vip-btn");

    // 방 이미지 
    const roomImg = document.getElementById("room-img");

    // 방 타이틀
    const roomTitle = document.getElementById("roomTitle");

    // 방 소개글 
    const roomIntroduction = document.getElementById("roomIntroduction");

    // 방 면적 
    const roomArea = document.getElementById("roomArea");

    // 방 개수 
    const roomCount = document.getElementById("roomCount")
    // 클래식 버튼 클릭시 
    classicBtn.addEventListener("click", () => {

        roomImg.src="/images/Introduction/classic.webp";

        roomTitle.innerText = "◾ 클래식룸";

        roomIntroduction.innerText = "실버타운의 클래식룸은 편안하고 아늑한 분위기를 제공합니다. 기본적인 편의시설을 완비하여, 집처럼 편안한 휴식을 취할 수 있습니다. 경제적이면서도 안락한 숙박을 원하시는 분들께 이상적인 선택입니다.";
    
        roomArea.innerText = "100.01㎡/74.99㎡(전용률75%)";

        roomCount.innerText = "3/1 개";
    });

    // 프리미엄 버튼 클릭 시 
    premiumBtn.addEventListener("click", () => {

        roomImg.src="/images/Introduction/premium.webp";

        roomTitle.innerText = "◾ 프리미엄";

        roomIntroduction.innerText = "프리미엄 룸은 최상의 품질과 서비스를 제공합니다. 고급스러운 인테리어와 넓은 공간이 편안한 휴식을 위한 최적의 환경을 조성합니다. 각종 고급 편의시설을 완비하여, 특별한 여행 경험을 선사합니다. 우아하고 편리한 휴식을 원하시는 분들께 이상적인 선택입니다.";
   
        roomArea.innerText = "128.15㎡/94.84㎡(전용률74%)";

        roomCount.innerText = "4/2 개";
    });

    // VIP 버튼 클릭 시 
    vipBtn.addEventListener("click", () => {

        roomImg.src="/images/Introduction/vip.webp";

        roomTitle.innerText = "◾ VIP";

        roomIntroduction.innerText = "당신을 위한 특별한 공간, 우리의 VIP 룸은 호화로움과 편안함을 한 곳에 모았습니다. 고급스러운 디자인과 최상의 시설이 특별한 여행을 위한 최상의 준비를 완성합니다. 개별적인 서비스와 특별한 혜택을 경험하실 수 있으며, 최상의 편안함과 럭셔리를 원하시는 분들께 이상적인 선택입니다.";
    
        roomArea.innerText = "230.69㎡/179.79㎡(전용률78%)";

        roomCount.innerText = "5/3 개";
    });
});
