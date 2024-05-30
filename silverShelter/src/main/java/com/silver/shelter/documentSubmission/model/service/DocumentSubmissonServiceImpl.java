package com.silver.shelter.documentSubmission.model.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.silver.shelter.documentSubmission.model.dto.DocumentSubmisson;
import com.silver.shelter.documentSubmission.model.mapper.DocumentSubmissonMapper;

import lombok.RequiredArgsConstructor;

@Service
@PropertySource("classpath:/config.properties")
@RequiredArgsConstructor
@Transactional(rollbackFor=Exception.class)
public class DocumentSubmissonServiceImpl implements DocumentSubmissionService{
	
	private final DocumentSubmissonMapper mapper;
	
	@Value("${category.document.resource-location}")
	private String documentsFolderPath;
	
	@Override
	public int submitDocument(DocumentSubmisson documentSubmisson) {
		
		try {
			// 모든 필수 문서가 비어있는지 확인
			if(!documentSubmisson.getHealthCheckup().isEmpty() 
				&& !documentSubmisson.getFamilyRelationship().isEmpty()
				&& !documentSubmisson.getResidentRegistration().isEmpty()
				&& !documentSubmisson.getResidentRegistration().isEmpty()
				&& !documentSubmisson.getIdCardCopy().isEmpty()) {
				
				
				String contactName = documentSubmisson.getContactName();
				Path contactDir = Paths.get(documentsFolderPath, contactName);
				
				// 디렉토리가 존재하지 않는 경우 디렉토리를 생성
				if(!Files.exists(contactDir)) {
					Files.createDirectories(contactDir);
				}
				
				// 파일 저장 
				saveFile(documentSubmisson.getHealthCheckup(),contactDir);
				saveFile(documentSubmisson.getFamilyRelationship(), contactDir);
				saveFile(documentSubmisson.getResidentRegistration(), contactDir);
				saveFile(documentSubmisson.getIdCardCopy(), contactDir);
				
			}
			
			
			
			return 1;
			
			
			
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
