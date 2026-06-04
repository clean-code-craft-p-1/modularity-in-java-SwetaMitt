package temperature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        parseLines(lines, readings, badLines);

        if (readings.isEmpty()) {
            System.out.println("No valid temperature data found.");
            return;
        }

        BatchSummary summary = summarize(lines.size(), readings, badLines);
        TemperatureSummaryPrinter.printSummary(summary);
        TemperatureSummaryWriter.writeSummaryReport(filename, summary);
        TemperatureAnalysisRunner.appendAnalyses(filename, readings);
    }

    private static void parseLines(List<String> lines,
                                   List<TemperatureReading> readings,
                                   List<String> badLines) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            Optional<TemperatureReading> parsed = TemperatureLineParser.parse(line);
            if (parsed.isPresent()) {
                readings.add(parsed.get());
            } else {
                badLines.add("  Line " + (i + 1) + ": " + line);
            }
        }
    }

    private static BatchSummary summarize(int totalLines,
                                          List<TemperatureReading> readings,
                                          List<String> badLines) {
        List<Double> temps = readings.stream().map(TemperatureReading::value).toList();
        double max = Collections.max(temps);
        double min = Collections.min(temps);
        double avg = temps.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        return new BatchSummary(totalLines, readings.size(), badLines.size(), max, min, avg, badLines);
    }
}
