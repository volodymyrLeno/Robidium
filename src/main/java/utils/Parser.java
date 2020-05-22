package utils;

import Segmentation.data.Node;
import data.Event;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static utils.Utils.groupByEventType;
import static utils.Utils.groupEvents;

public final class Parser {
    public static List<String> toSequence(List<Event> events, List<String> contextAttributes){
        List<String> sequence = new ArrayList<>();
        HashMap<String, List<Event>> groupedEvents = groupByEventType(events);
        for(var group: groupedEvents.keySet())
            setContextAttributes(groupedEvents.get(group), contextAttributes);
        for(var event: events)
            sequence.add(new Node(event.getEventType(), event.context, 1).toString());
        return sequence;
    }

    public static List<String> toSequence(List<Event> events){
        List<String> sequence = new ArrayList<>();
        for(var event: events)
            sequence.add(new Node(event.getEventType(), event.context, 1).toString());
        return sequence;
    }

    public static List<List<String>> toSequences(HashMap<Integer, List<Event>> cases, List<String> contextAttributes){
        List<List<String>> sequences = new ArrayList<>();

        List<Event> events = new ArrayList<>();
        cases.values().forEach(events::addAll);

        HashMap<String, List<Event>> groupedEvents = groupEvents(events);
        for(var group: groupedEvents.keySet())
            setContextAttributes(groupedEvents.get(group), contextAttributes);
        for(var caseID: cases.keySet()){
            List<String> sequence = new ArrayList<>();
            for(var event: cases.get(caseID))
                sequence.add(new Node(event.getEventType(), event.context, 1).toString());
            sequences.add(sequence);
        }
        return sequences;
    }

    static String eventListToString(List<Event> events){
        String[] header = {"caseID", "timeStamp", "userID", "targetApp", "eventType", "url", "content", "target.workbookName",
                "target.sheetName", "target.id", "target.class", "target.tagName", "target.type", "target.name",
                "target.value", "target.innerText", "target.checked", "target.href", "target.option", "target.title", "target.innerHTML"
        };
        String str = "";
        for(var event: events){
            str += "\"" + event.getTimestamp() + "\",";
            str += event.payload.containsKey("userID") ? "\"" + event.payload.get("userID") + "\"," : "\"\",";
            str += event.payload.containsKey("targetApp") ? "\"" + event.payload.get("targetApp") + "\"," : "\"\",";
            str += "\"" + event.getEventType() + "\",";

            for(int i = 5; i < header.length; i++)
                if(event.payload.containsKey(header[i]) && !event.payload.get(header[i]).equals("\"\""))
                    str += "\"" + event.payload.get(header[i]) + "\",";
                else
                    str += "\"\",";

            str = str.substring(0, str.lastIndexOf(",")) + "\n";
        }
        return str;
    }

    public static List<String> extractContextAttributes(String filePath){
        List<String> contextAttributes = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray context = (JSONArray) jsonObject.get("context");

            if (context != null) {
                for (int i = 0; i < context.size(); i++){
                    contextAttributes.add(context.get(i).toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contextAttributes;
    }

    public static void setContextAttributes(List<Event> events, List<String> contextAttributes){
        for(var event: events){
            HashMap<String, String> context = new HashMap<>();
            for(var attribute: event.payload.keySet())
                if(contextAttributes.contains(attribute)) {
                    if (attribute.equals("target.id") && event.getApplication().equals("Excel")) {
                        var uniqueColumns = events.stream().map(el -> el.payload.get("target.column")).distinct().collect(Collectors.toList());
                        var uniqueRows = events.stream().map(el -> el.payload.get("target.row")).distinct().collect(Collectors.toList());
                        if (uniqueColumns.size() < uniqueRows.size())
                            attribute = "target.column";
                        else
                            attribute = "target.row";
                    }
                    context.put(attribute, event.payload.get(attribute));
                }
            event.context = new HashMap<>(context);
        }
    }
}
