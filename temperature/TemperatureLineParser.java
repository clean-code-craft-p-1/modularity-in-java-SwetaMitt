package temperature;

import java.util.Optional;

/**
 * Pure parser: turns a single CSV line into either a valid
 * TemperatureReading or Optional.empty(). Has no side-effects on caller
 * state, so it composes cleanly and is trivial to test.
 */
final class TemperatureLineParser {

    private static final double MIN_PLAUSIBLE_TEMP = -100.0;
    private static final double MAX_PLAUSIBLE_TEMP = 200.0;
    private static final int EXPECTED_FIELDS = 2;
    private static final int EXPECTED_TIMESTAMP_PARTS = 3;

    private TemperatureLineParser() {
    }

    static Optional<TemperatureReading> parse(String line) {
        String[] parts = line.split(",");
        if (parts.length != EXPECTED_FIELDS) {
            return Optional.empty();
        }
        String timestamp = parts[0].strip();
        if (!isWellFormedTimestamp(timestamp)) {
            return Optional.empty();
        }
        return parsePlausibleTemperature(parts[1].strip())
                .map(temp -> new TemperatureReading(timestamp, temp));
    }

    private static boolean isWellFormedTimestamp(String timestamp) {
        return timestamp.split(":").length == EXPECTED_TIMESTAMP_PARTS;
    }

    private static Optional<Double> parsePlausibleTemperature(String value) {
        try {
            double temp = Double.parseDouble(value);
            if (temp < MIN_PLAUSIBLE_TEMP || temp > MAX_PLAUSIBLE_TEMP) {
                return Optional.empty();
            }
            return Optional.of(temp);
        } catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }
}
