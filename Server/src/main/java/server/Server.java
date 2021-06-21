package server;

import java.time.format.DateTimeFormatter;

import common.Services;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Server UI
 */
public class Server extends Application {
    public static boolean isRunning = true;
    public static TableView<Order> table = new TableView<>();
    private static OrderServer server = new OrderServer();
    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public void start(Stage stage) {
        Database.init();
        var plateCol = new TableColumn<Order, String>("Plate");
        plateCol.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getPlate()));
        table.getColumns().add(plateCol);
        var costCol = new TableColumn<Order, String>("Cost");
        costCol.setCellValueFactory(x ->
            new SimpleStringProperty(String.format("%.2f",
                Services.calculateCost(x.getValue().getServices()))));
        table.getColumns().add(costCol);
        var arrivalCol = new TableColumn<Order, String>("Arrival Time");
        arrivalCol.setCellValueFactory(x -> 
            new SimpleStringProperty(dtf.format(x.getValue().getArrivalTime())));
        table.getColumns().add(arrivalCol);

        for (var entry : Database.readPending()) {
            table.getItems().add(entry);
        }

        var tableEntryBtnPane = new HBox();
        var registerBtn = new Button("Register");
        var deleteBtn = new Button("Delete");
        tableEntryBtnPane.getChildren().addAll(registerBtn, deleteBtn);

        var bookBtn = new Button("Revenue log");

        var buttonPane = new BorderPane();
        buttonPane.setLeft(tableEntryBtnPane);
        BorderPane.setAlignment(tableEntryBtnPane, Pos.CENTER);
        buttonPane.setRight(bookBtn);
        BorderPane.setAlignment(bookBtn, Pos.CENTER);

        var mainPane = new VBox(table, buttonPane);
        var scene = new Scene(mainPane, 1280, 720);
        try {
            scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
        stage.setTitle("Server");
        stage.setScene(scene);
        stage.show();
        new Thread(server).start();

        registerBtn.setOnAction((event) -> {
            var selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                var receiptDialog = new ReceiptDialog(selected);
                receiptDialog.initOwner(stage);
                receiptDialog.initModality(Modality.APPLICATION_MODAL);
                receiptDialog.setTitle("Receipt");
                receiptDialog.setWidth(300);
                receiptDialog.setHeight(500);
                var window = receiptDialog.getDialogPane().getScene().getWindow();
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
            var selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                table.getItems().remove(selected);
                Database.delete(selected);
            } else {
                var alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("There is something wrong");
                alert.setContentText("You haven't selected anything");
                alert.showAndWait();
            }
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