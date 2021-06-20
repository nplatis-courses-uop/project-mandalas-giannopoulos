package server;

import java.time.format.DateTimeFormatter;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Revenue book UI
 */
public class BookDialog extends Dialog {
    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public BookDialog() {
        var dialogPane = getDialogPane();

        var table = new TableView<Order>();

        var plateCol = new TableColumn<Order, String>("Plate");
        plateCol.setCellValueFactory(x ->
            new SimpleStringProperty(x.getValue().getPlate()));
        table.getColumns().add(plateCol);

        var servicesCol = new TableColumn<Order, String>("Services");
        servicesCol.setCellValueFactory(x ->
            new SimpleStringProperty(String.join(", ", x.getValue().getServices())));
        table.getColumns().add(servicesCol);

        var arrivalCol = new TableColumn<Order, String>("Arrival Time");
        arrivalCol.setCellValueFactory(x ->
            new SimpleStringProperty(dtf.format(x.getValue().getArrivalTime())));
        table.getColumns().add(arrivalCol);

        var departureCol = new TableColumn<Order, String>("Arrival Time");
        departureCol.setCellValueFactory(x -> {
            var departureTime = x.getValue().getDepartureTime();
            return new SimpleStringProperty(departureTime != null ? dtf.format(departureTime) : "--");
        });
        table.getColumns().add(departureCol);

        var costCol = new TableColumn<Order, String>("Income");
        costCol.setCellValueFactory(x ->
            new SimpleStringProperty(String.format("%.2f", x.getValue().getCost())));
        table.getColumns().add(costCol);

        for (var entry : Database.read()) {
            table.getItems().add(entry);
        }

        dialogPane.setContent(table);
        dialogPane.setMinSize(1280, 720);
    }
}