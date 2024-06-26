// CareGiverMapper.java
package com.silver.shelter.careGiver.mapper;

import com.silver.shelter.careGiver.model.CareGiver;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CaregiverMapper {
    List<CareGiver> getAllCareGivers();
}
