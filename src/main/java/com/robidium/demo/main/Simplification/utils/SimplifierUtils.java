package com.robidium.demo.main.Simplification.utils;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class SimplifierUtils {
    public static void writeDataLineByLine(String filePath, String data) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filePath),
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.NO_ESCAPE_CHARACTER,
                    CSVWriter.RFC4180_LINE_END);

            String[] headers = {"\"timeStamp\"", "\"userID\"", "\"targetApp\"", "\"eventType\"", "\"url\"",
                    "\"content\"", "\"target.workbookName\"", "\"target.sheetName\"", "\"target.id\"", "\"target.class\"",
                    "\"target.tagName\"", "\"target.type\"", "\"target.name\"", "\"target.value\"", "\"target.innerText\"",
                    "\"target.checked\"", "\"target.href\"", "\"target.option\"", "\"target.title\"", "\"target.innerHTML\""
            };

            writer.writeNext(headers);
            writeActionsValues(writer, data);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeActionsValues(CSVWriter writer, String data){
        String[] actions = data.split("\n");

        for (String action : actions) {
            String[] actionValues = action.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            actionValues = Arrays.stream(actionValues)
                    .map(e -> e.replaceAll("\"{2}(([^\"]|\"\")*)\"{2}", "\"\"\"$1\"\"\""))
                    .toArray(String[]::new);
            writer.writeNext(actionValues);
        }
    }
}
