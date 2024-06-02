function showTab(tabId) {
    var tabs = document.getElementsByClassName('tab');
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].classList.remove('selected-tab');
    }

    var selectedTab = document.getElementById(tabId);
    if (selectedTab) {
        selectedTab.classList.add('selected-tab');
    }
}

async function loadTabContent(htmlFile, sectionId = null) {
    try {
        const response = await fetch(htmlFile);
        const html = await response.text();
        const tabContentContainer = document.getElementById('tabContentContainer');
        if (tabContentContainer) {
            tabContentContainer.innerHTML = html;
            if (sectionId != null) {
                // 섹션을 보여주는 로직을 이곳에서 호출
                console.info("이게됨?");
                if(sectionId == 'section1') {
                showSection(sectionId, "sectionBtn1");
                }
                if(sectionId == 'section2-1') {
                showSection(sectionId, "sectionBtn1");
                }
                if(sectionId == 'section3-1') {
                showSection(sectionId, "sectionBtn1");
                }
                if(sectionId == 'section4-1') {
                showSection(sectionId, "sectionBtn1");
                }
                if(sectionId == 'section5-1') {
                showSection(sectionId, "sectionBtn1");
                }
                // 이후에 추가적으로 필요한 작업 수행
                // 예: showFloor, showDep 등의 함수 호출
            } else {
                console.info("응안돼");
            }
        } else {
            console.error('tabContentContainer 요소를 찾을 수 없습니다.');
        }
    } catch (error) {
        console.error('Error fetching HTML file:', error);
    }
}

function showSection(sectionId, sectionBtnId) {
    console.log("Trying to show section with ID:", sectionId);
    var sections = document.getElementsByClassName('section');
    var btns = document.getElementsByClassName('sectionBtn');
    var sectionBtn = document.getElementById(sectionBtnId);

    console.log(sections);
    console.log(btns);
    console.log(sectionBtn);
        for (var i = 0; i < sections.length; i++) {
            sections[i].style.display = 'none';
        }
        for (var j = 0; j < btns.length; j++) {
            btns[j].classList.remove('sectionBtnClicked');
            console.log("답=" + btns[j].classList);
        }


    var selectedSection = document.getElementById(sectionId);
    if (selectedSection != null && sectionBtn != null) {
        selectedSection.style.display = 'flex';
        sectionBtn.classList.add('sectionBtnClicked');
        console.log("Displayed section with ID:", sectionId);
    } else {
        console.log("안됨");
    }
}

document.getElementById('infoTab').addEventListener('click', function () {
    showTab('infoTab');
    loadTabContent('medicalCenterIntro', 'section1');
});

document.getElementById('doctorTab').addEventListener('click', function () {
    loadTabContent('doctorMatching', 'section2-1');
});

document.getElementById('careGiverTab').addEventListener('click', function () {
    loadTabContent('careGiverMatching', 'section3-1');
});

document.getElementById('checkTab').addEventListener('click', function () {
    loadTabContent('ReservationCheck', 'section4-1');
});

document.getElementById('funeralTab').addEventListener('click', function () {
    loadTabContent('funeralService', 'section5-1');
});

var tabSelected = document.getElementsByClassName('selected-tab');
if (tabSelected.length > 0) {
    loadTabContent('medicalCenterIntro', 'section1');
}

function showFloor(sectionId, floorId) {
    console.log("Trying to show floor with ID:", sectionId);
    console.log("Trying to show floor with ID:", floorId);
    var selectedFloor = document.getElementById(floorId);
    var sections = document.getElementsByClassName('floor-sec');
    var floors = document.getElementsByClassName('floor');


        for (var i = 0; i < sections.length; i++) {
            sections[i].style.display = 'none';
        
        for (var j = 0; j < floors.length; j++) {
            floors[j].classList.remove('floorChecked');
            console.log(floors[j].classList);
        }
    }

    var selectedSection = document.getElementById(sectionId);
    console.log(selectedSection);
    if (selectedSection) {
        selectedSection.style.display = 'flex';
        selectedFloor.classList.add('floorChecked');
        console.log("Displayed floor with ID:", sectionId);
    } else {
        console.error("No floor found with ID:", sectionId);
    }
}

function showDep(sectionId, floorId) {
    console.log("Trying to show floor with ID:", sectionId);
    console.log("Trying to show floor with ID:", floorId);
    var selectedFloor = document.getElementById(floorId);
    var sections = document.getElementsByClassName('dep-sec');
    var floors = document.getElementsByClassName('dep-floor');

        for (var i = 0; i < sections.length; i++) {
            sections[i].style.display = 'none';
            sections[i].classList.remove('floorChecked1');
        
        for (var j = 0; j < floors.length; j++) {
            floors[j].classList.remove('floorChecked');
            console.log(floors[j].classList);
        }
    }

    var selectedSection = document.getElementById(sectionId);
    console.log(selectedSection);
    if (selectedSection) {
        selectedSection.style.display = 'inline';
        selectedSection.classList.add('floorChecked1');
        selectedFloor.classList.add('floorChecked');
        console.log("Displayed floor with ID:", sectionId);
    } else {
        console.error("No floor found with ID:", sectionId);
    }
}

// -------------------------------------------------------------------------------------------------
// 진료예약
    // 모든 버튼 요소와 콘텐츠 요소를 가져오기
    document.addEventListener("DOMContentLoaded", function() {
        try {
            // 모든 버튼 요소와 콘텐츠 요소를 가져오기
            const buttons = document.querySelectorAll(".reservation-toggleButton");
            const contents = document.querySelectorAll(".reservation-content");
            console.log("모든 버튼과 콘텐츠 요소를 가져오기 성공");
    
            // 초기 화면 설정
    
            // 각 버튼에 클릭 이벤트 핸들러 등록
            buttons.forEach(button => {
                button.addEventListener("click", function() {
                    try {
                        console.log("버튼 이벤트 핸들러 시작");
                        const target = this.getAttribute("data-target");
                        updateView(target);
                        console.log("버튼 이벤트 핸들러 성공: " + target);
                    } catch (error) {
                        console.error("버튼 이벤트 핸들러 오류:", error);
                    }
                });
            });
    
            // 화면을 업데이트하는 함수
            function updateView(target) {
                try {
                    // 모든 콘텐츠를 순회하면서 보이기/숨기기 설정
                    console.log("화면 업데이트 함수 시작");
                    contents.forEach(content => {
                        try {
                            console.log("콘텐츠 순회 시작");
                            content.style.display = content.id === `reservation-content${target}` ? "block" : "none";
                            console.log("콘텐츠 순회 성공: " + content.id);
                        } catch (error) {
                            console.error("콘텐츠 순회 오류:", error);
                        }
                    });
    
                    // 모든 버튼의 활성화 상태 초기화
                    buttons.forEach(button => {
                        try {
                            if (button.getAttribute("data-target") === target) {
                                button.classList.add("reservation-active");
                                console.log("버튼 활성화 설정 성공: " + button.getAttribute("data-target"));
                            } else {
                                button.classList.remove("reservation-active");
                            }
                        } catch (error) {
                            console.error("버튼 활성화 상태 초기화 오류:", error);
                        }
                    });
                    console.log("화면 업데이트 함수 성공: " + target);
                } catch (error) {
                    console.error("화면 업데이트 오류:", error);
                }
            }
        } catch (error) {
            console.error("초기 설정 오류:", error);
        }
    });
    