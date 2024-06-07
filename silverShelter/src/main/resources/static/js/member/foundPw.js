document.addEventListener("DOMContentLoaded", () => {
    const memberId = document.querySelector("#memberId");
    const memberTel = document.querySelector("#memberTel");
    const foundPwBtn = document.querySelector("#foundPwBtn");

    const pwChangeModal = document.querySelector("#pwChangeModal");
    const changePw = document.querySelector("#changePw");
    const changePwConfirm = document.querySelector("#changePwConfirm");
    const changePwBtn = document.querySelector("#changePwBtn");
    const changePwForm = document.querySelector("#changePwForm");

    function closeModal() {
        pwChangeModal.style.display = "none";
    }

    function openModal() {
        pwChangeModal.style.display = "flex";
    }

    // 초기 로드 시 모달 창을 닫음
    closeModal();

    foundPwBtn.addEventListener("click", async () => {
        try {
            const response = await fetch('/member/checkIdTel', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    memberId: memberId.value,
                    memberTel: memberTel.value
                })
            });
            const data = await response.text();
            if (data.trim() === "success") {
                openModal();
            } else {
                alert('아이디 또는 전화번호가 일치하지 않습니다');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    });

    document.querySelector(".close").addEventListener("click", closeModal);

    changePwForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        if (changePw.value !== changePwConfirm.value) {
            alert("입력하신 '변경할 비밀번호'와 '변경할 비밀번호 확인'이 일치하지 않습니다.");
            return;
        }
        
        try {
            const response = await fetch('/member/updatePw', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    memberId: memberId.value,
                    newPw: changePw.value
                })
            });
            const data = await response.json();
            if (data.success) {
                alert("비밀번호가 성공적으로 변경되었습니다.");
                closeModal();
                location.href = "/member/login";
            } else {
                alert("비밀번호 변경에 실패했습니다.");
            }
        } catch (error) {
            console.error('Error:', error);
        }
    });

    window.closeModal = closeModal;
    window.changePassword = changePassword;
});
