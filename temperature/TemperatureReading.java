package temperature;

/**
 * A single parsed temperature sample. Keeping the timestamp alongside the
 * value lets time-aware analyzers (e.g. fever-duration, circadian patterns)
 * work without re-parsing input.
 */
public record TemperatureReading(String timestamp, double value) {
}
