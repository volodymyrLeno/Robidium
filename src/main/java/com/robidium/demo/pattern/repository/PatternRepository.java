package com.robidium.demo.pattern.repository;

import com.robidium.demo.main.RoutineIdentification.data.Pattern;
import com.robidium.demo.main.RoutineIdentification.service.PatternsMiner;
import com.robidium.demo.main.data.Event;

import java.util.List;
import java.util.Map;


public interface PatternRepository {

    Pattern getById(String id);

    List<Pattern> findAll(Map<Integer, List<Event>> cases, PatternsMiner.SPMFAlgorithmName algorithm,
                          Double minSupport, Double minCoverage, String metric);
}
