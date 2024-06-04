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
            body : JSON.stringify({"examId" : examId})
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
                updateButtonValue(examId);


            }
            
        })
        
        
    })
})

function updateButtonValue(examId) {
    console.log("버튼에서 ",examId);
    var button = document.getElementById("examStatusBtn");
    button.name = examId;
}



    
//  지우면 안된 아직 하는중
//     document.getElementById("examStatusBtn").addEventListener("click", {
        
//         fetch("/admin/adminDocument", {
//             method : "UPDATE",
//             headers : {"Content-Type" : "application/json"},
//             body : examId
//         })
//         .then(resp => resp.json())
//         .then(result => {
            
//             if(result == null) {
//                 alert("변경 실패");
//             }
//             else {
//             }
            
//         }
        
//     })
    
    
// }

// 수정함
// function updateButtonValue(examId) {
//     var button = document.getElementById("examStatusBtn");
//     button.value = examId;
//     console.log(examId);
//     //  지우면 안된 아직 하는중
//     document.getElementById("examStatusBtn").addEventListener("click", function() {
        
//         fetch("/admin/adminDocument", {
//             method: "UPDATE",
//             headers: {"Content-Type": "application/json"},
//             body: JSON.stringify({ examId: examId })
//         })
//         .then(resp => resp.json())
//         .then(result => {
//             if(result == null) {
//                 alert("변경 실패");
//             } else {
//                 alert("변경 성공");
//             }
//         });
//     });
// }

const examStatusBtn= document.querySelector("#examStatusBtn");

examStatusBtn.addEventListener("click", (e) => {
    

    fetch("/admin/updateAdminDocument", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body: JSON.stringify({"examId" : e.target.name})
    })
    .then(resp => resp.json())
    .then(result => {

        if(result == 0) {
            alert("서류심사 실패")
        
        } else{
            alert("서류 심사 통과")

            fetch("/admin/signUpAdminDocument",{
                method : "POST",
                headers : {"Content-Type" : "application/json"},
                body: JSON.stringify({"examId" : e.target.name})              
            })
            .then(resp2 => resp2.json())
            .then(count => {
                console.log("값이 어떻게 넘어오려나",count);
                if(count == 0){
                    alert("이메일 전송 실패..")

                } else {
                    alert("이메일이 전송되었습니다.")

                }
            })

        }

        location.href = location.pathname;
    })

    
});

