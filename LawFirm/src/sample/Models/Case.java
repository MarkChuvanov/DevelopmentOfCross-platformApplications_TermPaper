package sample.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Case {

    private int id;
    private StringProperty number;
    private int clientId;
    private Date openingDate;
    private StringProperty openingDateProperty;
    private Date closingDate;
    private StringProperty closingDateProperty;
    private StringProperty status;
    private StringProperty caseResult;
    private IntegerProperty costs;

    public Case(){
        this("", 0, null, "", null, "", "", "", 0);
    }

    public Case(String number, int clientId, Date openingDate, String openingDateProperty, Date closingDate, String closingDateProperty, String status, String caseResult, int costs){
        this.number = new SimpleStringProperty(number);
        this.clientId = clientId;
        this.openingDate = openingDate;
        this.openingDateProperty = new SimpleStringProperty(openingDateProperty);
        this.closingDate = closingDate;
        this.closingDateProperty = new SimpleStringProperty(closingDateProperty);
        this.status = new SimpleStringProperty(status);
        this.caseResult = new SimpleStringProperty(caseResult);
        this.costs = new SimpleIntegerProperty(costs);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number.get();
    }
    public StringProperty numberProperty() {
        return number;
    }
    public void setNumber(String number) {
        this.number.set(number);
    }

    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Date getOpeningDate() {
        return openingDate;
    }
    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public String getOpeningDateProperty() {
        return openingDateProperty.get();
    }
    public StringProperty openingDatePropertyProperty() {
        return openingDateProperty;
    }
    public void setOpeningDateProperty(Date openingDate) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String openingDateProperty = dateFormat.format(openingDate);
        this.openingDateProperty.set(openingDateProperty);
    }

    public Date getClosingDate() {
        return closingDate;
    }
    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public String getClosingDateProperty() {
        return closingDateProperty.get();
    }
    public StringProperty closingDatePropertyProperty() {
        return closingDateProperty;
    }
    public void setClosingDateProperty(Date closingDate) {
        if (closingDate == null){
            this.closingDateProperty.set(null);
            return;
        }
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String closingDateProperty = dateFormat.format(closingDate);
        this.closingDateProperty.set(closingDateProperty);
    }

    public String getStatus() {
        return status.get();
    }
    public StringProperty statusProperty() {
        return status;
    }
    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getCaseResult() {
        return caseResult.get();
    }
    public StringProperty caseResultProperty() {
        return caseResult;
    }
    public void setCaseResult(String caseResult) {
        this.caseResult.set(caseResult);
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