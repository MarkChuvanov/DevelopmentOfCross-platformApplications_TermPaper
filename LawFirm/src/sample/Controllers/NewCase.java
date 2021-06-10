package sample.Controllers;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Model;
import sample.Models.*;
import sample.ViewModels.ActiveLawyer;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class NewCase {

    @FXML
    private TextField tfNumber;
    @FXML
    private TextField tfClientPassportSeries;
    @FXML
    private TextField tfClientPassportNumber;
    @FXML
    private ComboBox cbLawyers;

    private Stage dialogStage;
    private Case newCase;

    public void setAddStage(Stage addStage){
        this.dialogStage = addStage;
    }

    ArrayList<ActiveLawyer> lawyers;

    public int getLawyerId() {
        return lawyerId;
    }

    int lawyerId;

    public void setNewCase(Case newCase){
        this.newCase = newCase;
        tfNumber.setText(newCase.getNumber());
        tfClientPassportSeries.setText("");
        tfClientPassportNumber.setText("");
        try {
            ArrayList<String> activeLawyers = new ArrayList<>();
            lawyers = Model.getActiveLawyers();
            for (int i = 0; i < lawyers.size(); i++) {
                activeLawyers.add(lawyers.get(i).getFullName());
            }
            cbLawyers.setItems(FXCollections.observableArrayList(activeLawyers));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        cbLawyers.getSelectionModel().select(0);
    }

    @FXML
    private void onCreateCase(ActionEvent actionEvent) {
        String number = tfNumber.getText();
        if (number == ""){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Не указан номер дела!");
            alert.setResizable(false);
            alert.showAndWait();
            return;
        }
        else{
            if (!number.matches("(\\d)*-[у|г|а|А]")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Указан некорректный номер дела!");
                alert.setResizable(false);
                alert.showAndWait();
                return;
            }
            else{
                try {
                    if (!Model.isUniqueNumber(number)){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Ошибка");
                        alert.setContentText("Дело должно иметь уникальный номер!");
                        alert.setResizable(false);
                        alert.showAndWait();
                        return;
                    }
                    else{
                        newCase.setNumber(number);
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        String passportSeries = tfClientPassportSeries.getText();
        String passportNumber = tfClientPassportNumber.getText();
        if (passportSeries == "" || passportNumber == ""){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Не указаны персональные данные клиента!");
            alert.setResizable(false);
            alert.showAndWait();
            return;
        }
        else {
            if (!passportNumber.matches("(\\d){6}") || !passportSeries.matches("(\\d){4}")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Указаны некорректные персональные данные клиента!");
                alert.setResizable(false);
                alert.showAndWait();
                return;
            }
            else{
                try {
                    int clientId = Model.getClientId(passportSeries, passportNumber);
                    if (clientId != - 1){
                        newCase.setClientId(clientId);
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Ошибка");
                        alert.setContentText("Клиент не найден!");
                        alert.setResizable(false);
                        alert.showAndWait();
                        return;
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        newCase.setOpeningDate(new Date(System.currentTimeMillis()));
        newCase.setStatus("Работает");
        newCase.setCaseResult("(пусто)");
        lawyerId = lawyers.get(cbLawyers.getSelectionModel().getSelectedIndex()).getId();
        dialogStage.close();
    }
}