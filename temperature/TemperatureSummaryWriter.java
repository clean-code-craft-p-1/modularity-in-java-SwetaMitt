package temperature;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

class TemperatureSummaryWriter {

    static void writeSummaryReport(String filename, int totalReadings, int validReadings, int errors, double maxTemp,
            double minTemp, double avgTemp, List<String> badLines) {
        String outName = filename + "_summary.txt";
        try (PrintWriter out = new PrintWriter(new FileWriter(outName))) {
            out.println("Temperature Analysis Summary");
            out.println("==================================================");
            out.println("File analyzed: " + filename);
            out.println("Total readings: " + totalReadings);
            out.println("Valid readings: " + validReadings);
            out.println("Errors: " + errors);
            out.printf("Max temperature: %.2f%n", maxTemp);
            out.printf("Min temperature: %.2f%n", minTemp);
            out.printf("Average temperature: %.2f%n", avgTemp);
            out.println("------------------------------------------------------------");
            if (errors > 0) {
                out.println("\nInvalid lines:");
                for (String badLine : badLines) {
                    out.println(badLine);
                }
            }
            System.out.println("Report saved to " + outName);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
