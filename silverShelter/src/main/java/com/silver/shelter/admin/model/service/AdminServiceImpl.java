package com.silver.shelter.admin.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.silver.shelter.admin.model.dto.Reservation;
import com.silver.shelter.admin.model.mapper.AdminMapper;
import com.silver.shelter.board.model.dto.Board;
import com.silver.shelter.board.model.dto.Pagination;
import com.silver.shelter.careGiver.model.CareGiver;
import com.silver.shelter.clubReservation.model.dto.ClubReservation;
import com.silver.shelter.examination.model.dto.Examination;
import com.silver.shelter.member.model.dto.Member;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

   
   private final JavaMailSender mailSender;
   private final AdminMapper mapper;
   private final SpringTemplateEngine templateEngine;
   
   
   // 회원 목록 조회 / 검색 X
   @Override
   public Map<String, Object> memberAllSelect(int cp) {
      
      // 탈퇴하지 않은 회원 수 조회
      int memberCount = mapper.memberAllCount();
      
      // memberCount와 cp를 이용해 Pagination 객체 생성
      // cp는 현재 페이지 번호, memberCount는 전체 게시글 수를 나타냅니다.
      Pagination pagination = new Pagination(cp, memberCount);

      // 페이징 처리를 위해 한 페이지에 보여줄 게시글 수(limit)와
      // 현재 페이지에서 보여줄 게시글 목록의 시작 위치(offset) 계산
      // limit은 한 페이지에 보여줄 게시글 수입니다.
      int limit = pagination.getLimit();

      // offset은 현재 페이지에서 보여줄 게시글 목록의 시작 위치를 계산합니다.
      // 예를 들어, 현재 페이지(cp)가 2이고, 한 페이지에 10개의 게시글을 보여줄 경우,
      // offset은 (2 - 1) * 10 = 10이 됩니다. 즉, 11번째 게시글부터 보여줍니다.
      // -1하는 이유는 현재페이지 시작이 1인데 이걸 인덱스로 사용하기 위해 -1을 함
      int offset = (cp - 1) * limit;

      // MyBatis의 RowBounds 객체를 생성하여 페이징 처리에 필요한 offset과 limit 설정
      // RowBounds는 MyBatis에서 페이징 처리를 위해 사용하는 클래스입니다.
      RowBounds rowBounds = new RowBounds(offset, limit);
      
      // 회원 서류 관리 페이지에서 Examination테이블의 값들을 축력함
      List<Member> memberList = mapper.memberAllSelect(rowBounds);
      
      Map<String, Object> map = new HashMap<>();
      
      map.put("pagination", pagination);
      map.put("memberList", memberList);
      
      return map;
   }
   
   
   // 서비스 키 발급, 오픈api reponse 할때 xml json 타입 구분
   // 자바스크립트로 하는거만 배웠는데 자바로 되어있는거 한번보기
   // 공공데이터에서 맞는 api를 검색해서 맞는거를 찾아야함 주소를 사용해야해서

   // 회원 목록 조회 / 검색 O
   @Override
   public Map<String, Object> memberSearchSelect(Map<String, Object> paramMap, int cp) {
      
      // 탈퇴하지 않았고 검색조건에 맞는 회원 수 조회하기
      int memberCount = mapper.memberSearchCount(paramMap);
      
      log.info("memberCount {}" + memberCount);
      
      
      Pagination pagination = new Pagination(cp, memberCount);
      
      int limit = pagination.getLimit();
      int offset = (cp -1) * limit;
      RowBounds rowBounds = new RowBounds(offset, limit);
      
      
      List<Member> memberList = mapper.memberSearchSelect(paramMap, rowBounds);
      
      Map<String, Object> map = new HashMap<>();
      
      map.put("pagination", pagination);
      map.put("memberList", memberList);
      
      return map;
   }


   // 회원 상세 조회
   @Override
   public Member adminDetailSelect(int memberNo) {
      
      
      
      return mapper.adminDetailSelect(memberNo);
   }
   
   
   
   

   /** 심사 서류 관리 게시판 조회 검색X
    *
    */
   @Override
   public Map<String, Object> examinationAllSelect(int cp) {
      
      // 전체 서류 수
      int examinationCount = mapper.examinationAllCount();
      
      // memberCount + cp 을 이용해 pagination 생성
      Pagination pagination = new Pagination(cp, examinationCount);
      
      // 페이징 처리
      int limit = pagination.getLimit();
      int offset = (cp - 1) * limit;
      RowBounds rowBounds = new RowBounds(offset, limit);
      
      // 심사 서류 관리 페이지에서 Examination테이블의 값들을 축력함
      List<Examination> examinationList = mapper.examinationAllSelect(rowBounds);
      
      Map<String, Object> map = new HashMap<>();
      
      map.put("pagination", pagination);
      map.put("examinationList", examinationList);

      return map;
   }


   /** 심사 서류 관리 게시판 조회 검색O
    *
    */
   @Override
   public Map<String, Object> examinationSearchSelect(Map<String, Object> paramMap, int cp) {
      
      // 검색한 서류 수
      int examinationCount = mapper.examinationSearchCount(paramMap);
      
      Pagination pagination = new Pagination(cp, examinationCount);
      
      int limit = pagination.getLimit();
      int offset = (cp - 1) * limit;
      RowBounds rowBounds = new RowBounds(offset, limit);
      
      List<Examination> examinationList = mapper.examinationSearchSelect(paramMap, rowBounds);
      
      Map<String, Object> map = new HashMap<>();
      
      map.put("pagination", pagination);
      map.put("examinationList", examinationList);
      
      
      return map;
   }


   @Override
   public Examination examinationDetailSelect(int examId) {
      return mapper.examinationDetailSelect(examId);
   }


   // 서류 승인 
   @Override
   public int updateAdminDocument(int examId) {

      return mapper.updateAdminDocument(examId);
   }
   
   @Override
   public String signUpAdminDocument(String HtmlName,int examId) {
      // 서류 통과 클라이언트 조회 
            Examination exam = mapper.examinationDetailSelect(examId);
            
            log.info("서류 통과된 클라이언트 정보는? :"+exam);
            
            // 회원가입 폼 url만들기  
            String url = createSignUpUrl();
            
            url += "/"+exam.getExamId();
            
            log.info("url 주소는 어떻게 되지? "+url);
            
            try {
               
               String subject = "[goldenPrestige]에서 전송합니다."; 
               
               
               MimeMessage mimeMessage = mailSender.createMimeMessage();
               
               MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
               
               helper.setTo(exam.getExamEmail()); 
               helper.setSubject(subject);
               
               helper.setText( loadHtml(url, HtmlName),true);
               
               helper.addInline("logo", new ClassPathResource("static/images/main/Logo4.png"));
               
               //메일 전송 
               mailSender.send(mimeMessage);
               
            } catch(Exception e) {
               
               e.printStackTrace();
               
               return null;
            }
            
            

      return url;
   }
   



   private String loadHtml(String url, String htmlName) {
      
      Context context = new Context();
      
      context.setVariable("url", url);
      
      return templateEngine.process("email/" + htmlName, context);
   }


   /** url 조합 메서드 
 * @return
 */
