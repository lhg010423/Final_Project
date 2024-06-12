package com.silver.shelter.careGiver.service;

import com.silver.shelter.careGiver.clustering.KMeansClustering;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KMeansClusteringService {

    private final KMeansClustering kMeansClustering;

    public KMeansClusteringService() {
        this.kMeansClustering = new KMeansClustering();
    }

    public int[] clusterCaregivers(String filePath, int numClusters) {
        try {
            // KMeansClustering 클래스의 performClustering 메서드를 호출하여 클러스터링 수행
            return kMeansClustering.performClustering(filePath, numClusters);
        } catch (IOException e) {
            // 예외 발생 시 처리: 로그 출력 및 빈 배열 반환
            e.printStackTrace();
            return new int[0]; // 빈 배열 반환
        }
    }
}
