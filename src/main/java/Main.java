import RoutineIdentification.service.PatternsMiner;
import Segmentation.service.SegmentsDiscoverer;
import data.Event;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import utils.*;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    static Boolean segmented = null;
    static PatternsMiner.SPMFAlgorithmName algorithm = null;
    static String metric = null;
    static List<String> contextAttributes = null;
    static Double minSupport = 0.0;
    static Double minCoverage = 0.0;

    public static void main(String[] args) {
        String log = "StudentRecord.csv";
        String config = "config.json";

        for(int i = 0; i < 3; i++){
            readConfiguration(config);
            var events = LogReader.readCSV(log);
            var groupedEvents = Utils.groupEvents(events);

            for(var key: groupedEvents.keySet())
                Parser.setContextAttributes(groupedEvents.get(key), contextAttributes);

            HashMap<Integer, List<Event>> cases;

            if(!segmented){
                SegmentsDiscoverer disco = new SegmentsDiscoverer();
                cases = disco.extractSegments(events);
            }
            else{
                cases = Utils.extractCases(events);
            }

            var patterns = PatternsMiner.discoverPatterns(cases, algorithm, minSupport, minCoverage, metric);
            System.out.println();
        }

    }

    static void readConfiguration(String config){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(config));
            JSONObject jsonObject = (JSONObject) obj;

            segmented = (Boolean) jsonObject.get("segmented");
            algorithm = PatternsMiner.SPMFAlgorithmName.valueOf(jsonObject.get("algorithm").toString());
            minSupport = (Double) jsonObject.get("minSupport");
            minCoverage = (Double) jsonObject.get("minCoverage");
            metric = jsonObject.get("metric").toString();

            JSONArray context = (JSONArray) jsonObject.get("context");
            List<String> temp = new ArrayList<>();
            if (context != null) {
                for (int i = 0; i < context.size(); i++){
                    temp.add(context.get(i).toString());
                }
                contextAttributes = new ArrayList<>(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}