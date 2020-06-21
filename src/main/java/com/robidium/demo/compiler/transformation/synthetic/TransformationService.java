package com.robidium.demo.compiler.transformation.synthetic;

import com.robidium.demo.compiler.entity.Action;
import lombok.Data;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TransformationService {
    private static final TransformationService INSTANCE = new TransformationService();
    private List<Transformation> transformations;

    private TransformationService() {
        transformations = new ArrayList<>();
    }

    public static TransformationService getInstance() {
        return INSTANCE;
    }

    public void readTransformationsFromJSON(String filePath) {
        transformations.clear();
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(filePath)) {
            JSONArray ts = (JSONArray) parser.parse(reader);
            for (var t : ts) {
                JSONObject transformationObject = ((JSONObject) t);
                String source = transformationObject.get("source").toString();
                String target = transformationObject.get("target").toString();
                List<String> functions = new ArrayList<>();
                for (var f : (JSONArray) transformationObject.get("data_transformation")) {
                    functions.add(String.valueOf(f));
                }
                transformations.add(new Transformation(source, target, functions));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public List<Transformation> getTransformationsByAction(Action action) {
        return transformations.stream()
                .filter(t -> t.getTarget().equals(action.getTargetId()) || t.getTarget().equals(action.getTargetName()))
                .collect(Collectors.toList());
    }

    public String createCode(Transformation transformation) {
        return transformation.getFunctions()
                .stream()
                .reduce("", (t, f) -> t + Functions.getFunction(f)) +
                Functions.assignTransformedValue();
    }
}
