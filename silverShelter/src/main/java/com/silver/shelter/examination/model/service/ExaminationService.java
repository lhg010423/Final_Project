package com.silver.shelter.examination.model.service;

import com.silver.shelter.examination.model.dto.Examination;

public interface ExaminationService {


	int submitDocument(Examination documentSubmisson) throws Exception;

	int checkApplicantInfo(Examination applicantInfo);

}