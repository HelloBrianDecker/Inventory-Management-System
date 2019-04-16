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
import javafx.stage.Stage;
import model.Inhouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import static view.StartAppController.selectPart;
import static model.Inventory.getParts;

public class ModifyPartsController implements Initializable {

    @Override public void initialize(URL url, ResourceBundle rb) {
        Part part = getParts().get(select);
        ID = getParts().get(select).getPartID();
        IDText.setText("Auto: " + ID);
        nameText.setText(part.getPartName());
        inStockText.setText(Integer.toString(part.getPartInStock()));
        priceText.setText(Double.toString(part.getPartPrice()));
        minText.setText(Integer.toString(part.getPartMin()));
        maxText.setText(Integer.toString(part.getPartMax()));

        if (part instanceof Inhouse) {
            IHOText.setText(Integer.toString(((Inhouse) getParts().get(select)).getMachineID()));
            IHOPartLabel.setText("Machine ID");
            inhouseRadio.setSelected(true);
        } else {
            IHOText.setText(((Outsourced) getParts().get(select)).getCompanyName());
            IHOPartLabel.setText("Company Name");
            outsourcedRadio.setSelected(true); }
    }

    private boolean outsourced;
    public boolean inhouse;
    private int select = selectPart();
    private String expMessage = new String();
    private int ID;

    @FXML private TextField IDText;
    @FXML private TextField nameText;
    @FXML private TextField inStockText;
    @FXML private TextField priceText;
    @FXML private TextField minText;
    @FXML private TextField IHOText;
    @FXML private TextField maxText;
    @FXML private Label IHOPartLabel;
    @FXML private RadioButton inhouseRadio;
    @FXML private RadioButton outsourcedRadio;

    @FXML void inhouseRadio(ActionEvent event) {
        ToggleGroup group = new ToggleGroup();
        inhouseRadio.setToggleGroup(group);
        outsourcedRadio.setToggleGroup(group);
        inhouseRadio.setSelected(true);
        inhouse = true;
        IHOPartLabel.setText("Machine ID");
        IHOText.setPromptText("Machine ID");}

    @FXML void outsourcedRadio(ActionEvent event) {
        ToggleGroup group = new ToggleGroup();
        inhouseRadio.setToggleGroup(group);
        outsourcedRadio.setToggleGroup(group);
        outsourcedRadio.setSelected(true);
        outsourced = true;
        IHOPartLabel.setText("Company Name");
        IHOText.setPromptText("Company Name");}

    @FXML void save(ActionEvent event) throws IOException {
        String name = nameText.getText();
        String inStock = inStockText.getText();
        String price = priceText.getText();
        String max = maxText.getText();
        String min = minText.getText();
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
                if (!outsourced) {
                    Inhouse ih = new Inhouse();
                    ih.setPartID(ID);
                    ih.setPartName(name);
                    ih.setPartPrice(Double.parseDouble(price));
                    ih.setPartInStock(Integer.parseInt(inStock));
                    ih.setPartMin(Integer.parseInt(min));
                    ih.setPartMax(Integer.parseInt(max));
                    ih.setMachineID(Integer.parseInt(IHO));
                    Inventory.updatePart(select, ih);
                } else {
                    Outsourced os = new Outsourced();
                    os.setPartID(ID);
                    os.setPartName(name);
                    os.setPartPrice(Double.parseDouble(price));
                    os.setPartInStock(Integer.parseInt(inStock));
                    os.setPartMin(Integer.parseInt(min));
                    os.setPartMax(Integer.parseInt(max));
                    os.setCompanyName(IHO);
                    Inventory.updatePart(select, os);
                }
                Parent load = FXMLLoader.load(getClass().getResource("StartApp.fxml"));
                Scene scene = new Scene(load);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Exception Found");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText("Fields cannot be left blank");
            alert.showAndWait(); }
    }

    @FXML void cancel(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("StartApp.fxml"));
        Scene scene = new Scene(load);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}