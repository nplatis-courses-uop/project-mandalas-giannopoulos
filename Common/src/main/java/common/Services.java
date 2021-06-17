package common;

import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

import org.javatuples.Triplet;
import com.opencsv.CSVReader;

public class Services {
    private static final String path = "Common/resrc/services.csv";
    private static Services instance = null;
    public TreeSet<Triplet<Integer, String, Double>> services;

    private Services() {
        services = new TreeSet<>(new Comparator<Triplet<Integer, String, Double>>() {
            @Override
            public int compare(Triplet<Integer, String, Double> a,
                               Triplet<Integer, String, Double> b) {
                return a.getValue0() - b.getValue0();
            }
        });
        try (var reader = new CSVReader(new BufferedReader(new FileReader(path)))) {
            reader.forEach((line) -> {
                services.add(Triplet.with(
                    Integer.parseInt(line[0]),
                    line[1],
                    Double.parseDouble(line[2])
                ));
            });
        } catch (IOException e) {
            System.err.println("Could not parse csv file: " + e.getMessage());
        }
    }

    public static Services get() {
        if (instance == null) {
            instance = new Services();
        }
        return instance;
    }
}