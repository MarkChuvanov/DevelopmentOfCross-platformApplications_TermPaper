package sample.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LegalProcedure {

    private int id;
    private StringProperty name;
    private IntegerProperty costs;

    public LegalProcedure(){
        this("", 0);
    }

    public LegalProcedure(String name, int costs){
        this.name = new SimpleStringProperty(name);
        this.costs = new SimpleIntegerProperty(costs);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public int getCosts() {
        return costs.get();
    }
    public IntegerProperty costsProperty() {
        return costs;
    }
    public void setCosts(int costs) {
        this.costs.set(costs);
    }
}