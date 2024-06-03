// document.getElementById("examName").addEventListener("click", e => {
// 이렇게 안쓴 이유는 게시글은 반복문으로 리스트를 출력하는건데 고유값인 id로 하면
// 중복이 안된다. 버튼을 클래스명으로 불러와서 querySelectAll으로 가져온다 forEach문도 반복문때문에 쓰는거다

// 심사 서류 게시글 상세조회 버튼 클릭 - 회원 이름 클릭하면 작동됨
document.querySelectorAll(".examName").forEach(button => {
    button.addEventListener("click", e => {

        
        // 버튼의 심사 고유번호 가져오기
        // const examId = document.querySelector(".examName").name;
        // 아래처럼 안쓰고 위 처럼쓰면 게시글의 어떤 버튼을 눌러도 첫번째만 선택됨
        const examId = e.target.name;
        
        console.log(examId); // 잘됨, 값 잘 넘어옴
        
        fetch("/admin/adminDocument", {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : examId
        })
        .then(resp => resp.json())
        .then(result => {
            
            if(result == null) {
                alert("값 없음");
                e.preventDefault(); // 페이지 이동 막기
            } else {
                
                console.log(result);
                
                
                document.getElementById("nameSpan").innerText = result.examName;
                document.getElementById("telSpan").innerText = result.examPhone;
                
                
                // 상세페이지 버튼에 회원을 구분하는 심사 번호 값 넣기
                updateButtonValue("examId");


            }
            
        })
        
        
    })
})

function updateButtonValue(examId) {
    var button = document.getElementById("examStatusBtn");
    button.value = examId;

    fetch("/admin/adminDocument", {
        
    })

}

// function examStatus() {

//     var select = document.getElementById("examStatus");
//     var selectValue = select.options[select.selectedIndex].value;

//     fetch(`/admin/adminDocument?key=${selectValue}`, {
//         method : "GET",
//         headers : {"Content-Type" : "application/json"}
//     })
//     .then(resp => resp.json())
//     .then(result => {

//         if(result == null) {
//             alert("값 없음");
//             return;
//         }


//     })

// }
