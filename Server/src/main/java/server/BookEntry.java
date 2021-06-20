package server;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * <dl>
 * <dt><strong>ORM class</strong></dt>
 * <dt>Represents an entry in the revenue book</dt>
 * </dl>
 */
@DatabaseTable(tableName = "book")
public class BookEntry {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String plate;

    @DatabaseField(canBeNull = false, dataType = DataType.SERIALIZABLE)
    private ArrayList<String> services;

    @DatabaseField(canBeNull = false, dataType = DataType.SERIALIZABLE)
    private LocalDateTime arrivalTime;

    @DatabaseField(dataType = DataType.SERIALIZABLE, columnName = "departureTime")
    private LocalDateTime departureTime;

    @DatabaseField
    private double cost;

    // Required by ORM
    public BookEntry() { }

    public BookEntry(String plate, ArrayList<String> services, LocalDateTime arrivalTime) {
        this.plate = plate;
        this.services = services;
        this.arrivalTime = arrivalTime;
    }

    public String getPlate() {
        return plate;
    }

    public ArrayList<String> getServices() {
        return services;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public double getCost() {
        return cost;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
