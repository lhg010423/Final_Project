package com.silver.shelter.careGiver.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.silver.shelter.careGiver.model.CareGiver;
import com.silver.shelter.careGiver.model.CaregiverCluster;

@Service
public class CareGiverService {
    private List<CaregiverCluster> clusters;

    public CareGiverService(List<CaregiverCluster> clusters) {
        this.clusters = clusters;
    }

    public List<CareGiver> recommendCaregivers(CareGiver userData) {
        // 사용자 정보를 기반으로 요양사 추천
        return null;
    }
}
