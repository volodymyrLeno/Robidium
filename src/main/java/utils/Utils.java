package utils;

import data.Event;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    public static HashMap<String, List<Event>> groupByEventType(List<Event> events){
        HashMap<String, List<Event>> groupedEvents = new HashMap<>();
        for(var event: events){
            if(!groupedEvents.containsKey(event.getEventType()))
                groupedEvents.put(event.getEventType(), Collections.singletonList(event));
            else
                groupedEvents.put(event.getEventType(), Stream.concat(groupedEvents.get(event.getEventType()).stream(),
                        Stream.of(event)).collect(Collectors.toList()));
        }
        return groupedEvents;
    }

    public static HashMap<String, List<Event>> groupEvents(List<Event> events){
        HashMap<String, List<Event>> groupedEvents = new HashMap<>();
        for(var event: events){
            var key = event.getEventType() + "_" + event.getApplication();
            if(!groupedEvents.containsKey(key))
                groupedEvents.put(key, Collections.singletonList(event));
            else
                groupedEvents.put(key, Stream.concat(groupedEvents.get(key).stream(),
                        Stream.of(event)).collect(Collectors.toList()));
        }

        return groupedEvents;
    }

    public static HashMap<Integer, List<Event>> extractCases(List<Event> events){
        HashMap<Integer, List<Event>> cases = new HashMap<>();
        for(var event: events){
            var caseID = Integer.valueOf(event.getCaseID());
            if(!cases.containsKey(caseID))
                cases.put(caseID, Collections.singletonList(event));
            else
                cases.put(caseID, Stream.concat(cases.get(caseID).stream(),
                        Stream.of(event)).collect(Collectors.toList()));
        }
        return cases;
    }

    /* Summary */

    /*
    public static double getEditDistance(HashMap<Integer, List<Event>> discoveredSegments,  HashMap<Integer, List<Event>> originalTraces){
        List<Double> editDistances = new ArrayList<>();
        for(var caseID: discoveredSegments.keySet()){
            Pattern pattern = new Pattern(toSequence(discoveredSegments.get(caseID)));
            List<List<Event>> coveredTraces = new ArrayList<>();
            int startIdx = discoveredSegments.get(caseID).get(0).getID();
            int endIdx = discoveredSegments.get(caseID).get(discoveredSegments.get(caseID).size()-1).getID();
            for(var trace: originalTraces.keySet()){
                var ids = originalTraces.get(trace).stream().map(el -> el.getID()).filter(el -> el >= startIdx &&
                        el <= endIdx).collect(Collectors.toList());
                if(ids.size() > 0){
                    if(ids.contains(endIdx)){
                        coveredTraces.add(originalTraces.get(trace));
                        break;
                    }
                    else
                        coveredTraces.add(originalTraces.get(trace));
                }
            }
            double editDistance = Double.MAX_VALUE;
            for(int i = 0; i < coveredTraces.size(); i++){
                var trace = toSequence(coveredTraces.get(i));
                var dist = (double)pattern.LevenshteinDistance(pattern.getPattern(), trace)/Math.max(discoveredSegments.get(caseID).size(),
                        trace.size());
                if(dist < editDistance)
                    editDistance = dist;
            }
            editDistances.add(editDistance);
        }
        var meanEditDistance = editDistances.stream().mapToDouble(d -> d).average().orElse(0.0);
        return meanEditDistance;
    }

    public static void getSummary(List<Pattern> patterns, List<List<String>> groundTruth, List<Event> events){
        int i = 1;
        for(var pattern: patterns){
            pattern.assignClosestMatch(groundTruth);
            pattern.computeConfusionMatrix(events);
            System.out.println("\nPattern " + i + ":\n" + pattern + "\n" + pattern.getClosestMatch());
            System.out.println("Length = " + pattern.getLength());
            System.out.printf("Sup = %.2f\n", pattern.getRelativeSupport());
            System.out.printf("Coverage = %.2f\n", pattern.getCoverage());
            System.out.printf("Precision = %.3f\n", pattern.calculatePrecision());
            System.out.printf("Recall = %.3f\n", pattern.calculateRecall());
            System.out.printf("Accuracy = %.3f\n", pattern.calculateAccuracy());
            System.out.printf("F-score = %.3f\n", pattern.calculateFScore());
            System.out.printf("Jaccard = %.3f\n", pattern.calculateJaccard(groundTruth, events));
            i++;
        }
        System.out.println("\nOverall results:\n");
        System.out.printf("Average length = %.2f\n", patterns.stream().mapToInt(Pattern::getLength).average().orElse(0.0));
        System.out.printf("Average support = %.2f\n", patterns.stream().mapToDouble(Pattern::getRelativeSupport).average().orElse(0.0));
        System.out.printf("Total coverage = %.2f\n", patterns.stream().mapToDouble(Pattern::getCoverage).sum());
        System.out.printf("Average coverage = %.2f\n", patterns.stream().mapToDouble(Pattern::getCoverage).average().orElse(0.0));
        System.out.printf("Average precision = %.3f\n", patterns.stream().mapToDouble(Pattern::getPrecision).average().orElse(0.0));
        System.out.printf("Average recall = %.3f\n", patterns.stream().mapToDouble(Pattern::getRecall).average().orElse(0.0));
        System.out.printf("Average accuracy = %.3f\n", patterns.stream().mapToDouble(Pattern::getAccuracy).average().orElse(0.0));
        System.out.printf("Average f-score = %.3f\n", patterns.stream().mapToDouble(Pattern::getFscore).average().orElse(0.0));
        System.out.printf("Average Jaccard = %.3f\n", patterns.stream().mapToDouble(Pattern::getJaccard).average().orElse(0.0));
    }*/
}