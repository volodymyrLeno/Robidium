package com.robidium.demo.compiler.transformation.synthetic;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TargetDataTransformer extends DataTransformer {

    public TargetDataTransformer() {
        transformations = new HashMap<>();
        ReadDataTransformationFromFile();
    }

    @SuppressWarnings("unchecked")
    public void parseJSONTransformations(JSONArray dataTransformationList) {
        dataTransformationList.forEach(transformation -> {
            JSONObject transformationObject = ((JSONObject) transformation);
            String id = transformationObject.get("target").toString();
            List<String> instructions = new ArrayList<>();
            for (var t : (JSONArray) transformationObject.get("data_transformation")) {
                instructions.add(String.valueOf(t));
            }
            transformations.put(id, instructions);
        });
    }
}
