package com.robidium.demo.main.AutomatabilityAssessment.service.FunctionalDependencies;

import com.robidium.demo.main.AutomatabilityAssessment.data.TaneDependency;
import com.robidium.demo.main.AutomatabilityAssessment.service.FunctionalDependencies.TaneExecutor;
import com.robidium.demo.main.RoutineIdentification.data.Pattern;
import com.robidium.demo.main.data.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaneService {
    private List<List<String>> instances;

    public List<TaneDependency> getFunctionalDependencies(Pattern pattern) {
        TaneExecutor taneExecutor = new TaneExecutor(pattern);
        taneExecutor.createInstancesFile();
        taneExecutor.createTaneDataFiles();
        this.instances = taneExecutor.getInstances();

        return taneExecutor.getFunctionalDependencies();
    }

    public Map<String, List<List<String>>> getDependeeValuesPerDepender(Integer dependerIdx,
                                                                        List<TaneDependency> functionalDependencies) {
        Map<String, List<List<String>>> dependeeValuesPerDepender = new HashMap<>();
        List<Integer> dependee = getDependeeByDepender(functionalDependencies, dependerIdx);

        instances.forEach(instance -> {
            String dependerValue = instance.get(dependerIdx);
            dependeeValuesPerDepender.putIfAbsent(dependerValue, new ArrayList<>());
            List<String> values = new ArrayList<>();
            dependee.forEach(d -> values.add(d == -1 ? instance.get(dependerIdx) : instance.get(d)));
            dependeeValuesPerDepender.get(dependerValue).add(values);
        });

        return dependeeValuesPerDepender;
    }

    public List<Integer> getDependeeByDepender(List<TaneDependency> dependencies, Integer depender) {
        List<Integer> indexes = new ArrayList<>();
        dependencies.stream().filter(dependency -> dependency.getDependerIdx().equals(depender))
                .forEach(dependency -> indexes.add(dependency.getDependeeIdx()));

        return indexes;
    }
}