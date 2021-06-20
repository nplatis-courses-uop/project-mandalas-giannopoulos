package server;

import java.time.format.DateTimeFormatter;

import common.Services;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
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

        var selectionModel = table.getSelectionModel();
        
        var buttonPane = new HBox();
        var registerBtn = new Button("Register");
        var deleteBtn = new Button("Delete");
        var bookBtn = new Button("Revenue log");
        buttonPane.getChildren().addAll(registerBtn, deleteBtn, bookBtn);
        
        var mainPane = new VBox(table, buttonPane);
        var scene = new Scene(mainPane, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("Server");
        stage.show();
        new Thread(server).start();

        registerBtn.setOnAction((event) -> {
            var selectedRow = selectionModel.getSelectedItem();
            if (selectedRow != null) {
                var receiptDialog = new ReceiptDialog(selectedRow);
                receiptDialog.initOwner(stage);
                receiptDialog.initModality(Modality.APPLICATION_MODAL);
                receiptDialog.setTitle("Receipt");
                Window window = receiptDialog.getDialogPane().getScene().getWindow();
                window.setOnCloseRequest((event1) -> window.hide());
                receiptDialog.show();
            } else {
                var alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("There is something wrong");
                alert.setContentText("You haven't selected anything");
                alert.showAndWait();
            }  
        });

        deleteBtn.setOnAction((event) -> {
            Database.delete(selectionModel.getSelectedItem());
        });

        bookBtn.setOnAction((event) -> {
            var bookDialog = new BookDialog();
            bookDialog.initOwner(stage);
            bookDialog.initModality(Modality.APPLICATION_MODAL);
            bookDialog.setTitle("Revenue log");
            var window = bookDialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest((event1) -> window.hide());
            bookDialog.show();
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