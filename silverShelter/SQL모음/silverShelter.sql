﻿CREATE TABLE "MEMBER" (
	"MEMBER_NO"	NUMBER		NOT NULL,
	"MEMBER_ID"	NVARCHAR2(20)		NOT NULL,
	"MEMBER_PW"	NVARCHAR2(100)		NOT NULL,
	"MEMBER_NAME"	NVARCHAR2(20)		NOT NULL,
	"MEMBER_EMAIL"	NVARCHAR2(30)		NOT NULL,
	"MEMBER_ADDRESS"	NVARCHAR2(100)		NULL,
	"MEMBER_TEL"	NVARCHAR2(13)		NOT NULL,
	"GUARDIAN_TEL"	NVARCHAR2(13)		NOT NULL,
	"ENROLL_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"MEMBER_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"MEMBER_ROLE"	NUMBER	DEFAULT 2	NOT NULL,
	"CAREGIVERS_NO"	NUMBER		NULL,
	"ROOM_NO"	NUMBER		NULL
);

COMMENT ON COLUMN "MEMBER"."MEMBER_NO" IS '회원번호';

COMMENT ON COLUMN "MEMBER"."MEMBER_ID" IS '회원 아이디';

COMMENT ON COLUMN "MEMBER"."MEMBER_PW" IS '회원 비밀번호';

COMMENT ON COLUMN "MEMBER"."MEMBER_NAME" IS '회원 이름';

COMMENT ON COLUMN "MEMBER"."MEMBER_EMAIL" IS '회원 이메일';

COMMENT ON COLUMN "MEMBER"."MEMBER_ADDRESS" IS '회원 주소';

COMMENT ON COLUMN "MEMBER"."MEMBER_TEL" IS '회원 전화번호';

COMMENT ON COLUMN "MEMBER"."GUARDIAN_TEL" IS '보호자 전화번호';

COMMENT ON COLUMN "MEMBER"."ENROLL_DATE" IS '회원 가입일';

COMMENT ON COLUMN "MEMBER"."MEMBER_DEL_FL" IS '회원탈퇴여부(Y/N)';

COMMENT ON COLUMN "MEMBER"."MEMBER_ROLE" IS '관리자 계정 판별(관리자: 1, 일반: 2)';

COMMENT ON COLUMN "MEMBER"."CAREGIVERS_NO" IS '요양사 번호';

COMMENT ON COLUMN "MEMBER"."ROOM_NO" IS '방 번호';

