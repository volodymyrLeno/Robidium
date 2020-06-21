package com.robidium.demo.main.AutomatabilityAssessment.data;

import com.robidium.demo.main.RoutineIdentification.data.Pattern;
import com.robidium.demo.main.RoutineIdentification.data.PatternItem;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class Sequence {
    private int id;
    private List<String> items;

    public Sequence(List<String> items) {
        this.items = items;
    }

    public Sequence(int id, List<String> items) {
        this.id = id;
        this.items = items;
    }

    public boolean contains(Pattern pattern) {
        List<String> patternEvents = new ArrayList<>(pattern.getItemsValues());
        List<String> sequenceEvents = new ArrayList<>(items);

        for (String patternEvent : patternEvents) {
            if (sequenceEvents.contains(patternEvent)) {
                sequenceEvents.remove(patternEvent);
            } else {
                return false;
            }
        }

        return true;
    }

    public boolean containsPattern(Pattern pattern) {
        List<String> sequenceItems = new ArrayList<>(items);
        for (String patternItem : pattern.getItemsValues()) {
            if (sequenceItems.contains(patternItem)) {
                sequenceItems.remove(patternItem);
            } else {
                return false;
            }
        }

        return true;
    }

    public List<PatternItem> removePatternElements(Pattern pattern) {
        List<String> sequenceItems = new ArrayList<>(items);
        if (containsPattern(pattern)) {
            pattern.getItemsValues().forEach(sequenceItems::remove);
            setItems(sequenceItems);

            return pattern.getItems();
        }

        return new ArrayList<>();
    }
}

