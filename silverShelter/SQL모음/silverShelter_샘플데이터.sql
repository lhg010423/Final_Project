/* 계정 생성 (관리자 계정으로 접속) */
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;

CREATE USER silver_shelter IDENTIFIED BY silver1234;

GRANT CONNECT, RESOURCE TO silver_shelter;

ALTER USER silver_shelter DEFAULT TABLESPACE SYSTEM QUOTA UNLIMITED ON SYSTEM;

COMMIT;
--------------------------------------------------------------------------------------------
-- 회원 삭제
DELETE FROM "MEMBER"
WHERE MEMBER_NO = 15;


COMMIT;

-- MEMBER 테이블의 MEMBER_ID에 UNIQUE 추가하기 ---     아직 적용안함
ALTER TABLE MEMBER ADD CONSTRAINT uc_member_id UNIQUE (MEMBER_ID);

-- 번호, ID, PW, 이름, 이메일,
-- 주소, 전화번호, 보호자전화번호, 가입일, 탈퇴여부,
-- 관리자계정판별, 요양사번호, 방번호



-- 심사 데이터 생성
INSERT INTO "EXAMINATION"
VALUES(1, '관리자', 'admin@kh.com', '010-1234-1234', 'Y', SYSDATE, 1, 1);


-- 관리자 계정 생성
INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'admin', 'admin', '관리자', 'admin@kh.com',
			NULL, '010-1111-2222', '010-2222-3333', DEFAULT, DEFAULT,
			'1', NULL, NULL, 1);

-- 회원 삭제
DELETE FROM "MEMBER"
WHERE MEMBER_NO = 2;

DELETE FROM "EXAMINATION"
WHERE EXAM_ID = 6;
COMMIT;

-- 시퀀스 생성
CREATE SEQUENCE SEQ_BOARD_CODE NOCACHE;
CREATE SEQUENCE SEQ_BOARD_NO NOCACHE;
CREATE SEQUENCE SEQ_MEMBER_NO NOCACHE;
CREATE SEQUENCE SEQ_CAREGIVERS_NO NOCACHE;
CREATE SEQUENCE SEQ_EXAM_ID NOCACHE;
CREATE SEQUENCE SEQ_CLUB_RESV_NO NOCACHE;
-- 심사 번호 2번부터 시작
CREATE SEQUENCE SEQ_EXAMINATION_NO NOCACHE
START WITH 2
INCREMENT BY 1;
CREATE SEQUENCE SEQ_DOCUMENT_NO NOCACHE;
CREATE SEQUENCE SEQ_COMMENT_NO NOCACHE
START WITH 12
INCREMENT BY 1;




-- 시퀀스삭제
DROP SEQUENCE ;
-- 요양사 추가
INSERT INTO "CAREGIVERS"
VALUES(SEQ_CAREGIVERS_NO.NEXTVAL, '이춘자', 50, 'F', '010-1234-5678', 20, 'MOR');




INSERT INTO "BOARD_TYPE"
VALUES(1, '공지게시판');

INSERT INTO "BOARD_TYPE"
VALUES(2, '자유게시판');

INSERT INTO "BOARD_TYPE"
VALUES(3, '문의게시판');

INSERT INTO "BOARD_TYPE"
VALUES(4, '회원 정보');

INSERT INTO "BOARD_TYPE"
VALUES(5, '요양사 정보');

INSERT INTO "BOARD_TYPE"
VALUES(6, '병원 예약');

INSERT INTO "BOARD_TYPE"
VALUES(7, '클럽 예약');

INSERT INTO "BOARD_TYPE"
VALUES(8, '게스트룸');

INSERT INTO "BOARD_TYPE"
VALUES(9, '회원 서류');



-- 유저일
INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'user01', 'pass01', '유저일', 'user01@kh.com',
			NULL, '010-1234-5678', '010-1234-5678', DEFAULT, DEFAULT,
			DEFAULT, NULL, NULL, 1);




-- 회원번호, 아이디, 비번, 이름, 이메일,
-- 주소, 전화번호, 가입일, 탈퇴여부, 관리자계정여부,
-- 요양사 번호, 방번호




COMMIT;







