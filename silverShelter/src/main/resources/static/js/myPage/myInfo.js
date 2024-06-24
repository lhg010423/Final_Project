/* 페이지네이션 js*/
document.addEventListener("DOMContentLoaded", function() {
		
    var links = document.querySelectorAll('a[data-form]');
    
	links.forEach(function(link) {
        
		link.addEventListener('click', function(event) {
            
			event.preventDefault();
            
			var formId = link.getAttribute('data-form');
            
			var formContainer = document.getElementById(formId);
            
			document.querySelectorAll('.form-container').forEach(function(container) {
            
				    container.style.display = 'none';
            
			});
			
            formContainer.style.display = 'block';
        });
    });
});
/*페이지네이션 js*/


document.addEventListener("DOMContentLoaded", function() {
	
    const checkObj = {
        "memberPw": true,
        "memberPwConfirm": true,
        "memberTel": true,
        "guardianTel": true,
        "memberAddress": false
    };

    // 비밀번호 유효성 검사
    const memberPw = document.querySelector("#memberPw");
    const memberPwConfirm = document.querySelector("#memberPwConfirm");
    const pwMessage = document.querySelector("#pwMessage");

    const checkPw = () => {
        if (memberPw.value === memberPwConfirm.value) {
            pwMessage.innerText = "* 비밀번호가 일치합니다";
            pwMessage.classList.add("confirm");
            pwMessage.classList.remove("error");
            checkObj.memberPwConfirm = true;
        } else {
            pwMessage.innerText = "* 비밀번호가 일치하지 않습니다";
            pwMessage.classList.add("error");
            pwMessage.classList.remove("confirm");
            checkObj.memberPwConfirm = false;
        }
    };

    memberPw.addEventListener("input", e => {
        const inputPw = e.target.value;

        if (inputPw.trim().length === 0) {
            pwMessage.innerText = "* 영어,숫자,특수문자(!,@,#,-,_) 6~20글자 사이로 입력해주세요";
            pwMessage.classList.remove("confirm", "error");
            checkObj.memberPw = false;
            memberPw.value = "";
            return;
        }

        const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;
        if (!regExp.test(inputPw)) {
            pwMessage.innerText = "* 비밀번호가 유효하지 않습니다";
            pwMessage.classList.add("error");
            pwMessage.classList.remove("confirm");
            checkObj.memberPw = false;
            return;
        }

        pwMessage.innerText = "* 유효한 형식입니다";
        pwMessage.classList.add("confirm");
        pwMessage.classList.remove("error");
        checkObj.memberPw = true;

        if (memberPwConfirm.value.length > 0) {
            checkPw();
        }
    });

    memberPwConfirm.addEventListener("input", () => {
        if (checkObj.memberPw) {
            checkPw();
        } else {
            checkObj.memberPwConfirm = false;
        }
    });

    // 보호자 전화번호 유효성 검사
    const guardianTel = document.querySelector("#guardianTel");
    const guardianTelMessage = document.querySelector("#guardianTelMessage");

    guardianTel.addEventListener("input", e => {
        const inputGuardianTel = e.target.value;

        if (inputGuardianTel.trim().length === 0) {
            guardianTelMessage.innerText = "* 전화번호를 입력해주세요.(- 포함)";
            guardianTelMessage.classList.remove("confirm", "error");
            checkObj.guardianTel = false;
            return;
        }

        const regExp = /^01[0-9]{1}-[0-9]{3,4}-[0-9]{4}$/;
        if (!regExp.test(inputGuardianTel)) {
            guardianTelMessage.innerText = "* 유효하지 않은 전화번호 형식입니다";
            guardianTelMessage.classList.add("error");
            guardianTelMessage.classList.remove("confirm");
            checkObj.guardianTel = false;
            return;
        }

        guardianTelMessage.innerText = "* 사용할 수 있는 전화번호 형식입니다.";
        guardianTelMessage.classList.add("confirm");
        guardianTelMessage.classList.remove("error");
        checkObj.guardianTel = true;
    });

    // 전화번호 유효성 검사
    const memberTel = document.querySelector("#memberTel");
    const telMessage = document.querySelector("#telMessage");

    memberTel.addEventListener("input", e => {
        const inputTel = e.target.value;

        if (inputTel.trim().length === 0) {
            telMessage.innerText = "* 전화번호를 입력해주세요. (-제외)";
            telMessage.classList.remove("confirm", "error");
            checkObj.memberTel = false;
            memberTel.value = "";
            return;
        }

        const regExp = /^01[0-9]{1}-[0-9]{3,4}-[0-9]{4}$/;
        
        if (!regExp.test(inputTel)) {
            telMessage.innerText = "* 유효하지 않은 전화번호 형식입니다";
            telMessage.classList.add("error");
            telMessage.classList.remove("confirm");
            checkObj.memberTel = false;
            return;
        }

        telMessage.innerText = "* 유효한 전화번호 형식입니다.";
        telMessage.classList.add("confirm");
        telMessage.classList.remove("error");
        checkObj.memberTel = true;
    });

    // 주소 유효성 검사
    const memberAddress = document.querySelectorAll("[name='memberAddress']");
    const addressFields = {
        postcode: memberAddress[0],
        roadAddress: memberAddress[1],
        detailAddress: memberAddress[2]
    };

    const checkAddress = () => {
        const addr0 = addressFields.postcode.value.trim().length > 0;
        const addr1 = addressFields.roadAddress.value.trim().length > 0;
        const addr2 = addressFields.detailAddress.value.trim().length > 0;

        if ( (addr0 && addr1) || (addr0 && addr2) || (addr1 && addr2) ) {
            checkObj.memberAddress = true;
        } else {
            checkObj.memberAddress = false;
        }
    };

    Object.values(addressFields).forEach(field => {
        field.addEventListener("input", checkAddress);
    });

    // 다음 주소 API 활용하기
    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                let addr = '';
                if (data.userSelectedType === 'R') {
                    addr = data.roadAddress;
                } else {
                    addr = data.jibunAddress;
                }

                document.getElementById('postcode').value = data.zonecode;
                document.getElementById("roadAddress").value = addr;
                document.getElementById("detailAddress").focus();
            }
        }).open();
    }

    document.querySelector("#searchAddress").addEventListener("click", execDaumPostcode);

    // 폼 제출 시 유효성 검사
    const infoForm = document.querySelector("#infoForm");
    infoForm.addEventListener("submit", e => {
        for (let key in checkObj) {
            if (!checkObj[key]) {
                let str;
                switch (key) {
                    case "memberPw":
                        str = "비밀번호가 유효하지 않습니다";
                        break;
                    case "memberPwConfirm":
                        str = "비밀번호 확인이 유효하지 않습니다";
                        break;
                    case "memberTel":
                        str = "전화번호가 유효하지 않습니다";
                        break;
                    case "guardianTel":
                        str = "보호자 전화번호가 유효하지 않습니다";
                        break;
                    case "memberAddress":
                        str = "주소가 유효하지 않습니다";
                        break;
                }
                    alert(str);
                    e.preventDefault();

            if(key == 'memberAddress') {
                	
            	const memberAddress = document.querySelectorAll(`[name='${key}']`);
		            /* console.log(memberAddress[0].value);
    	            console.log(memberAddress[1].value);
                    console.log(memberAddress[2].value;) */
                if(memberAddress[0].value == 0) {
                        memberAddress[0].focus();
                    } else if(memberAddress[1].value == 0) {
                        memberAddress[1].focus();
	                } else if(memberAddress[2].value == 0) {
                        memberAddress[2].focus();
                    }

				} else {
                	document.querySelector('#${key}').focus();
				}
                
                e.preventDefault();
                return;
            }
        }
    });
});



