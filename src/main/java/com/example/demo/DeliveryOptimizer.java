package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class DeliveryOptimizer {

    public static void main(String[] args) {
        List<String> bestPath = findShortestDeliveryPath();
        double totalTime = calculateTotalTime(bestPath);
        System.out.println("Shortest delivery time: " + totalTime + " hours");
        System.out.println("Best path: " + bestPath);
    }

    // Coordinates of locations
    private static final Map<String, double[]> coordinates = new HashMap<>();

    static {
        coordinates.put("DE", new double[]{12.9345, 77.6221}); // Delivery Executive
        coordinates.put("R1", new double[]{12.9177, 77.6223}); // Restaurant 1
        coordinates.put("R2", new double[]{12.9255, 77.6222}); // Restaurant 2
        coordinates.put("C1", new double[]{12.9302, 77.6245}); // Consumer 1
        coordinates.put("C2", new double[]{12.9384, 77.6255}); // Consumer 2
    }

    // Preparation times for restaurants (in minutes)
    private static final Map<String, Integer> preparationTimes = new HashMap<>();

    static {
        preparationTimes.put("R1", 15); // Restaurant 1
        preparationTimes.put("R2", 20); // Restaurant 2
    }

    // Average speed for travel (in km/hr)
    private static final double AVERAGE_SPEED_KM_PER_HR = 20.0;


    private static List<String> findShortestDeliveryPath() {
        List<List<String>> possiblePaths= new ArrayList<>( DeliveryPathsGenerator.generatePossiblePaths("DE", "R1","R2","C1","C2"));
        List<String> bestPath = null;
        double shortestTime = Double.MAX_VALUE;

        for (List<String> path : possiblePaths) {
            double totalTime = calculateTotalTime(path);
            if (totalTime < shortestTime) {
                shortestTime = totalTime;
                bestPath = path;
            }
        }
        return bestPath;
    }

    private static double calculateTotalTime(List<String> path) {
        double totalTime = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            String currentLocation = path.get(i);
            String nextLocation = path.get(i + 1);
            double distance = calculateDistance(currentLocation, nextLocation);
            double travelTime = distance / AVERAGE_SPEED_KM_PER_HR;
            totalTime += travelTime;
            if (coordinates.containsKey(currentLocation) && coordinates.containsKey(nextLocation)) {
                int prepTime = preparationTimes.getOrDefault(nextLocation, 0);
                totalTime += prepTime / 60.0; // Convert preparation time
                totalTime += prepTime / 60.0; // Convert preparation time to hours
            }
        }
        return totalTime;
    }

    private static double calculateDistance(String from, String to) {
        double[] fromCoords = coordinates.get(from);
        double[] toCoords = coordinates.get(to);
        double lat1 = Math.toRadians(fromCoords[0]);
        double lon1 = Math.toRadians(fromCoords[1]);
        double lat2 = Math.toRadians(toCoords[0]);
        double lon2 = Math.toRadians(toCoords[1]);

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double radiusOfEarth = 6371; // Radius of Earth in kilometers

        return radiusOfEarth * c;
    }
}


