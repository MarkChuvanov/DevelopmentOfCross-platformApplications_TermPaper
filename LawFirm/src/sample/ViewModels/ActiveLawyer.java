package sample.ViewModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ActiveLawyer {

    private int id;
    private StringProperty fullName;

    public ActiveLawyer(){
        this(0, "");
    }

    public ActiveLawyer(int id, String fullName){
        this.id = id;
        this.fullName = new SimpleStringProperty(fullName);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName.get();
    }
    public StringProperty fullNameProperty() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }
}