package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Inhouse extends Part {
    private final IntegerProperty machineID;
    public Inhouse() { super();machineID = new SimpleIntegerProperty(); }
    public void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }
    public int getMachineID() {
        return this.machineID.get();
    }
}