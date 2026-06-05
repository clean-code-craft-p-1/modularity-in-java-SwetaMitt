package temperature.tests;

import java.util.List;

import temperature.model.BatchSummary;
import temperature.model.TemperatureReading;

final class BatchSummaryTest {

    private BatchSummaryTest() {
    }

    static void runAll() {
        computesMaxMinAvg();
        countsBadLinesAsErrors();
        carriesTotalLineCount();
    }

    private static void computesMaxMinAvg() {
        List<TemperatureReading> readings = List.of(
                new TemperatureReading("09:00:00", 20.0),
                new TemperatureReading("09:01:00", 30.0),
                new TemperatureReading("09:02:00", 25.0));
        BatchSummary s = BatchSummary.from(3, readings, List.of());
        assert s.maxTemp() == 30.0 : "max";
        assert s.minTemp() == 20.0 : "min";
        assert s.avgTemp() == 25.0 : "avg";
    }

    private static void countsBadLinesAsErrors() {
        List<TemperatureReading> readings = List.of(
                new TemperatureReading("09:00:00", 22.0));
        List<String> bad = List.of("  Line 2: junk", "  Line 3: also junk");
        BatchSummary s = BatchSummary.from(3, readings, bad);
        assert s.errors() == 2 : "errors == bad lines size";
        assert s.validReadings() == 1 : "valid count";
    }

    private static void carriesTotalLineCount() {
        BatchSummary s = BatchSummary.from(10,
                List.of(new TemperatureReading("09:00:00", 22.0)),
                List.of());
        assert s.totalReadings() == 10 : "total lines preserved";
    }
}
