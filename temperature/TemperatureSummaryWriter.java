package temperature;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

final class TemperatureSummaryWriter {

    private TemperatureSummaryWriter() {
    }

    static void writeSummaryReport(String filename, BatchSummary s) {
        String outName = filename + "_summary.txt";
        try (PrintWriter out = new PrintWriter(new FileWriter(outName))) {
            out.println("Temperature Analysis Summary");
            out.println("==================================================");
            out.println("File analyzed: " + filename);
            out.println("Total readings: " + s.totalReadings());
            out.println("Valid readings: " + s.validReadings());
            out.println("Errors: " + s.errors());
            out.printf("Max temperature: %.2f%n", s.maxTemp());
            out.printf("Min temperature: %.2f%n", s.minTemp());
            out.printf("Average temperature: %.2f%n", s.avgTemp());
            out.println("------------------------------------------------------------");
            if (s.errors() > 0) {
                out.println("\nInvalid lines:");
                for (String badLine : s.badLines()) {
                    out.println(badLine);
                }
            }
            System.out.println("Report saved to " + outName);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
