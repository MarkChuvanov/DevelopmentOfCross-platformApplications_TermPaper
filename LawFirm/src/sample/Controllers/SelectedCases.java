package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model;
import sample.Models.Case;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SelectedCases {

    @FXML
    private RadioButton rbLastMonth;
    @FXML
    private RadioButton rbLastYear;
    @FXML
    private RadioButton rbOwnValue;

    @FXML
    private TableView<Case> tvSelectedCases;
    @FXML
    private DatePicker dpStartDate;
    @FXML
    private DatePicker dpEndDate;
    @FXML
    private TableColumn<Case, String> tcSelectedCasesNumber;
    @FXML
    private TableColumn<Case, String> tcSelectedCasesStatus;
    @FXML
    private TableColumn<Case, String> tcSelectedCasesResult;
    @FXML
    private TableColumn<Case, Integer> tcSelectedCasesCosts;

    private LocalDate startDate;
    private LocalDate endDate;

    private Stage dialogStage;
    public void setAddStage(Stage addStage){
        this.dialogStage = addStage;
        dpStartDate.getEditor().setDisable(true);
        dpEndDate.getEditor().setDisable(true);
        ToggleGroup toggleGroup = new ToggleGroup();
        rbLastMonth.setToggleGroup(toggleGroup);
        rbLastYear.setToggleGroup(toggleGroup);
        rbOwnValue.setToggleGroup(toggleGroup);
    }

    public void onTimePeriodChecked(ActionEvent actionEvent) {
        RadioButton radioButton = (RadioButton) actionEvent.getSource();
        switch (radioButton.getText()){
            case "За последний месяц":{
                endDate = LocalDate.now();
                startDate = endDate.minusMonths(1);
                break;
            }
            case "За последний год":{
                endDate = LocalDate.now();
                startDate = endDate.minusYears(1);
                break;
            }
        }
    }

    public void onShowSelectedCases(ActionEvent actionEvent) {
        if (rbOwnValue.isSelected()){
            endDate = dpEndDate.getValue();
            startDate = dpStartDate.getValue();
        }
        if (startDate == null || endDate == null || startDate.isAfter(endDate)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Указан некорректный временной период!");
            alert.setResizable(false);
            alert.showAndWait();
            return;
        }
        try {
            ArrayList<Case> cases = Model.getSelectedCases(Date.valueOf(startDate), Date.valueOf(endDate));
            tvSelectedCases.setItems(FXCollections.observableArrayList(cases));
            tcSelectedCasesNumber.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().numberProperty());
            tcSelectedCasesCosts.setCellValueFactory(cellData -> cellData.getValue().costsProperty().asObject());
            tcSelectedCasesResult.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().caseResultProperty());
            tcSelectedCasesStatus.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().statusProperty());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
