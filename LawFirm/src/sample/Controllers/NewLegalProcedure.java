package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Model;
import sample.Models.LegalProcedure;

import java.sql.SQLException;

public class NewLegalProcedure {

    @FXML
    private TextField tfNewLegalProcedureName;
    @FXML
    private TextField tfNewLegalProcedureCosts;
    private Stage dialogStage;
    private LegalProcedure legalProcedure;

    public void setAddStage(Stage addStage){
        this.dialogStage = addStage;
    }

    public void setLegalProcedure(LegalProcedure legalProcedure){
        this.legalProcedure = legalProcedure;
        tfNewLegalProcedureName.setText(legalProcedure.getName());
        tfNewLegalProcedureCosts.setText(Integer.toString(legalProcedure.getCosts()));
    }

    public void onAddNewLegalProcedure(ActionEvent actionEvent) {
        String name = tfNewLegalProcedureName.getText();
        String costs = tfNewLegalProcedureCosts.getText();
        if (name.equals("") || costs.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Указаны некорректные данные о юридической процедуре!");
            alert.setResizable(false);
            alert.showAndWait();
            return;
        }
        else{
            if (!costs.matches("(\\d)*") || Integer.parseInt(costs) <= 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Стоимость процедуры должна представлять собой положительное число!");
                alert.setResizable(false);
                alert.showAndWait();
                return;
            }
            else{
                this.legalProcedure.setName(name);
                this.legalProcedure.setCosts(Integer.parseInt(costs));
                dialogStage.close();
            }
        }
    }
}