package temperature;

import java.util.List;

/**
 * Extension point for new analytics. Each feature (fever detection,
 * circadian summary, ...) lives in its own file implementing this
 * interface, so teams can ship end-to-end without editing shared code.
 */
interface Analyzer {

    String name();

    String analyze(List<TemperatureReading> readings);
}
