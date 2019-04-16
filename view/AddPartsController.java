package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Inhouse;
import model.Outsourced;
import model.Inventory;


public class AddPartsController implements Initializable {

    @Override public void initialize(URL url, ResourceBundle rb) {
        ID = getPartIDAuto();
        IDText.setText("Auto: " + ID);
    }

    @FXML private Label IHOPartLabel;
    @FXML private TextField IHOText;
    @FXML private RadioButton inHouseRadio;
    @FXML private RadioButton outsourcedRadio;
    @FXML private TextField IDText;
    @FXML private TextField nameText;
    @FXML private TextField inStockText;
    @FXML private TextField priceText;
    @FXML private TextField minText;
    @FXML private TextField maxText;

    public static int getPartIDAuto() { partIDAuto++; return partIDAuto; }
    private static int partIDAuto = 0;
    private boolean inhouse;
    private boolean outsourced;
    private String expMessage = new String();
    private int ID;
    private ToggleGroup group = new ToggleGroup();


    @FXML void inHouseRadio(ActionEvent event) {
        IHOPartLabel.setText("Machine ID");
        IHOText.setPromptText("Machine ID");
        inHouseRadio.setToggleGroup(group);
        inHouseRadio.setSelected(true);
        inhouse = true;
    }

    @FXML void outsourcedRadio(ActionEvent event) {
        IHOPartLabel.setText("Company Name");
        IHOText.setPromptText("Company Name");
        outsourcedRadio.setToggleGroup(group);
        outsourced = true;
    }

    @FXML void save(ActionEvent event) throws IOException {
        String name = nameText.getText();
        String inStock = inStockText.getText();
        String price = priceText.getText();
        String min = minText.getText();
        String max = maxText.getText();
        String IHO = IHOText.getText();
        try {
            if (name.equals("")) {expMessage = expMessage + ("Name Field Blank" + '\n');}
            if (Integer.parseInt(inStock) < 1) { expMessage = expMessage + ("The inventory must be greater than 0" + '\n');}
            if (Double.parseDouble(price) < 1) { expMessage = expMessage + ("Price must be greater than $0.00" + '\n');}
            if (Integer.parseInt(max) < Integer.parseInt(min)) { expMessage = expMessage + ("Inventory Maximum must be more than Minimum" + '\n');}
            if (Integer.parseInt(inStock) < Integer.parseInt(min)) { expMessage = expMessage + ("Inventory must be more than Minimum" + '\n');}
            if (Integer.parseInt(inStock) > Integer.parseInt(max)) { expMessage = expMessage + ("Inventory must be less than Maximum" + '\n');}
            if (!inhouse & !outsourced) { expMessage = expMessage + ("Inhouse or Outsourced must be Selected" + '\n');}
            if (expMessage.length() != 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Exception Found");
                alert.setHeaderText("Error Adding Part");
                alert.setContentText(expMessage);
                alert.showAndWait();
                expMessage = "";
            }
            else {
                if (inhouse) {
                    Inhouse ih = new Inhouse();
                    ih.setPartID(ID);
                    ih.setPartName(name);
                    ih.setPartPrice(Double.parseDouble(price));
                    ih.setPartInStock(Integer.parseInt(inStock));
                    ih.setPartMin(Integer.parseInt(min));
                    ih.setPartMax(Integer.parseInt(max));
                    ih.setMachineID(Integer.parseInt(IHO));
                    Inventory.addPart(ih);
                }
                else {
                    Outsourced os = new Outsourced();
                    os.setPartID(ID);
                    os.setPartName(name);
                    os.setPartPrice(Double.parseDouble(price));
                    os.setPartInStock(Integer.parseInt(inStock));
                    os.setPartMin(Integer.parseInt(min));
                    os.setPartMax(Integer.parseInt(max));
                    os.setCompanyName(IHO);
                    Inventory.addPart(os);
                }
                Parent load = FXMLLoader.load(getClass().getResource("StartApp.fxml"));
                Scene scene = new Scene(load);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Exception Found");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText("Please fill in all Fields");
            alert.showAndWait();
        }
    }

    @FXML void cancel(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("StartApp.fxml"));
        Scene scene = new Scene(load);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}

