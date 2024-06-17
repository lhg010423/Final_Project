// 추가 버튼 
const addTarget = document.querySelector("#addTarget");

// 팝업 레이어 
const addTargetPopupLayer = document.querySelector("#addTargetPopupLayer");

// 닫기 버튼
const closeBtn =document.querySelector("#closeBtn");

// 사용자 검색결과
const targetInput = document.querySelector("#targetInput");

// 검색결과 
const resultArea = document.querySelector("#resultArea");

// 채팅방 영역
const chattingContent = document.querySelector(".chatting-content");

// 채팅방 선택 전 메세지
const prevMessage = document.querySelector(".prev-message");

let selectChattingNo; // 선택한 채팅방 번호
let selectTargetNo; // 현재 채팅 대상
let selectTargetName; // 대상의 이름 

// 검색 팝업 레이어 열기
addTarget.addEventListener("click", e => {
	addTargetPopupLayer.classList.toggle("popup-layer-close");
	targetInput.focus();
});

// 검색 팝업 레이어  닫기
closeBtn.addEventListener("click", e => {
	addTargetPopupLayer.classList.toggle("popup-layer-close");
	resultArea.innerHTML = "";
});

// 사용자 검색(ajax)
targetInput.addEventListener("input", e => {

	const query = e.target.value.trim();

	// 입력된게 없을 때
	if(query.length == 0){
		resultArea.innerHTML = ""; // 이전 검색 결과 비우기
		return;
	}
	// 입력된게 있을 때
	if(query.length > 0){
		fetch("/chatting/selectTarget?query="+query)
		.then(resp => resp.json())
		.then(list => {
			console.log(list);

			resultArea.innerHTML = ""; // 이전 검색 결과 비우기

			if(list.length == 0){
				const li = document.createElement("li");
				li.classList.add("result-row");
				li.innerText = "일치하는 회원이 없습니다";
				resultArea.append(li);
			}

			for(let member of list){
				// li요소 생성(한 행을 감싸는 요소)
				const li = document.createElement("li");
				li.classList.add("result-row");
				li.setAttribute("data-id", member.memberNo);

				let memberName = member.memberName;
				let email = member.memberEmail;

				const span = document.createElement("span");
				span.innerHTML = `${memberName} ${email}`.replace(query, `<mark>${query}</mark>`);

				// 요소 조립(화면에 추가)
				li.append(span);
				resultArea.append(li);

				// li요소에 클릭 시 채팅방에 입장하는 이벤트 추가
				li.addEventListener('click', chattingEnter);
			}

		})
		.catch(err => console.log(err) );
	}
});

// 채팅방 입장 또는 선택 함수 
function chattingEnter(e){

	console.log(e.target);
	console.log(e.currentTarget);

	const targetNo = e.currentTarget.getAttribute("data-id");

	fetch("/chatting/enter?targetNo="+targetNo)
	.then(resp => resp.text())
	.then(chattingNo => {
		console.log(chattingNo);

		selectRoomList(); // 채팅방 목록 조회

		setTimeout(()=>{
			const itemList = document.querySelectorAll(".chatting-item")
			for(let item of itemList) {		
				if(item.getAttribute("chat-no") == chattingNo){
					item.focus();
					item.click();
					addTargetPopupLayer.classList.toggle("popup-layer-close");
					targetInput.value = "";
					resultArea.innerHTML = "";
					return;
				}
			}

		}, 200);
	})
	.catch(err => console.log(err));
}

// 비동기로 채팅방 목록 조회
function selectRoomList(){

	fetch("/chatting/roomList")
	.then(resp => resp.json())
	.then(roomList => {
		console.log(roomList);

		// 채팅방 목록 출력 영역 선택
		const chattingList = document.querySelector(".chatting-list");

		//240617 채팅리스트 버튼사라지는거 해결 1
		const addTargetButton = document.getElementById("addTarget");
		// 채팅방 목록 지우기
		chattingList.innerHTML = "";
		
		//240617 채팅리스트 버튼사라지는거 해결 1
		chattingList.appendChild(addTargetButton);
		
		// 조회한 채팅방 목록을 화면에 추가
		for(let room of roomList){
			const li = document.createElement("li");
			li.classList.add("chatting-item");
			li.setAttribute("chat-no", room.chattingNo);
			li.setAttribute("target-no", room.targetNo);

			if(room.chattingNo == selectChattingNo){
				li.classList.add("select");
			}

			// item 부분
			const itemBody = document.createElement("div");
			itemBody.classList.add("item");

			const p = document.createElement("p");

			const targetName = document.createElement("span");
			targetName.classList.add("target-name");
			targetName.innerText = room.targetName;
			
			const recentSendTime = document.createElement("span");
			recentSendTime.classList.add("recent-send-time");
			recentSendTime.innerText = room.sendTime;
			
			
			p.append(targetName, recentSendTime);
			
			
			const div = document.createElement("div");
			
			const recentMessage = document.createElement("p");
			recentMessage.classList.add("recent-message");

			if(room.lastMessage != undefined){
				recentMessage.innerHTML = room.lastMessage;
			}
			
			div.append(recentMessage);

			itemBody.append(p,div);

			// 현재 채팅방을 보고있는게 아니고 읽지 않은 개수가 0개 이상인 경우 -> 읽지 않은 메세지 개수 출력
			if(room.notReadCount > 0 && room.chattingNo != selectChattingNo ){
			// if(room.chattingNo != selectChattingNo ){
				const notReadCount = document.createElement("p");
				notReadCount.classList.add("not-read-count");
				notReadCount.innerText = room.notReadCount;
				div.append(notReadCount);
			}else{

				// 현재 채팅방을 보고있는 경우
				// 비동기로 해당 채팅방 글을 읽음으로 표시
				fetch("/chatting/updateReadFlag",{
					method : "PUT",
					headers : {"Content-Type": "application/json"},
					body : JSON.stringify({"chattingNo" : selectChattingNo, "memberNo" : loginMemberNo})
				})
				.then(resp => resp.text())
				.then(result => console.log(result))
				.catch(err => console.log(err));

			}
			

			li.append(itemBody);
			chattingList.append(li);
		}

		roomListAddEvent();
	})
	.catch(err => console.log(err));

}

