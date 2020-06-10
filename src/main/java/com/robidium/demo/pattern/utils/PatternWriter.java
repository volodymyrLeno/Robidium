package com.robidium.demo.pattern.utils;

import com.robidium.demo.main.AutomatabilityAssessment.data.Sequence;
import com.robidium.demo.main.RoutineIdentification.data.Pattern;

public interface PatternWriter {

    void writePatternInstance(Sequence sequence, Pattern pattern);

    void writeDataTransformations(Pattern pattern);

    void writeFunctionalDependencies(Pattern pattern);

    void writeTransformationMap(Pattern pattern);
}
