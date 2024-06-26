package com.silver.shelter.careGiver.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.silver.shelter.careGiver.model.CareGiver;
@Service
public class CareGiverClustering {

    private List<CareGiver> clusterCentroids; // 클러스터 중심값을 저장할 리스트

 // 클러스터링 메서드
    public List<List<CareGiver>> clusterCareGivers(List<CareGiver> caregivers, int numClusters) {
        // 클러스터 중심 초기화
        List<List<CareGiver>> clusters = initializeClusters(caregivers, numClusters);

        // 랜덤으로 초기 클러스터 중심 설정
        clusterCentroids = new ArrayList<>();
        List<Integer> randomIndices = generateRandomIndices(caregivers.size(), numClusters);
        for (int i = 0; i < numClusters; i++) {
            clusterCentroids.add(caregivers.get(randomIndices.get(i)));
        }

        
        // 클러스터 할당
        int[] clusterAssignments = assignClusters(caregivers, clusterCentroids);

        // 클러스터링 반복 - 중심 재계산
        boolean centroidsChanged = true;
        while (centroidsChanged) {
            recomputeCentroids(caregivers, clusterCentroids, clusterAssignments);
            int[] newClusterAssignments = assignClusters(caregivers, clusterCentroids);
            centroidsChanged = !Arrays.equals(clusterAssignments, newClusterAssignments);
            clusterAssignments = newClusterAssignments;
        }

        // 클러스터 리스트 구성
        for (int i = 0; i < caregivers.size(); i++) {
            clusters.get(clusterAssignments[i]).add(caregivers.get(i));
        }

        // 실루엣 점수 계산
        double silhouetteScore = calculateSilhouetteScore(caregivers, clusterAssignments);
        System.out.println("Silhouette Score: " + silhouetteScore);
        
        return clusters;
        
    }

    // 실루엣 점수 계산 메서드
    private double calculateSilhouetteScore(List<CareGiver> caregivers, int[] clusterAssignments) {
        double[] silhouetteValues = new double[caregivers.size()];
        for (int i = 0; i < caregivers.size(); i++) {
            double a_i = calculateA(caregivers, clusterAssignments, i);
            double b_i = calculateB(caregivers, clusterAssignments, i);
            silhouetteValues[i] = (b_i - a_i) / Math.max(a_i, b_i);
        }
        return Arrays.stream(silhouetteValues).average().orElse(Double.NaN);
    }

    // 클러스터 내부 거리 평균 계산
    private double calculateA(List<CareGiver> caregivers, int[] clusterAssignments, int index) {
        int clusterLabel = clusterAssignments[index];
        double sumDistance = 0;
        int count = 0;

        for (int i = 0; i < caregivers.size(); i++) {
            if (clusterAssignments[i] == clusterLabel && i != index) {
                sumDistance += calculateDistance(caregivers.get(index), caregivers.get(i));
                count++;
            }
        }

        return count > 0 ? sumDistance / count : 0;
    }

    // 다른 클러스터와의 평균 거리 계산
    private double calculateB(List<CareGiver> caregivers, int[] clusterAssignments, int index) {
        int clusterLabel = clusterAssignments[index];
        Map<Integer, Double> clusterDistanceSum = new HashMap<>();
        Map<Integer, Integer> clusterDataCount = new HashMap<>();

        for (int i = 0; i < caregivers.size(); i++) {
            if (clusterAssignments[i] != clusterLabel) {
                int otherCluster = clusterAssignments[i];
                double distance = calculateDistance(caregivers.get(index), caregivers.get(i));
                clusterDistanceSum.put(otherCluster, clusterDistanceSum.getOrDefault(otherCluster, 0.0) + distance);
                clusterDataCount.put(otherCluster, clusterDataCount.getOrDefault(otherCluster, 0) + 1);
            }
        }

        double minAverageDistance = Double.MAX_VALUE;
        for (Map.Entry<Integer, Double> entry : clusterDistanceSum.entrySet()) {
            int cluster = entry.getKey();
            double sumDistance = entry.getValue();
            int count = clusterDataCount.get(cluster);
            double averageDistance = sumDistance / count;
            if (averageDistance < minAverageDistance) {
                minAverageDistance = averageDistance;
            }
        }

        return minAverageDistance;
    }
    
