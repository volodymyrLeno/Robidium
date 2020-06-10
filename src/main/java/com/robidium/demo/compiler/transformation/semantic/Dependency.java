package com.robidium.demo.compiler.transformation.semantic;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@ToString
public class Dependency {
    private List<String> dependeeIndices;
    private String dependerIndex;
    private Map<String, String> dependerPerDependee;

    public Dependency(List<String> dependeeIndices, String dependerIndex, Map<String, String> dependerPerDependee) {
        this.dependeeIndices = dependeeIndices;
        this.dependerIndex = dependerIndex;
        this.dependerPerDependee = dependerPerDependee;
    }
}
