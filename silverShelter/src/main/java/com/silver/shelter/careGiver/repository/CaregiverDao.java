package com.silver.shelter.careGiver.repository;
import java.math.BigDecimal; // Import the BigDecimal class

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.silver.shelter.careGiver.model.CareGiver;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class CaregiverDao {

    private static final Logger logger = LoggerFactory.getLogger(CaregiverDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CareGiver getCaregiverInfoById(int caregiverId) {
        String sql = "SELECT CAREGIVERS_NO, CAREGIVERS_NAME, CAREGIVERS_AGE, CAREGIVERS_GENDER, "
                   + "CAREGIVERS_TEL, CAREGIVERS_EXPERIENCE, CAREGIVERS_WORK_HOURS, CAREGIVERS_ROLE "
                   + "FROM CAREGIVERS WHERE CAREGIVERS_NO = ?";
        logger.info("Executing query to retrieve caregiver info for ID: {}", caregiverId);

        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, caregiverId);

            if (result != null && !result.isEmpty()) {
                CareGiver caregiver = new CareGiver();
                caregiver.setCaregiversNo(((BigDecimal) result.get("CAREGIVERS_NO")).intValue());
                caregiver.setCaregiversName((String) result.get("CAREGIVERS_NAME"));
                caregiver.setCaregiversAge((String) result.get("CAREGIVERS_AGE"));
                caregiver.setCaregiversGender((String) result.get("CAREGIVERS_GENDER"));
                caregiver.setCaregiversTel((String) result.get("CAREGIVERS_TEL"));
                caregiver.setCaregiversExperience((String) result.get("CAREGIVERS_EXPERIENCE"));
                caregiver.setCaregiversWorkHours((String) result.get("CAREGIVERS_WORK_HOURS"));
                caregiver.setCaregiversRole((String) result.get("CAREGIVERS_ROLE"));

                logger.info("Query result: {}", caregiver);
                return caregiver;
            } else {
                logger.warn("No caregiver found with ID: {}", caregiverId);
                return null; // 또는 예외를 던질 수 있음
            }
        } catch (Exception e) {
            logger.error("Error executing query: ", e);
            throw e;
        }
    }

    @Transactional
    public void selectCaregiver(int caregiverId, int memberNo) {
        String sql = "UPDATE MEMBER SET CAREGIVERS_NO = ? WHERE MEMBER_NO = ?";
        logger.info("Executing update to set CAREGIVERS_NO to {} for MEMBER_NO {}", caregiverId, memberNo);

        try {
            int rowsAffected = jdbcTemplate.update(sql, caregiverId, memberNo);
            logger.info("Rows affected by update: {}", rowsAffected);
        } catch (Exception e) {
            logger.error("Error executing update: ", e);
            throw e;
        }
    }
}
