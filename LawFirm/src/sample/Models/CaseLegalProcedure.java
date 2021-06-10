package sample.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaseLegalProcedure {

    private int id;
    private int caseId;
    private int legalProcedureId;
    private Date date;
    private StringProperty dateProperty;
    private StringProperty result;

    public CaseLegalProcedure(){
        this(0, 0, null, "", "");
    }

    public CaseLegalProcedure(int caseId, int legalProcedureId, Date date, String dateProperty, String result){
        this.caseId = caseId;
        this.legalProcedureId = legalProcedureId;
        this.date = date;
        this.dateProperty = new SimpleStringProperty(dateProperty);
        this.result = new SimpleStringProperty(result);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getCaseId() {
        return caseId;
    }
    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public int getLegalProcedureId() {
        return legalProcedureId;
    }
    public void setLegalProcedureId(int legalProcedureId) {
        this.legalProcedureId = legalProcedureId;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateProperty() {
        return dateProperty.get();
    }
    public StringProperty datePropertyProperty() {
        return dateProperty;
    }
    public void setDateProperty(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateProperty = dateFormat.format(date);
        this.dateProperty.set(dateProperty);
    }

    public String getResult() {
        return result.get();
    }
    public StringProperty resultProperty() {
        return result;
    }
    public void setResult(String result) {
        this.result.set(result);
    }
}