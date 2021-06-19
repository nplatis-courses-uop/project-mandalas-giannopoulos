package server;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "book")
public class BookEntry {
    @DatabaseField(generatedId = true)
    private final int id;

    @DatabaseField(canBeNull = false)
    private final String plate;

    @DatabaseField(canBeNull = false)
    private final ArrayList<String> services;

    @DatabaseField(canBeNull = false)
    private final LocalDateTime arrivalTime;

    private LocalDateTime departureTime;

    private double cost;

    // Mandatory for ORM to work
    public BookEntry() { }

    public BookEntry(String plate, ArrayList<String> services, LocalDateTime arrivalTime) {
        this.plate = plate;
        this.services = services;
        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
