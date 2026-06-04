package temperature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

final class TemperatureFileReader {

    private TemperatureFileReader() {
    }

    static List<String> readLines(String filename) {
        try {
            return Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            System.out.println("Error: File not found.");
            return null;
        }
    }
}
