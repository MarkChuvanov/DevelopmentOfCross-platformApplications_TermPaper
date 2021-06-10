package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Models.Lawyer;

public class NewLawyer {

    @FXML
    private TextField tfNewLawyerSurname;
    @FXML
    private TextField tfNewLawyerName;
    @FXML
    private TextField tfNewLawyerPatronymic;
    private Stage dialogStage;
    private Lawyer lawyer;

    public void setAddStage(Stage addStage){
        this.dialogStage = addStage;
    }

    public void setLawyer(Lawyer lawyer){
        this.lawyer = lawyer;
        tfNewLawyerSurname.setText(lawyer.getSurname());
        tfNewLawyerName.setText(lawyer.getName());
        tfNewLawyerPatronymic.setText(lawyer.getPatronymic());
    }

    public void onAddNewLawyer(ActionEvent actionEvent) {
        String surname = tfNewLawyerSurname.getText();
        String name = tfNewLawyerName.getText();
        if (surname.equals("") || surname.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Указаны некорректные данные об адвокате!");
            alert.setResizable(false);
            alert.showAndWait();
            return;
        }
        else{
            this.lawyer.setSurname(surname);
            this.lawyer.setName(name);
            this.lawyer.setPatronymic(tfNewLawyerPatronymic.getText());
            dialogStage.close();
        }
    }
}