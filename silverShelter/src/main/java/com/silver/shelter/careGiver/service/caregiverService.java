package com.silver.shelter.careGiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.silver.shelter.careGiver.repository.CaregiverDao;

import java.util.Map;

@Service
public class caregiverService {

    @Autowired
    private static CaregiverDao caregiverDao;

    public Map<String, Object> getCaregiverInfoById(int caregiverId) {
        return caregiverDao.getCaregiverInfoById(caregiverId);
    }
}