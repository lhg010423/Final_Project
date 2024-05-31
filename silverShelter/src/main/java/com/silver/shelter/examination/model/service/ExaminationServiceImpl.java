package com.silver.shelter.examination.model.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.silver.shelter.common.utill.Utility;
import com.silver.shelter.document.model.dto.Document;
import com.silver.shelter.examination.model.dto.Examination;
import com.silver.shelter.examination.model.mapper.ExaminationMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@PropertySource("classpath:/config.properties")
@RequiredArgsConstructor
@Transactional(rollbackFor=Exception.class)
public class ExaminationServiceImpl implements ExaminationService{
	
	private final ExaminationMapper mapper;
	
	@Value("${gp.documents.folder-path}")
	private String documentsFolderPath;
	
	@Value("${gp.documents.web-path}")
	private String webPath;
	
	
	@Override
	public int checkApplicantInfo(Examination applicantInfo) {
		// TODO Auto-generated method stub
		return mapper.checkApplicantInfo(applicantInfo);
	}
	
	/**
	 * 서류 제출 확인 
	 */
	@Override
	public int submitDocument(Examination documentSubmisson) throws Exception {
		
		int result = mapper.insertApplicantInfo(documentSubmisson);
		
		if(result ==0) return 0;
		
		int examId = documentSubmisson.getExamId();
		
		
		try {
			
			// 모든 필수 문서가 비어있는지 확인
			if(!documentSubmisson.getHealthCheckup().isEmpty() 
				&& !documentSubmisson.getFamilyRelationship().isEmpty()
				&& !documentSubmisson.getResidentRegistration().isEmpty()
				&& !documentSubmisson.getIdCardCopy().isEmpty()) {
				
				
				String contactName = documentSubmisson.getContactName();
				String contactPhone = documentSubmisson.getContactPhone();
				
				Path contactDir = Paths.get(documentsFolderPath, 
								  Utility.createExamFolderName(contactName, contactPhone));
				
				// 디렉토리가 존재하지 않는 경우 디렉토리를 생성
				if(!Files.exists(contactDir)) {
					Files.createDirectories(contactDir);
				}
				
				 // examination객체의 4가지 파일 새 List에 담기
				List<MultipartFile> docOriginList = new ArrayList<>();
				
				docOriginList.add(documentSubmisson.getHealthCheckup());
				docOriginList.add(documentSubmisson.getFamilyRelationship());
				docOriginList.add(documentSubmisson.getResidentRegistration());
				docOriginList.add(documentSubmisson.getIdCardCopy());
				
				// DB로 보낼 Document 타입의 List 생성 
				List<Document> docList = new ArrayList<>();
				
				for(int i = 0; i < docOriginList.size(); i++) {
					
					Document doc = Document.builder()
											.documentOriginalName(docOriginList.get(i).getOriginalFilename())
											.documentPath(webPath)
											.docTypeCode(i+1)
											.examId(examId)
											.build();
					
					log.info("이름이 뭐에요 : "+doc.getDocumentOriginalName());
					log.info("파일 경로 : "+doc.getDocumentPath());
					log.info("번호 : "+doc.getDocTypeCode());
					log.info("아이디는 뭐에요 : "+examId);
					
					docList.add(doc);
			}
				
				
           result = mapper.insertDocument(docList);
			
           
           if(result == docList.size()) {
        	   
           	// 서버에 파일 저장
	           saveFile(documentSubmisson.getHealthCheckup(), contactDir);
	           saveFile(documentSubmisson.getFamilyRelationship(), contactDir);
	           saveFile(documentSubmisson.getResidentRegistration(), contactDir);
	           saveFile(documentSubmisson.getIdCardCopy(), contactDir);
               
           } else {
        	   
        	   throw new Exception("서류가 정상 삽입되지 않았습니다.");
           }
           
			}
			
			return result;

			
			
			
		} catch(Exception e) {
			e.printStackTrace();
			
			return 0;
		}
		

	}
	
	/** 디렉토리와 파일 이름을 연결하여 최종 파일 경로를 생성
	 * @param file
	 * @param directory
	 * @throws IOException
	 */
	private void saveFile(MultipartFile file, Path directory) throws IOException{
		
		if(file != null && !file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			Path filePath = directory.resolve(fileName);
			file.transferTo(filePath.toFile() );
		}
		
	}
}
