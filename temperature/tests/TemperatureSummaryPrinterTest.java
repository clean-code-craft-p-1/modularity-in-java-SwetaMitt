package temperature.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import temperature.model.BatchSummary;
import temperature.report.TemperatureSummaryPrinter;

final class TemperatureSummaryPrinterTest {

    private TemperatureSummaryPrinterTest() {
    }

    static void runAll() {
        prints_counts_and_stats();
        omits_invalid_section_when_no_errors();
        prints_invalid_section_when_errors_present();
    }

    private static String capture(BatchSummary s) {
        PrintStream original = System.out;
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buf));
        try {
            TemperatureSummaryPrinter.printSummary(s);
        } finally {
            System.setOut(original);
        }
        return buf.toString();
    }

    private static void prints_counts_and_stats() {
        BatchSummary s = new BatchSummary(10, 10, 0, 26.1, 22.4, 24.1, List.of());
        String out = capture(s);
        assert out.contains("Total readings: 10") : "totals printed";
        assert out.contains("Valid readings: 10") : "valid printed";
        assert out.contains("Errors: 0") : "errors printed";
        assert out.contains("Max temperature: 26.10") : "max formatted to 2dp";
    }

    private static void omits_invalid_section_when_no_errors() {
        BatchSummary s = new BatchSummary(5, 5, 0, 22.0, 20.0, 21.0, List.of());
        assert !capture(s).contains("Invalid lines:") : "no invalid section when errors == 0";
    }

    private static void prints_invalid_section_when_errors_present() {
        BatchSummary s = new BatchSummary(3, 1, 2, 22.0, 22.0, 22.0,
                List.of("  Line 2: junk", "  Line 3: also junk"));
        String out = capture(s);
        assert out.contains("Invalid lines:") : "section header present";
        assert out.contains("Line 2: junk") : "bad line listed";
    }
}
