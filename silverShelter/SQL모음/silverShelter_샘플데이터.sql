/* 계정 생성 (관리자 계정으로 접속) */
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;

CREATE USER silver_shelter IDENTIFIED BY silver1234;

GRANT CONNECT, RESOURCE TO silver_shelter;

ALTER USER silver_shelter DEFAULT TABLESPACE SYSTEM QUOTA UNLIMITED ON SYSTEM;

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

-- 관리자 계정 생성
INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'admin', 'admin', '관리자', 'admin@kh.com',
			NULL, '010-1111-2222', '010-2222-3333', DEFAULT, DEFAULT,
			'1', NULL, NULL);



CREATE SEQUENCE SEQ_BOARD_CODE NOCACHE;
CREATE SEQUENCE SEQ_MEMBER_NO NOCACHE;
CREATE SEQUENCE SEQ_CAREGIVERS_NO NOCACHE;
CREATE SEQUENCE SEQ_BOARD_NO NOCACHE;






-- 회원번호, 아이디, 비번, 이름, 이메일,
-- 주소, 전화번호, 가입일, 탈퇴여부, 관리자계정여부,
-- 요양사 번호, 방번호

-- 유저 계정 생성
INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'user01', 'pass01', '유저일', 'user01@kh.com',
			NULL, '010-1234-5678', '010-4235-2456', DEFAULT, DEFAULT,
			DEFAULT, NULL, NULL);
			
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
	FOR I IN 1..300 LOOP
		
		INSERT INTO "BOARD"
		VALUES(SEQ_BOARD_NO.NEXTVAL,
					SEQ_BOARD_NO.CURRVAL || '번째 게시글',
					SEQ_BOARD_NO.CURRVAL || '번째 게시글 내용 입니다',
					DEFAULT, NULL, DEFAULT, DEFAULT, DEFAULT, DEFAULT,
					CEIL( DBMS_RANDOM.VALUE(1, 9) ),  -- BOARD_CODE 1~9번 게시판
					1 -- MEMBER_NO
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
WHERE MEMBER_NO = 2;


-- 기존 회원에 주소 추가하기        우편번호^^^주소^^^상세주소
-- 사이에 ^^^ 꼭써야함 그걸로 3가지 구분하는거
UPDATE "MEMBER" 
SET MEMBER_ADDRESS = '12345^^^서울 종로^^^101동 101호'
WHERE MEMBER_NO = 2;


-- 기존 회원에 방 번호 추가하기
UPDATE "MEMBER"
SET ROOM_NO = 301
WHERE MEMBER_NO = 2;



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
		VALUES(I, 3, DEFAULT);
		
	END LOOP;
END;



-- 방 타입마다 남은 방 개수 구하는 SELECT문 중요***************************************
SELECT
    FLOOR(ROOM_NO / 100) AS BUILDING_NUMBER,
    COUNT(*) AS EMPTY_ROOMS
FROM
    ROOM
WHERE
    OCCUPIED = 'N'
GROUP BY
    FLOOR(ROOM_NO / 100)
ORDER BY
    BUILDING_NUMBER;


-- 방 타입마다 전체 방 개수 구하는 SELECT문 중요***************************************
SELECT
    FLOOR(ROOM_NO / 100) AS BUILDING_NUMBER,
    COUNT(*) AS EMPTY_ROOMS
FROM
    ROOM
GROUP BY
    FLOOR(ROOM_NO / 100)
ORDER BY
    BUILDING_NUMBER;








