package sample.ViewModels;

import javafx.beans.property.*;

public class StockOfActivity {

    private StringProperty indicator;
    private IntegerProperty value;

    public StockOfActivity(){
        this("", 0);
    }

    public StockOfActivity(String indicator, int value){
        this.indicator = new SimpleStringProperty(indicator);
        this.value = new SimpleIntegerProperty(value);
    }

    public String getIndicator() {
        return indicator.get();
    }
    public StringProperty indicatorProperty() {
        return indicator;
    }
    public void setIndicator(String indicator) {
        this.indicator.set(indicator);
    }

    public double getValue() {
        return value.get();
    }
    public IntegerProperty valueProperty() {
        return value;
    }
    public void setValue(double value) {
        this.value.set((int)value);
    }
}