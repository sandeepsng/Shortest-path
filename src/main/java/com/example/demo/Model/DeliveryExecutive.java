package com.example.demo.Model;

import lombok.*;
import lombok.extern.log4j.Log4j2;

@Data
public class DeliveryExecutive {
    private final String name;
    private final double[] location;
}
