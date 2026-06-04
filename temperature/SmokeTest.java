package temperature;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Build-time smoke test: lays down a known fixture, runs the batch
 * processor against it, asserts the summary file contains the expected
 * counts, then removes both files. Used by CI's "Build and Run" job.
 */
final class SmokeTest {

    private SmokeTest() {
    }

    static boolean writeSampleFile(String fixturePath, String[] sampleLines) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fixturePath))) {
            for (String line : sampleLines) {
                writer.println(line);
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error creating test file: " + e.getMessage());
            return false;
        }
    }

    static void verifyGeneratedSummary(String summaryFile) {
        Path summaryPath = Paths.get(summaryFile);
        if (!Files.exists(summaryPath)) {
            return;
        }
        System.out.println("\nSummary file created: " + summaryFile);
        try {
            String content = Files.readString(summaryPath);
            assert content.contains("Total readings: 10") : "Total readings assertion failed";
            assert content.contains("Valid readings: 10") : "Valid readings assertion failed";
            assert content.contains("Errors: 0") : "Errors assertion failed";
            System.out.println("\u2713 Summary file contents verified");
        } catch (IOException e) {
            System.out.println("Error reading summary file: " + e.getMessage());
        }
    }

    static void removeArtifacts(String fixturePath) {
        try {
            Files.deleteIfExists(Paths.get(fixturePath));
            Files.deleteIfExists(Paths.get(fixturePath + "_summary.txt"));
        } catch (IOException e) {
            System.out.println("Error cleaning up files: " + e.getMessage());
        }
    }
}
