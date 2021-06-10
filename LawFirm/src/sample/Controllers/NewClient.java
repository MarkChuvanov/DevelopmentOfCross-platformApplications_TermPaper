package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Models.Client;

import java.sql.Date;
import java.time.LocalDate;

public class NewClient {

    @FXML
    private TextField tfNewClientSurname;
    @FXML
    private TextField tfNewClientName;
    @FXML
    private TextField tfNewClientPatronymic;
    @FXML
    private DatePicker dpNewClientDateOfBirth;
    @FXML
    private TextField tfNewClientPassportSeries;
    @FXML
    private TextField tfNewClientPassportNumber;
    @FXML
    private TextArea taNewClientPlaceOfResidence;
    private Stage dialogStage;
    private Client client;

    public void setAddStage(Stage addStage){
        this.dialogStage = addStage;
    }

    public void setClient(Client client){
        this.client = client;
        tfNewClientSurname.setText(client.getSurname());
        tfNewClientName.setText(client.getName());
        tfNewClientPatronymic.setText(client.getPatronymic());
        tfNewClientPassportSeries.setText(client.getPassportSeries());
        tfNewClientPassportNumber.setText(client.getPassportNumber());
        dpNewClientDateOfBirth.setValue(LocalDate.now());
        taNewClientPlaceOfResidence.setText(client.getPlaceOfResidence());

        dpNewClientDateOfBirth.getEditor().setDisable(true);
        dpNewClientDateOfBirth.getEditor().setOpacity(1);

    }

    public void onAddNewClient(ActionEvent actionEvent) {
        String surname = tfNewClientSurname.getText();
        String name = tfNewClientName.getText();
        String patronymic = tfNewClientPatronymic.getText();
        String passportSeries = tfNewClientPassportSeries.getText();
        String passportNumber = tfNewClientPassportNumber.getText();
        LocalDate dateOfBirth = dpNewClientDateOfBirth.getValue();
        String placeOfResidence = taNewClientPlaceOfResidence.getText();
        if (surname.equals("") || name.equals("") || passportSeries.equals("") || passportNumber.equals("") || placeOfResidence.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Указаны некорректные данные о клиенте!");
            alert.setResizable(false);
            alert.showAndWait();
            return;
        }
        if (!passportNumber.matches("(\\d){6}") || !passportSeries.matches("(\\d){4}")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Серия и номер паспорта должны состоять из четырех и шести цифр соответственно!");
            alert.setResizable(false);
            alert.showAndWait();
            return;
        }

        client.setSurname(surname);
        client.setName(name);
        client.setPatronymic(patronymic);
        client.setPassportSeries(passportSeries);
        client.setPassportNumber(passportNumber);
        client.setPlaceOfResidence(placeOfResidence);
        client.setDateOfBirth(Date.valueOf(dateOfBirth));
        dialogStage.close();
    }
}