package com.robidium.demo.script.service;

import com.robidium.demo.compiler.Compiler;
import com.robidium.demo.main.RoutineIdentification.data.Pattern;
import com.robidium.demo.pattern.utils.PatternSerializer;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Arrays;

@Service
public class UIPathScriptService implements ScriptService {

    @Override
    public void generateScript(Pattern pattern) {
        Path instancePath = PatternSerializer.writeInstance(pattern);
        Path dataTransformationsPath = PatternSerializer.writeDataTransformations(pattern);
        Path functionalDependenciesPath = PatternSerializer.writeFunctionalDependencies(pattern);
        Path transformationMapPath = PatternSerializer.writeTransformationMap(pattern);

         Compiler.compile(Arrays.asList(instancePath, dataTransformationsPath,
                functionalDependenciesPath, transformationMapPath), pattern.getId().toString());
    }
}
