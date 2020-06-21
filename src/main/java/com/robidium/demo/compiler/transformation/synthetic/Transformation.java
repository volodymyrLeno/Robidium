package com.robidium.demo.compiler.transformation.synthetic;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class Transformation {
    private String source;
    private String target;
    private List<String> functions;

    public Transformation(String source, String target, List<String> functions) {
        this.source = source;
        this.target = target;
        this.functions = functions;
    }
}
