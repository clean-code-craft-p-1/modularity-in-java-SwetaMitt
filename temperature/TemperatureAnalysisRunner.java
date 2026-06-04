package temperature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

final class TemperatureAnalysisRunner {

    private static final String SEPARATOR =
            "------------------------------------------------------------";

    private TemperatureAnalysisRunner() {
    }

    static void run(String filename, List<TemperatureReading> readings) {
        List<Analyzer> analyzers = AnalyzerRegistry.all();
        if (analyzers.isEmpty()) {
            return;
        }
        StringBuilder report = new StringBuilder();
        for (Analyzer a : analyzers) {
            String section = renderSection(a, readings);
            System.out.print(section);
            report.append(section);
        }
        appendToSummary(filename, report.toString());
    }

    private static String renderSection(Analyzer a, List<TemperatureReading> readings) {
        String body = a.analyze(readings);
        String nl = System.lineSeparator();
        StringBuilder sb = new StringBuilder()
                .append(SEPARATOR).append(nl)
                .append(a.name()).append(nl)
                .append(SEPARATOR).append(nl)
                .append(body);
        if (!body.endsWith(nl)) {
            sb.append(nl);
        }
        return sb.toString();
    }

    private static void appendToSummary(String filename, String content) {
        Path path = Paths.get(filename + "_summary.txt");
        try {
            Files.writeString(path, content, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error appending analyzer output: " + e.getMessage());
        }
    }
}
