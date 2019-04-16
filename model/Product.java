package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Product {

    private final IntegerProperty productID;
    private final StringProperty productName;
    private final DoubleProperty productPrice;
    private final IntegerProperty productInStock;
    private final IntegerProperty productMin;
    private final IntegerProperty productMax;
    private static ObservableList<Part> parts = FXCollections.observableArrayList();

    public Product() {
        productID = new SimpleIntegerProperty();
        productName = new SimpleStringProperty();
        productPrice = new SimpleDoubleProperty();
        productInStock = new SimpleIntegerProperty();
        productMin = new SimpleIntegerProperty();
        productMax = new SimpleIntegerProperty();
    }

    public IntegerProperty productIDProperty() {
        return productID;
    }
    public StringProperty productNameProperty() {
        return productName;
    }
    public DoubleProperty productPriceProperty() {
        return productPrice;
    }
    public IntegerProperty productInvProperty() {
        return productInStock;
    }

    public int getProductID() {
        return this.productID.get();
    }
    public void setProductID(int productID) { this.productID.set(productID); }
    public String getProductName() {
        return this.productName.get();
    }
    public void setProductName(String name) { this.productName.set(name); }
    public double getProductPrice() { return this.productPrice.get(); }
    public void setProductPrice(double price) { this.productPrice.set(price); }
    public int getProductInStock() { return this.productInStock.get(); }
    public void setProductInStock(int inStock) { this.productInStock.set(inStock); }
    public int getProductMin() {
        return this.productMin.get();
    }
    public void setProductMin(int min) { this.productMin.set(min); }
    public int getProductMax() {
        return this.productMax.get();
    }
    public void setProductMax(int max) { this.productMax.set(max); }
    public ObservableList getProductParts() { return parts; }
    public void setProductParts(ObservableList<Part> parts) { this.parts = parts; }
}