package com.silver.shelter.careGiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.silver.shelter.careGiver.model.CareGiver;
import com.silver.shelter.careGiver.repository.CaregiverDao;
import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 간병인 관리 서비스 클래스.
 */
@Service
public class CaregiverService {

    private static final Logger logger = LoggerFactory.getLogger(CaregiverService.class);

    @Autowired
    private CaregiverDao caregiverDao;

    /**
     * MEMBER 테이블에서 간병인을 선택합니다.
     * 
     * @param caregiverId 간병인 ID.
     * @param memberNo 회원 번호.
     */
    public void selectCaregiver(int caregiverId, int memberNo) {
        logger.info("MEMBER 테이블에서 간병인을 업데이트 합니다: caregiverId={}, memberNo={}", caregiverId, memberNo);
        caregiverDao.selectCaregiver(caregiverId, memberNo);
        logger.info("간병인이 성공적으로 업데이트 되었습니다.");
    }

    /**
     * ID로 간병인 정보를 조회합니다.
     * 
     * @param caregiverId 조회할 간병인의 ID.
     * @return 조회된 간병인 정보를 담은 CareGiver 객체.
     * @throws NotFoundException 해당 ID로 간병인을 찾을 수 없을 때 발생하는 예외.
     */
    public CareGiver getCaregiverInfoById(int caregiverId) throws NotFoundException {
        CareGiver caregiverInfo = caregiverDao.getCaregiverInfoById(caregiverId);
        return caregiverInfo;
    }
}
