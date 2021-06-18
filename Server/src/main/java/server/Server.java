package server;

import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Server extends Application {
    public static TableView<List<String>> table = new TableView<>();
    @Override
    public void start(Stage stage) {
        table.getColumns().add(makeCol(0, "Plate"));
        table.getColumns().add(makeCol(1, "Cost"));
        table.getColumns().add(makeCol(2, "Time of arrival"));
        var mainPane = new StackPane(table);
        var scene = new Scene(mainPane, 640, 480);
        stage.setScene(scene);
        stage.show();
        new Thread(new OrderServer()).start();
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