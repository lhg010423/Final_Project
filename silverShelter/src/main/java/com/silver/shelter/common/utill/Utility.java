package com.silver.shelter.common.utill;

import java.text.SimpleDateFormat;


public class Utility {
	
	public static int seqNum =1;
	
	public static String fileRename(String originalFileName) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		String date = sdf.format(new java.util.Date());
		
		String number = String.format("%05d", seqNum);
		
		seqNum++;
		
		if(seqNum == 100000) seqNum = 1;
		
		String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
		
		
		return date+"_"+number+ext;
	}
	
	/** 서류 폴더명 만들기
	 * @param contactName
	 * @param contactPhone 
	 * @return
	 */
	public static String createExamFolderName(String contactName, String contactPhone) {
		
		String folderName = contactName + "_" + contactPhone.substring(contactPhone.length() - 4);
		
		return folderName;
		
	}
}
