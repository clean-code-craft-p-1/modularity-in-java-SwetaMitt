package temperature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class TemperatureBatchProcessor {

    static void processBatch(String filename) {
        List<String> lines = TemperatureFileReader.readLines(filename);
        if (lines == null) {
            return;
        }

        List<Double> temps = new ArrayList<>();
        int errors = 0;
        List<String> badLines = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            if (!TemperatureLineParser.tryAddReading(line, i + 1, temps, badLines)) {
                errors++;
            }
        }

        if (temps.isEmpty()) {
            System.out.println("No valid temperature data found.");
            return;
        }

        double maxTemp = Collections.max(temps);
        double minTemp = Collections.min(temps);
        double avgTemp = temps.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        TemperatureSummaryPrinter.printSummary(lines.size(), temps.size(), errors, maxTemp, minTemp, avgTemp, badLines);
        TemperatureSummaryWriter.writeSummaryReport(filename, lines.size(), temps.size(), errors, maxTemp, minTemp, avgTemp, badLines);
    }
}
