package server;

import java.time.format.DateTimeFormatter;
import java.util.List;

import common.Services;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Server extends Application {
    public static boolean isRunning = true;
    public static TableView<BookEntry> table = new TableView<>();
    private static OrderServer server = new OrderServer();
    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public void start(Stage stage) {
        var plateCol = new TableColumn<BookEntry, String>("Plate");
        plateCol.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getPlate()));
        table.getColumns().add(plateCol);
        var costCol = new TableColumn<BookEntry, String>("Cost");
        costCol.setCellValueFactory(x ->
            new SimpleStringProperty(String.format("%.2f",
                Services.calculateCost(x.getValue().getServices()))));
        table.getColumns().add(costCol);
        var arrivalCol = new TableColumn<BookEntry, String>("Arrival Time");
        arrivalCol.setCellValueFactory(x -> 
            new SimpleStringProperty(dtf.format(x.getValue().getArrivalTime())));
        table.getColumns().add(arrivalCol);
        
        var buttonPane = new HBox();
        var registerBtn = new Button("Register");
        var deleteBtn = new Button("Delete");
        buttonPane.getChildren().addAll(registerBtn, deleteBtn);
        
        var mainPane = new VBox(table, buttonPane);
        var scene = new Scene(mainPane, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("Server");
        stage.show();
        new Thread(server).start();

        registerBtn.setOnAction((event) -> {
            var receiptDialog = new ReceiptDialog();
            receiptDialog.initOwner(stage);
            receiptDialog.initModality(Modality.APPLICATION_MODAL);
            receiptDialog.setTitle("Receipt");
            Window window = receiptDialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest((event1) -> window.hide());
            receiptDialog.show();
        });
    }

    @Override
    public void stop() {
        isRunning = false;
        server.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}