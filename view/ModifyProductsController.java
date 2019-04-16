package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import static view.StartAppController.selectProduct;
import static model.Inventory.getParts;
import static model.Inventory.getProducts;

public class ModifyProductsController implements Initializable {

    @Override public void initialize(URL url, ResourceBundle rb) {
        Product product = getProducts().get(select);
        ID = getProducts().get(select).getProductID();
        IDText.setText("Auto: " + ID);
        nameText.setText(product.getProductName());
        inStockText.setText(Integer.toString(product.getProductInStock()));
        priceText.setText(Double.toString(product.getProductPrice()));
        minText.setText(Integer.toString(product.getProductMin()));
        maxText.setText(Integer.toString(product.getProductMax()));
        currentProductParts = product.getProductParts();
        IDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        inStockColumn.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        currentIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        currentNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        currentInStockColumn.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        currentPriceColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        modifyProductAddTableView.setItems(getParts());
        modifyProductDeleteTableView.setItems(currentProductParts);
    }

    private ObservableList<Part> currentProductParts = FXCollections.observableArrayList();
    private int select = selectProduct();
    private String expMessage = new String();
    private int ID;

    @FXML private TextField IDText;
    @FXML private TextField nameText;
    @FXML private TextField minText;
    @FXML private TextField maxText;
    @FXML private TextField inStockText;
    @FXML private TextField priceText;
    @FXML private TextField addPartSearchText;
    @FXML private TextField deletePartSearchText;
    @FXML private TableView<Part> modifyProductAddTableView;
    @FXML private TableView<Part> modifyProductDeleteTableView;
    @FXML private TableColumn<Part, Integer> IDColumn;
    @FXML private TableColumn<Part, String> nameColumn;
    @FXML private TableColumn<Part, Integer> inStockColumn;
    @FXML private TableColumn<Part, Double> priceColumn;
    @FXML private TableColumn<Part, Integer> currentIDColumn;
    @FXML private TableColumn<Part, String> currentNameColumn;
    @FXML private TableColumn<Part, Integer> currentInStockColumn;
    @FXML private TableColumn<Part, Double> currentPriceColumn;

    @FXML void clearSearchAdd(ActionEvent event) {
        modifyProductAddTableView.setItems(getParts());
        addPartSearchText.clear();
    }

    @FXML void clearSearchProduct (ActionEvent event) {
        modifyProductDeleteTableView.setItems(currentProductParts);
        deletePartSearchText.clear();
    }

    @FXML void searchPartAdd(ActionEvent event) {
        String search = addPartSearchText.getText();
        int pin = -1;
        if (Inventory.lookupPart(search) == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Search Exception");
            alert.setHeaderText("Part not found");
            alert.setContentText("Part was not found");
            alert.showAndWait();
        } else {
            pin = Inventory.lookupPart(search);
            Part part = Inventory.getParts().get(pin);
            ObservableList<Part> parts = FXCollections.observableArrayList();
            parts.add(part);
            modifyProductAddTableView.setItems(parts);
        }
    }
    @FXML void add(ActionEvent event) {
        Part part = modifyProductAddTableView.getSelectionModel().getSelectedItem();
        currentProductParts.add(part);
        modifyProductDeleteTableView.setItems(currentProductParts);
    }

    @FXML void searchPartDelete(ActionEvent event) {
        boolean find = false;
        String search = deletePartSearchText.getText();
        for (int i = 0; i < currentProductParts.size(); i++) {
            if (Integer.parseInt(search) == currentProductParts.get(i).getPartID() || search.equals(currentProductParts.get(i).getPartName())) {
                Part part = currentProductParts.get(i);
                ObservableList<Part> parts = FXCollections.observableArrayList();
                parts.add(part);
                modifyProductDeleteTableView.setItems(parts);
                find = true;
            }
        }
        if (!find) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Search Exception");
            alert.setHeaderText("Part not found");
            alert.setContentText("Part was not found");
            alert.showAndWait();
        }
    }

    @FXML void delete(ActionEvent event) {
        Part part = modifyProductDeleteTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Part Delete");
        alert.setContentText("Are you sure you want to delete " + part.getPartName());
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> currentProductParts.remove(part));
    }

    @FXML void save(ActionEvent event) throws IOException {
        String name = nameText.getText();
        String inStock = inStockText.getText();
        String price = priceText.getText();
        String min = minText.getText();
        String max = maxText.getText();
        try {
            double partsPrice = 0;
            for (int i = 0; i < currentProductParts.size(); i++) { partsPrice = partsPrice + currentProductParts.get(i).getPartPrice(); }
            if (name.equals("")) { expMessage = expMessage + ("Name Field Blank" + '\n'); }
            if (Integer.parseInt(min) < 0) { expMessage = expMessage + ("Inventory must be greater than 0" + '\n'); }
            if (Double.parseDouble(price) < 0) { expMessage = expMessage + ("Price must be greater than $0.00" + '\n'); }
            if (Integer.parseInt(max) < Integer.parseInt(min)) { expMessage = expMessage + ("Maximum Inventory must be more than Minimum" + '\n'); }
            if (Integer.parseInt(inStock) < Integer.parseInt(min)) { expMessage = expMessage + ("Inventory must be more than Minimum" + '\n');}
            if (Integer.parseInt(inStock) > Integer.parseInt(max)) { expMessage = expMessage + ("Inventory must be less than Maximum" + '\n');}
            if (currentProductParts.size() < 1) { expMessage = expMessage + ("Product must contain at least 1 part" + '\n'); }
            if (partsPrice > Double.parseDouble(price)) { expMessage = expMessage + ("Product price must be greater than cost of parts" + '\n'); }
            if (expMessage.length() != 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Exception Found");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText(expMessage);
                alert.showAndWait();
                expMessage = "";
            } else {
                Product p = new Product();
                p.setProductID(ID);
                p.setProductName(name);
                p.setProductPrice(Double.parseDouble(price));
                p.setProductInStock(Integer.parseInt(inStock));
                p.setProductMin(Integer.parseInt(min));
                p.setProductMax(Integer.parseInt(max));
                p.setProductParts(currentProductParts);
                Inventory.updateProduct(select, p);

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