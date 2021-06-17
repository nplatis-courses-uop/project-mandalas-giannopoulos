package client;

import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.CheckBox;

public class ServiceDialog extends Dialog {
    private final RadioButton carRb = new RadioButton("Car");
    private final RadioButton suvRb = new RadioButton("SUV");
    private final RadioButton motoRb = new RadioButton("Motorcycle");
    private final CheckBox washIn = new CheckBox("Interior wash");
    private final CheckBox inSpecial = new CheckBox("Special");
    private final CheckBox washOut = new CheckBox("Exterior wash");
    private final CheckBox outSpecial = new CheckBox("Special");
    private final CheckBox washIO = new CheckBox("Inside/Out wash");
    private final CheckBox ioSpecial = new CheckBox("Special");
    private final CheckBox bioWash = new CheckBox("Bio wash (Interior)");
    private final CheckBox wax = new CheckBox("Wax");
    private final CheckBox engineWash = new CheckBox("Engine Wash");
    private final CheckBox chassisWash = new CheckBox("Chassis Wash");

    public ServiceDialog() {
        var dialogPane = getDialogPane();

        var mainPane = new VBox();

        var instructions = new Label("Choose your service/services...");
        
        var secondPane = new HBox();

        var vehiclePane = new VBox();
        final ToggleGroup vehicleGroup = new ToggleGroup();
        carRb.setToggleGroup(vehicleGroup);
        carRb.setSelected(true);
        suvRb.setToggleGroup(vehicleGroup);
        motoRb.setToggleGroup(vehicleGroup);
        vehiclePane.getChildren().addAll(carRb, suvRb, motoRb);

        var optionsPane = new VBox();

        var inwPane = new HBox();
        inSpecial.setDisable(true);
        inwPane.getChildren().addAll(washIn, inSpecial);

        var outwPane = new HBox();
        outSpecial.setDisable(true);
        outwPane.getChildren().addAll(washOut, outSpecial);

        var iowPane = new HBox();
        ioSpecial.setDisable(true);
        iowPane.getChildren().addAll(washIO, ioSpecial);

        optionsPane.getChildren().addAll(inwPane, outwPane, iowPane, bioWash, wax, engineWash, chassisWash);
        secondPane.getChildren().addAll(vehiclePane, optionsPane);

        mainPane.getChildren().addAll(instructions, secondPane);
        dialogPane.setContent(mainPane);
        dialogPane.setMinSize(1920, 1080);

    }
}