BEGIN
	FOR I IN 1..2000 LOOP
		
		INSERT INTO "BOARD"
		VALUES(SEQ_BOARD_NO.NEXTVAL,
					 SEQ_BOARD_NO.CURRVAL || '번째 게시글',
					 SEQ_BOARD_NO.CURRVAL || '번째 게시글 내용 입니다',
					 DEFAULT, DEFAULT, DEFAULT, DEFAULT,
					 CEIL( DBMS_RANDOM.VALUE(0,1) ), -- BOARD_CODE(게시판종류)
					 1 -- MEMBER_NO(작성회원번호)
		);
		
	END LOOP;
END;


-- 게시글 데이터
BEGIN
	FOR I IN 301..450 LOOP
		
		INSERT INTO "BOARD"
		VALUES(SEQ_BOARD_NO.NEXTVAL,
					SEQ_BOARD_NO.CURRVAL || '번째 게시글',
					SEQ_BOARD_NO.CURRVAL || '번째 게시글 내용 입니다',
					DEFAULT, NULL, DEFAULT, DEFAULT, DEFAULT, DEFAULT,
					CEIL( DBMS_RANDOM.VALUE(1, 3) ),  -- BOARD_CODE 1~9번 게시판
					4 -- MEMBER_NO
		);
					
	END LOOP;
END;


SELECT MEMBER_NAME, MEMBER_ID, MEMBER_PW,
			MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL,
			GUARDIAN_TEL
		FROM "MEMBER"
--		JOIN "CAREGIVERS" USING(CAREGIVERS_NO)
		WHERE MEMBER_NO = 2;



-- 기존 회원에 요양사 추가하기
UPDATE MEMBER
SET CAREGIVERS_NO = 1
WHERE MEMBER_NO = 4;




-- 기존 회원에 주소 추가하기        우편번호^^^주소^^^상세주소
-- 사이에 ^^^ 꼭써야함 그걸로 3가지 구분하는거
UPDATE "MEMBER" 
SET MEMBER_ADDRESS = '12345^^^서울 종로^^^101동 101호'
WHERE MEMBER_NO = 4;


-- 기존 회원에 방 번호 추가하기
UPDATE "MEMBER"
SET ROOM_NO = 301
WHERE MEMBER_NO = 2;

UPDATE "ROOM"
SET OCCUPIED = 'Y'
WHERE ROOM_NO = 301;

-- 방 타입 데이터 추가하기
INSERT INTO "ROOM_TYPE"
VALUES(1, 'VIP', '700000000');

INSERT INTO "ROOM_TYPE"
VALUES(2, '프리미엄', '500000000');

INSERT INTO "ROOM_TYPE"
VALUES(3, '클래식', '300000000');





COMMIT;




DELETE FROM "ROOM";


-- 컬럼 삭제하기
ALTER TABLE ROOM_TYPE
DROP COLUMN ROOM_PRICE;


-- 테이블 삭제안하고 컬럼명 및 주석 추가하기
ALTER TABLE ROOM
ADD OCCUPIED CHAR(1) DEFAULT 'N' NOT NULL;
COMMENT ON COLUMN ROOM.OCCUPIED IS '방 입실 여부 (Y, N)';



-- 방추가하는 반복문 - 이제 쓸일 없음 (방번호, 방타입, 입실여부N)
BEGIN
	FOR I IN 301..330 LOOP
		
		INSERT INTO "ROOM"
		
		VALUES(I, DEFAULT, 3, DEFAULT);
		
	END LOOP;
END;



-- 방 타입마다 남은 방 개수 구하는 SELECT문 중요***************************************
SELECT
    FLOOR(ROOM_NO / 100) AS BUILDING_NUMBER,
    COUNT(*) AS EMPTY_ROOMS
FROM ROOM
WHERE OCCUPIED = 'N'
GROUP BY FLOOR(ROOM_NO / 100)
ORDER BY BUILDING_NUMBER;


-- 방 타입마다 전체 방 개수 구하는 SELECT문 중요***************************************
SELECT
    FLOOR(ROOM_NO / 100) AS BUILDING_NUMBER,
    COUNT(*) AS EMPTY_ROOMS
FROM ROOM
GROUP BY FLOOR(ROOM_NO / 100)
ORDER BY BUILDING_NUMBER;

SELECT * FROM MEMBER;

