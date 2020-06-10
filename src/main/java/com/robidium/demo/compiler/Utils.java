package com.robidium.demo.compiler;

import com.opencsv.*;
import com.robidium.demo.compiler.entity.Action;
import com.robidium.demo.compiler.entity.ImperativeAction;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void writeDataLineByLine(String filePath, String data) {
        File file = new File(filePath);
        try {
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Action> readLog() {
        List<Action> actions = new ArrayList<>();
        try {
            RFC4180Parser rfc4180Parser = new RFC4180ParserBuilder().build();
            BufferedReader filereader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(ImperativeCompiler.LOG_FILE_PATH), StandardCharsets.UTF_8));
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .withCSVParser(rfc4180Parser)
                    .build();

            String[] line;
            int index = 0;
            while ((line = csvReader.readNext()) != null) {
                actions.add(ImperativeAction.of(line, index++));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return actions;
    }
}
