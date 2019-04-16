package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Part {

    private final StringProperty partName;
    private final IntegerProperty partID;
    private final DoubleProperty partPrice;
    private final IntegerProperty partInStock;
    private final IntegerProperty partMin;
    private final IntegerProperty partMax;

    public Part() {
        partID = new SimpleIntegerProperty();
        partName = new SimpleStringProperty();
        partPrice = new SimpleDoubleProperty();
        partInStock = new SimpleIntegerProperty();
        partMin = new SimpleIntegerProperty();
        partMax = new SimpleIntegerProperty();
    }

    public IntegerProperty partIDProperty() {
        return partID;
    }
    public StringProperty partNameProperty() {
        return partName;
    }
    public DoubleProperty partPriceProperty() {
        return partPrice;
    }
    public IntegerProperty partInStockProperty() {
        return partInStock;
    }
    public int getPartID() {
        return this.partID.get();
    }
    public void setPartID(int partID) { this.partID.set(partID); }
    public String getPartName() {
        return this.partName.get();
    }
    public void setPartName(String name) { this.partName.set(name); }
    public double getPartPrice() {
        return this.partPrice.get();
    }
    public void setPartPrice(double price) { this.partPrice.set(price); }
    public int getPartInStock() { return this.partInStock.get(); }
    public void setPartInStock(int inStock) { this.partInStock.set(inStock); }
    public int getPartMin() {
        return this.partMin.get();
    }
    public void setPartMin(int min) { this.partMin.set(min); }
    public int getPartMax() { return this.partMax.get(); }
    public void setPartMax(int max) { this.partMax.set(max); }
}