COMMIT;
---------------------------------------------------------------
SELECT * FROM EXAMINATION;


COMMIT;
-- 밑에거 지워도 됨
SELECT MEMBER_NO, MEMBER_NAME, MEMBER_ID, EXAM_STATUS "examStatus"
		FROM "MEMBER"
		JOIN "EXAMINATION" USING(EXAM_ID)
		ORDER BY MEMBER_NO DESC;
	

		SELECT MEMBER_NO, MEMBER_NAME, MEMBER_ID, MEMBER_PW,
			MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL,
			GUARDIAN_TEL, ROOM_NO, CAREGIVERS_NAME
		FROM "MEMBER"
		JOIN "CAREGIVERS" USING(CAREGIVERS_NO)
		WHERE MEMBER_NO = 4;

SELECT MEMBER_NO, MEMBER_NAME, MEMBER_ID, MEMBER_PW,
			MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL,
			GUARDIAN_TEL, ROOM_NO, CAREGIVERS_NAME
FROM "MEMBER"
JOIN "CAREGIVERS" USING(CAREGIVERS_NO)
WHERE MEMBER_NO = 4;



SELECT MEMBER_NO, CAREGIVERS_NO
FROM "MEMBER"
JOIN "CAREGIVERS" USING(CAREGIVERS_NO);

SELECT MEMBER_NO, MEMBER_NAME, MEMBER_ID, EXAM_STATUS,
		TO_CHAR(EXAM_DATE, 'YYYY"."MM"."DD  HH24":"MI":"SS') EXAM_DATE
		FROM "MEMBER"
		JOIN "EXAMINATION" USING(EXAM_ID)
		WHERE MEMBER_ROLE = 2
		ORDER BY MEMBER_NO DESC;

	
	
SELECT EXAM_ID, EXAM_NAME, EXAM_EMAIL, EXAM_ROOM_TYPE || '/' || EXAM_ROOM_CAPACITY AS EXAM_ROOM,
			EXAM_STATUS, EXAM_DATE
FROM "EXAMINATION"
ORDER BY EXAM_STATUS, EXAM_ID;


SELECT EXAM_ID, EXAM_NAME, EXAM_EMAIL, EXAM_PHONE,
			EXAM_STATUS, EXAM_DATE, EXAM_ROOM_TYPE || '/' || EXAM_ROOM_CAPACITY AS EXAM_ROOM
		FROM "EXAMINATION"
		WHERE EXAM_ID = 1;

COMMIT;


SELECT COUNT(*)
FROM "BOARD"
WHERE BOARD_CODE = 1;


SELECT *
FROM EXAMINATION;

DELETE FROM "BOARD_TYPE"
WHERE BOARD_CODE = 9;

ROLLBACK;
UPDATE "BOARD"
SET BOARD_TITLE = '골든 프레스티지사이트에 공기게시판이 개설되었습니다.@@@@@@@@@'
WHERE BOARD_NO = 301;

DELETE FROM "BOARD"
WHERE BOARD_NO = 301;
ROLLBACK;

SELECT BOARD_TITLE FROM "BOARD"
WHERE BOARD_NO = 301;

SELECT COUNT(*)
		FROM "BOARD"
		JOIN "MEMBER" USING(MEMBER_NO)
		WHERE BOARD_CODE = 1
		AND BOARD_DEL_FL = 'N'
		AND BOARD_TITLE LIKE '%ㅎㅎ%';



-- 회원 방 번호 추가하기
UPDATE "MEMBER"
SET ROOM_NO = 301
WHERE MEMBER_NO = 2;


-- 게시글 상세보기
SELECT BOARD_NO, BOARD_TITLE, BOARD_CONTENT, MEMBER_NAME, BOARD_WRITE_DATE,
			BOARD_UPDATE_DATE, READ_COUNT, LIKE_COUNT, BOARD_CODE, MEMBER_NO
		FROM "BOARD"
		JOIN "MEMBER" USING(MEMBER_NO);
--		WHERE BOARD_NO = 4;
		WHERE BOARD_CODE = 2
		AND BOARD_NO = 4;