// 채팅 메세지 영역
const display = document.getElementsByClassName("display-chatting")[0];


// 채팅방 목록에 이벤트를 추가하는 함수 
function roomListAddEvent(){
	const chattingItemList = document.getElementsByClassName("chatting-item");
	
	for(let item of chattingItemList){
		item.addEventListener("click", e => {
	
			// 클릭한 채팅방의 번호 얻어오기
			//const id = item.getAttribute("id");
			//const arr = id.split("-");
			// 전역변수에 채팅방 번호, 상대 번호, 상태 프로필, 상대 이름 저장
			selectChattingNo = item.getAttribute("chat-no");
			selectTargetNo = item.getAttribute("target-no");

			selectTargetName = item.children[0].children[0].children[0].innerText;
			
			if (item.children[0].children[1].children[1] != undefined) {
				item.children[0].children[1].children[1].remove();
			}

			

			// 모든 채팅방에서 select 클래스를 제거
			for(let it of chattingItemList) it.classList.remove("select")
	
			// 현재 클릭한 채팅방에 select 클래스 추가
			item.classList.add("select");
	
			// 비동기로 메세지 목록을 조회하는 함수 호출
			selectChattingFn();
		});
	}
}
// 비동기로 메세지 목록을 조회하는 함수
function selectChattingFn() {

	fetch("/chatting/selectMessage?"+`chattingNo=${selectChattingNo}&memberNo=${loginMemberNo}`)
	.then(resp => resp.json())
	.then(messageList => {
		console.log(messageList);

		prevMessage.classList.add("display-none");
		chattingContent.classList.remove("display-none");

		// <ul class="display-chatting">
		const ul = document.querySelector(".display-chatting");

		ul.innerHTML = ""; // 이전 내용 지우기

		// 메세지 만들어서 출력하기
		for(let msg of messageList){
			//<li>,  <li class="my-chat">
			const li = document.createElement("li");

			// 보낸 시간
			const span = document.createElement("span");
			span.classList.add("chatDate");
			span.innerText = msg.sendTime;

			// 메세지 내용
			const p = document.createElement("p");
			p.classList.add("chat");
			p.innerHTML = msg.messageContent; // br태그 해석을 위해 innerHTML

			// 내가 작성한 메세지인 경우
			if(loginMemberNo == msg.senderNo){ 
				li.classList.add("my-chat");
				
				li.append(span, p);
				
			}else{ // 상대가 작성한 메세지인 경우
				li.classList.add("target-chat");
				
				const div = document.createElement("div");
				// 상대 이름
				const b = document.createElement("b");
				b.innerText = selectTargetName; // 전역변수

				const br = document.createElement("br");

				div.append(b, br, p, span);
				li.append(div);

			}

			ul.append(li);
			display.scrollTop = display.scrollHeight; // 스크롤 제일 밑으로
		}

	})
	.catch(err => console.log(err));

}

// sockjs를 이용한 WebSocket 구현

// 로그인이 되어 있을 경우에만
// /chattingSock 이라는 요청 주소로 통신할 수 있는  WebSocket 객체 생성
let chattingSock;

if(loginMemberNo != ""){
	chattingSock = new SockJS("/chattingSock");
}

// 채팅 입력

const send = document.getElementById("send");

const sendMessage = () => {
	const inputChatting = document.getElementById("inputChatting");

	if (inputChatting.value.trim().length == 0) {
		alert("채팅을 입력해주세요.");
		inputChatting.value = "";
	} else {
		var obj = {
			"senderNo": loginMemberNo,
			"targetNo": selectTargetNo,
			"chattingNo": selectChattingNo,
			"messageContent": inputChatting.value,
		};
		console.log(obj)

		// JSON.stringify() : 자바스크립트 객체를 JSON 문자열로 변환
		chattingSock.send(JSON.stringify(obj));

		inputChatting.value = "";
		
	}
	
}

