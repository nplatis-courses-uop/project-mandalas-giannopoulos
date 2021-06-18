package server;

import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Server extends Application {
    public static boolean isRunning = true;
    public static TableView<List<String>> table = new TableView<>();
    private static OrderServer server = new OrderServer();

    @Override
    public void start(Stage stage) {
        table.getColumns().add(makeCol(0, "Plate"));
        table.getColumns().add(makeCol(1, "Cost"));
        table.getColumns().add(makeCol(2, "Time of arrival"));
        
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
    }

    @Override
    public void stop() {
        isRunning = false;
        server.shutdown();
    }

    public static TableColumn<List<String>, String> makeCol(int index, String label) {
        var col = new TableColumn<List<String>, String>(label);
        col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(index)));
        return col;
    }

    public static void main(String[] args) {
        launch(args);
    }
}