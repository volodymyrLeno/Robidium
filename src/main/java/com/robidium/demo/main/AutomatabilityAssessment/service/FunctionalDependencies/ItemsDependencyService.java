package com.robidium.demo.main.AutomatabilityAssessment.service.FunctionalDependencies;

import com.robidium.demo.main.AutomatabilityAssessment.data.ItemsDependency;
import com.robidium.demo.main.AutomatabilityAssessment.data.TaneDependency;
import com.robidium.demo.main.RoutineIdentification.data.Pattern;
import com.robidium.demo.main.RoutineIdentification.data.PatternItem;
import com.robidium.demo.main.data.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemsDependencyService {
    private final TaneService taneService;

    @Autowired
    public ItemsDependencyService(TaneService taneService) {
        this.taneService = taneService;
    }

    public List<ItemsDependency> findDependencies(Map<Integer, List<Event>> cases, Pattern pattern) {
        List<ItemsDependency> itemsDependencies = new ArrayList<>();
        List<TaneDependency> functionalDependencies = taneService.getFunctionalDependencies(cases, pattern);
        var emptyTransformationPairs = pattern.getEmptyTransformationPairs();

        emptyTransformationPairs.forEach(pair -> {
            int dependerIdx = pair.getRight().getIndex();
            var dependeeValuesPerDepender = taneService.getDependeeValuesPerDepender(dependerIdx, functionalDependencies);
            List<Integer> dependeeIndices = taneService.getDependeeByDepender(functionalDependencies, dependerIdx);
            List<PatternItem> dependee = dependeeIndices.contains(-1) ? null : dependeeIndices.stream()
                    .map(idx -> pattern.getItems().get(idx)).collect(Collectors.toList());
            if (dependee != null && dependee.stream().anyMatch(d -> d.getIndex() > dependerIdx)) {
                dependee.clear();
                dependeeValuesPerDepender.forEach((k, v) -> v.forEach(List::clear));
            }
            itemsDependencies.add(getItemsDependency(pair.getRight(), dependee, dependeeValuesPerDepender));
        });

        return itemsDependencies;
    }

    private ItemsDependency getItemsDependency(PatternItem depender,
                                               List<PatternItem> dependee,
                                               Map<String, List<List<String>>> dependeeValuesPerDepender) {
        ItemsDependency dependency = new ItemsDependency();
        dependency.setDepender(depender);
        dependency.setDependee(dependee);
        Map<List<String>, String> uniqueDependerPerDependee = evaluateDependencyValues(dependeeValuesPerDepender);
        dependency.setDependerPerDependee(uniqueDependerPerDependee);

        return dependency;
    }

    private Map<List<String>, String> evaluateDependencyValues(Map<String, List<List<String>>> dependeeValuesPerDepender) {
        Map<List<String>, String> uniqueDependerPerDependee = new HashMap<>();
        dependeeValuesPerDepender.forEach((depender, dependeeValues) -> {
            Set<List<String>> uniqueDependeeListValues = new HashSet<>(dependeeValues);
            uniqueDependeeListValues.forEach(dependee -> uniqueDependerPerDependee.put(dependee,
                    uniqueDependerPerDependee.containsKey(dependee) ? null : depender));
        });

        return uniqueDependerPerDependee;
    }
}
