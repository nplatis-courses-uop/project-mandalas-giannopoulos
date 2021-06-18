package server;

import java.time.LocalDateTime;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "book")
public class BookEntry {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private final String plate;

    @DatabaseField(canBeNull = false)
    private final List<String> services;

    @DatabaseField(canBeNull = false)
    private final LocalDateTime arrivalTime;

    private LocalDateTime departureTime;

    private double cost;

    public BookEntry (String plate, List<String> services, LocalDateTime arrivalTime) {
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
