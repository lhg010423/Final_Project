package com.silver.shelter.careGiver.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository
public class CaregiverDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> getCaregiverInfoById(int caregiverId) {
        String sql = "SELECT CAREGIVERS_NO, CAREGIVERS_NAME, CAREGIVERS_AGE, CAREGIVERS_GENDER,"
        		+ "CAREGIVERS_TEL, CAREGIVERS_EXPERIENCE, CAREGIVERS_WORK_HOURS, CAREGIVERS_ROLE" +
                     "FROM CAREGIVERS " +
                     "WHERE CAREGIVERS_NO = ?";

        return jdbcTemplate.queryForMap(sql, caregiverId);
    }
}
