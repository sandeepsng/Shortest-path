package com.example.demo.Model;

import lombok.Data;

@Data
public class Restaurant {
    private final String name;
    private final double[] location;
    private final int preparationTime;
}
