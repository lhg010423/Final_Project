document.getElementById("memberId").addEventListener("click", e => {

    // 버튼에 회원 번호 가져오기
    const memberNo = document.getElementById("memberId").name;

    console.log(memberNo);

    fetch("/admin/adminSelect", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify({"memberNo" : memberNo})
    })
    .then(resp => resp.json())
    .then(result => {

        if(result == null) {
            alert("값 없음");
            e.preventDefault();
        } else {

            console.log(result);



            const nameInput = document.getElementById("nameInput");
            nameInput.value = result.memberName;

            const idInput = document.getElementById("idInput");
            idInput.innerText = result.memberId;


        }
    })



})