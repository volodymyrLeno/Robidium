package com.robidium.demo.miner.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Data
@ToString
public class Config {
    private String algorithm;
    private String logName;
    private double minSupport;
    private double minCoverage;
    private String metric;
    private boolean segmented;
    private List<String> context;
}