    // 랜덤 인덱스 생성 메서드
    private static List<Integer> generateRandomIndices(int dataSize, int numIndices) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < dataSize; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);
        return indices.subList(0, numIndices);
    }

    // 초기 클러스터 설정
    private static List<List<CareGiver>> initializeClusters(List<CareGiver> caregivers, int numClusters) {
        List<List<CareGiver>> clusters = new ArrayList<>();
        for (int i = 0; i < numClusters; i++) {
            clusters.add(new ArrayList<>());
        }
        return clusters;
    }

    // 클러스터 할당
    private static int[] assignClusters(List<CareGiver> caregivers, List<CareGiver> clusterCentroids) {
        int[] clusterAssignments = new int[caregivers.size()];
        for (int i = 0; i < caregivers.size(); i++) {
            CareGiver dataPoint = caregivers.get(i);
            int closestCluster = 0;
            double minDistance = Double.MAX_VALUE;
            for (int j = 0; j < clusterCentroids.size(); j++) {
                CareGiver centroid = clusterCentroids.get(j);
                double distance = calculateDistance(dataPoint, centroid);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCluster = j;
                }
            }
            clusterAssignments[i] = closestCluster;
        }
        return clusterAssignments;
    }

    // 클러스터 중심 재계산
    private static void recomputeCentroids(List<CareGiver> caregivers, List<CareGiver> clusterCentroids,
                                           int[] clusterAssignments) {
        // 각 클러스터의 합산값 초기화
        List<double[]> clusterSums = new ArrayList<>();
        for (int i = 0; i < clusterCentroids.size(); i++) {
            clusterSums.add(new double[5]); // 5개의 특성에 대한 합산값 (age, gender, experience, workHours, role)
        }
        int[] clusterSizes = new int[clusterCentroids.size()];

        // 각 데이터 포인트의 값을 클러스터에 누적
        for (int i = 0; i < caregivers.size(); i++) {
            CareGiver caregiver = caregivers.get(i);
            int clusterIndex = clusterAssignments[i];
            clusterSizes[clusterIndex]++;
            double[] sum = clusterSums.get(clusterIndex);
            sum[0] += mapAgeToValue(caregiver.getCaregiversAge());
            sum[1] += mapGenderToValue(caregiver.getCaregiversGender());
            sum[2] += mapExperienceToValue(caregiver.getCaregiversExperience());
            sum[3] += mapWorkHoursToValue(caregiver.getCaregiversWorkHours());
            sum[4] += mapRoleToValue(caregiver.getCaregiversRole());
        }

        // 클러스터 중심 재계산
        for (int i = 0; i < clusterCentroids.size(); i++) {
            double[] sum = clusterSums.get(i);
            if (clusterSizes[i] > 0) {
                CareGiver centroid = clusterCentroids.get(i);
                centroid.setCaregiversAge(mapValueToAge(sum[0] / clusterSizes[i]));
                centroid.setCaregiversGender(mapValueToGender(sum[1] / clusterSizes[i]));
                centroid.setCaregiversExperience(mapValueToExperience(sum[2] / clusterSizes[i]));
                centroid.setCaregiversWorkHours(mapValueToWorkHours(sum[3] / clusterSizes[i]));
                centroid.setCaregiversRole(mapValueToRole(sum[4] / clusterSizes[i]));
            }
        }
    }

    // 거리 계산
    private static double calculateDistance(CareGiver dataPoint, CareGiver centroid) {
        double ageDistance = Math.abs(mapAgeToValue(dataPoint.getCaregiversAge()) - mapAgeToValue(centroid.getCaregiversAge()));
        double genderDistance = Math.abs(mapGenderToValue(dataPoint.getCaregiversGender()) - mapGenderToValue(centroid.getCaregiversGender()));
        double experienceDistance = Math.abs(mapExperienceToValue(dataPoint.getCaregiversExperience()) - mapExperienceToValue(centroid.getCaregiversExperience()));
        double workHoursDistance = Math.abs(mapWorkHoursToValue(dataPoint.getCaregiversWorkHours()) - mapWorkHoursToValue(centroid.getCaregiversWorkHours()));
        double roleDistance = Math.abs(mapRoleToValue(dataPoint.getCaregiversRole()) - mapRoleToValue(centroid.getCaregiversRole()));

        // 유클리디안 거리 계산
        return Math.sqrt(Math.pow(ageDistance, 2) + Math.pow(genderDistance, 2) + Math.pow(experienceDistance, 2) +
                Math.pow(workHoursDistance, 2) + Math.pow(roleDistance, 2));
    }
    // 나이 값을 숫자로 변환
    private static int mapAgeToValue(String age) {
        if (age.equals("20-40")) {
            return 20000;
        } else if (age.equals("40-50")) {
            return 30000;
        } else {
            return 10000;
        }
    }

    // 성별 값을 숫자로 변환
    private static int mapGenderToValue(String gender) {
        if (gender.equals("female")) {
            return 100000;
        } else {
            return 200000;
        }
    }

    // 경험 값을 숫자로 변환
    private static int mapExperienceToValue(String experience) {
        if (experience.equals("novice")) {
            return 100;
        } else if (experience.equals("intermediate")) {
            return 200;
        } else {
            return 300;
        }
    }

    // 근무 시간 값을 숫자로 변환
    private static int mapWorkHoursToValue(String workHours) {
        if (workHours.equals("morning")) {
            return 1000;
        } else if (workHours.equals("afternoon")) {
            return 2000;
        } else {
            return 3000; // evening
        }
    }

    // 역할 값을 숫자로 변환
    private static int mapRoleToValue(String role) {
        if (role.equals("housekeeping")) {
            return 1;
        } else if (role.equals("personalCare")) {
            return 2;
        } else {
            return 3; // companionship
        }
    }

 // 숫자를 나이 범위로 변환
    private static String mapValueToAge(double value) {
        if (value < 15000) {
            return "50+";
        } else if (value < 25000) {
            return "20-40";
        } else {
            return "40-50";
        }
    }

    // 숫자를 성별로 변환
    private static String mapValueToGender(double value) {
        if (value < 150000) {
            return "female";
        } else {
            return "male";
        }
    }

    // 숫자를 경험으로 변환
    private static String mapValueToExperience(double value) {
        if (value < 150) {
            return "novice";
        } else if (value < 250) {
            return "intermediate";
        } else {
            return "experienced";
        }
    }

    // 숫자를 근무 시간으로 변환
    private static String mapValueToWorkHours(double value) {
        if (value < 1500) {
            return "morning";
        } else if (value < 2500) {
            return "afternoon";
        } else {
            return "evening";
        }
    }

    // 숫자를 역할로 변환
    private static String mapValueToRole(double value) {
        if (value < 1.5) {
            return "housekeeping";
        } else if (value < 2.5) {
            return "personalCare";
        } else {
            return "companionship";
        }
    }

    // 클러스터 중심값 리스트 반환
    public List<CareGiver> getClusterCentroids() {
        return clusterCentroids;
    }
   

}
