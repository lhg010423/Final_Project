document.getElementById("examName").addEventListener("click", e => {

    // 버튼의 심사 고유번호 가져오기
    const examId = document.getElementById("examName").name;

    console.log(examId); // 잘됨

    fetch("/admin/adminDocument", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : examId
    })
    .then(resp => resp.json())
    .then(result => {

        if(result = null) {
            alert("값 없음");
            e.preventDefault(); // 페이지 이동 막기
        } else {

            console.log(result);

            const name = document.getElementById("nameSpan");
            name.innerText = result.examName;




        }





    })



})