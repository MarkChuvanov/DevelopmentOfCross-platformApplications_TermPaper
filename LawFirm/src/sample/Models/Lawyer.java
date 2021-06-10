package sample.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;
import java.time.Period;

public class Lawyer {

    private int id;
    private StringProperty surname;
    private StringProperty name;
    private StringProperty patronymic;
    private Date startDate;
    private boolean isWorked;
    private StringProperty lengthOfService;

    public Lawyer(){
        this("", "", "", null, true, "");
    }

    public Lawyer(String surname, String name, String patronymic, Date startDate, boolean isWorked, String lengthOfService){
        this.surname = new SimpleStringProperty(surname);
        this.name = new SimpleStringProperty(name);
        this.patronymic = new SimpleStringProperty(patronymic);
        this.startDate = startDate;
        this.isWorked = isWorked;
        this.lengthOfService = new SimpleStringProperty(lengthOfService);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname.get();
    }
    public StringProperty surnameProperty() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname.set(surname);
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

    public String getPatronymic() {
        return patronymic.get();
    }
    public StringProperty patronymicProperty() {
        return patronymic;
    }
    public void setPatronymic(String patronymic) {
        if (patronymic == null){
            this.patronymic.set(null);
            return;
        }
        this.patronymic.set(patronymic);
    }

    public void setWorked(boolean worked) {
        isWorked = worked;
    }
    public boolean isWorked() {
        return isWorked;
    }

    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getLengthOfService() {
        return lengthOfService.get();
    }
    public StringProperty lengthOfServiceProperty() {
        return lengthOfService;
    }
    public void setLengthOfService(Date startDate) {
        Date date = new Date(System.currentTimeMillis());
        int length = Period.between(startDate.toLocalDate(), date.toLocalDate()).getYears();
        if (length < 1){
            this.lengthOfService.set("менее года");
        }
        else{
            this.lengthOfService.set(Integer.toString(length));
        }
    }
}