document.querySelectorAll(".caregiversId").forEach(button => {
    button.addEventListener("click", e => {

        const caregiversNo = e.target.name;

        console.log(caregiversNo);

        fetch("/admin/caregivers", {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : caregiversNo
            // body : JSON.stringify({"caregiversNo" : caregiversNo})
        })
        .then(resp => resp.json())
        .then(result => {

            // console.log(result);

            if(result == null) {
                alert("값 없음");
                e.preventDefault();
            } else {

                document.getElementById("no").innerText = caregiversNo;
                document.getElementById("name").innerText = result.caregiversName;
                document.getElementById("age").innerText = result.caregiversAge;
                document.getElementById("gender").innerText = result.caregiversGender;
                document.getElementById("tel").innerText = result.caregiversTel;
                document.getElementById("experience").innerText = result.caregiversExperience;
                document.getElementById("workHours").innerText = result.caregiversWorkHours;
                document.getElementById("role").innerText = result.caregiversRole;





            }

        })

    })
})