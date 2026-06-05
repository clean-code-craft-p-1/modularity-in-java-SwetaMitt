package temperature.analyze;

import java.util.List;

import temperature.model.TemperatureReading;

/**
 * Extension point for new analytics. Each feature (fever detection,
 * circadian summary, ...) lives in its own file implementing this
 * interface, so teams can ship end-to-end without editing shared code.
 */
public interface Analyzer {

    String name();

    String analyze(List<TemperatureReading> readings);
}
