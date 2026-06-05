package temperature.report;

import temperature.model.BatchSummary;

public final class TemperatureSummaryPrinter {

    private TemperatureSummaryPrinter() {
    }

    public static void printSummary(BatchSummary s) {
        System.out.println("============================================================");
        System.out.println("Temperature Analysis Summary");
        System.out.println("============================================================");
        System.out.println("Total readings: " + s.totalReadings());
        System.out.println("Valid readings: " + s.validReadings());
        System.out.println("Errors: " + s.errors());
        System.out.println("------------------------------------------------------------");
        System.out.printf("Max temperature: %.2f%n", s.maxTemp());
        System.out.printf("Min temperature: %.2f%n", s.minTemp());
        System.out.printf("Average temperature: %.2f%n", s.avgTemp());
        System.out.println("------------------------------------------------------------");
        if (s.errors() > 0) {
            System.out.println("Invalid lines:");
            for (String badLine : s.badLines()) {
                System.out.println(badLine);
            }
        }
    }
}
