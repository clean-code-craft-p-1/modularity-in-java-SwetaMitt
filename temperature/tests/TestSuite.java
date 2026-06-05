package temperature.tests;

/**
 * Local test runner. Invoke with:
 *
 *   javac temperature/tests/TestSuite.java
 *   java -ea -classpath . temperature.tests.TestSuite
 *
 * Each *Test class exposes a static runAll() that asserts its module's
 * contract. New test classes plug in by adding one line below.
 */
public final class TestSuite {

    private TestSuite() {
    }

    public static void main(String[] args) {
        TemperatureLineParserTest.runAll();
        BatchSummaryTest.runAll();
        TemperatureSummaryPrinterTest.runAll();
        System.out.println("\u2713 All unit tests passed");
    }
}
