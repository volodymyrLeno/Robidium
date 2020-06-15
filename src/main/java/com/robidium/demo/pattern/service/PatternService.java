package com.robidium.demo.pattern.service;

import com.robidium.demo.main.RoutineIdentification.data.Pattern;
import com.robidium.demo.main.RoutineIdentification.service.PatternsMiner;
import com.robidium.demo.main.data.Event;
import com.robidium.demo.pattern.repository.PatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PatternService {
    private final PatternRepository patternRepository;

    @Autowired
    public PatternService(PatternRepository patternRepository) {
        this.patternRepository = patternRepository;
    }

    public Pattern getById(String id) {
        return patternRepository.getById(id);
    }

    public List<Pattern> extractAll(PatternsMiner.SPMFAlgorithmName algorithm,
                                    Double minSupport, Double minCoverage, String metric) {
        return patternRepository.findAll(algorithm, minSupport, minCoverage, metric);
    }
}
