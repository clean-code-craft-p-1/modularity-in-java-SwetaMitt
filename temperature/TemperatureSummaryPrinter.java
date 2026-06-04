package temperature;

import java.util.List;

class TemperatureSummaryPrinter {

    static void printSummary(int totalReadings, int validReadings, int errors, double maxTemp, double minTemp,
            double avgTemp, List<String> badLines) {
        System.out.println("============================================================");
        System.out.println("Temperature Analysis Summary");
        System.out.println("============================================================");
        System.out.println("Total readings: " + totalReadings);
        System.out.println("Valid readings: " + validReadings);
        System.out.println("Errors: " + errors);
        System.out.println("------------------------------------------------------------");
        System.out.printf("Max temperature: %.2f%n", maxTemp);
        System.out.printf("Min temperature: %.2f%n", minTemp);
        System.out.printf("Average temperature: %.2f%n", avgTemp);
        System.out.println("------------------------------------------------------------");
        if (errors > 0) {
            System.out.println("Invalid lines:");
            for (String badLine : badLines) {
                System.out.println(badLine);
            }
        }
    }
}
