package com.silver.shelter.careGiver.controller;



import com.silver.shelter.careGiver.service.KMeansClusteringService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CareGiverController {

    private final com.silver.shelter.careGiver.service.KMeansClusteringService clusteringService;

    public CareGiverController(KMeansClusteringService clusteringService) {
        this.clusteringService = clusteringService;
    }

    @GetMapping("/cluster-caregivers")
    public int[] clusterCaregivers(@RequestParam("filePath") String filePath, @RequestParam("numClusters") int numClusters) {
        return clusteringService.clusterCaregivers(filePath, numClusters);
    }

}