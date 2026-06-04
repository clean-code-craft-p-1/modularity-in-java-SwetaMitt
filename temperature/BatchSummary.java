package temperature;

import java.util.List;

/**
 * Aggregated outcome of a parse + stats pass. Passed to the printer and
 * writer instead of long parameter lists, and ready for future analyzers
 * that want to consume a single immutable view.
 */
record BatchSummary(
        int totalReadings,
        int validReadings,
        int errors,
        double maxTemp,
        double minTemp,
        double avgTemp,
        List<String> badLines) {
}
