package com.robidium.demo.main.AutomatabilityAssessment.data;

import com.robidium.demo.main.RoutineIdentification.data.PatternItem;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ItemsDependency {
    private PatternItem depender;
    private List<PatternItem> dependee;
    private Map<List<String>, String> dependerPerDependee;
}
