package temperature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class TemperatureBatchProcessor {

    private TemperatureBatchProcessor() {
    }

    static void processBatch(String filename) {
        List<String> lines = TemperatureFileReader.readLines(filename);
        if (lines == null) {
            return;
        }

        List<TemperatureReading> readings = new ArrayList<>();
        List<String> badLines = new ArrayList<>();
        int errors = parseLines(lines, readings, badLines);

        if (readings.isEmpty()) {
            System.out.println("No valid temperature data found.");
            return;
        }

        BatchSummary summary = summarize(lines.size(), readings, errors, badLines);
        TemperatureSummaryPrinter.printSummary(summary);
        TemperatureSummaryWriter.writeSummaryReport(filename, summary);
        TemperatureAnalysisRunner.run(filename, readings);
    }

    private static BatchSummary summarize(int totalLines, List<TemperatureReading> readings,
                                          int errors, List<String> badLines) {
        List<Double> temps = readings.stream().map(TemperatureReading::value).toList();
        double max = Collections.max(temps);
        double min = Collections.min(temps);
        double avg = temps.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        return new BatchSummary(totalLines, readings.size(), errors, max, min, avg, badLines);
    }

    private static int parseLines(List<String> lines, List<TemperatureReading> readings, List<String> badLines) {
        int errors = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            if (!TemperatureLineParser.tryAddReading(line, i + 1, readings, badLines)) {
                errors++;
            }
        }
        return errors;
    }
}