public String createSignUpUrl() {
      
      String url = "https://goldenprestige.store/member/signUp/";
      
      for(int i=0; i <6; i++) {
         int numberPath = (int)(Math.random()*5);
         
         url+=numberPath;
         
      }
      
      for(int i=0; i<6; i++) {
         
         char messagePath = (char)(Math.random() * 26 + 65);
         url+= messagePath;
      }
      
      
      String specialPath = "!@$^";
      
      url+=specialPath;
      
      log.info("url주소 어떻게 되는지 알려주세요"+url);
      return url;
   }


   
   
	/** 요양사 전체 조회
	 *
	 */
	@Override
	public Map<String, Object> caregiversAllSelect(int cp) {
		
		// 전체 요양사 수 조회
		int caregiversCount = mapper.caregiversAllCount();
		
		Pagination pagination = new Pagination(cp, caregiversCount);
		
		// 한페이지에 보여줄 게시글 수 (10개)
		int limit = pagination.getLimit();
		
		// 현재 페이지에서 시작할 게시글 번호
		// 인덱스로 이용할거니 -1을 하고 현재페이지가 3이면
		// 20 => 21번 게시글 부터 보여줌
		int offset = (cp -1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
	      
	    // 요양사 관리 페이지에서 Examination테이블의 값들을 축력함
	    List<CareGiver> caregiversList = mapper.caregiversAllSelect(rowBounds);
	      
	    Map<String, Object> map = new HashMap<>();
	    
	    map.put("pagination", pagination);
	    map.put("caregiversList", caregiversList);
		
		
		
		return map;
	}


	/** 요양사 검색한 결과 조회
	 *
	 */
	@Override
	public Map<String, Object> caregiversSearchSelect(Map<String, Object> paramMap, int cp) {
		
		// 탈퇴하지 않았고 검색조건에 맞는 회원 수 조회하기
	    int caregiversCount = mapper.caregiversSearchCount(paramMap);
	      
	    log.info("caregiversCount {}" + caregiversCount);
	      
	      
	    Pagination pagination = new Pagination(cp, caregiversCount);
	      
	    int limit = pagination.getLimit();
	    int offset = (cp -1) * limit;
	    RowBounds rowBounds = new RowBounds(offset, limit);
	      
	      
	    List<CareGiver> caregiversList = mapper.caregiversSearchSelect(paramMap, rowBounds);
	      
	    System.out.println("caregiversList@@@@@@@@@@@@@@@@" + caregiversList);
	    
	    
	    Map<String, Object> map = new HashMap<>();
	      
	    map.put("pagination", pagination);
	    map.put("caregiversList", caregiversList);
	      
		return map;
	}


	@Override
	public CareGiver caregiversDetailSelect(int caregiversNo) {
		return mapper.caregiversDetailSelect(caregiversNo);
	}


	/** 여가 일정 전체 조회
	 *
	 */
	@Override
	public ClubReservation clubAllSelect(Map<String, Object> paramMap) {
		return mapper.clubAllSelect(paramMap);
	}


	/** 게시글 상세조회
	 *
	 */
	@Override
	public Board boardDetailSelect(Object boardNo) {
		return mapper.boardDetailSelect(boardNo);
	}


	/** 일정 상세 조회
	 *
	 */
	@Override
	public List<Reservation> reservationAllSelect(ClubReservation reservation) {
		
		return mapper.reservationAllSelect(reservation);
	}


	/** 일정 조회
	 *
	 */
	@Override
	public List<Reservation> getReservedDates() {
		return mapper.getReservedDates();
	}


	/** 특정 날짜의 예약 목록을 가져오는 함수
	 *
	 */
	@Override
	public List<Reservation> getReservationsForDate(String clubResvTime) {
		return mapper.getReservationsForDate(clubResvTime);
	}
   
}