-- 댓글 샘플 데이터
-- 최상위 댓글 샘플 데이터 삽입
INSERT INTO COMMENT (COMMENT_NO, COMMENT_CONTENT, COMMENT_WRITE_DATE, COMMENT_DEL_FL, BOARD_NO, MEMBER_NO, REF, REF_LEVEL)
VALUES (1, '첫 번째 댓글입니다.', SYSDATE, 'N', 293, 1, 1, 0);

INSERT INTO COMMENT (COMMENT_NO, COMMENT_CONTENT, COMMENT_WRITE_DATE, COMMENT_DEL_FL, BOARD_NO, MEMBER_NO, REF, REF_LEVEL)
VALUES (2, '두 번째 댓글입니다.', SYSDATE, 'N', 293, 2, 2, 0);

INSERT INTO COMMENT (COMMENT_NO, COMMENT_CONTENT, COMMENT_WRITE_DATE, COMMENT_DEL_FL, BOARD_NO, MEMBER_NO, REF, REF_LEVEL)
VALUES (3, '세 번째 댓글입니다.', SYSDATE, 'N', 293, 3, 3, 0);

INSERT INTO COMMENT (COMMENT_NO, COMMENT_CONTENT, COMMENT_WRITE_DATE, COMMENT_DEL_FL, BOARD_NO, MEMBER_NO, REF, REF_LEVEL)
VALUES (4, '네 번째 댓글입니다.', SYSDATE, 'N', 293, 4, 4, 0);

INSERT INTO COMMENT (COMMENT_NO, COMMENT_CONTENT, COMMENT_WRITE_DATE, COMMENT_DEL_FL, BOARD_NO, MEMBER_NO, REF, REF_LEVEL)
VALUES (5, '다섯 번째 댓글입니다.', SYSDATE, 'N', 293, 5, 5, 0);

-- 대댓글 샘플 데이터 삽입
INSERT INTO COMMENT (COMMENT_NO, COMMENT_CONTENT, COMMENT_WRITE_DATE, COMMENT_DEL_FL, BOARD_NO, MEMBER_NO, PARENT_COMMENT_NO, REF, REF_LEVEL)
VALUES (6, '첫 번째 댓글의 대댓글입니다.', SYSDATE, 'N', 293, 6, 1, 1, 1);

INSERT INTO COMMENT (COMMENT_NO, COMMENT_CONTENT, COMMENT_WRITE_DATE, COMMENT_DEL_FL, BOARD_NO, MEMBER_NO, PARENT_COMMENT_NO, REF, REF_LEVEL)
VALUES (7, '첫 번째 댓글의 또 다른 대댓글입니다.', SYSDATE, 'N', 293, 7, 1, 1, 1);

INSERT INTO COMMENT (COMMENT_NO, COMMENT_CONTENT, COMMENT_WRITE_DATE, COMMENT_DEL_FL, BOARD_NO, MEMBER_NO, PARENT_COMMENT_NO, REF, REF_LEVEL)
VALUES (8, '세 번째 댓글의 대댓글입니다.', SYSDATE, 'N', 293, 8, 3, 3, 1);

INSERT INTO COMMENT (COMMENT_NO, COMMENT_CONTENT, COMMENT_WRITE_DATE, COMMENT_DEL_FL, BOARD_NO, MEMBER_NO, PARENT_COMMENT_NO, REF, REF_LEVEL)
VALUES (9, '네 번째 댓글의 대댓글입니다.', SYSDATE, 'N', 293, 9, 4, 4, 1);

INSERT INTO COMMENT (COMMENT_NO, COMMENT_CONTENT, COMMENT_WRITE_DATE, COMMENT_DEL_FL, BOARD_NO, MEMBER_NO, PARENT_COMMENT_NO, REF, REF_LEVEL)
VALUES (10, '네 번째 댓글의 또 다른 대댓글입니다.', SYSDATE, 'N', 293, 10, 4, 4, 1);


INSERT INTO "COMMENT" (COMMENT_NO, COMMENT_CONTENT, COMMENT_WRITE_DATE, COMMENT_DEL_FL, BOARD_NO, MEMBER_NO, PARENT_COMMENT_NO, REF, REF_LEVEL)
VALUES (11, '대댓글.', SYSDATE, 'N', 293, 4, 9, 4, 2);

COMMIT;

