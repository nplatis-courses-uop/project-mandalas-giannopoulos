package gr.uop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Client extends Application {
    private TextField plateText = new TextField();

    @Override
    public void start(Stage stage) {
        var mainPane = new VBox();

        var instrLabel = new Label("Please insert your vehicle plate...");
        var keyboardPane = new HBox();
        mainPane.getChildren().addAll(instrLabel, plateText, keyboardPane);

        var letterKeyboard = new VBox();

        var lkRow1 = new HBox();
        var backspace = new Button("<--");
        var space = new Button("____");
        var enter = new Button("Enter");
        var delete = new Button("Delete All");
        lkRow1.getChildren().addAll(space, backspace, delete, enter);

        var lkRow2 = new HBox();
        var bQ = makeKeyButton("Q");
        var bW = makeKeyButton("W");
        var bE = makeKeyButton("E");
        var bR = makeKeyButton("R");
        var bT = makeKeyButton("T");
        var bY = makeKeyButton("Y");
        var bU = makeKeyButton("U");
        var bI = makeKeyButton("I");
        var bO = makeKeyButton("O");
        var bP = makeKeyButton("P");
        lkRow2.getChildren().addAll(bQ, bW, bE, bR, bT, bY, bU, bI, bO, bP);

        var lkRow3 = new HBox();
        var bA = makeKeyButton("A");
        var bS = makeKeyButton("S");
        var bD = makeKeyButton("D");
        var bF = makeKeyButton("F");
        var bG = makeKeyButton("G");
        var bH = makeKeyButton("H");
        var bJ = makeKeyButton("J");
        var bK = makeKeyButton("K");
        var bL = makeKeyButton("L");
        lkRow3.getChildren().addAll(bA, bS, bD, bF, bG, bH, bJ, bK, bL);

        var lkRow4 = new HBox();
        var bZ = makeKeyButton("Z");
        var bX = makeKeyButton("X");
        var bC = makeKeyButton("C");
        var bV = makeKeyButton("V");
        var bB = makeKeyButton("B");
        var bN = makeKeyButton("N");
        var bM = makeKeyButton("M");
        lkRow4.getChildren().addAll(bZ, bX, bC, bV, bB, bN, bM);

        letterKeyboard.getChildren().addAll(lkRow1, lkRow2, lkRow3, lkRow4);

        var numberKeyboard = new VBox();

        var nkRow1 = new HBox();
        var b0 = makeKeyButton("0");
        nkRow1.getChildren().add(b0);

        var nkRow2 = new HBox();
        var b7 = makeKeyButton("7");
        var b8 = makeKeyButton("8");
        var b9 = makeKeyButton("9");
        nkRow2.getChildren().addAll(b7, b8, b9);

        var nkRow3 = new HBox();
        var b4 = makeKeyButton("4");
        var b5 = makeKeyButton("5");
        var b6 = makeKeyButton("6");
        nkRow3.getChildren().addAll(b4, b5, b6);

        var nkRow4 = new HBox();
        var b1 = makeKeyButton("1");
        var b2 = makeKeyButton("2");
        var b3 = makeKeyButton("3");
        nkRow4.getChildren().addAll(b1, b2, b3);

        numberKeyboard.getChildren().addAll(nkRow1, nkRow2, nkRow3, nkRow4);

        keyboardPane.getChildren().addAll(letterKeyboard, numberKeyboard);

        var scene = new Scene(mainPane, 1920, 1080);
        stage.setTitle("Client");
        stage.setScene(scene);
        stage.show();

        space.setOnAction((event) -> {
            plateText.setText(plateText.getText() + " ");
        });
        delete.setOnAction((event) -> {
            plateText.clear();
        });
        backspace.setOnAction((event) -> {
            plateText.setText(plateText.getText().substring(0, plateText.getText().length() - 1));
        });
        enter.setOnAction((event) -> {
            var dialogSe = new Services();
            dialogSe.setTitle("Choose services");
            Window window = dialogSe.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest((event1) -> window.hide());
            dialogSe.show();

        });
    }

    public Button makeKeyButton(String label) {
        var result = new Button(label);
        result.setOnAction((event) -> {
            plateText.setText(plateText.getText() + label);
        });
        return result;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
