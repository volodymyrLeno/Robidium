package com.robidium.demo.miner.controller;

import com.robidium.demo.cases.CaseService;
import com.robidium.demo.main.AutomatabilityAssessment.service.Foofah.FoofahService;
import com.robidium.demo.main.AutomatabilityAssessment.service.FunctionalDependencies.ItemsDependencyService;
import com.robidium.demo.main.RoutineIdentification.data.Pattern;
import com.robidium.demo.main.RoutineIdentification.service.PatternsMiner;
import com.robidium.demo.main.data.Event;
import com.robidium.demo.main.utils.LogReader;
import com.robidium.demo.main.utils.Parser;
import com.robidium.demo.main.utils.Utils;
import com.robidium.demo.miner.entity.Config;
import com.robidium.demo.pattern.service.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RoutineMinerController {
    private final FoofahService foofahService;
    private final ItemsDependencyService itemsDependencyService;
    private final CaseService caseService;
    private final PatternService patternService;

    @Autowired
    public RoutineMinerController(FoofahService foofahService, ItemsDependencyService itemsDependencyService,
                                  CaseService caseService, PatternService patternService) {
        this.foofahService = foofahService;
        this.itemsDependencyService = itemsDependencyService;
        this.caseService = caseService;
        this.patternService = patternService;
    }

    @PostMapping("/routines")
    public ResponseEntity<List<Pattern>> handleRoutineIdentification(@RequestBody Config configuration) {
        List<Event> events = LogReader.readCSV("log-dir/" + configuration.getLogName());
        Map<String, List<Event>> groupedEvents = Utils.groupEvents(events);

        for (var key : groupedEvents.keySet()) {
            Parser.setContextAttributes(groupedEvents.get(key), configuration.getContext());
        }

        caseService.extractCases(events, configuration.isSegmented());

        List<Pattern> patterns = patternService.extractAll(PatternsMiner.SPMFAlgorithmName.valueOf(configuration.getAlgorithm()),
                configuration.getMinSupport(), configuration.getMinCoverage(), configuration.getMetric());

        if (patterns != null) {
            patterns.forEach(pattern -> pattern.setTransformations(foofahService.findTransformations(pattern)));
            patterns.forEach(pattern -> pattern.setItemsDependencies(itemsDependencyService.findDependencies(pattern)));
        }

        return new ResponseEntity<>(patterns, HttpStatus.OK);
    }
}
