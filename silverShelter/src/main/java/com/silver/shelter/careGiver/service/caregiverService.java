package com.silver.shelter.careGiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.silver.shelter.careGiver.model.CareGiver;
import com.silver.shelter.careGiver.repository.CaregiverDao;

import java.util.Map;

import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class caregiverService {

    private static final Logger logger = LoggerFactory.getLogger(caregiverService.class);

    @Autowired
    private CaregiverDao caregiverDao;

    public void selectCaregiver(int caregiverId, int memberNo) {
        logger.info("Updating caregiver in MEMBER table: caregiverId={}, memberNo={}", caregiverId, memberNo);
        caregiverDao.selectCaregiver(caregiverId, memberNo);
        logger.info("Caregiver updated successfully");
    }

    public CareGiver getCaregiverInfoById(int caregiverId) throws NotFoundException {
        CareGiver caregiverInfo = caregiverDao.getCaregiverInfoById(caregiverId);


        
        return caregiverInfo;
    }

}
