function showSection(sectionId) {
    console.log("Trying to show section with ID:", sectionId);
    // 모든 섹션을 숨깁니다.
    var sections = document.getElementsByClassName('section');
    for (var i = 0; i < sections.length; i++) {
        sections[i].style.display = 'none';
    }

    // 선택된 섹션만 보이게 합니다.
    var selectedSection = document.getElementById(sectionId);
    if (selectedSection) {
        selectedSection.style.display = 'flex';
        console.log("Displayed section with ID:", sectionId);
    } else {
        console.error("No section found with ID:", sectionId);
    }
}

function showFloor(sectionId) {
    console.log("Trying to show section with ID:", sectionId);
    // 모든 섹션을 숨깁니다.
    var sections = document.getElementsByClassName('sec');
    for (var i = 0; i < sections.length; i++) {
        sections[i].style.display = 'none';
    }

    // 선택된 섹션만 보이게 합니다.
    var selectedSection = document.getElementById(sectionId);
    if (selectedSection) {
        selectedSection.style.display = 'flex';
        console.log("Displayed section with ID:", sectionId);
    } else {
        console.error("No section found with ID:", sectionId);
    }
}

// 탭 클릭 이벤트 처리
document.getElementById('infoTab').addEventListener('click', function() {
    showTab('infoTab');
    loadTabContent('medicalCenterIntro.html', 'section1');
});

document.getElementById('doctorTab').addEventListener('click', function() {
    showTab('doctorTab');
    loadTabContent('doctorMatching.html');
});

document.getElementById('careGiverTab').addEventListener('click', function() {
    showTab('careGiverTab');
    loadTabContent('careGiverMatching.html');
});

document.getElementById('checkTab').addEventListener('click', function() {
    showTab('checkTab');
    loadTabContent('ReservationCheck.html');
});

document.getElementById('funeralTab').addEventListener('click', function() {
    showTab('funeralTab');
    loadTabContent('funeralService.html');
});

async function loadTabContent(htmlFile, sectionId = null) {
    try {
        const response = await fetch(htmlFile);
        const html = await response.text();
        const tabContentContainer = document.getElementById('tabContentContainer');
        if (tabContentContainer) {
            tabContentContainer.innerHTML = html;
            if (sectionId) {
                showSection(sectionId);
                console.info("이게됨?")
            }
        } else {
            console.error('tabContentContainer 요소를 찾을 수 없습니다.');
        }
    } catch (error) {
        console.error('Error fetching HTML file:', error);
    }
}

var tabSelected = document.getElementsByClassName('selected-tab');
if(tabSelected) {
    showTab('infoTab');
    loadTabContent('medicalCenterIntro.html', 'section1');
}

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

function showBtn(sectionId) {
    var tabs = document.getElementsByClassName('sectionBtn');
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].classList.remove('sectionBtnClicked');
    }

    var selectedTab = document.getElementById(sectionId);
    if (selectedTab) {
        selectedTab.classList.add('sectionBtnClicked');
    }
}

var sectionSelected = document.getElementsByClassName('sectionBtnClicked');
if(sectionSelected) {
    showBtn('sectionBtn1');
    showSection('sec1');
}
if(document.getElementById('sectionBtn1')){
document.getElementById('sectionBtn1').addEventListener('click', function() {
    showBtn('sectionBtn1');
});}
if(document.getElementById('sectionBtn2')){
document.getElementById('sectionBtn2').addEventListener('click', function() {
    showBtn('sectionBtn2');
});}