package com.silver.shelter.careGiver.clustering;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataProcessor {

    public void processCSVData(String inputFilePath, String outputFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            // 첫 번째 줄을 읽고 건너 뜁니다.
            br.readLine();

            // 데이터 처리 및 구간 할당
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // caregiversAge를 범주형으로 변환하여 구간 할당
                String age = values[2];
                int ageGroup;
                if (age.equals("20-40")) {
                    ageGroup = 0;
                } else if (age.equals("40-50")) {
                    ageGroup = 1;
                } else {
                    ageGroup = 2;
                }
                
             // values 배열에서 성별 값을 가져옵니다.
                String gender = values[3];

                // 성별 값을 0 또는 1로 변환합니다.
                int genderValue; // 초기값을 설정합니다.
                if (gender.equals("female")) {
                    genderValue = 0;
                } else {
                    genderValue = 1;
                }
                
                // caregiversExperience을 숫자형으로 변환
                int experience;
                switch (values[5]) {
                    case "novice":
                        experience = 0;
                        break;
                    case "intermediate":
                        experience = 1;
                        break;
                    case "experienced":
                        experience = 2;
                        break;
                    default:
                        experience = -1;
                }

                // caregiversWorkHours를 숫자형으로 변환
                int workHours;
                switch (values[6]) {
                    case "morning":
                        workHours = 0;
                        break;
                    case "afternoon":
                        workHours = 1;
                        break;
                    case "evening":
                        workHours = 2;
                        break;
                    default:
                        workHours = -1;
                }

                // caregiversRole을 숫자형으로 변환
                int role;
                switch (values[7]) {
                    case "housekeeping":
                        role = 0;
                        break;
                    case "personalCare":
                        role = 1;
                        break;
                    case "companionship":
                        role = 2;
                        break;
                    default:
                        role = -1;
                }

                // 처리된 데이터 쓰기
                String processedLine = String.format("%s,%s,%d,%d,%s,%d,%d,%d",
                        values[0], values[1], ageGroup, genderValue, values[4], experience, workHours, role);
                bw.write(processedLine);
                bw.newLine();
            }

            System.out.println("Data processing complete.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
