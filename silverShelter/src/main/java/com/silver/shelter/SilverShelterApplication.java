package com.silver.shelter;

import java.io.IOException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import com.silver.shelter.careGiver.clustering.KMeansClustering;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SilverShelterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SilverShelterApplication.class, args);

//        try {
//            KMeansClustering.main(args);
//        } catch (IOException e) {
//            System.err.println("An error occurred while running KMeansClustering: " + e.getMessage());
//            e.printStackTrace();
//        }

	}

}
