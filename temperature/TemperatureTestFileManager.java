package temperature;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

final class TemperatureTestFileManager {

    private TemperatureTestFileManager() {
    }

    static boolean writeTestData(String testFilename, String[] testData) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(testFilename))) {
            for (String line : testData) {
                writer.println(line);
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error creating test file: " + e.getMessage());
            return false;
        }
    }

    static void verifySummaryFile(String summaryFile) {
        Path summaryPath = Paths.get(summaryFile);
        if (Files.exists(summaryPath)) {
            System.out.println("\nSummary file created: " + summaryFile);
            try {
                String content = Files.readString(summaryPath);
                assert content.contains("Total readings: 10") : "Total readings assertion failed";
                assert content.contains("Valid readings: 10") : "Valid readings assertion failed";
                assert content.contains("Errors: 0") : "Errors assertion failed";
                System.out.println("✓ Summary file contents verified");
            } catch (IOException e) {
                System.out.println("Error reading summary file: " + e.getMessage());
            }
        }
    }

    static void cleanupFiles(String testFilename) {
        try {
            Files.deleteIfExists(Paths.get(testFilename));
            Files.deleteIfExists(Paths.get(testFilename + "_summary.txt"));
        } catch (IOException e) {
            System.out.println("Error cleaning up files: " + e.getMessage());
        }
    }
}
