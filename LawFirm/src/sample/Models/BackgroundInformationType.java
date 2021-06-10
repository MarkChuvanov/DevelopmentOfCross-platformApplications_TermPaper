package sample.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BackgroundInformationType {

    private int id;
    private StringProperty caseResultType;
    private IntegerProperty premiumRate;

    public BackgroundInformationType(){
        this("", 0);
    }

    public BackgroundInformationType(String caseResultType, int premiumRate){
        this.caseResultType = new SimpleStringProperty(caseResultType);
        this.premiumRate = new SimpleIntegerProperty(premiumRate);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCaseResultType() {
        return caseResultType.get();
    }
    public StringProperty caseResultTypeProperty() {
        return caseResultType;
    }
    public void setCaseResultType(String caseResultType) {
        this.caseResultType.set(caseResultType);
    }

    public int getPremiumRate() {
        return premiumRate.get();
    }
    public IntegerProperty premiumRateProperty() {
        return premiumRate;
    }
    public void setPremiumRate(int premiumRate) {
        this.premiumRate.set(premiumRate);
    }
}