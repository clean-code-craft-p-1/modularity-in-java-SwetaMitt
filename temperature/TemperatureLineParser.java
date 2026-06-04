package temperature;

import java.util.List;

class TemperatureLineParser {

    static boolean tryAddReading(String line, int lineNumber, List<Double> temps, List<String> badLines) {
        String[] parts = line.split(",");
        if (parts.length != 2) {
            badLines.add("  Line " + lineNumber + ": " + line);
            return false;
        }

        String timestamp = parts[0].strip();
        String value = parts[1].strip();
        if (timestamp.split(":").length != 3) {
            badLines.add("  Line " + lineNumber + ": " + line);
            return false;
        }

        double temp;
        try {
            temp = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            badLines.add("  Line " + lineNumber + ": " + line);
            return false;
        }

        if (temp < -100 || temp > 200) {
            badLines.add("  Line " + lineNumber + ": " + line);
            return false;
        }

        temps.add(temp);
        return true;
    }
}
