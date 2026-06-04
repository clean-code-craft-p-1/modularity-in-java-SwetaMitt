package temperature;

import java.util.List;

final class TemperatureLineParser {

    private static final double MIN_PLAUSIBLE_TEMP = -100.0;
    private static final double MAX_PLAUSIBLE_TEMP = 200.0;

    private TemperatureLineParser() {
    }

    static boolean tryAddReading(String line, int lineNumber,
                                 List<TemperatureReading> readings, List<String> badLines) {
        String[] parts = line.split(",");
        if (parts.length != 2) {
            return reject(badLines, lineNumber, line);
        }

        String timestamp = parts[0].strip();
        if (timestamp.split(":").length != 3) {
            return reject(badLines, lineNumber, line);
        }

        double temp;
        try {
            temp = Double.parseDouble(parts[1].strip());
        } catch (NumberFormatException ignored) {
            return reject(badLines, lineNumber, line);
        }

        if (temp < MIN_PLAUSIBLE_TEMP || temp > MAX_PLAUSIBLE_TEMP) {
            return reject(badLines, lineNumber, line);
        }

        readings.add(new TemperatureReading(timestamp, temp));
        return true;
    }

    private static boolean reject(List<String> badLines, int lineNumber, String line) {
        badLines.add("  Line " + lineNumber + ": " + line);
        return false;
    }
}
