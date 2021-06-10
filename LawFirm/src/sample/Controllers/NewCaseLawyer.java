package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sample.Model;
import sample.ViewModels.ActiveLawyer;

import java.sql.SQLException;
import java.util.ArrayList;

public class NewCaseLawyer {

    @FXML
    private ComboBox cbCaseAdditionalLawyers;

    private Stage dialogStage;

    public int getLawyerId() {
        return lawyerId;
    }

    private int lawyerId;
    ArrayList<ActiveLawyer> lawyers;

    public void setAddStage(Stage addStage, int caseId){
        this.dialogStage = addStage;
        try {
            lawyers = Model.getAdditionalLawyers(caseId);
            ArrayList<String> additionalLawyers = new ArrayList<>();
            for (int i = 0; i < lawyers.size(); i++) {
                additionalLawyers.add(lawyers.get(i).getFullName());
            }
            cbCaseAdditionalLawyers.setItems(FXCollections.observableArrayList(additionalLawyers));
            cbCaseAdditionalLawyers.getSelectionModel().select(0);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void onAddNewCaseLawyer(ActionEvent actionEvent) {
        lawyerId = lawyers.get(cbCaseAdditionalLawyers.getSelectionModel().getSelectedIndex()).getId();
        dialogStage.close();
    }
}