package sample.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class Client {

    private int id;
    private StringProperty surname;
    private StringProperty name;
    private StringProperty patronymic;
    private Date dateOfBirth;
    private StringProperty dateOfBirthProperty;
    private StringProperty passportSeries;
    private StringProperty passportNumber;
    private StringProperty placeOfResidence;

    public Client(){
        this("", "", "", null, "", "", "", "");
    }

    public Client(String surname, String name, String patronymic, Date dateOfBirth, String dateOfBirthProperty, String passportSeries, String passportNumber, String placeOfResidence){
        this.surname = new SimpleStringProperty(surname);
        this.name = new SimpleStringProperty(name);
        this.patronymic = new SimpleStringProperty(patronymic);
        this.dateOfBirth = dateOfBirth;
        this.dateOfBirthProperty = new SimpleStringProperty(dateOfBirthProperty);
        this.passportSeries = new SimpleStringProperty(passportSeries);
        this.passportNumber = new SimpleStringProperty(passportNumber);
        this.placeOfResidence = new SimpleStringProperty(placeOfResidence);
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfBirthProperty() {
        return dateOfBirthProperty.get();
    }
    public StringProperty dateOfBirthPropertyProperty() {
        return dateOfBirthProperty;
    }
    public void setDateOfBirthProperty(Date dateOfBirth) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateOfBirthProperty = dateFormat.format(dateOfBirth);
        this.dateOfBirthProperty.set(dateOfBirthProperty);
    }

    public String getPassportSeries() {
        return passportSeries.get();
    }
    public StringProperty passportSeriesProperty() { return passportSeries; }
    public void setPassportSeries(String passportSeries) {
        this.passportSeries.set(passportSeries);
    }

    public String getPassportNumber() {
        return passportNumber.get();
    }
    public StringProperty passportNumberProperty() {
        return passportNumber;
    }
    public void setPassportNumber(String passportNumber) {
        this.passportNumber.set(passportNumber);
    }

    public String getPlaceOfResidence() {
        return placeOfResidence.get();
    }
    public StringProperty placeOfResidenceProperty() {
        return placeOfResidence;
    }
    public void setPlaceOfResidence(String placeOfResidence) {
        this.placeOfResidence.set(placeOfResidence);
    }
}