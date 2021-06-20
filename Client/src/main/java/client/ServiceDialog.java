package client;

import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;

import common.Services;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;

/**
 * UI for selecting cleaning services
 */
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
    private final CheckBox org = new CheckBox("Organic cleaning (Interior)");
    private final CheckBox wax = new CheckBox("Wax");
    private final CheckBox engineWash = new CheckBox("Engine Wash");
    private final CheckBox chassisWash = new CheckBox("Chassis Wash");
    private final Label priceLabel = new Label("Your total is 0.00");

    public ServiceDialog(TextField plate) {
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

        var buttonPane = new HBox();
        var doneBtn = new Button("Done");
        var cancelBtn = new Button("Cancel");
        buttonPane.getChildren().addAll(doneBtn, cancelBtn);

        optionsPane.getChildren().addAll(inwPane, outwPane, iowPane, org, wax, engineWash, chassisWash);
        secondPane.getChildren().addAll(vehiclePane, optionsPane);

        mainPane.getChildren().addAll(instructions, secondPane, priceLabel, buttonPane);
        dialogPane.setContent(mainPane);
        dialogPane.setMinSize(1920, 1080);

        doneBtn.setOnAction((event) -> {
            var serviceList = new ArrayList<String>();
            var prefix = "";
            var selectedRb = vehicleGroup.getSelectedToggle();
            if (selectedRb == carRb) {
                prefix = "CAR";
            } else if (selectedRb == suvRb) {
                prefix = "SUV";
            } else if (selectedRb == motoRb) {
                prefix = "MOT";
            }
            if (washIn.isSelected()) {
                var code = prefix + "IN";
                code += inSpecial.isSelected() ? "S" : "R";
                serviceList.add(code);
            }
            if (washOut.isSelected()) {
                var code = prefix + "EX";
                code += outSpecial.isSelected() ? "S" : "R";
                serviceList.add(code);
            }
            if (washIO.isSelected()) {
                var code = prefix + "IE";
                code += ioSpecial.isSelected() ? "S" : "R";
                serviceList.add(code);
            }
            if (org.isSelected()) {
                serviceList.add(prefix + "ORG");
            }
            if (wax.isSelected()) {
                serviceList.add(prefix + "WAX");
            }
            if (engineWash.isSelected()) {
                serviceList.add(prefix + "ENG");
            }
            if (chassisWash.isSelected()) {
                serviceList.add(prefix + "CHA");
            }
            if (serviceList.size() == 0) {
                var noSelectionError = new Alert(AlertType.ERROR);
                noSelectionError.setTitle("Error");
                noSelectionError.setHeaderText("There is something wrong with your selections");
                noSelectionError.setContentText("You haven't selected anything!");
                noSelectionError.showAndWait();
            } else {
                var alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Please confirm information");
                var content = "Plate: " + plate.getText() + "\n";
                content += "Services:\n";
                for (var service : serviceList) {
                    var pair = Services.get().services.get(service);
                    content += "\t" + pair.getValue0() + " (" + String.format("%.2f", pair.getValue1()) + ")\n";
                }
                content += "Your total is: " + String.format("%.2f", Services.calculateCost(serviceList));
                alert.setContentText(content);
                var pushed = alert.showAndWait();
                if (pushed.get() == ButtonType.OK) {
                    App.send(plate.getText(), serviceList);
                    dialogPane.getScene().getWindow().hide();
                    plate.clear();
                }
            }   
        });
        cancelBtn.setOnAction((event) -> {
            dialogPane.getScene().getWindow().hide();
            plate.clear();
        });
        carRb.setOnAction((event) -> {
            handleVisibility();
            updateCost();
        });
        suvRb.setOnAction((event) -> {
            handleVisibility();
            updateCost();
        });
        motoRb.setOnAction((event) -> {
            handleVisibility();
            updateCost();
        });
        washIn.setOnAction((event) -> {
            handleVisibility();
            updateCost();
        });
        inSpecial.setOnAction((event) -> {
            handleVisibility();
            updateCost();
        });
        washOut.setOnAction((event) -> {
            handleVisibility();
            updateCost();
        });
        outSpecial.setOnAction((event) -> {
            handleVisibility();
            updateCost();
        });
        washIO.setOnAction((event) -> {
            handleVisibility();
            updateCost();
        });
        ioSpecial.setOnAction((event) -> {
            handleVisibility();
            updateCost();
        });
        org.setOnAction((event) -> {
            handleVisibility();
            updateCost();
        });
        wax.setOnAction((event) -> {
            handleVisibility();
            updateCost();
        });
        engineWash.setOnAction((event) -> {
            handleVisibility();
            updateCost();
        });
        chassisWash.setOnAction((event) -> {
            handleVisibility();
            updateCost();
        });
    }

    private void handleVisibility() {
        washIn.setDisable(motoRb.isSelected() || org.isSelected() || washIO.isSelected());
        inSpecial.setDisable(motoRb.isSelected() || !washIn.isSelected());
        outSpecial.setDisable(!washOut.isSelected());
        washIO.setDisable(motoRb.isSelected() || washIn.isSelected() || washOut.isSelected() || org.isSelected());
        ioSpecial.setDisable(motoRb.isSelected() || !washIO.isSelected());
        org.setDisable(motoRb.isSelected() || washIn.isSelected());
        chassisWash.setDisable(motoRb.isSelected());
        washOut.setDisable(washIO.isSelected());
    }

    private void updateCost() {
        var codes = new ArrayList<String>();
        if (carRb.isSelected()) {
            if (washIn.isSelected() && !washIn.isDisabled()) {
                if (inSpecial.isSelected()) {
                    codes.add("CARINS");
                } else {
                    codes.add("CARINR");
                }
            }
            if (washOut.isSelected() && !washOut.isDisabled()) {
                if (outSpecial.isSelected()) {
                    codes.add("CAREXS");
                } else {
                    codes.add("CAREXR");
                }
            }
            if (washIO.isSelected() && !washIO.isDisabled()) {
                if (ioSpecial.isSelected()) {
                    codes.add("CAREIS");
                } else {
                    codes.add("CAREIR");
                }
            }
            if (org.isSelected() && !org.isDisabled()) {
                codes.add("CARORG");
            }
            if (wax.isSelected()) {
                codes.add("CARWAX");
            }
            if (engineWash.isSelected()) {
                codes.add("CARENG");
            }
            if (chassisWash.isSelected() && !chassisWash.isDisabled()) {
                codes.add("CARCHA");
            }
        } else if (suvRb.isSelected()) {
            if (washIn.isSelected() && !washIn.isDisabled()) {
                if (inSpecial.isSelected()) {
                    codes.add("SUVINS");
                } else {
                    codes.add("SUVINR");
                }
            }
            if (washOut.isSelected() && !washOut.isDisabled()) {
                if (outSpecial.isSelected()) {
                    codes.add("SUVEXS");
                } else {
                    codes.add("SUVEXR");
                }
            }
            if (washIO.isSelected() && !washIO.isDisabled()) {
                if (ioSpecial.isSelected()) {
                    codes.add("SUVEIS");
                } else {
                    codes.add("SUVEIR");
                }
            }
            if (org.isSelected() && !org.isDisabled()) {
                codes.add("SUVORG");
            }
            if (wax.isSelected()) {
                codes.add("SUVWAX");
            }
            if (engineWash.isSelected()) {
                codes.add("SUVENG");
            }
            if (chassisWash.isSelected() && !chassisWash.isDisabled()) {
                codes.add("SUVCHA");
            }
        } else if (motoRb.isSelected()) {
            if (washOut.isSelected() && !washOut.isDisabled()) {
                if (outSpecial.isSelected()) {
                    codes.add("MOTEXS");
                } else {
                    codes.add("MOTEXR");
                }
            }
            if (wax.isSelected()) {
                codes.add("MOTWAX");
            }
            if (engineWash.isSelected()) {
                codes.add("MOTENG");
            }
        }
        priceLabel.setText("Your total is " + String.format("%.2f", Services.calculateCost(codes)));
    }
}