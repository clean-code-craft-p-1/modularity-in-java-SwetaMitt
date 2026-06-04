package temperature;

public class Main {

    private static final String TEST_FILENAME = "test_temps.csv";
    private static final String[] TEST_DATA = {
        "09:15:30,23.5",
        "09:16:00,24.1",
        "09:16:30,22.8",
        "09:17:00,25.3",
        "09:17:30,23.9",
        "09:18:00,24.7",
        "09:18:30,22.4",
        "09:19:00,26.1",
        "09:19:30,23.2",
        "09:20:00,25.0"
    };

    public static void processBatch(String filename) {
        TemperatureBatchProcessor.processBatch(filename);
    }

    public static void main(String[] args) {
        if (!TemperatureTestFileManager.writeTestData(TEST_FILENAME, TEST_DATA)) {
            return;
        }
        System.out.println("Created test file: " + TEST_FILENAME);
        processBatch(TEST_FILENAME);
        TemperatureTestFileManager.verifySummaryFile(TEST_FILENAME + "_summary.txt");
        TemperatureTestFileManager.cleanupFiles(TEST_FILENAME);
    }
}