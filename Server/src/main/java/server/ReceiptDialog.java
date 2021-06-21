package server;

import java.time.LocalDateTime;

import common.Services;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Cleaning services receipt UI
 */
public class ReceiptDialog extends Dialog {

    public ReceiptDialog(Order selectedRow) {
        var dialogPane = getDialogPane();

        var mainPane = new VBox();
        var receiptText = "Plate Number: " + selectedRow.getPlate() + "\nServices:\n";
        
        for (var service : selectedRow.getServices()) {
            var pair = Services.get().services.get(service);
            receiptText += "\t" + pair.getValue0() + " (" + String.format("%.2f", pair.getValue1()) + ")\n";
        }
        receiptText += "Your total is: " + Services.calculateCost(selectedRow.getServices());

        mainPane.getChildren().add(new Label(receiptText));
        var btnPane = new HBox();
        var registerBtn = new Button("Register");
        var cancelBtn = new Button("Cancel");
        btnPane.getChildren().addAll(registerBtn, cancelBtn);
        mainPane.getChildren().add(btnPane);

        dialogPane.setContent(mainPane);

        registerBtn.setOnAction((event) -> {
            selectedRow.setCost(Services.calculateCost(selectedRow.getServices()));
            selectedRow.setDepartureTime(LocalDateTime.now());
            Database.update(selectedRow);
            Server.table.getItems().remove(selectedRow);
            dialogPane.getScene().getWindow().hide();
        });

        cancelBtn.setOnAction((event) -> {
            dialogPane.getScene().getWindow().hide();
        });
    }
}
