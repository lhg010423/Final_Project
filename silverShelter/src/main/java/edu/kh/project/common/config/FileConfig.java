package edu.kh.project.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.MultipartConfigElement;

@Configuration
@PropertySource("classpath:/config.properties")
public class FileConfig implements WebMvcConfigurer{
	
	@Value("${spring.servlet.multipart.file-size-threshold}")
	private long fileSizeThreshold;
	
	// 요청당 파일 최대 크기
	@Value("${spring.servlet.multipart.max-request-size}")
	private long maxRequestSize;
	
	// 개별 파일당 최대 크기
	@Value("${spring.servlet.multipart.max-file-size}")
	private long maxFileSize;
	
	// 임계값 초과 시 임시 저장 폴더 경로
	@Value("${spring.servlet.multipart.location}")
	private String location;
	
	// 프로필 이미지 파일 경로명, 서버 폴더 경로
	@Value("${my.profile.resource-handler}")
	private String profileResourceHandler;
//	@Value("${my.profile.resource-location}")
//	private String profileResourceLocation;
	
	// 게시글 이미지 파일 경로명, 서버 폴더 경로
	@Value("${my.board.resource-handler}")
	private String boardResourceHandler;
//	@Value("${my.board.resource-location}")
//	private String boardResourceLocation;
	
	// norazo 프로젝트 classpath"/static/images/ 하위 폴더 경로
	String fullClassPath = System.getProperty("java.class.path");
	int idx = fullClassPath.indexOf("bin");
	String classPath = fullClassPath.substring(0, idx) + "src/main/resources/static/images/";
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 카테고리 이미지
		registry.addResourceHandler("/category/**")
		.addResourceLocations("file:///" + classPath + "/category/");
	
		// 프로필 이미지 요청 - 서버 폴더 연결 추가
		registry.addResourceHandler(profileResourceHandler) // /myPage/profile/**
		.addResourceLocations("file:///" + classPath + "/profile/"); // classpath:/static/images/profile/
		
		// 게시글 썸네일 이미지 요청 - 서버 폴더 연결 추가
		registry.addResourceHandler(boardResourceHandler) // /images/board/**
		.addResourceLocations("file:///" + classPath + "/board/"); // classpath:/static/images/board/
		
		registry.addResourceHandler("/images/**")
		.addResourceLocations("classpath:/static/images/");
		
	}
	
	
	
	@Bean
	public MultipartConfigElement configElement() {
		// MultipartConfigElement :
		// 파일 업로드를 처리하는데 사용되는 MultipartConfigElement를 구성하고 반환
		// 파일 업로드를 위한 구성 옵션을 설정하는데 사용
		// 업로드 파일의 최대 크기, 메모리에서의 임시 저장 경로 등을 설정 가능
		// -> 서버 경로 작성은 config.properties 에서 작성해야함 (보안 문제 때문에)
		
		MultipartConfigFactory factory = new MultipartConfigFactory();
		
		// long 타입 못 받기 때문에 DataSize.ofBytes 라는 함수 이용해야함
		factory.setFileSizeThreshold(DataSize.ofBytes(fileSizeThreshold));
		
		factory.setMaxFileSize(DataSize.ofBytes(maxRequestSize));
		
		factory.setMaxRequestSize(DataSize.ofBytes(maxFileSize));
		
		factory.setLocation(location);
		
		return factory.createMultipartConfig();
	}
	
	// MultipartResolver 객체를 Bean으로 추가
	// -> 추가 후 위에서 만든 MultipartConfig 자동으로 이용함
	@Bean
	public MultipartResolver multipartResolver() {
		// MultipartResolver : MultipartFile을 처리해주는 해결사
		// MultipartResolver는 클라이언트로부터 받은 Multipart 요청을 처리하고
		// 이 중에서 업로드된 파일을 추출하여 MultipartFile 객체로 제공하는 역할
		
		StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
		
		return multipartResolver;
	}
	
}