SELECT c.*, m.MEMBER_NAME
		FROM "COMMENT" c
		
		JOIN "MEMBER" m ON c.MEMBER_NO = m.MEMBER_NO
		WHERE BOARD_NO = 293;

	
SELECT * FROM "COMMENT" WHERE BOARD_NO = 293;

SELECT c.*
FROM "COMMENT" c
LEFT JOIN "MEMBER" m ON c.MEMBER_NO = m.MEMBER_NO
WHERE c.BOARD_NO = 293;

SELECT c.*, m.MEMBER_NAME
FROM "COMMENT" c
LEFT JOIN "MEMBER" m ON c.MEMBER_NO = m.MEMBER_NO
WHERE c.BOARD_NO = 293;

SELECT MEMBER_NO, MEMBER_NAME, MEMBER_ID, MEMBER_PW,
			MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL,
			GUARDIAN_TEL, CAREGIVERS_NAME
		FROM "MEMBER" M
		LEFT JOIN "CAREGIVERS" c ON M.CAREGIVERS_N0 = c.CAREGIVERS_NO;
		WHERE MEMBER_NO = ;
		
		
		SELECT MEMBER_NO, MEMBER_NAME, MEMBER_ID, MEMBER_PW,
			MEMBER_EMAIL, MEMBER_ADDRESS, MEMBER_TEL,
			GUARDIAN_TEL
		FROM "MEMBER";
		JOIN "CAREGIVERS" USING(CAREGIVERS_NO)
		WHERE MEMBER_NO = #{memberNo};
	
SELECT CAREGIVERS_NO, CAREGIVERS_NAME, CAREGIVERS_AGE,
		
			CAREGIVERS_GENDER,
			CASE 
				WHEN CAREGIVERS_GENDER = 'male' THEN '남자'
				WHEN CAREGIVERS_GENDER = 'female' THEN '여자'
				
			END AS CAREGIVERS_GENDER
		FROM CAREGIVERS;
			,
			
			CAREGIVERS_TEL,
			CASE CAREGIVERS_EXPERIENCE
				WHEN 'novice' THEN '3년 미만'
				WHEN 'intermediate' THEN '3-7년'
				WHEN 'experienced' THEN '8년 이상'
			END as CAREGIVERS_EXPERIENCE,
			
			CASE CAREGIVERS_WORK_HOURS
				WHEN 'morning' THEN '09:00 - 13:00'
				WHEN 'afternoon' THEN '13:00 - 18:00'
				WHEN 'evening' THEN '18:00 - 22:00'
			END as CAREGIVERS_WORK_HOURS,
			
			CASE CAREGIVERS_ROLE
				WHEN 'companionship' THEN '정서적 지원'
				WHEN 'personalCare' THEN '의료적 지원'
				WHEN 'housekeeping' THEN '일상생활 지원'
			END as CAREGIVERS_ROLE
			
		FROM "CAREGIVERS";
	
	

-- 클럽 일정 조회  중요 지우면 안됨------------------------------------------------
SELECT
	
	CLUB_NAME AS "type",
	CONCAT('CR_', CLUB_RESV_NO) AS "resvId",
	MEMBER_NAME AS "memberName",
	TO_DATE(CLUB_RESV_TIME, 'YY-MM-DD') AS "reservationDate",
	TO_CHAR(CLUB_RESV_TIME, 'HH24') AS "reservationTime"

FROM CLUB_RESERVATION
JOIN MEMBER USING(MEMBER_NO)
JOIN CLUB_TYPE USING(CLUB_CODE)
WHERE TO_DATE(CLUB_RESV_TIME, 'YY-MM-DD') = TO_DATE('2024-06-20', 'YY-MM-DD');

UNION
	
SELECT
	
	MAJOR_NAME AS "type",
	CONCAT('DR_', DR_APPT_NO) AS "resvId",
	MEMBER_NAME AS "memberName",
	TO_DATE(DR_APPT_TIME, 'YY-MM-DD') AS "reservationDate",
	TO_CHAR(DR_APPT_TIME, 'HH24') AS "reservationTime"

FROM DOCTOR
JOIN DOCTOR_APPOINTMENT USING(DOCTOR_NO)
JOIN DOCTOR_MAJOR USING(MAJOR_CODE)
JOIN MEMBER USING(MEMBER_NO)
WHERE TO_DATE(CLUB_RESV_TIME, 'YY-MM-DD') = TO_DATE('2024-06-20', 'YY-MM-DD')
;