CREATE TABLE "BOARD" (
	"BOARD_NO"	NUMBER		NOT NULL,
	"BOARD_TITLE"	NVARCHAR2(100)		NOT NULL,
	"BOARD_CONTENT"	NVARCHAR2(2000)		NOT NULL,
	"BOARD_WRITE_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"BOARD_UPDATE_DATE"	DATE		NULL,
	"READ_COUNT"	NUMBER	DEFAULT 0	NOT NULL,
	"BOARD_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"LIKE_COUNT"	NUMBER	DEFAULT 0	NOT NULL,
	"BOARD_YN"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"BOARD_CODE"	NUMBER		NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "BOARD"."BOARD_NO" IS '게시글 번호(PK)';

COMMENT ON COLUMN "BOARD"."BOARD_TITLE" IS '게시글 제목';

COMMENT ON COLUMN "BOARD"."BOARD_CONTENT" IS '게시글 내용';

COMMENT ON COLUMN "BOARD"."BOARD_WRITE_DATE" IS '게시글 작성일';

COMMENT ON COLUMN "BOARD"."BOARD_UPDATE_DATE" IS '게시글 마지막 수정일';

COMMENT ON COLUMN "BOARD"."READ_COUNT" IS '조회수';

COMMENT ON COLUMN "BOARD"."BOARD_DEL_FL" IS '게시글삭제여부(Y/N)';

COMMENT ON COLUMN "BOARD"."LIKE_COUNT" IS '좋아요 수';

COMMENT ON COLUMN "BOARD"."BOARD_YN" IS '승인 여부';

COMMENT ON COLUMN "BOARD"."BOARD_CODE" IS '게시판 코드 번호(PK)';

COMMENT ON COLUMN "BOARD"."MEMBER_NO" IS '작성한 회원번호';

CREATE TABLE "BOARD_TYPE" (
	"BOARD_CODE"	NUMBER		NOT NULL,
	"BOARD_NAME"	NVARCHAR2(10)		NOT NULL
);

COMMENT ON COLUMN "BOARD_TYPE"."BOARD_CODE" IS '게시판 코드 번호(PK)';

COMMENT ON COLUMN "BOARD_TYPE"."BOARD_NAME" IS '게시판 이름';

CREATE TABLE "COMMENT" (
	"COMMENT_NO"	NUMBER		NOT NULL,
	"COMMENT_CONT"	NVARCHAR2(500)		NOT NULL,
	"COMMENT_WRITE_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"COMMENT_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"PARENT_COMMENT_NO"	NUMBER		NULL
);

COMMENT ON COLUMN "COMMENT"."COMMENT_NO" IS '댓글 번호';

COMMENT ON COLUMN "COMMENT"."COMMENT_CONT" IS '댓글 내용';

COMMENT ON COLUMN "COMMENT"."COMMENT_WRITE_DATE" IS '댓글 작성일';

COMMENT ON COLUMN "COMMENT"."COMMENT_DEL_FL" IS '댓글 삭제 여부';

COMMENT ON COLUMN "COMMENT"."BOARD_NO" IS '게시글 번호(PK)';

COMMENT ON COLUMN "COMMENT"."MEMBER_NO" IS '회원번호';

COMMENT ON COLUMN "COMMENT"."PARENT_COMMENT_NO" IS '부모 댓글 번호';

CREATE TABLE "BOARD_IMG" (
	"BOARD_IMG_NO"	NUMBER		NOT NULL,
	"BOARD_IMG_PATH"	NVARCHAR2(200)		NOT NULL,
	"BOARD_IMG_ORIGINAL_NAME"	NVARCHAR2(50)		NOT NULL,
	"BOARD_IMG_RENAME"	NVARCHAR2(50)		NOT NULL,
	"BOARD_IMG_ORDER"	NUMBER		NULL,
	"BOARD_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "BOARD_IMG"."BOARD_IMG_NO" IS '이미지 번호(PK)';

COMMENT ON COLUMN "BOARD_IMG"."BOARD_IMG_PATH" IS '이미지 요청 경로';

COMMENT ON COLUMN "BOARD_IMG"."BOARD_IMG_ORIGINAL_NAME" IS '이미지 원본명';

COMMENT ON COLUMN "BOARD_IMG"."BOARD_IMG_RENAME" IS '이미지 변경명';

COMMENT ON COLUMN "BOARD_IMG"."BOARD_IMG_ORDER" IS '이미지 순서';

COMMENT ON COLUMN "BOARD_IMG"."BOARD_NO" IS '게시글 번호(PK)';

CREATE TABLE "DOCUMENT" (
	"DOCUMENT_NO"	NUMBER		NOT NULL,
	"DOCUMENT_ORIGINAL_NAME"	NVARCHAR2(50)		NOT NULL,
	"DOCUMENT_UPDATE_NAME"	NVARCHAR2(50)		NOT NULL,
	"DOCUMENT_EMAIL"	NVARCHAR2(20)		NOT NULL,
	"DOCUMENT_MEMBER_NAME"	NVARCHAR2(20)		NOT NULL,
	"DOCUMENT_PATH"	NVARCHAR2(200)		NOT NULL,
	"DOCUMENT_ENTRY_STATUS"	NVARCHAR2(2)	DEFAULT 'N'	NOT NULL,
	"DOC_TYPE_CODE"	NUMBER		NOT NULL,
	"ROOM_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "DOCUMENT"."DOCUMENT_NO" IS '서류 번호';

COMMENT ON COLUMN "DOCUMENT"."DOCUMENT_ORIGINAL_NAME" IS '서류 이름';

COMMENT ON COLUMN "DOCUMENT"."DOCUMENT_UPDATE_NAME" IS '서류 수정이름';

COMMENT ON COLUMN "DOCUMENT"."DOCUMENT_EMAIL" IS '이메일';

COMMENT ON COLUMN "DOCUMENT"."DOCUMENT_MEMBER_NAME" IS '회원 이름';

COMMENT ON COLUMN "DOCUMENT"."DOCUMENT_PATH" IS '서류경로';

COMMENT ON COLUMN "DOCUMENT"."DOCUMENT_ENTRY_STATUS" IS '승인 여부(Y/N)';

COMMENT ON COLUMN "DOCUMENT"."DOC_TYPE_CODE" IS '서류타입코드';

COMMENT ON COLUMN "DOCUMENT"."ROOM_NO" IS '방 번호';

CREATE TABLE "DOCTOR" (
	"DOCTOR_NO"	NUMBER		NOT NULL,
	"MAJOR_CODE"	NUMBER		NOT NULL,
	"DOCTOR_NAME"	NVARCHAR2(10)		NOT NULL,
	"DOCTOR_TEL"	NVARCHAR2(13)		NOT NULL
);

COMMENT ON COLUMN "DOCTOR"."DOCTOR_NO" IS '의사 번호';

COMMENT ON COLUMN "DOCTOR"."MAJOR_CODE" IS '의사 전공 코드';

COMMENT ON COLUMN "DOCTOR"."DOCTOR_NAME" IS '의사 이름';

COMMENT ON COLUMN "DOCTOR"."DOCTOR_TEL" IS '의사 연락처';

CREATE TABLE "ROOM" (
	"ROOM_NO"	NUMBER		NOT NULL,
	"ROOM_CODE"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "ROOM"."ROOM_NO" IS '방 번호';

COMMENT ON COLUMN "ROOM"."ROOM_CODE" IS '방 코드 번호';

CREATE TABLE "ROOM_TYPE" (
	"ROOM_CODE"	NUMBER		NOT NULL,
	"ROOM_TYPE_ID"	NVARCHAR2(20)		NOT NULL,
	"ROOM_PRICE"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "ROOM_TYPE"."ROOM_CODE" IS '방 코드 번호';

COMMENT ON COLUMN "ROOM_TYPE"."ROOM_TYPE_ID" IS '방 등급';

COMMENT ON COLUMN "ROOM_TYPE"."ROOM_PRICE" IS '방 가격';

CREATE TABLE "DOCTOR_MAJOR" (
	"MAJOR_CODE"	NUMBER		NOT NULL,
	"MAJOR_NAME"	NVARCHAR2(20)		NOT NULL
);

COMMENT ON COLUMN "DOCTOR_MAJOR"."MAJOR_CODE" IS '의사 전공 코드';

COMMENT ON COLUMN "DOCTOR_MAJOR"."MAJOR_NAME" IS '의사 전공 분야';

CREATE TABLE "DOCTOR_APPOINTMENT" (
	"DR_APPT_NO"	NUMBER		NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"DOCTOR_NO"	NUMBER		NOT NULL,
	"DR_APPT_TIME"	DATE		NOT NULL,
	"DR_APPT_STATUS"	CHAR(1)	DEFAULT 'N'	NOT NULL
);

COMMENT ON COLUMN "DOCTOR_APPOINTMENT"."DR_APPT_NO" IS '의사 일정 번호';

COMMENT ON COLUMN "DOCTOR_APPOINTMENT"."MEMBER_NO" IS '회원번호';

COMMENT ON COLUMN "DOCTOR_APPOINTMENT"."DOCTOR_NO" IS '의사 번호';

COMMENT ON COLUMN "DOCTOR_APPOINTMENT"."DR_APPT_TIME" IS '진료예약시간';

COMMENT ON COLUMN "DOCTOR_APPOINTMENT"."DR_APPT_STATUS" IS '진료 완료';

CREATE TABLE "SURVEYS" (
	"SURVEYS_NO"	NUMBER		NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"DATE_COMPLETED"	DATE	DEFAULT SYSDATE	NULL,
	"SURVEYS_GENDER"	NVARCHAR2(2)		NOT NULL,
	"SURVEYS_AGE"	NUMBER		NOT NULL,
	"SURVEYS_CAREER"	NUMBER		NOT NULL,
	"SURVEYS_WORK_HOURS"	NVARCHAR2(10)		NOT NULL,
	"SURVEYS_MAJOR_ROLE"	NVARCHAR2(20)		NOT NULL
);

COMMENT ON COLUMN "SURVEYS"."SURVEYS_NO" IS '설문조사 번호';

COMMENT ON COLUMN "SURVEYS"."MEMBER_NO" IS '회원번호';

COMMENT ON COLUMN "SURVEYS"."DATE_COMPLETED" IS '설문조사 완료 날짜';

COMMENT ON COLUMN "SURVEYS"."SURVEYS_GENDER" IS '성별(남: M, 여: F)';

COMMENT ON COLUMN "SURVEYS"."SURVEYS_WORK_HOURS" IS '근무시간(MOR, AFT, EVE)';

CREATE TABLE "CAREGIVERS" (
	"CAREGIVERS_NO"	NUMBER		NOT NULL,
	"CAREGIVERS_NAME"	NVARCHAR2(10)		NOT NULL,
	"CAREGIVERS_AGE"	NUMBER		NOT NULL,
	"CAREGIVERS_GENDER"	CHAR(1)		NOT NULL,
	"CAREGIVERS_TEL"	NVARCHAR2(13)		NOT NULL,
	"CAREGIVERS_EXPERIENCE"	NUMBER		NULL,
	"CAREGIVERS_WORK_HOURS"	NVARCHAR2(10)		NOT NULL
);

COMMENT ON COLUMN "CAREGIVERS"."CAREGIVERS_NO" IS '요양사 번호';

COMMENT ON COLUMN "CAREGIVERS"."CAREGIVERS_NAME" IS '요양사 이름';

COMMENT ON COLUMN "CAREGIVERS"."CAREGIVERS_AGE" IS '요양사 나이';

COMMENT ON COLUMN "CAREGIVERS"."CAREGIVERS_GENDER" IS '성별(남:M/여:F)';

COMMENT ON COLUMN "CAREGIVERS"."CAREGIVERS_TEL" IS '전화번호';

COMMENT ON COLUMN "CAREGIVERS"."CAREGIVERS_EXPERIENCE" IS '경력';

COMMENT ON COLUMN "CAREGIVERS"."CAREGIVERS_WORK_HOURS" IS '근무시간(MOR, AFT, EVE)';

CREATE TABLE "CLUB_TYPE" (
	"CLUB_CODE"	NUMBER		NOT NULL,
	"CLUB_NAME"	NVARCHAR2(20)		NOT NULL
);

COMMENT ON COLUMN "CLUB_TYPE"."CLUB_CODE" IS '클럽 코드 번호';

COMMENT ON COLUMN "CLUB_TYPE"."CLUB_NAME" IS '클럽 이름';

CREATE TABLE "CLINICROOM" (
	"CLINICROOM_NO"	NUMBER		NOT NULL,
	"CLINICROOM_NAME"	NVARCHAR2(100)		NOT NULL,
	"DOCTOR_NUMBER"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "CLINICROOM"."DOCTOR_NUMBER" IS '의사 번호';

CREATE TABLE "AUTH_KEY" (
	"KEY_NO"	NUMBER		NOT NULL,
	"EMAIL"	NVARCHAR2(50)		NOT NULL,
	"AUTH_KEY"	NVARCHAR2(6)		NOT NULL,
	"CREATE_TIME"	DATE	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN "AUTH_KEY"."KEY_NO" IS '인증키';

COMMENT ON COLUMN "AUTH_KEY"."EMAIL" IS '인증 이메일';

COMMENT ON COLUMN "AUTH_KEY"."AUTH_KEY" IS '인증 번호';

COMMENT ON COLUMN "AUTH_KEY"."CREATE_TIME" IS '인증번호 생성시간';

CREATE TABLE "DOCUMENT_TYPE" (
	"DOC_TYPE_CODE"	NUMBER		NOT NULL,
	"DOC_TYPE_NAME"	NVARCHAR2(20)		NOT NULL
);

COMMENT ON COLUMN "DOCUMENT_TYPE"."DOC_TYPE_CODE" IS '서류 타입 코드(1/2/3/4)';

COMMENT ON COLUMN "DOCUMENT_TYPE"."DOC_TYPE_NAME" IS '(주민등록본/가족관계/건강검진/신분증)';

CREATE TABLE "CLUB_RESERVATION" (
	"CLUB_RESV_NO"	NUMBER		NOT NULL,
	"CLUB_CODE"	NUMBER		NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"CLUB_RESV_TIME"	DATE		NOT NULL,
	"CLUB_LOCATION"	NVARCHAR2(200)		NOT NULL
);

COMMENT ON COLUMN "CLUB_RESERVATION"."CLUB_RESV_NO" IS '클럽 예약 번호';

COMMENT ON COLUMN "CLUB_RESERVATION"."CLUB_CODE" IS '클럽 코드 번호';

COMMENT ON COLUMN "CLUB_RESERVATION"."MEMBER_NO" IS '회원번호';

COMMENT ON COLUMN "CLUB_RESERVATION"."CLUB_RESV_TIME" IS '클럽 예약 시간';

COMMENT ON COLUMN "CLUB_RESERVATION"."CLUB_LOCATION" IS '클럽 장소';

CREATE TABLE "GUESTROOM_RESV" (
	"GSTRM_RESV_NO"	NUMBER		NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"GSTRM_RESV_DATE"	DATE		NOT NULL,
	"GSTRM_RESV_COUNT"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "GUESTROOM_RESV"."GSTRM_RESV_NO" IS '게스트룸 예약번호';

COMMENT ON COLUMN "GUESTROOM_RESV"."MEMBER_NO" IS '회원번호';

COMMENT ON COLUMN "GUESTROOM_RESV"."GSTRM_RESV_DATE" IS '예약일자';

COMMENT ON COLUMN "GUESTROOM_RESV"."GSTRM_RESV_COUNT" IS '예약인원';

ALTER TABLE "MEMBER" ADD CONSTRAINT "PK_MEMBER" PRIMARY KEY (
	"MEMBER_NO"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "PK_BOARD" PRIMARY KEY (
	"BOARD_NO"
);

ALTER TABLE "BOARD_TYPE" ADD CONSTRAINT "PK_BOARD_TYPE" PRIMARY KEY (
	"BOARD_CODE"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "PK_COMMENT" PRIMARY KEY (
	"COMMENT_NO"
);

ALTER TABLE "BOARD_IMG" ADD CONSTRAINT "PK_BOARD_IMG" PRIMARY KEY (
	"BOARD_IMG_NO"
);

ALTER TABLE "DOCUMENT" ADD CONSTRAINT "PK_DOCUMENT" PRIMARY KEY (
	"DOCUMENT_NO"
);

ALTER TABLE "DOCTOR" ADD CONSTRAINT "PK_DOCTOR" PRIMARY KEY (
	"DOCTOR_NO"
);

ALTER TABLE "ROOM" ADD CONSTRAINT "PK_ROOM" PRIMARY KEY (
	"ROOM_NO"
);

ALTER TABLE "ROOM_TYPE" ADD CONSTRAINT "PK_ROOM_TYPE" PRIMARY KEY (
	"ROOM_CODE"
);

ALTER TABLE "DOCTOR_MAJOR" ADD CONSTRAINT "PK_DOCTOR_MAJOR" PRIMARY KEY (
	"MAJOR_CODE"
);

ALTER TABLE "DOCTOR_APPOINTMENT" ADD CONSTRAINT "PK_DOCTOR_APPOINTMENT" PRIMARY KEY (
	"DR_APPT_NO"
);

ALTER TABLE "SURVEYS" ADD CONSTRAINT "PK_SURVEYS" PRIMARY KEY (
	"SURVEYS_NO"
);

ALTER TABLE "CAREGIVERS" ADD CONSTRAINT "PK_CAREGIVERS" PRIMARY KEY (
	"CAREGIVERS_NO"
);

ALTER TABLE "CLUB_TYPE" ADD CONSTRAINT "PK_CLUB_TYPE" PRIMARY KEY (
	"CLUB_CODE"
);

ALTER TABLE "CLINICROOM" ADD CONSTRAINT "PK_CLINICROOM" PRIMARY KEY (
	"CLINICROOM_NO"
);

ALTER TABLE "AUTH_KEY" ADD CONSTRAINT "PK_AUTH_KEY" PRIMARY KEY (
	"KEY_NO"
);

ALTER TABLE "DOCUMENT_TYPE" ADD CONSTRAINT "PK_DOCUMENT_TYPE" PRIMARY KEY (
	"DOC_TYPE_CODE"
);

ALTER TABLE "CLUB_RESERVATION" ADD CONSTRAINT "PK_CLUB_RESERVATION" PRIMARY KEY (
	"CLUB_RESV_NO"
);

ALTER TABLE "GUESTROOM_RESV" ADD CONSTRAINT "PK_GUESTROOM_RESV" PRIMARY KEY (
	"GSTRM_RESV_NO"
);

ALTER TABLE "MEMBER" ADD CONSTRAINT "FK_CAREGIVERS_TO_MEMBER_1" FOREIGN KEY (
	"CAREGIVERS_NO"
)
REFERENCES "CAREGIVERS" (
	"CAREGIVERS_NO"
);

ALTER TABLE "MEMBER" ADD CONSTRAINT "FK_ROOM_TO_MEMBER_1" FOREIGN KEY (
	"ROOM_NO"
)
REFERENCES "ROOM" (
	"ROOM_NO"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "FK_BOARD_TYPE_TO_BOARD_1" FOREIGN KEY (
	"BOARD_CODE"
)
REFERENCES "BOARD_TYPE" (
	"BOARD_CODE"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "FK_MEMBER_TO_BOARD_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_BOARD_TO_COMMENT_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_MEMBER_TO_COMMENT_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_COMMENT_TO_COMMENT_1" FOREIGN KEY (
	"PARENT_COMMENT_NO"
)
REFERENCES "COMMENT" (
	"COMMENT_NO"
);

ALTER TABLE "BOARD_IMG" ADD CONSTRAINT "FK_BOARD_TO_BOARD_IMG_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "DOCUMENT" ADD CONSTRAINT "FK_DOCUMENT_TYPE_TO_DOCUMENT_1" FOREIGN KEY (
	"DOC_TYPE_CODE"
)
REFERENCES "DOCUMENT_TYPE" (
	"DOC_TYPE_CODE"
);

ALTER TABLE "DOCUMENT" ADD CONSTRAINT "FK_ROOM_TO_DOCUMENT_1" FOREIGN KEY (
	"ROOM_NO"
)
REFERENCES "ROOM" (
	"ROOM_NO"
);

ALTER TABLE "DOCTOR" ADD CONSTRAINT "FK_DOCTOR_MAJOR_TO_DOCTOR_1" FOREIGN KEY (
	"MAJOR_CODE"
)
REFERENCES "DOCTOR_MAJOR" (
	"MAJOR_CODE"
);

ALTER TABLE "ROOM" ADD CONSTRAINT "FK_ROOM_TYPE_TO_ROOM_1" FOREIGN KEY (
	"ROOM_CODE"
)
REFERENCES "ROOM_TYPE" (
	"ROOM_CODE"
);

ALTER TABLE "DOCTOR_APPOINTMENT" ADD CONSTRAINT "FK_MEMBER_TO_DOCTOR_APPOINTMENT_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "DOCTOR_APPOINTMENT" ADD CONSTRAINT "FK_DOCTOR_TO_DOCTOR_APPOINTMENT_1" FOREIGN KEY (
	"DOCTOR_NO"
)
REFERENCES "DOCTOR" (
	"DOCTOR_NO"
);

ALTER TABLE "SURVEYS" ADD CONSTRAINT "FK_MEMBER_TO_SURVEYS_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "CLINICROOM" ADD CONSTRAINT "FK_DOCTOR_TO_CLINICROOM_1" FOREIGN KEY (
	"DOCTOR_NUMBER"
)
REFERENCES "DOCTOR" (
	"DOCTOR_NO"
);

ALTER TABLE "CLUB_RESERVATION" ADD CONSTRAINT "FK_CLUB_TYPE_TO_CLUB_RESERVATION_1" FOREIGN KEY (
	"CLUB_CODE"
)
REFERENCES "CLUB_TYPE" (
	"CLUB_CODE"
);

ALTER TABLE "CLUB_RESERVATION" ADD CONSTRAINT "FK_MEMBER_TO_CLUB_RESERVATION_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "GUESTROOM_RESV" ADD CONSTRAINT "FK_MEMBER_TO_GUESTROOM_RESV_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

