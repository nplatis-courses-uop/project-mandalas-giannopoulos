package common;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.javatuples.Pair;
import com.opencsv.CSVReader;

/**
 * <dl>
 * <dt><strong>Singleton class</strong></dt>
 * <dt>Parses the cleaning services menu from the csv file Common/resrc/services.csv into a Map</dt>
 * </dl>
 */
public class Services {
    private static final String path = "Common/resrc/services.csv";
    private static Services instance = null;
    public Map<String, Pair<String, Double>> services;

    private Services() {
        services = new HashMap<>();
        try (var reader = new CSVReader(new BufferedReader(new FileReader(path)))) {
            reader.forEach((line) -> {
                services.put(line[0], Pair.with(line[1], Double.parseDouble(line[2])));
            });
        } catch (IOException e) {
            System.err.println("Could not parse csv file: " + e.getMessage());
        }
    }

    /**
     * @return The instance of the singleton
     */
    public static Services get() {
        if (instance == null) {
            instance = new Services();
        }
        return instance;
    }

    /**
     * @param codes is a list of service codes
     * @return The total cost
     */
    public static double calculateCost(List<String> codes) {
        var cost = 0.;
        for (var code : codes) {
            cost += Services.get().services.get(code).getValue1();
        }
        return cost;
    }
}