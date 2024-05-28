// 드롭다운
function showSection(sectionId) {
    // 모든 섹션을 숨깁니다.
    var sections = document.getElementsByClassName('section');
    for (var i = 0; i < sections.length; i++) {
        sections[i].style.display = 'none';
    }
    
    // 선택된 섹션만 보이게 합니다.
    var selectedSection = document.getElementById(sectionId);
    if (selectedSection) {
        selectedSection.style.display = 'flex';
    }
}

// 페이지 로드 시 초기 설정
document.addEventListener("DOMContentLoaded", function() {
    loadTabContent('medicalCenterIntro.html');
    // 초기에 보여질 탭 설정
    var defaultTab = 'infoTab'; // 초기 탭 설정
    showTab(defaultTab); // 초기 탭에 대한 내용 표시
        // 드롭다운 메뉴의 변경 이벤트를 처리합니다.
        var selectElement = document.getElementById('section-select');
        if (selectElement) { // 요소가 존재하는지 확인
            selectElement.addEventListener('change', function() {
                var sectionId = this.value;
                showSection(sectionId);
            });
        }    
});

// 탭 클릭 이벤트 처리
document.getElementById('infoTab').addEventListener('click', function() {
    showTab('infoTab');
    loadTabContent('medicalCenterIntro.html'); // 'info.html' 파일을 가져와서 표시
});

document.getElementById('doctorTab').addEventListener('click', function() {
    showTab('doctorTab');
    loadTabContent('doctorMatching.html'); // 'doctor.html' 파일을 가져와서 표시
});

document.getElementById('careGiverTab').addEventListener('click', function() {
    showTab('careGiverTab');
    loadTabContent('careGiverMatching.html'); // 'caregiver.html' 파일을 가져와서 표시
});

document.getElementById('checkTab').addEventListener('click', function() {
    showTab('checkTab');
    loadTabContent('ReservationCheck.html'); // 'check.html' 파일을 가져와서 표시
});

document.getElementById('funeralTab').addEventListener('click', function() {
    showTab('funeralTab');
    loadTabContent('funeralService.html'); // 'funeral.html' 파일을 가져와서 표시
});

// 외부 HTML 파일을 가져와서 탭 내용을 표시하는 함수
function loadTabContent(htmlFile) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', htmlFile, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById('tabContentContainer').innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}

function showTab(tabId) {
    // 모든 탭의 선택 상태를 해제합니다.
    var tabs = document.getElementsByClassName('tab');
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].classList.remove('selected-tab');
    }

    // 선택된 탭에만 선택 상태를 추가합니다.
    var selectedTab = document.getElementById(tabId);
    if (selectedTab) {
        selectedTab.classList.add('selected-tab');
    }
}
