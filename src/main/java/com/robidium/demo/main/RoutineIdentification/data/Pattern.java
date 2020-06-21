package com.robidium.demo.main.RoutineIdentification.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.robidium.demo.main.AutomatabilityAssessment.data.ItemsDependency;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class Pattern implements Comparable<Pattern> {
    private UUID id = UUID.randomUUID();
    private List<PatternItem> items;
    private int absSupport;
    private double relSupport;
    private int length;
    private int cohesionScore;
    private boolean automatable = false;
    private Map<Pair<PatternItem, PatternItem>, String> transformations;
    private List<ItemsDependency> itemsDependencies;
    private double coverage;
    private double RAI;

    public Pattern() {
        items = new ArrayList<>();
        transformations = new HashMap<>();
        itemsDependencies = new ArrayList<>();
    }

    public Pattern(List<PatternItem> items) {
        this.items = items;
        this.transformations = new HashMap<>();
        this.itemsDependencies = new ArrayList<>();
        this.length = items.size();
    }

    public Pattern(List<PatternItem> items, int support) {
        this.items = items;
        this.absSupport = support;
        this.relSupport = 0.0;
        this.length = items.size();
        this.transformations = new HashMap<>();
        this.itemsDependencies = new ArrayList<>();
    }

    public Pattern(List<PatternItem> items, Double relSup, int absSup) {
        this.items = items;
        this.absSupport = absSup;
        this.relSupport = relSup;
        this.length = items.size();
        this.transformations = new HashMap<>();
        this.itemsDependencies = new ArrayList<>();
    }

    public List<String> getItemsValues() {
        return items.stream().map(PatternItem::getValue).collect(Collectors.toList());
    }

    public List<Pair<PatternItem, PatternItem>> getEmptyTransformationPairs() {
        return transformations.entrySet().stream()
                .filter(transformation -> transformation.getValue().isEmpty())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        ArrayNode items = mapper.createArrayNode();
        this.items.forEach(i -> items.add(i.getValue()));

        ArrayNode transformations = mapper.createArrayNode();
        this.transformations.forEach((k, v) -> {
            ObjectNode tn = mapper.createObjectNode();
            ArrayNode ts = mapper.createArrayNode();
            Arrays.asList(v.split("\n")).forEach(t -> {
                if (!t.isBlank()) ts.add(t.trim());
            });
            tn.set(k.getLeft() + " -> " + k.getRight(), ts);
            transformations.add(tn);
        });

        ArrayNode dependencies = mapper.createArrayNode();
        itemsDependencies.forEach(d -> {
            ObjectNode itemDep = mapper.createObjectNode();

            if (d.getDependee() != null) {
                ArrayNode dependee = mapper.createArrayNode();
                d.getDependee().forEach(dep -> dependee.add(dep.getValue()));
                itemDep.set("dependee", dependee);
            }

            if (d.getDepender() != null) {
                itemDep.put("depender", d.getDepender().getValue());
            }

            if (d.getDependerPerDependee() != null) {
                ArrayNode values = mapper.createArrayNode();
                d.getDependerPerDependee().forEach((k, v) -> {
                    ObjectNode value = mapper.createObjectNode();

                    if (d.getDependee() != null) {
                        ArrayNode dependeeValues = mapper.createArrayNode();
                        for (int i = 0; i < k.size(); i++) {
                            ObjectNode dependeeValue = mapper.createObjectNode();
                            dependeeValue.put(d.getDependee().get(i).getValue(), k.get(i));
                            dependeeValues.add(dependeeValue);
                        }
                        value.set("dependee", dependeeValues);
                    }

                    if (d.getDepender() != null) {
                        value.put("depender", v);
                    }

                    values.add(value);
                });
                itemDep.set("values", values);
            }
            dependencies.add(itemDep);
        });

        ObjectNode pattern = mapper.createObjectNode();
        pattern.set("items", items);
        pattern.put("absolute support", absSupport);
        pattern.put("relative support", relSupport);
        pattern.put("cohesion score", cohesionScore);
        pattern.set("transformations", transformations);
        pattern.set("functional dependencies", dependencies);
        pattern.put("automatable", automatable);
        pattern.put("coverage", coverage);
        pattern.put("RAI", RAI);

        ObjectNode root = mapper.createObjectNode();
        root.set("pattern@" + hashCode(), pattern);

        return root.toPrettyString();
    }

    public Double getRelativeSupport() {
        return this.relSupport;
    }

    public void setRelativeSupport(double support) {
        this.relSupport = support;
    }

    public Integer getAbsoluteSupport() {
        return this.absSupport;
    }

    public void setAbsoluteSupport(Integer support) {
        this.absSupport = support;
    }

    public int getLength() {
        return this.length;
    }

    public double getCoverage() {
        return this.coverage;
    }

    public void setCoverage(double coverage) {
        this.coverage = coverage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            Pattern pattern = (Pattern) obj;
            return this.getItemsValues().equals(pattern.getItemsValues());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getItemsValues());
    }

    @Override
    public int compareTo(Pattern e) {
        if (e.getLength() == this.length)
            return e.getAbsoluteSupport() - this.getAbsoluteSupport();
        else
            return e.getLength() - this.getLength();
    }
}
