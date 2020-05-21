import Segmentation.service.SegmentsDiscoverer;

public class Main {

    public static void main(String[] args) {
        String log = "src/main/java/StudentRecord.csv";
        String config = "src/main/java/test.json";
        SegmentsDiscoverer disco = new SegmentsDiscoverer();
        disco.segmentLog(log, config);
    }
}