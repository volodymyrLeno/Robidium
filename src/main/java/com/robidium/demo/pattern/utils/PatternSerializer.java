package com.robidium.demo.pattern.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.opencsv.CSVWriter;
import com.robidium.demo.cases.CaseService;
import com.robidium.demo.log.StorageProperties;
import com.robidium.demo.main.RoutineIdentification.data.Pattern;
import com.robidium.demo.main.RoutineIdentification.data.PatternItem;
import com.robidium.demo.main.data.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatternSerializer {
    private static final String[] columns = {"timeStamp", "userID", "targetApp", "eventType", "url", "content",
            "target.workbookName", "target.sheetName", "target.id", "target.class", "target.tagName",
            "target.type", "target.name", "target.value", "target.innerText", "target.checked", "target.href",
            "target.option", "target.title", "target.innerHTML"};
    private static Path rootLocation;
    private static CaseService caseService;

    @Autowired
    public PatternSerializer(StorageProperties properties, CaseService caseService) {
        PatternSerializer.rootLocation = Paths.get(properties.getPatternInfoLocation());
        PatternSerializer.caseService = caseService;
    }

    public static Path writeInstance(Pattern pattern) {
        List<Event> firstContainingCase = caseService.getFirstContainingCase(pattern);

        File file = new File(rootLocation + "/pattern.csv");
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(file));

            csvWriter.writeNext(columns);

            firstContainingCase.forEach(event -> {
                List<String> data = new ArrayList<>();

                data.add(event.getTimestamp());
                data.add(event.getPayload().get("userID"));
                data.add(event.getPayload().get("targetApp"));
                data.add(event.getEventType());

                event.getAttributes().forEach(attr -> {
                    if (event.getEventType().equalsIgnoreCase("copyCell") &&
                            attr.equalsIgnoreCase("target.id")) {
                        String column = event.getContext().getOrDefault("target.column", "");
                        String row = event.getContext().getOrDefault("target.row", "");
                        data.add(column + row);
                    } else if (!attr.equalsIgnoreCase("caseID") &&
                            !attr.equalsIgnoreCase("timestamp") &&
                            !attr.equalsIgnoreCase("userID") &&
                            !attr.equalsIgnoreCase("targetApp") &&
                            !attr.equalsIgnoreCase("eventType")) {
                        data.add(event.getPayload().get(attr));
                    }
                });

                csvWriter.writeNext(data.toArray(new String[0]));
            });

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.toPath();
    }

    public static Path writeDataTransformations(Pattern pattern) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode ts = mapper.createArrayNode();

        pattern.getTransformations().forEach((pair, sTransformation) -> {
            if (!sTransformation.isBlank()) {
                ObjectNode t = mapper.createObjectNode();

                // Create source
                String source = pair.getLeft().getContextId();
                // Create target
                String target = pair.getRight().getContextId();
                // Create transformation functions array
                sTransformation = sTransformation.trim().replaceAll("\n", "");
                List<String> functions = Arrays.asList(sTransformation.split("t = "));
                ArrayNode fs = mapper.createArrayNode();
                functions.stream().filter(f -> !f.isBlank()).forEach(fs::add);

                t.put("source", source);
                t.put("target", target);
                t.set("data_transformation", fs);
                ts.add(t);
            }
        });

        File file = new File(rootLocation + "/transformations.json");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(ts.toPrettyString());

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.toPath();
    }

    public static Path writeFunctionalDependencies(Pattern pattern) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode dependenciesArray = mapper.createArrayNode();
        pattern.getItemsDependencies().forEach(dependency -> {
            ObjectNode dependencyNode = mapper.createObjectNode();

            List<Integer> dependeeOrders = new ArrayList<>();
            int[] i = {0};

            // Set dependee indices
            if (dependency.getDependee() != null) {
                // TODO: not only editField
                List<Integer> dependeeIndices = dependency.getDependee().stream()
                        .filter(d -> d.getValue().contains("editField"))
                        .peek(d -> dependeeOrders.add(i[0]++))
                        .map(PatternItem::getIndex)
                        .collect(Collectors.toList());

                ArrayNode dependeeIdx = mapper.createArrayNode();
                dependeeIndices.stream().map(String::valueOf).forEach(dependeeIdx::add);
                dependencyNode.set("dependee_idx", dependeeIdx);
            }

            // Set depender index
            dependencyNode.put("depender_idx", String.valueOf(dependency.getDepender().getIndex()));

            // Set values
            ArrayNode values = mapper.createArrayNode();
            dependency.getDependerPerDependee().forEach((dependeeValues, dependerValue) -> {
                ObjectNode valueObject = mapper.createObjectNode();
                if (dependency.getDependee() != null) {
                    ArrayNode dependeeValuesNode = mapper.createArrayNode();
                    dependeeOrders.forEach(j -> dependeeValuesNode.add(dependeeValues.get(j)));
                    valueObject.set("dependee_values", dependeeValuesNode);
                }
                valueObject.put("depender_value", dependerValue);
                values.add(valueObject);
            });
            dependencyNode.set("values", values);
            dependenciesArray.add(dependencyNode);
        });

        File file = new File(rootLocation + "/functional_dependencies.json");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(dependenciesArray.toPrettyString());

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.toPath();
    }

    public static Path writeTransformationMap(Pattern pattern) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode root = mapper.createArrayNode();

        pattern.getTransformations().forEach((k, v) -> {
            if (!v.isEmpty()) {
                ObjectNode transformation = mapper.createObjectNode();
                transformation.put("index", String.valueOf(k.getRight().getIndex()));
                transformation.put("type", "synthetic");
                root.add(transformation);
            }
        });

        pattern.getItemsDependencies().forEach(dependency -> {
            ObjectNode transformation = mapper.createObjectNode();
            transformation.put("index", String.valueOf(dependency.getDepender().getIndex()));
            transformation.put("type", "semantic");
            root.add(transformation);
        });

        File file = new File(rootLocation + "/transformation_map.json");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(root.toPrettyString());

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.toPath();
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize pattern info storage", e);
        }
    }
}
