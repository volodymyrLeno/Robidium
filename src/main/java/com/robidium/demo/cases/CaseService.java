package com.robidium.demo.cases;

import com.robidium.demo.main.AutomatabilityAssessment.data.Sequence;
import com.robidium.demo.main.RoutineIdentification.data.Pattern;
import com.robidium.demo.main.Segmentation.data.Node;
import com.robidium.demo.main.Segmentation.service.SegmentsDiscoverer;
import com.robidium.demo.main.data.Event;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class CaseService {

    private Map<Integer, List<Event>> cases;

    public void extractCases(List<Event> events, boolean isSegmented) {
        if (isSegmented) {
            cases = extractSegmentedCases(events);
        } else {
            SegmentsDiscoverer disco = new SegmentsDiscoverer();
            cases = disco.extractSegments(events);
        }
    }

    private Map<Integer, List<Event>> extractSegmentedCases(List<Event> events) {
        Map<Integer, List<Event>> cases = new HashMap<>();

        for (Event event : events) {
            int caseId = Integer.parseInt(event.getCaseID());
            if (!cases.containsKey(caseId))
                cases.put(caseId, Collections.singletonList(event));
            else
                cases.put(caseId, Stream.concat(cases.get(caseId).stream(),
                        Stream.of(event)).collect(Collectors.toList()));
        }

        return cases;
    }

    public List<Event> getFirstContainingCase(Pattern pattern) {
        return IntStream.range(1, cases.size())
                .mapToObj(i -> new Sequence(i, caseToString(cases.get(i))))
                .filter(s -> s.containsPattern(pattern))
                .map(s -> cases.get(s.getId()))
                .findFirst().orElse(Collections.emptyList());
    }

    public Map<Integer, List<Event>> getCases() {
        return cases;
    }

    private List<List<String>> casesToSequences(Map<Integer, List<Event>> cases) {
        return cases.keySet().stream()
                .map(key -> cases.get(key).stream()
                        .map(el -> new Node(el).toString())
                        .collect(Collectors.toList()))
                .map(ArrayList::new)
                .collect(Collectors.toList());
    }

    private List<String> caseToString(List<Event> events) {
        return events.stream().map(el -> new Node(el).toString()).collect(Collectors.toList());
    }
}
