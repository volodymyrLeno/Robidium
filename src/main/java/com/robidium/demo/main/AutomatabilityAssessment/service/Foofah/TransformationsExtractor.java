package com.robidium.demo.main.AutomatabilityAssessment.service.Foofah;

import com.robidium.demo.cases.CaseService;
import com.robidium.demo.main.AutomatabilityAssessment.data.Transformation;
import com.robidium.demo.main.RoutineIdentification.data.Pattern;
import com.robidium.demo.main.RoutineIdentification.data.PatternItem;
import com.robidium.demo.main.Segmentation.data.Node;
import com.robidium.demo.main.data.Event;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TransformationsExtractor {
    private final CaseService caseService;

    private List<String> readActions;
    private List<String> writeActions;

    @Autowired
    public TransformationsExtractor(CaseService caseService) {
        this.caseService = caseService;
        writeActions = new ArrayList<>(Arrays.asList("editField", "editCell"));
        readActions = new ArrayList<>(Arrays.asList("copyCell", "copy"));
    }

    public Map<Pair<PatternItem, PatternItem>, List<Transformation>> getPatternTransformations(Pattern pattern) {
        PatternEventsFlowExtractor extractor = new PatternEventsFlowExtractor();
        Map<PatternItem, List<PatternItem>> writesPerReadEvents = extractor.extractWriteEventsPerReadEvent(pattern);
        Map<Pair<PatternItem, PatternItem>, List<Transformation>> transformationsPerReadWrite = new LinkedHashMap<>();
        List<Transformation> examples = extractAllTransformations(caseService.getCases());

        writesPerReadEvents.forEach((key, value) -> value.forEach(writeEvent -> {
            if (key != null) {
                List<Transformation> transformations = examples.stream()
                        .filter(e -> e.getSource().contains(key.getValue()) &&
                                e.getTarget().contains(writeEvent.getValue()))
                        .collect(Collectors.toList());
                if (!transformations.isEmpty()) {
                    Pair<PatternItem, PatternItem> readWrite = new ImmutablePair<>(key, writeEvent);
                    transformationsPerReadWrite.putIfAbsent(readWrite, transformations);
                }
            } else {
                Pair<PatternItem, PatternItem> readWrite = new ImmutablePair<>(new PatternItem(""), writeEvent);
                transformationsPerReadWrite.putIfAbsent(readWrite, new ArrayList<>());
            }
        }));

        return transformationsPerReadWrite;
    }

    public Map<String, List<Transformation>> getTransformationsGroupByTarget(List<Transformation> transformations) {
        Map<String, List<Transformation>> data = new HashMap<>();

        transformations.forEach(transformationExample -> {
            String target = transformationExample.getTarget();
            data.put(target, data.containsKey(target) ?
                    Stream.concat(data.get(target).stream(), Stream.of(transformationExample)).collect(Collectors.toList()) :
                    Collections.singletonList(transformationExample));
        });

        return data;
    }

    public List<Transformation> extractAllTransformations(Map<Integer, List<Event>> cases) {
        List<Transformation> transformations = new ArrayList<>();

        for (Integer caseID : cases.keySet()) {
            List<Event> events = new ArrayList<>(cases.get(caseID));
            List<String> targets = new ArrayList<>();

            for (int i = events.size() - 1; i >= 0; i--) {
                if (writeActions.contains(events.get(i).getEventType()) /*&& !targets.contains(events.get(i).payload.get("target.name"))*/) {
                    String target = events.get(i).payload.containsKey("target.name") ? events.get(i).payload.get("target.name") : events.get(i).payload.get("target.id");
                    String output = events.get(i).payload.get("target.value").replaceAll("\\P{Print}", " ");

                    StringBuilder source = new StringBuilder();
                    List<String> input = new ArrayList<>();
                    targets.add(target);

                    for (int j = 0; j < i; j++) {
                        Event e = events.get(j);
                        if (events.get(j).getEventType().contains("paste") &&
                                (e.payload.getOrDefault("target.name", "").equals(target)) ||
                                (e.payload.getOrDefault("target.id", "").equals(target)) || i == j + 1) {
                            for (int k = j; k >= 0; k--) {
                                if (readActions.contains(events.get(k).getEventType())) {
                                    source = new StringBuilder(events.get(k).getEventNameAndContext());
                                    input.add(events.get(k).payload.get("target.value").replaceAll("\\P{Print}", " "));
                                    break;
                                }
                            }
                        }
                    }

                    if (input.size() > 0) {
                        if (input.size() > 1)
                            transformations.add(new Transformation(String.valueOf(caseID), source.toString(),
                                    new Node(events.get(i)).toString(),
                                    input, Collections.singletonList(output)));
                        else
                            transformations.add(new Transformation(String.valueOf(caseID), source.toString(),
                                    new Node(events.get(i)).toString(),
                                    input.get(0), output));
                    }
                }
            }
        }

        return transformations;
    }
}