package Simplification.service;

import Simplification.utils.SimplifierUtils;
import data.Event;
import utils.LogReader;

import java.util.List;

public class SimplifierService {
    public static List<Event> applyPreprocessing(String filePath, String events, Boolean segmented){
        events = Preprocessing.sortLog(events);
        events = Preprocessing.deleteChromeClipboardCopy(events);
        events = Preprocessing.mergeNavigationCellCopy(events);
        events = Preprocessing.identifyPasteAction(events);

        events = NavigationSimplifier.removeRedundantClickTextField(events);
        events = NavigationSimplifier.removeExcelNavigation(events);

        if(segmented){
            while (ReadSimplifier.containsRedundantCopy(events) ||
                    ReadSimplifier.containsSingleCopy(events) ||
                    WriteSimplifier.containsRedundantDoublePaste(events) ||
                    WriteSimplifier.containsRedundantEditCell(events) ||
                    WriteSimplifier.containsRedundantEditField(events) ||
                    WriteSimplifier.containsRedundantPasteIntoCell(events) ||
                    WriteSimplifier.containsRedundantPasteIntoRange(events) ||
                    WriteSimplifier.containsRedundantDoubleEditField(events)){

                events = ReadSimplifier.removeRedundantCopy(events);
                events = ReadSimplifier.removeSingleCopy(events);
                events = WriteSimplifier.removeRedundantDoublePaste(events);
                events = WriteSimplifier.removeRedundantEditCell(events);
                events = WriteSimplifier.removeRedundantEditField(events);
                events = WriteSimplifier.removeRedundantPasteIntoCell(events);
                events = WriteSimplifier.removeRedundantPasteIntoRange(events);
                events = WriteSimplifier.removeRedundantDoubleEditField(events);
            }
        }
        else{
            while (ReadSimplifier.containsRedundantCopy(events) ||
                    ReadSimplifier.containsSingleCopy(events)) {
                events = ReadSimplifier.removeRedundantCopy(events);
                events = ReadSimplifier.removeSingleCopy(events);
            }
        }
        String preprocessedLog = filePath.substring(0, filePath.lastIndexOf(".")) + "_preprocessed.csv";
        SimplifierUtils.writeDataLineByLine(preprocessedLog, events);
        return LogReader.readCSV(preprocessedLog);
    }
}
