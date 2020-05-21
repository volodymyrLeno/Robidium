import RoutineIdentification.service.patternsMiner;
import Segmentation.data.DirectlyFollowsGraph;
import Segmentation.service.SegmentsDiscoverer;
import data.Event;
import org.apache.commons.lang3.builder.DiffResult;

import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String log = "src/main/java/StudentRecord.csv";
        String config = "src/main/java/config.json";

        boolean segmentation = true;

        var events = utils.logReader.readCSV(log);
        var groupedEvents = utils.Utils.groupEvents(events);
        var contextAttributes = utils.Utils.extractContextAttributes(config);
        for(var key: groupedEvents.keySet())
            utils.Utils.setContextAttributes(groupedEvents.get(key), contextAttributes);

        HashMap<Integer, List<Event>> cases = new HashMap<>();

        if(segmentation){
            DirectlyFollowsGraph dfg = new DirectlyFollowsGraph(events);
            dfg.buildGraph();

            SegmentsDiscoverer disco = new SegmentsDiscoverer();
            cases = disco.extractSegmentsFromDFG(dfg);
        }
        else{
            cases = utils.Utils.extractCases(events);
        }

        var patterns = patternsMiner.discoverPatterns2(cases, config);
        System.out.println();
    }
}