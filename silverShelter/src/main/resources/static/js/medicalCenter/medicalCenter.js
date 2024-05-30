function showSection(sectionId, sectionBtnId) {
    console.log("Trying to show section with ID:", sectionId);
    var sections = document.getElementsByClassName('section');
    var btns = document.getElementsByClassName('sectionBtn');
    var sectionBtn = document.getElementById(sectionBtnId);

    if (sections && btns) {
        for (var i = 0; i < sections.length; i++) {
            sections[i].style.display = 'none';
        }
        for (var j = 0; j < btns.length; j++) {
            btns[j].classList.remove('sectionBtnClicked');
            console.log("답=" + btns[j].classList);
        }
    }

    var selectedSection = document.getElementById(sectionId);
    if (selectedSection && sectionBtn) {
        selectedSection.style.display = 'flex';
        sectionBtn.classList.add('sectionBtnClicked');
        console.log("Displayed section with ID:", sectionId);
    } else {
        console.log("안됨");
    }
}

function showFloor(sectionId, floorId) {
    console.log("Trying to show floor with ID:", sectionId);
    console.log("Trying to show floor with ID:", floorId);
    var selectedFloor = document.getElementById(floorId);
    var sections = document.getElementsByClassName('floor-sec');
    var floors = document.getElementsByClassName('floor');

    if (sections && floors) {
        for (var i = 0; i < sections.length; i++) {
            sections[i].style.display = 'none';
        }
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

    if (sections && floors) {
        for (var i = 0; i < sections.length; i++) {
            sections[i].style.display = 'none';
            sections[i].classList.remove('floorChecked1');
        }
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

document.getElementById('infoTab').addEventListener('click', function () {
    showTab('infoTab');
    loadTabContent('medicalCenterIntro', 'section1');
});

document.getElementById('doctorTab').addEventListener('click', function () {
    showTab('doctorTab');
    loadTabContent('doctorMatching');
});

document.getElementById('careGiverTab').addEventListener('click', function () {
    showTab('careGiverTab');
    loadTabContent('careGiverMatching');
});

document.getElementById('checkTab').addEventListener('click', function () {
    showTab('checkTab');
    loadTabContent('ReservationCheck');
});

document.getElementById('funeralTab').addEventListener('click', function () {
    showTab('funeralTab');
    loadTabContent('funeralService');
});

async function loadTabContent(htmlFile, sectionId = null) {
    try {
        const response = await fetch(htmlFile);
        const html = await response.text();
        const tabContentContainer = document.getElementById('tabContentContainer');
        if (tabContentContainer) {
            tabContentContainer.innerHTML = html;
            if (sectionId != null) {
                showSection(sectionId, "sectionBtn1");
                console.info("이게됨?");
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

var tabSelected = document.getElementsByClassName('selected-tab');
if (tabSelected.length > 0) {
    showTab('infoTab');
    loadTabContent('medicalCenterIntro', 'section1');
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

var sectionSelected = document.getElementsByClassName('sectionBtnClicked');
if (sectionSelected.length > 0) {
    showSection('section1', 'sectionBtn1');
    showFloor('floor-sec1', '1f');
}
