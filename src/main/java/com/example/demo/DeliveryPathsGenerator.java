package com.example.demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeliveryPathsGenerator {

    public static Set<List<String>> generatePossiblePaths(String DE, String R1, String R2, String C1, String C2) {
        List<String> allLocations = new ArrayList<>();
        allLocations.add(DE);
        allLocations.add(R1);
        allLocations.add(R2);
        allLocations.add(C1);
        allLocations.add(C2);

        Set<List<String>> paths = new HashSet<>();
        List<String> currentPath = new ArrayList<>();

        HashSet<String> visited = new HashSet<>();
        currentPath.add(DE);  // Start with DE
        visited.add(DE);
        permute(allLocations,visited, paths, currentPath);
        return paths;
    }

    private static void permute(List<String> arr, Set<String> visited, Set<List<String>> paths, List<String> currentPath) {
        if (currentPath.size() == arr.size()) {
            paths.add(new ArrayList<>(currentPath));
            return;
        }

        for (String location : arr) {
            if (!visited.contains(location) && canVisit(location, currentPath)) {
                visited.add(location);
                currentPath.add(location);
                permute(arr, visited, paths, currentPath);
                currentPath.remove(currentPath.size() - 1);
                visited.remove(location);
            }
        }
    }

    private static boolean canVisit(String location, List<String> currentPath) {
        if (location.equals("C1") && !currentPath.contains("R1")) {
            return false;
        }
        if (location.equals("C2") && !currentPath.contains("R2")) {
            return false;
        }
        return true;
    }
}
