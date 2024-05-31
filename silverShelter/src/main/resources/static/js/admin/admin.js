document.getElementById("memberId").addEventListener("click", e => {

    // 버튼에 회원 번호 가져오기
    const memberNo = document.getElementById("memberId").name;

    console.log(memberNo);

    fetch("/admin/adminSelect", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : memberNo
    })
    .then(resp => resp.json())
    .then(result => {

        if(result == null) {
            alert("값 없음");
            e.preventDefault();
        } else {

            // console.log(result);



            const nameInput = document.getElementById("nameInput");
            nameInput.innerText = result.memberName;

            const idInput = document.getElementById("idInput");
            idInput.innerText = result.memberId;

            const telInput = document.getElementById("telInput");
            telInput.innerText = result.memberTel;

            const gdtelInput = document.getElementById("gdtelInput");
            gdtelInput.innerText = result.guardianTel;

            const postCode = document.getElementById("postCode");
            postCode.innerText = result.postCode;

            const address = document.getElementById("address");
            address.innerText = result.address;

            const detailAddress = document.getElementById("detailAddress");
            detailAddress.innerText = result.detailAddress;

            const caregiversName = document.getElementById("caregiversName");
            caregiversName.innerText = result.caregiversName;

            const roomNo = document.getElementById("roomNo");
            roomNo.innerText = result.roomNo;

        }
    })

})


// 회원 목록 조회 게시글에서 회원버튼을 클릭시 오른쪽 상세조회에 값이 출력되는 함수
// function memberDetail() {

//     const memberNo = document.getElementById("memberBtn").getAttribute("data-memberNo");
//     const cp = document.getElementById("memberBtn").getAttribute("data-cp");

//     const obj = {
//         "memberNo" : memberNo,
//         "cp" : cp
//     };

//     console.log("memberNo = ", memberNo);
//     console.log("cp = ", cp);


//     // js에서 문자열 내에 변수를 포함하려면 ''나 ""대신 ``(백틱)을 써야한다
//     fetch("/admin/adminSelect", {
//         method : "POST",
//         headers : {"Content-Type" : "application/json"},
//         body : JSON.stringify(obj)
//     })
//     .then(resp => resp.json())
//     .then(result => {


//         document.getElementById("nameInput").innerText = result.memberName;
//         document.getElementById("idInput").innerText = result.idInput;
//         document.getElementById("telInput").innerText = result.memberTel;
//         document.getElementById("gdtelInput").innerText = result.guardianTel;
//         document.getElementById("postCode").innerText = result.postCode;
//         document.getElementById("address").innerText = result.address;
//         document.getElementById("detailAddress").innerText = result.detailAddress;
//         document.getElementById("caregiversName").innerText = result.caregiversName;
//         document.getElementById("roomNo").innerText = result.roomNo;



//     })

// }

// const memberRows = document.querySelectorAll("#memberBtn");

// memberRows.forEach(row => {
//     row.addEventListener("click", function() {

//         const memberName = document.getElementById("memberName")
//         const memberNo = memberName.getAttribute("data-memberNo");
//         const cp = memberName.getAttribute("data-cp");

//         const obj = {
//             "memberNo" : memberNo,
//             "cp"       : cp
//         };

//         console.log(memberNo);
//         console.log(cp);


//     })
// })

