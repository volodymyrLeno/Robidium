package com.robidium.demo.compiler.transformation;

import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class TransformationMap {
    private static final TransformationMap INSTANCE = new TransformationMap();

    @Getter
    private Map<String, String> actionTransformations;

    private TransformationMap() {
        actionTransformations = new HashMap<>();
    }

    public static TransformationMap getInstance() {
        return INSTANCE;
    }

    public void parse(String path) {
        actionTransformations.clear();
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(path)) {
            for (Object p : (JSONArray) parser.parse(reader)) {
                JSONObject pointer = ((JSONObject) p);
                String index = pointer.get("index").toString();
                String type = pointer.get("type").toString();
                actionTransformations.put(index, type);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
