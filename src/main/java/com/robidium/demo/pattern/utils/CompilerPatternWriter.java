package com.robidium.demo.pattern.utils;

import com.opencsv.CSVWriter;
import com.robidium.demo.cases.CaseService;
import com.robidium.demo.main.AutomatabilityAssessment.data.Sequence;
import com.robidium.demo.main.RoutineIdentification.data.Pattern;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CompilerPatternWriter implements PatternWriter {
    private final CaseService caseService;

    @Autowired
    public CompilerPatternWriter(CaseService caseService) {
        this.caseService = caseService;
    }

    @Override
    public void writePatternInstance(Sequence sequence, Pattern pattern) {
        try {
            File file = File.createTempFile("pattern", ".csv");
            CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeDataTransformations(Pattern pattern) {

    }

    @Override
    public void writeFunctionalDependencies(Pattern pattern) {

    }

    @Override
    public void writeTransformationMap(Pattern pattern) {

    }
}
