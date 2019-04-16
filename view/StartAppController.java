package view;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import app.StartApp;
import model.Inventory;
import model.Part;
import model.Product;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import static model.Inventory.*;

public class StartAppController implements Initializable {

    private static Part modifyPart;
    private static int selectedPart;
    private static Product modifyProduct;
    private static int selectedProduct;
    public static int selectPart() { return selectedPart; }
    public static int selectProduct() { return selectedProduct; }
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInStockColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Product, Integer> productIDColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInStockColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    @FXML private TextField searchPartText;
    @FXML private TextField searchProductText;

    public StartAppController() {}

    @Override public void initialize(URL url, ResourceBundle rb) {
        partIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        partInStockColumn.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        partPriceColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        productIDColumn.setCellValueFactory(cellData -> cellData.getValue().productIDProperty().asObject());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        productInStockColumn.setCellValueFactory(cellData -> cellData.getValue().productInvProperty().asObject());
        productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
        partsTableView.setItems(getParts());
        productsTableView.setItems(getProducts());
    }

    @FXML void addPart(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("AddParts.fxml"));
        Scene scene = new Scene(load);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML void addProduct(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("AddProducts.fxml"));
        Scene scene = new Scene(load);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML void modifyPart(ActionEvent event) throws IOException {
        modifyPart = partsTableView.getSelectionModel().getSelectedItem();
        if (modifyPart != null) {
            selectedPart = getParts().indexOf(modifyPart);
            Parent load = FXMLLoader.load(getClass().getResource("ModifyParts.fxml"));
            Scene scene = new Scene(load);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Exception Found");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a Part from the table");
            alert.showAndWait();
        }
    }

    @FXML void modifyProduct(ActionEvent event) throws IOException {
        modifyProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (modifyProduct != null) {
            selectedProduct = getProducts().indexOf(modifyProduct);
            Parent load = FXMLLoader.load(getClass().getResource("ModifyProducts.fxml"));
            Scene scene = new Scene(load);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Exception Found");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a Product from the table");
            alert.showAndWait();
        }
    }

    @FXML void deletePart(ActionEvent event) throws IOException {
        Part part = partsTableView.getSelectionModel().getSelectedItem();
        if (part != null) {
            if (partDelete(part)) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Exception Found");
                alert.setHeaderText("Part cannot be removed");
                alert.setContentText("This part is used in a product");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirm Delete");
                alert.setHeaderText("Confirm");
                alert.setContentText("Are you sure you want to delete " + part.getPartName());
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> {
                            removePart(part);
                            partsTableView.setItems(getParts());
                        });
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Exception Found");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a Part from the table");
            alert.showAndWait();
        }
    }

    @FXML void deleteProduct(ActionEvent event) throws IOException {
        Product product = productsTableView.getSelectionModel().getSelectedItem();
        if (product != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm");
            alert.setContentText("Are you sure you want to delete " + product.getProductName());
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        removeProduct(product);
                        productsTableView.setItems(getProducts());
                    });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Exception Found");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a Product from the table");
            alert.showAndWait();
        }
    }

    @FXML void searchPart (ActionEvent event) throws IOException {
        String search = searchPartText.getText();
        int pin = -1;
        if (Inventory.lookupPart(search) != -1) {
            pin = Inventory.lookupPart(search);
            Part part = Inventory.getParts().get(pin);
            ObservableList<Part> parts = FXCollections.observableArrayList();
            parts.add(part);
            partsTableView.setItems(parts);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Exception Found");
            alert.setHeaderText("Part not found");
            alert.setContentText("The search entered does not match any Part");
            alert.showAndWait();
        }
    }

    @FXML void searchProduct(ActionEvent event) throws IOException {
        int pin = -1;
        String search = searchProductText.getText();
        if (Inventory.lookupProduct(search) != -1) {
            pin = Inventory.lookupProduct(search);
            Product product = Inventory.getProducts().get(pin);
            ObservableList<Product> products = FXCollections.observableArrayList();
            products.add(product);
            productsTableView.setItems(products);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Exception Found");
            alert.setHeaderText("Product not found");
            alert.setContentText("The search entered does not match any Product");
            alert.showAndWait();
        }
    }

    @FXML void clearSearchPart(ActionEvent event) throws IOException {
        partsTableView.setItems(getParts());
        searchPartText.clear();
    }

    @FXML void clearSearchProduct(ActionEvent event) throws IOException {
        productsTableView.setItems(getProducts());
        searchProductText.clear();
    }

    @FXML void exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirma Exit");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you would like to exit");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.exit(0));
    }

    public void setStartApp(StartApp startApp) {
        partsTableView.setItems(getParts());
        productsTableView.setItems(getProducts());
    }
}