// 엔터 == 제출
// 쉬프트 + 엔터 == 줄바꿈
inputChatting.addEventListener("keyup", e => {
	if(e.key == "Enter"){ 
		if (!e.shiftKey) {
			sendMessage();
		}
	}
})

// WebSocket 객체 chattingSock이 서버로 부터 메세지를 통지 받으면 자동으로 실행될 콜백 함수
chattingSock.onmessage = function(e) {
	// 메소드를 통해 전달받은 객체값을 JSON객체로 변환해서 obj 변수에 저장.
	const msg = JSON.parse(e.data);
	console.log(msg);


	// 현재 채팅방을 보고있는 경우
	if(selectChattingNo == msg.chattingNo){

		const ul = document.querySelector(".display-chatting");
	
		// 메세지 만들어서 출력하기
		//<li>,  <li class="my-chat">
		const li = document.createElement("li");
	
		// 보낸 시간
		const span = document.createElement("span");
		span.classList.add("chatDate");
		span.innerText = msg.sendTime;
	
		// 메세지 내용
		const p = document.createElement("p");
		p.classList.add("chat");
		p.innerHTML = msg.messageContent; // br태그 해석을 위해 innerHTML
	
		// 내가 작성한 메세지인 경우
		if(loginMemberNo == msg.senderNo){ 
			li.classList.add("my-chat");
			
			li.append(span, p);
			
		}else{ // 상대가 작성한 메세지인 경우
			li.classList.add("target-chat");
	
			const div = document.createElement("div");
	
			// 상대 이름
			const b = document.createElement("b");
			b.innerText = selectTargetName; // 전역변수
	
			const br = document.createElement("br");
	
			div.append(b, br, p, span);
			li.append(div);
	
		}
	
		ul.append(li)
		display.scrollTop = display.scrollHeight; // 스크롤 제일 밑으로
	}



	selectRoomList();
}

// 문서 로딩 완료 후 수행할 기능
document.addEventListener("DOMContentLoaded", ()=>{
	
	// 채팅방 목록에 클릭 이벤트 추가
	roomListAddEvent(); 

	// 보내기 버튼에 이벤트 추가
	send.addEventListener("click", sendMessage);
});


/* ------------------------------------------------------------------------- */
let mediaRecorder;
// 목소리가 저장될 빈 배열
let audioChunks = [];

let isRecording = false;
const recordButton = document.querySelector('#recordButton');

recordButton.addEventListener("click", async () => {
    if (!isRecording) {
        const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
        mediaRecorder = new MediaRecorder(stream);

        mediaRecorder.ondataavailable = event => {
            audioChunks.push(event.data);
        };

        mediaRecorder.onstop = () => {
            const audioBlob = new Blob(audioChunks, { type: 'audio/wav' });
            audioChunks = [];
            const audioUrl = URL.createObjectURL(audioBlob);
            audioPlayback.src = audioUrl; // 녹음된 오디오를 재생할 수 있도록 설정
            uploadAudioFile(audioBlob);
        };

        mediaRecorder.start();
        isRecording = true;
        recordButton.className = "fa-solid fa-record-vinyl"; // 클래스명 변경
    } else {
        // 녹음 정지
        mediaRecorder.stop();
        isRecording = false;
        // 여기에 추가하면
        recordButton.className = "fa-solid fa-microphone"; // 클래스명 변경
    }
});

// 서버로 오디오 파일을 업로드하는 함수
function uploadAudioFile(audioBlob) {
    // FormData 객체를 생성합니다. FormData는 폼 데이터를 쉽게 다룰 수 있게 해줍니다.
    const formData = new FormData(); 

    // 오디오 Blob 객체를 'upload'라는 이름으로 FormData에 추가합니다.
    formData.append('upload', audioBlob, 'recording.wav');

    // fetch API를 사용하여 '/sttChatting/stt' 경로로 POST 요청을 보냅니다.
    fetch('/sttChatting/stt', {
        method: 'POST', // HTTP 메서드를 POST로 설정합니다.
        body: formData   // 요청의 본문에 FormData 객체를 포함시킵니다.
    })
    .then(resp => resp.json())  // 서버로부터의 응답을 JSON 형태로 변환합니다.
    .then(result => {
        console.log('Success: ', result);  // 변환된 JSON 데이터를 콘솔에 출력합니다.

        let resultString = result.text;  // 서버에서 반환한 텍스트 결과를 저장합니다.
        let index = 0;

        // inputChatting 요소를 가져옵니다.
        const inputChatting = document.getElementById('inputChatting');

        // 100 밀리초마다 한 글자씩 텍스트를 입력하는 애니메이션을 구현합니다.
        let interval = setInterval(function() {
            inputChatting.value = resultString.substring(0, index); // 텍스트의 부분 문자열을 설정합니다.
            index++;

            // 텍스트의 모든 글자가 입력되면 인터벌을 중지합니다.
            if (index > resultString.length) {
                clearInterval(interval);
            }
        }, 100);  // 100 밀리초 간격으로 실행됩니다.
    })
    .catch(error => {
        console.error('에러 발생 ', error);  // 오류가 발생하면 콘솔에 에러 메시지를 출력합니다.
    });
}