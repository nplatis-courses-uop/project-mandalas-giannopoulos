package server;

import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Server extends Application {
    TableView<List<String>> table = new TableView<>();
    @Override
    public void start(Stage stage) {
        table.getColumns().add(makeCol("Plate"));
        table.getColumns().add(makeCol("Cost"));
        table.getColumns().add(makeCol("Time of arrival"));
        var mainPane = new StackPane(table);
        var scene = new Scene(mainPane, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static TableColumn<List<String>, String> makeCol(String label) {
        return new TableColumn<List<String>, String>(label);
    }

    public static void main(String[] args) {
        launch(args);
    }
}