package com.robidium.demo.compiler.transformation.synthetic;

import com.robidium.demo.compiler.ImperativeCompiler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActionDataTransformer extends DataTransformer {

    public ActionDataTransformer() {
        fileName = ImperativeCompiler.TRANSFORMATIONS_FILE;
        transformations = new HashMap<>();
        ReadDataTransformationFromFile();
    }


    @SuppressWarnings("unchecked")
    public void parseJSONTransformations(JSONArray dataTransformationList) {
        dataTransformationList.forEach(transformation -> {
            JSONObject transformationObject = ((JSONObject) transformation);

            String source = transformationObject.get("source").toString();
            String target = transformationObject.get("target").toString();

            List<String> instructions = new ArrayList<>();
            for (var t : (JSONArray) transformationObject.get("data_transformation")) {
                instructions.add(String.valueOf(t));
            }
            transformations.put(target, instructions);
        });
    }
}
