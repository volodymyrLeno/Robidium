package com.robidium.demo.compiler.transformation.semantic;

import lombok.ToString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString
public class DependencyService {
    private static final DependencyService INSTANCE = new DependencyService();
    private List<Dependency> dependencies;

    private DependencyService() {
        dependencies = new ArrayList<>();
    }

    public static DependencyService getInstance() {
        return INSTANCE;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public Dependency getDependencyByDependerIndex(int index) {
        return dependencies.stream()
                .filter(d -> d.getDependerIndex().equals(String.valueOf(index)))
                .findFirst().orElse(null);
    }

    public List<String> getDependeeByDepender(String dependerIndex) {
        List<String> dependee = new ArrayList<>();
        dependencies.stream()
                .filter(dependency -> dependency.getDependerIndex().equals(dependerIndex))
                .findFirst().ifPresent(d -> dependee.addAll(d.getDependeeIndices()));

        return dependee;
    }

    public void parse(String path) {
        dependencies.clear();
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(path)) {
            for (var d : (JSONArray) parser.parse(reader)) {
                JSONObject jsonDependency = ((JSONObject) d);

                List<String> dependeeIndices = new ArrayList<>();
                if (jsonDependency.containsKey("dependee_idx")) {
                    JSONArray jsonDependeeIndices = (JSONArray) jsonDependency.get("dependee_idx");
                    for (Object jsonDependeeIndex : jsonDependeeIndices) {
                        dependeeIndices.add(jsonDependeeIndex.toString());
                    }
                }

                String dependerIndex = jsonDependency.get("depender_idx").toString();
                JSONArray jsonValues = (JSONArray) jsonDependency.get("values");
                Map<String, String> dependerValuePerDependee = new HashMap<>();
                for (Object jsonValue : jsonValues) {
                    JSONObject v = (JSONObject) jsonValue;
                    List<String> dependeeValues = new ArrayList<>();
                    if (v.containsKey("dependee_values")) {
                        JSONArray jsonDependeeValues = (JSONArray) v.get("dependee_values");
                        for (Object jsonDependeeValue : jsonDependeeValues) {
                            dependeeValues.add(jsonDependeeValue.toString());
                        }
                    }
                    String dependerValue = v.get("depender_value").toString();
                    dependerValuePerDependee.put(String.join(",", dependeeValues), dependerValue);
                }
                dependencies.add(new Dependency(dependeeIndices, dependerIndex, dependerValuePerDependee));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