SELECT TO_DATE(DR_APPT_TIME, 'YY-MM-DD') AS "reservationDate"
FROM DOCTOR_APPOINTMENT;
WHERE DR_APPT_STATUS = 'N';
	
	




SELECT TO_DATE(DR_APPT_TIME, 'YYYY-MM-DD') AS "reservationDate"
FROM DOCTOR_APPOINTMENT

UNION

SELECT TO_DATE(CLUB_RESV_TIME, 'YYYY-MM-DD') AS "reservationDate"
FROM CLUB_RESERVATION;
	

SELECT TO_CHAR(DR_APPT_TIME, 'YYYY-MM-DD') AS "reservationDate"
   		FROM DOCTOR_APPOINTMENT
   		
   		UNION
   		
   		SELECT TO_CHAR(CLUB_RESV_TIME, 'YYYY-MM-DD') AS "reservationDate"
   		FROM CLUB_RESERVATION;
   	


   	
   	
   	
   	
SELECT
	
			CLUB_NAME AS "type",
			CONCAT('CR_', CLUB_RESV_NO) AS "resvId",
			MEMBER_NAME AS "memberName",
			TO_CHAR(CLUB_RESV_TIME, 'YYYY-MM-DD') AS "reservationDate",
			TO_CHAR(CLUB_RESV_TIME, 'HH24') AS "reservationTime"
		
		FROM CLUB_RESERVATION
		JOIN MEMBER USING(MEMBER_NO)
		JOIN CLUB_TYPE USING(CLUB_CODE)
		WHERE TO_CHAR(CLUB_RESV_TIME, 'YYYY-MM-DD') = '2024-06-19';
	
		TO_CHAR(#{reservation}, 'YYYY-MM-DD');
		
		UNION
			
		SELECT
			
			MAJOR_NAME AS "type",
			CONCAT('DR_', DR_APPT_NO) AS "resvId",
			MEMBER_NAME AS "memberName",
			TO_DATE(DR_APPT_TIME, 'YYYY-MM-DD') AS "reservationDate",
			TO_CHAR(DR_APPT_TIME, 'HH24') AS "reservationTime"
		
		FROM DOCTOR
		JOIN DOCTOR_APPOINTMENT USING(DOCTOR_NO)
		JOIN DOCTOR_MAJOR USING(MAJOR_CODE)
		JOIN MEMBER USING(MEMBER_NO)
		WHERE TO_CHAR(CLUB_RESV_TIME, 'YYYY-MM-DD') = TO_CHAR(#{reservation}, 'YYYY-MM-DD');
   	
   	

	
	
	

SELECT
	
			CLUB_NAME AS "type",
			CONCAT('CR_', CLUB_RESV_NO) AS "resvId",
			MEMBER_NAME AS "memberName",
			TO_CHAR(CLUB_RESV_TIME, 'YYYY-MM-DD') AS "reservationDate",
			TO_CHAR(CLUB_RESV_TIME, 'HH24') AS "reservationTime"
		
		FROM CLUB_RESERVATION
		JOIN MEMBER USING(MEMBER_NO)
		JOIN CLUB_TYPE USING(CLUB_CODE)
		WHERE TO_CHAR(CLUB_RESV_TIME, 'YYYY-MM-DD') = '2024-06-20'
		
		UNION
			
		SELECT
			
			MAJOR_NAME AS "type",
			CONCAT('DR_', DR_APPT_NO) AS "resvId",
			MEMBER_NAME AS "memberName",
			TO_CHAR(DR_APPT_TIME, 'YYYY-MM-DD') AS "reservationDate",
			TO_CHAR(DR_APPT_TIME, 'HH24') AS "reservationTime"
		
		FROM DOCTOR
		JOIN DOCTOR_APPOINTMENT USING(DOCTOR_NO)
		JOIN DOCTOR_MAJOR USING(MAJOR_CODE)
		JOIN MEMBER USING(MEMBER_NO)
		WHERE TO_CHAR(DR_APPT_TIME, 'YYYY-MM-DD') = '2024-06-20';
   	
   	
   	
   	
   	
   	
