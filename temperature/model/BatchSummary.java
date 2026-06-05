package temperature.model;

import java.util.Collections;
import java.util.List;

public record BatchSummary(
        int totalReadings,
        int validReadings,
        int errors,
        double maxTemp,
        double minTemp,
        double avgTemp,
        List<String> badLines) {

    public static BatchSummary from(int totalLines,
                                    List<TemperatureReading> readings,
                                    List<String> badLines) {
        List<Double> temps = readings.stream().map(TemperatureReading::value).toList();
        double avg = temps.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        return new BatchSummary(totalLines, readings.size(), badLines.size(),
                Collections.max(temps), Collections.min(temps), avg, badLines);
    }
}
