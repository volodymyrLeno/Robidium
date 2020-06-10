package com.robidium.demo.pattern.repository;

import com.robidium.demo.main.RoutineIdentification.data.Pattern;
import com.robidium.demo.main.RoutineIdentification.service.PatternsMiner;
import com.robidium.demo.main.data.Event;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class SimplePatternRepository implements PatternRepository {

    @Getter
    private List<Pattern> patterns;

    @Override
    public Pattern getById(String id) {
        return patterns.stream().filter(p -> p.getId().equals(UUID.fromString(id)))
                .findFirst().orElse(null);
    }

    @Override
    public List<Pattern> findAll(Map<Integer, List<Event>> cases, PatternsMiner.SPMFAlgorithmName algorithm,
                                 Double minSupport, Double minCoverage, String metric) {
        patterns = PatternsMiner.discoverPatterns(cases, algorithm, minSupport, minCoverage, metric);
        return patterns;
    }
}
