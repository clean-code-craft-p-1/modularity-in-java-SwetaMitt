package temperature;

import java.util.List;

/**
 * The single place where new analyzers are registered. Adding a feature is
 * a one-line change here plus a new Analyzer file, so unrelated modules
 * stay untouched.
 */
final class AnalyzerRegistry {

    private AnalyzerRegistry() {
    }

    static List<Analyzer> all() {
        return List.of(
                // new FeverDetectionAnalyzer(),
                // new CircadianPatternAnalyzer()
        );
    }
}
