package com.robidium.demo.main.utils;


import com.robidium.demo.main.data.Event;

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
}