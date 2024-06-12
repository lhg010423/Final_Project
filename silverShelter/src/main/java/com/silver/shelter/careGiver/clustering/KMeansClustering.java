package com.silver.shelter.careGiver.clustering;

import java.util.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.stream.*;
import org.apache.commons.lang3.ArrayUtils;

public class KMeansClustering {
    private static final int caregiversAgeIndex = 2; // caregiversAge 열은 3번째 열에 해당하는 인덱스입니다.
    
    public static void main(String[] args) throws IOException {
        // 데이터 파일 경로
        String filePath = "C:\\workspace\\Final_Project\\silverShelter\\src\\main\\resources\\caregiver_data1.csv";
        
        // CSV 파일에서 데이터 읽기
        List<String[]> data = readCSV(filePath);
        
        // 필요한 특징 열 선택
        List<String[]> selectedData = selectFeatures(data);
        
        // 범주형 데이터를 레이블 인코딩으로 변환
        Map<String, Integer[]> labelEncodings = encodeLabels(selectedData);
        
        // 데이터를 표준화
        double[][] scaledFeatures = standardizeData(labelEncodings, selectedData);
        
        // K-평균 클러스터링을 적용
        int numClusters = 40;
        KMeans kmeans = new KMeans(numClusters);
        kmeans.fit(scaledFeatures);
        
        // 클러스터링 결과 출력
        int[] clusterAssignments = kmeans.getClusterAssignments();
        for (int i = 0; i < clusterAssignments.length; i++) {
            System.out.println("Data point " + i + " belongs to cluster " + clusterAssignments[i]);
        }
    }
    
    // CSV 파일에서 데이터 읽기
    public static List<String[]> readCSV(String filePath) throws IOException {
        CSVParser parser = CSVParser.parse(new File(filePath), Charset.defaultCharset(), CSVFormat.DEFAULT);
        List<CSVRecord> records = parser.getRecords();
        List<String[]> data = new ArrayList<>();
        for (CSVRecord record : records) {
            String[] values = StreamSupport.stream(record.spliterator(), false)
                                           .toArray(String[]::new);
            data.add(values);
        }
        return data;
    }
    
    // 필요한 특징 열 선택
    public static List<String[]> selectFeatures(List<String[]> data) {
        List<String[]> selectedData = new ArrayList<>();
        for (String[] row : data) {
            String[] selectedRow = {row[0], row[1], row[3], row[4], row[5]}; // caregiversAge 열 제외
            selectedData.add(selectedRow);
        }
        return selectedData;
    }
    
    // 범주형 데이터를 레이블 인코딩으로 변환
    public static Map<String, Integer[]> encodeLabels(List<String[]> data) {
        Map<String, Integer[]> labelEncodings = new HashMap<>();
        for (int i = 0; i < data.get(0).length; i++) {
            final int index = i; // final로 선언된 변수
            String[] column = data.stream()
                                  .map(row -> row[index])
                                  .distinct()
                                  .toArray(String[]::new);
            Integer[] encoding = ArrayUtils.toObject(IntStream.range(0, column.length).toArray());
            labelEncodings.put("Column" + i, encoding);
        }
        return labelEncodings;
    }
    
    // 데이터를 표준화
    public static double[][] standardizeData(Map<String, Integer[]> labelEncodings, List<String[]> data) {
        int numRows = data.size();
        int numCols = data.get(0).length;
        double[][] standardizedData = new double[numRows][numCols];

        // caregiversAge 구간을 숫자로 변환하지 않고 다른 열의 데이터만 표준화
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (col == caregiversAgeIndex) { // caregiversAge 열은 건너뜁니다.
                    continue;
                } else {
                    double value = Double.parseDouble(data.get(row)[col]);
                    standardizedData[row][col] = value;
                }
            }
        }

        return standardizedData;
    }
    public static class KMeans {
        private int numClusters;
        private int[] clusterAssignments;

        public KMeans(int numClusters) {
            this.numClusters = numClusters;
        }

        public void fit(double[][] data) {
            // 데이터 포인트의 개수
            int numDataPoints = data.length;
            
            // 클러스터 중심 초기화
            double[][] clusterCentroids = initializeCentroids(data);

            // 최대 반복 횟수
            int maxIterations = 100;
            
            // 반복적으로 클러스터링 수행
            for (int iteration = 0; iteration < maxIterations; iteration++) {
                // 이전 할당 저장
                int[] prevClusterAssignments = Arrays.copyOf(clusterAssignments, numDataPoints);

                // 각 데이터 포인트를 가장 가까운 클러스터에 할당
                assignClusters(data, clusterCentroids);

                // 클러스터 중심 재계산
                recomputeCentroids(data, clusterCentroids);

                // 수렴 여부 확인
                if (Arrays.equals(prevClusterAssignments, clusterAssignments)) {
                    break;
                }
            }
        }

     // 중심 초기화를 위한 메소드
        private double[][] initializeCentroids(double[][] data) {
            int numDataPoints = data.length;
            int numFeatures = data[0].length;
            
            // 중심 초기화를 위한 배열 생성
            double[][] centroids = new double[numClusters][numFeatures];
            
            // 중심을 무작위로 선택
            Random random = new Random();
            for (int i = 0; i < numClusters; i++) {
                int randomIndex = random.nextInt(numDataPoints);
                centroids[i] = Arrays.copyOf(data[randomIndex], numFeatures);
            }
            
            return centroids;
        }

        // 각 데이터 포인트에 가장 가까운 클러스터 할당
        private void assignClusters(double[][] data, double[][] clusterCentroids) {
            int numDataPoints = data.length;
            
            // 클러스터 할당 배열 초기화
            clusterAssignments = new int[numDataPoints];
            
            // 각 데이터 포인트에 대해 가장 가까운 클러스터 할당
            for (int i = 0; i < numDataPoints; i++) {
                double minDistance = Double.MAX_VALUE;
                int closestCluster = -1;
                for (int j = 0; j < numClusters; j++) {
                    double distance = euclideanDistance(data[i], clusterCentroids[j]);
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestCluster = j;
                    }
                }
                clusterAssignments[i] = closestCluster;
            }
        }

        // 유클리드 거리 계산
        private double euclideanDistance(double[] point1, double[] point2) {
            double sum = 0.0;
            for (int i = 0; i < point1.length; i++) {
                sum += Math.pow(point1[i] - point2[i], 2);
            }
            return Math.sqrt(sum);
        }

        // 클러스터 중심 재계산
        private void recomputeCentroids(double[][] data, double[][] clusterCentroids) {
            int numFeatures = data[0].length;

            // 클러스터에 속한 데이터 포인트들의 평균을 계산하여 중심 재계산
            for (int i = 0; i < numClusters; i++) {
                double[] centroid = new double[numFeatures];
                int count = 0;
                for (int j = 0; j < data.length; j++) {
                    if (clusterAssignments[j] == i) {
                        for (int k = 0; k < numFeatures; k++) {
                            centroid[k] += data[j][k];
                        }
                        count++;
                    }
                }
                if (count > 0) {
                    for (int k = 0; k < numFeatures; k++) {
                        centroid[k] /= count;
                    }
                }
                clusterCentroids[i] = centroid;
            }
        }

        // 클러스터 할당 배열 반환
        public int[] getClusterAssignments() {
            return clusterAssignments;
        }
        }

}