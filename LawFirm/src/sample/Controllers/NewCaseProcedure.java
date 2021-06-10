package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Model;
import sample.Models.Case;
import sample.Models.CaseLegalProcedure;
import sample.Models.LegalProcedure;

import java.sql.SQLException;
import java.util.ArrayList;

public class NewCaseProcedure {

    @FXML
    private ComboBox cbLegalProcedures;
    @FXML
    private TextField tfResult;

    private Stage dialogStage;
    private CaseLegalProcedure caseLegalProcedure;
    ArrayList<LegalProcedure> procedures;

    public void setAddStage(Stage addStage){
        this.dialogStage = addStage;
    }

    public void setCaseLegalProcedure(CaseLegalProcedure procedure){
        this.caseLegalProcedure = procedure;
        tfResult.setText(procedure.getResult());
        try {
            procedures = Model.getLegalProcedures();
            ArrayList<String> legalProcedures = new ArrayList<>();
            for (int i = 0; i < procedures.size(); i++) {
                legalProcedures.add(procedures.get(i).getName());
            }
            cbLegalProcedures.setItems(FXCollections.observableArrayList(legalProcedures));
            cbLegalProcedures.getSelectionModel().select(0);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void onAddCaseLegalProcedure(ActionEvent actionEvent) {
        caseLegalProcedure.setLegalProcedureId(procedures.get(cbLegalProcedures.getSelectionModel().getSelectedIndex()).getId());
        String result = tfResult.getText();
        if (result.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Не указан результат оказания процедуры!");
            alert.setResizable(false);
            alert.showAndWait();
            return;
        }
        else{
            caseLegalProcedure.setResult(result);
        }
        dialogStage.close();
    }
}