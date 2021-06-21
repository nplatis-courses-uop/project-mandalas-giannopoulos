package client;

import java.util.ArrayList;

import common.Services;

import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;

/**
 * UI for selecting cleaning services
 */
public class ServiceWindow {
    private static final RadioButton carRb = new RadioButton("Car");
    private static final RadioButton suvRb = new RadioButton("SUV");
    private static final RadioButton motoRb = new RadioButton("Motorcycle");
    private static final CheckBox washIn = new CheckBox("Interior wash");
    private static final CheckBox inSpecial = new CheckBox("Special");
    private static final CheckBox washOut = new CheckBox("Exterior wash");
    private static final CheckBox outSpecial = new CheckBox("Special");
    private static final CheckBox washIO = new CheckBox("Interior/exterior wash");
    private static final CheckBox ioSpecial = new CheckBox("Special");
    private static final CheckBox org = new CheckBox("Organic cleaning (Interior)");
    private static final CheckBox wax = new CheckBox("Wax");
    private static final CheckBox engineWash = new CheckBox("Engine Wash");
    private static final CheckBox chassisWash = new CheckBox("Chassis Wash");
    private static final Label priceLabel = new Label("Your total is 0.00"); 

    public static void display(Stage primaryStage, TextField plate) {
        
        var mainPane = new VBox();
        mainPane.setId("main");

        var instructions = new Label("Choose your service/services...");
        instructions.setId("inst");
        
        var vehiclePane = new VBox();
        vehiclePane.setId("radio");
        final ToggleGroup vehicleGroup = new ToggleGroup();
        carRb.setToggleGroup(vehicleGroup);
        carRb.setSelected(true);
        suvRb.setToggleGroup(vehicleGroup);
        motoRb.setToggleGroup(vehicleGroup);
        vehiclePane.getChildren().addAll(carRb, suvRb, motoRb);

        var optionsPane = new VBox();
        optionsPane.setId("options");
        
        var specialPane = new VBox(inSpecial, outSpecial, ioSpecial);
        specialPane.setId("special");

        optionsPane.getChildren().addAll(washIn, washOut, washIO, org, wax, engineWash, chassisWash);
        var midPane = new HBox(vehiclePane, optionsPane, specialPane);
        midPane.setId("midPane");

        inSpecial.setDisable(true);
        outSpecial.setDisable(true);
        ioSpecial.setDisable(true);

        var botPane = new VBox();
        botPane.setId("bot");
        var buttonPane = new HBox();
        var doneBtn = new Button("Done");
        doneBtn.setId("buttons1");
        var cancelBtn = new Button("Cancel");
        cancelBtn.setId("buttons1");
        buttonPane.getChildren().addAll(doneBtn, cancelBtn);
        botPane.getChildren().addAll(priceLabel, buttonPane);

        mainPane.getChildren().addAll(instructions, midPane, botPane);

        var stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(primaryStage);
        stage.setTitle("Select services");
        stage.setResizable(false);
        var scene = new Scene(mainPane, primaryStage.getWidth(), primaryStage.getHeight());
        try {
            scene.getStylesheets().add(ServiceWindow.class.getResource("stylesheet.css").toExternalForm());
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
        stage.setScene(scene);
        stage.show();

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
                    stage.close();
                    plate.clear();
                }
            }   
        });
        cancelBtn.setOnAction((event) -> {
            stage.close();
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

    private static void handleVisibility() {
        washIn.setDisable(motoRb.isSelected() || org.isSelected() || washIO.isSelected());
        inSpecial.setDisable(motoRb.isSelected() || !washIn.isSelected());
        outSpecial.setDisable(!washOut.isSelected());
        washIO.setDisable(motoRb.isSelected() || washIn.isSelected() || washOut.isSelected() || org.isSelected());
        ioSpecial.setDisable(motoRb.isSelected() || !washIO.isSelected());
        org.setDisable(motoRb.isSelected() || washIn.isSelected());
        chassisWash.setDisable(motoRb.isSelected());
        washOut.setDisable(washIO.isSelected());
    }

    private static void updateCost() {
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