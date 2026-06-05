package temperature.tests;

import java.util.Optional;

import temperature.ingest.TemperatureLineParser;
import temperature.model.TemperatureReading;

/**
 * Asserts the contract of TemperatureLineParser. Run with -ea (assertions
 * enabled). Each method returns silently on success and throws
 * AssertionError on failure.
 */
final class TemperatureLineParserTest {

    private TemperatureLineParserTest() {
    }

    static void runAll() {
        parsesValidLine();
        rejectsMissingComma();
        rejectsMalformedTimestamp();
        rejectsNonNumericValue();
        rejectsImplausiblyHighValue();
        rejectsImplausiblyLowValue();
    }

    private static void parsesValidLine() {
        Optional<TemperatureReading> r = TemperatureLineParser.parse("09:15:30, 23.5");
        assert r.isPresent() : "expected valid reading";
        assert r.get().timestamp().equals("09:15:30") : "timestamp preserved";
        assert r.get().value() == 23.5 : "value preserved";
    }

    private static void rejectsMissingComma() {
        assert TemperatureLineParser.parse("09:15:30 23.5").isEmpty() : "no comma -> empty";
    }

    private static void rejectsMalformedTimestamp() {
        assert TemperatureLineParser.parse("09-15-30,23.5").isEmpty() : "bad timestamp -> empty";
    }

    private static void rejectsNonNumericValue() {
        assert TemperatureLineParser.parse("09:15:30,hot").isEmpty() : "non-number -> empty";
    }

    private static void rejectsImplausiblyHighValue() {
        assert TemperatureLineParser.parse("09:15:30,500").isEmpty() : "out-of-range high -> empty";
    }

    private static void rejectsImplausiblyLowValue() {
        assert TemperatureLineParser.parse("09:15:30,-200").isEmpty() : "out-of-range low -> empty";
    }
}
