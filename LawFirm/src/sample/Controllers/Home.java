package sample.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.Model;
import sample.Models.*;
import sample.ViewModels.StockOfActivity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Home {

    @FXML
    private TextField tfPremiumRate;
    @FXML
    private TableView<BackgroundInformationType> tvBackgroundInformation;
    @FXML
    private TableColumn<BackgroundInformationType, String> tcCaseResultType;
    @FXML
    private TableColumn<BackgroundInformationType, Integer> tcPremiumRate;
    private ObservableList backgroundInformation;

    @FXML
    private MenuItem miAddAdditionalLawyer;
    @FXML
    private MenuItem miAddCaseLegalProcedure;

    @FXML
    public TableView<StockOfActivity> tvStockOActivity;
    @FXML
    public TableColumn<StockOfActivity, String> tcIndicator;
    @FXML
    public TableColumn<StockOfActivity, Integer> tcValue;
    private ObservableList stockOfActivity;

    @FXML
    private TableView<Case> tvLawyerCases;
    @FXML
    private TableColumn<Case, String> tcLawyerCaseNumber;
    @FXML
    private TableColumn<Case, String> tcLawyerCaseOpeningDate;
    @FXML
    private TableColumn<Case, String> tcLawyerCaseClosingDate;
    @FXML
    private TableColumn<Case, String> tcLawyerCaseStatus;
    @FXML
    private TableColumn<Case, String> tcLawyerCaseResult;
    @FXML
    private TableColumn<Case, Integer> tcLawyerCaseCosts;
    private ObservableList lawyerCases;

    @FXML
    private TextField tfLawyerSurname;
    @FXML
    private TextField tfLawyerName;
    @FXML
    private TextField tfLawyerPatronymic;
    @FXML
    private ComboBox cbLawyerStatus;
    @FXML
    private Button bEditLawyer;

    @FXML
    private TableView<Lawyer> tvLawyers;
    @FXML
    private TableColumn<Lawyer, String> tcLawyerSurname;
    @FXML
    private TableColumn<Lawyer, String> tcLawyerName;
    @FXML
    private TableColumn<Lawyer, String> tcLawyerPatronymic;
    @FXML
    private TableColumn<Lawyer, String> tcLawyerLengthOfService;
    private ObservableList lawyers;

    @FXML
    private TableView<LegalProcedure> tvLegalProcedures;
    @FXML
    private TableColumn<LegalProcedure, String> tcLegalProcedureName;
    @FXML
    private TableColumn<LegalProcedure, Integer> tcLegalProcedureCosts;
    @FXML
    private TextField tfLegalProcedureName;
    @FXML
    private TextField tfLegalProcedureCosts;
    private ObservableList legalProcedures;

    @FXML
    private TableView<Case> tvClientCases;
    @FXML
    public TableColumn<Case, String> tcClientCaseNumber;
    @FXML
    public TableColumn<Case, String> tcClientCaseOpeningDate;
    @FXML
    public TableColumn<Case, String> tcClientCaseClosingDate;
    @FXML
    public TableColumn<Case, String> tcClientCaseStatus;
    @FXML
    public TableColumn<Case, String> tcClientCaseResult;
    @FXML
    public TableColumn<Case, Integer> tcClientCaseCosts;
    private ObservableList clientCases;
    @FXML
    private TextField tfClientSurname;
    @FXML
    private TextField tfClientName;
    @FXML
    private TextField tfClientPatronymic;
    @FXML
    private TextField tfClientDateOfBirth;
    @FXML
    private TextField tfClientPassportSeries;
    @FXML
    private TextField tfClientPassportNumber;
    @FXML
    private TextArea taClientPlaceOfResidence;

    @FXML
    private TableView<Client> tvClients;
    @FXML
    private TableColumn<Client, String> tcClientSurname;
    @FXML
    private TableColumn<Client, String> tcClientName;
    @FXML
    private TableColumn<Client, String> tcClientPatronymic;
    @FXML
    private TableColumn<Client, String> tcClientPassportSeries;
    @FXML
    private TableColumn<Client, String> tcClientPassportNumber;
    private ObservableList clients;

    @FXML
    private Button bEditCase;

    @FXML
    private TableView<Case> tvCases;
    @FXML
    private TableColumn<Case, String> tcCaseNumber;
    @FXML
    private TableColumn<Case, String> tcCaseOpeningDate;
    @FXML
    private TableColumn<Case, String> tcCaseClosingDate;
    @FXML
    private TableColumn<Case, String> tcCaseStatus;
    @FXML
    private TableColumn<Case, String> tcCaseResult;
    @FXML
    private TableColumn<Case, Integer> tcCaseCosts;
    private ObservableList<Case> cases;

    @FXML
    private TableView<CaseLegalProcedure> tvCaseLegalProcedures;
    @FXML
    private TableColumn<CaseLegalProcedure, String> tcCaseLegalProcedureDate;
    @FXML
    private TableColumn<CaseLegalProcedure, String> tcCaseLegalProcedureResult;
    private ObservableList<CaseLegalProcedure> caseLegalProcedures;

    @FXML
    private TextField tfCaseNumber;
    @FXML
    private TextField tfCaseOpeningDate;
    @FXML
    private TextField tfCaseClosingDate;
    @FXML
    private TextField tfCaseStatus;
    @FXML
    private ComboBox cbCaseResult;
    @FXML
    private TextField tfCaseCosts;
    ObservableList<String> caseResults;

    @FXML
    private TextField tfCaseLegalProcedureDate;
    @FXML
    private TextArea taCaseLegalProcedureResult;
    @FXML
    private ListView lvCaseLawyers;

    public Home() {
        try{
            Model.setConnectionToDB();
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
            System.out.println("Driver loading failed!");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Connection error!");
        }
    }

    @FXML
    void initialize(){
        try {
            cases = FXCollections.observableArrayList(Model.getCases());
            caseResults = FXCollections.observableArrayList(Model.getCaseResults());
            clients = FXCollections.observableArrayList(Model.getClients());
            legalProcedures = FXCollections.observableArrayList(Model.getLegalProcedures());
            lawyers = FXCollections.observableArrayList(Model.getLawyers());
            ArrayList<String> lawyerStatus = new ArrayList(Arrays.asList("Работает", "Не работает"));
            cbLawyerStatus.setItems(FXCollections.observableArrayList(lawyerStatus));
            backgroundInformation = FXCollections.observableArrayList(Model.getBackgroundInformation());
            tvBackgroundInformation.setItems(backgroundInformation);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        tcCaseResultType.setCellValueFactory(backgroundInformationTypeStringCellDataFeatures -> backgroundInformationTypeStringCellDataFeatures.getValue().caseResultTypeProperty());
        tcPremiumRate.setCellValueFactory(cellData -> cellData.getValue().premiumRateProperty().asObject());
        tvBackgroundInformation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BackgroundInformationType>() {
            @Override
            public void changed(ObservableValue<? extends BackgroundInformationType> observableValue, BackgroundInformationType backgroundInformationType, BackgroundInformationType t1) {
                if (t1 != null){
                    type = t1;
                    tfPremiumRate.setText(Integer.toString(t1.getPremiumRate()));
                }
            }
        });
        tvBackgroundInformation.getSelectionModel().select(0);
        cbCaseResult.setItems(caseResults);
        tcCaseNumber.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().numberProperty());
        tcCaseOpeningDate.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().openingDatePropertyProperty());
        tcCaseClosingDate.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().closingDatePropertyProperty());
        tcCaseStatus.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().statusProperty());
        tcCaseResult.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().caseResultProperty());
        tcCaseCosts.setCellValueFactory(cellData -> cellData.getValue().costsProperty().asObject());
        tvCases.setItems(cases);
        tvCases.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Case>() {
            @Override
            public void changed(ObservableValue<? extends Case> observableValue, Case aCase, Case t1) {
                if (t1 != null){
                    doShowCase(t1);
                }
            }
        });
        tvCases.getSelectionModel().select(0);
        tvCases.getSelectionModel().focus(0);
        tcClientSurname.setCellValueFactory(clientStringCellDataFeatures -> clientStringCellDataFeatures.getValue().surnameProperty());
        tcClientName.setCellValueFactory((clientStringCellDataFeatures -> clientStringCellDataFeatures.getValue().nameProperty()));
        tcClientPatronymic.setCellValueFactory(clientStringCellDataFeatures -> clientStringCellDataFeatures.getValue().patronymicProperty());
        tcClientPassportSeries.setCellValueFactory(clientStringCellDataFeatures -> clientStringCellDataFeatures.getValue().passportSeriesProperty());
        tcClientPassportNumber.setCellValueFactory(clientStringCellDataFeatures -> clientStringCellDataFeatures.getValue().passportNumberProperty());
        tvClients.setItems(clients);
        tvClients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Client>() {
            @Override
            public void changed(ObservableValue<? extends Client> observableValue, Client client, Client t1) {
                if (t1 != null){
                    doShowClient(t1);
                }
            }
        });
        tvClients.getSelectionModel().select(0);
        tvClients.getSelectionModel().focus(0);
        tvLegalProcedures.setItems(legalProcedures);
        tcLegalProcedureName.setCellValueFactory(legalProcedureStringCellDataFeatures -> legalProcedureStringCellDataFeatures.getValue().nameProperty());
        tcLegalProcedureCosts.setCellValueFactory(legalProcedureIntegerCellDataFeatures -> legalProcedureIntegerCellDataFeatures.getValue().costsProperty().asObject());
        tvLegalProcedures.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LegalProcedure>() {
            @Override
            public void changed(ObservableValue<? extends LegalProcedure> observableValue, LegalProcedure legalProcedure, LegalProcedure t1) {
                if (t1 != null){
                    doShowLegalProcedure(t1);
                }
            }
        });
        tvLegalProcedures.getSelectionModel().select(0);
        tvLegalProcedures.getSelectionModel().focus(0);
        tvLawyers.setItems(lawyers);
        tcLawyerSurname.setCellValueFactory(lawyerStringCellDataFeatures -> lawyerStringCellDataFeatures.getValue().surnameProperty());
        tcLawyerName.setCellValueFactory(lawyerStringCellDataFeatures -> lawyerStringCellDataFeatures.getValue().nameProperty());
        tcLawyerPatronymic.setCellValueFactory(lawyerStringCellDataFeatures -> lawyerStringCellDataFeatures.getValue().patronymicProperty());
        tcLawyerLengthOfService.setCellValueFactory(lawyerStringCellDataFeatures -> lawyerStringCellDataFeatures.getValue().lengthOfServiceProperty());
        tvLawyers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Lawyer>() {
            @Override
            public void changed(ObservableValue<? extends Lawyer> observableValue, Lawyer lawyer, Lawyer t1) {
                if (t1 != null){
                    doShowLawyer(t1);
                }
            }
        });
        tvLawyers.getSelectionModel().select(0);
        tvLawyers.getSelectionModel().focus(0);
    }

    private void doShowLawyer(Lawyer lawyer){
        this.lawyer = lawyer;
        tfLawyerSurname.setText(lawyer.getSurname());
        tfLawyerName.setText(lawyer.getName());
        tfLawyerPatronymic.setText(lawyer.getPatronymic());
        if (lawyer.isWorked()){
            cbLawyerStatus.getSelectionModel().select(0);
            bEditLawyer.setDisable(false);
        }
        else{
            cbLawyerStatus.getSelectionModel().select(1);
            bEditLawyer.setDisable(true);
        }
        try {
            lawyerCases = FXCollections.observableArrayList(Model.getLawyerCases(lawyer.getId()));
            tvLawyerCases.setItems(lawyerCases);
            tcLawyerCaseNumber.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().numberProperty());
            tcLawyerCaseOpeningDate.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().openingDatePropertyProperty());
            tcLawyerCaseClosingDate.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().closingDatePropertyProperty());
            tcLawyerCaseStatus.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().statusProperty());
            tcLawyerCaseResult.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().caseResultProperty());
            tcLawyerCaseCosts.setCellValueFactory(cellData -> cellData.getValue().costsProperty().asObject());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void doShowLegalProcedure(LegalProcedure legalProcedure){
        this.legalProcedure = legalProcedure;
        tfLegalProcedureName.setText(legalProcedure.getName());
        tfLegalProcedureCosts.setText(Integer.toString(legalProcedure.getCosts()));
    }

    private void doShowClient(Client client){
        this.client = client;
        tfClientSurname.setText(client.getSurname());
        tfClientName.setText(client.getName());
        tfClientPatronymic.setText(client.getPatronymic());
        tfClientPassportSeries.setText(client.getPassportSeries());
        tfClientPassportNumber.setText(client.getPassportNumber());
        tfClientDateOfBirth.setText(client.getDateOfBirthProperty());
        taClientPlaceOfResidence.setText(client.getPlaceOfResidence());
        try {
            tvClientCases.setItems(FXCollections.observableList(Model.getClientCases(client.getId())));
            tcClientCaseNumber.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().numberProperty());
            tcClientCaseOpeningDate.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().openingDatePropertyProperty());
            tcClientCaseClosingDate.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().closingDatePropertyProperty());
            tcClientCaseStatus.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().statusProperty());
            tcClientCaseResult.setCellValueFactory(caseStringCellDataFeatures -> caseStringCellDataFeatures.getValue().caseResultProperty());
            tcClientCaseCosts.setCellValueFactory(cellData -> cellData.getValue().costsProperty().asObject());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void doShowCase(Case element){
        this.element = element;
        tfCaseNumber.setText(element.getNumber());
        tfCaseOpeningDate.setText(element.getOpeningDateProperty());
        tfCaseClosingDate.setText(element.getClosingDateProperty());
        tfCaseStatus.setText(element.getStatus());
        tfCaseCosts.setText(Integer.toString(element.getCosts()));
        cbCaseResult.getSelectionModel().select(element.getCaseResult());
        if (tfCaseStatus.getText().equals("Закрыто")){
            bEditCase.setDisable(true);
            miAddAdditionalLawyer.setDisable(true);
            miAddCaseLegalProcedure.setDisable(true);
        }
        else{
            bEditCase.setDisable(false);
            miAddAdditionalLawyer.setDisable(false);
            miAddCaseLegalProcedure.setDisable(false);
        }
        try {
            lvCaseLawyers.setItems(FXCollections.observableList(Model.getCaseLawyers(element.getId())));
            caseLegalProcedures = FXCollections.observableArrayList(Model.getCaseLegalProcedures(element.getId()));
            if (caseLegalProcedures.size() == 0){
                tfCaseLegalProcedureDate.setText("");
                taCaseLegalProcedureResult.setText("");
            }
            tcCaseLegalProcedureDate.setCellValueFactory(caseLegalProcedureStringCellDataFeatures -> caseLegalProcedureStringCellDataFeatures.getValue().datePropertyProperty());
            tcCaseLegalProcedureResult.setCellValueFactory(caseLegalProcedureStringCellDataFeatures -> caseLegalProcedureStringCellDataFeatures.getValue().resultProperty());
            tvCaseLegalProcedures.setItems(caseLegalProcedures);
            tvCaseLegalProcedures.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CaseLegalProcedure>() {
                @Override
                public void changed(ObservableValue<? extends CaseLegalProcedure> observableValue, CaseLegalProcedure caseLegalProcedure, CaseLegalProcedure t1) {
                    if (t1 != null){
                        tfCaseLegalProcedureDate.setText(t1.getDateProperty());
                        taCaseLegalProcedureResult.setText(t1.getResult());
                    }
                }
            });
            tvCaseLegalProcedures.getSelectionModel().select(0);
            tvCaseLegalProcedures.getSelectionModel().focus(0);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void onAddCase(ActionEvent actionEvent) throws IOException {
        Case newCase = new Case();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("Views/NewCase.fxml"));
        Parent page = loader.load();
        Stage addStage = new Stage();
        addStage.setTitle("Добавить новое дело");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(Main.getPrimaryStage());
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        NewCase controller = loader.getController();
        controller.setAddStage(addStage);
        controller.setNewCase(newCase);
        addStage.setResizable(false);
        addStage.showAndWait();
        try {
            Model.doAddNewCase(newCase, controller.getLawyerId());
            cases.add(newCase);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Case element;

    public void onEditCase(ActionEvent actionEvent){
        int caseResultId = cbCaseResult.getSelectionModel().getSelectedIndex();
        try {
            Model.doEditDataAboutCase(this.element, caseResultId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        doShowCase(this.element);
    }

    private Client client;

    public void onEditClient(ActionEvent actionEvent) {
        if (tfClientSurname.getText().equals("") || tfClientName.getText().equals("") || tfClientName.getText().equals("") || tfClientPassportNumber.getText().equals("") || tfClientPassportSeries.getText().equals("") || taClientPlaceOfResidence.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Указаны некорректные персональные данные клиента!");
            alert.setResizable(false);
            alert.showAndWait();
            return;
        }
        else{
            if(!tfClientPassportNumber.getText().matches("(\\d){6}") || !tfClientPassportSeries.getText().matches("(\\d){4}")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Серия и номер паспорта должны состоять из четырех и шести цифр соответственно!");
                alert.setResizable(false);
                alert.showAndWait();
                return;
            }
            else{
                this.client.setSurname(tfClientSurname.getText());
                this.client.setName(tfClientName.getText());
                this.client.setPatronymic(tfClientPatronymic.getText());
                this.client.setPassportSeries(tfClientPassportSeries.getText());
                this.client.setPassportNumber(tfClientPassportNumber.getText());
                this.client.setPlaceOfResidence(taClientPlaceOfResidence.getText());
                try {
                    Model.doEditDataAboutClient(this.client);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private LegalProcedure legalProcedure;

    public void onEditLegalProcedure(ActionEvent actionEvent) {
        if (tfLegalProcedureName.getText().equals("") || tfLegalProcedureCosts.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Указаны некорректные данные о юридической процедуре!");
            alert.setResizable(false);
            alert.showAndWait();
            return;
        }
        else{
            if (!tfLegalProcedureCosts.getText().matches("(\\d)*") || Integer.parseInt(tfLegalProcedureCosts.getText()) <= 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Стоимость процедуры должна представлять собой положительное число!");
                alert.setResizable(false);
                alert.showAndWait();
                return;
            }
            else{
                this.legalProcedure.setName(tfLegalProcedureName.getText());
                this.legalProcedure.setCosts(Integer.parseInt(tfLegalProcedureCosts.getText()));
                try {
                    Model.doEditDataAboutLegalProcedure(this.legalProcedure);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private Lawyer lawyer;

    public void onEditLawyer(ActionEvent actionEvent) {
        if (tfLawyerSurname.getText().equals("") || tfLawyerName.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Указаны некорректные персональные данные адвоката!");
            alert.setResizable(false);
            alert.showAndWait();
            return;
        }
        else{
            this.lawyer.setSurname(tfLawyerSurname.getText());
            this.lawyer.setName(tfLawyerName.getText());
            this.lawyer.setPatronymic(tfLawyerPatronymic.getText());
            try {
                Model.doEditDataAboutLawyer(this.lawyer, cbLawyerStatus.getSelectionModel().getSelectedIndex());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void onTakeStockOfActivity(ActionEvent actionEvent) {
        Lawyer lawyer = tvLawyers.getSelectionModel().getSelectedItem();
        try {
            stockOfActivity = FXCollections.observableArrayList(Model.getStockOfActivity(lawyer.getId()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        tvStockOActivity.setItems(stockOfActivity);
        tcIndicator.setCellValueFactory(stockOfActivityStringCellDataFeatures -> stockOfActivityStringCellDataFeatures.getValue().indicatorProperty());
        tcValue.setCellValueFactory(stockOfActivityDoubleCellDataFeatures -> stockOfActivityDoubleCellDataFeatures.getValue().valueProperty().asObject());
    }

    public void onAddLegalProcedure(ActionEvent actionEvent) throws IOException {
        LegalProcedure legalProcedure = new LegalProcedure();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("Views/NewLegalProcedure.fxml"));
        Parent page = loader.load();
        Stage addStage = new Stage();
        addStage.setTitle("Добавить новую процедуру в прейскурант");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(Main.getPrimaryStage());
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        NewLegalProcedure controller = loader.getController();
        controller.setAddStage(addStage);
        controller.setLegalProcedure(legalProcedure);
        addStage.setResizable(false);
        addStage.showAndWait();
        try {
            Model.doAddNewLegalProcedure(legalProcedure);
            if (!legalProcedure.getName().equals("")){
                legalProcedures.add(legalProcedure);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void onAddLawyer(ActionEvent actionEvent) throws IOException {
        Lawyer lawyer = new Lawyer();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("Views/NewLawyer.fxml"));
        Parent page = loader.load();
        Stage addStage = new Stage();
        addStage.setTitle("Добавить нового адвоката");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(Main.getPrimaryStage());
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        NewLawyer controller = loader.getController();
        controller.setAddStage(addStage);
        controller.setLawyer(lawyer);
        addStage.setResizable(false);
        addStage.showAndWait();
        try {
            Model.doAddNewLawyer(lawyer);
            lawyers.add(lawyer);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void onAddClient(ActionEvent actionEvent) throws IOException {
        Client client = new Client();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("Views/NewClient.fxml"));
        Parent page = loader.load();
        Stage addStage = new Stage();
        addStage.setTitle("Добавить нового клиента");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(Main.getPrimaryStage());
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        NewClient controller = loader.getController();
        controller.setAddStage(addStage);
        controller.setClient(client);
        addStage.setResizable(false);
        addStage.showAndWait();
        try {
            Model.doAddNewClient(client);
            clients.add(client);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void onAddCaseLegalProcedure(ActionEvent actionEvent) throws IOException {
        CaseLegalProcedure caseProcedure = new CaseLegalProcedure();
        Case element = tvCases.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("Views/NewCaseProcedure.fxml"));
        Parent page = loader.load();
        Stage addStage = new Stage();
        addStage.setTitle("Добавить новую процедуру к делу");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(Main.getPrimaryStage());
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        NewCaseProcedure controller = loader.getController();
        controller.setAddStage(addStage);
        controller.setCaseLegalProcedure(caseProcedure);
        addStage.setResizable(false);
        addStage.showAndWait();
        try {
            Model.doAddNewCaseLegalProcedure(caseProcedure, element.getId());
            doShowCase(element);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void onAddAdditionalLawyer(ActionEvent actionEvent) throws IOException {
        Case element = tvCases.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("Views/NewCaseLawyer.fxml"));
        Parent page = loader.load();
        Stage addStage = new Stage();
        addStage.setTitle("Назначить дополнительного адвоката");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(Main.getPrimaryStage());
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        NewCaseLawyer controller = loader.getController();
        controller.setAddStage(addStage, element.getId());
        addStage.setResizable(false);
        addStage.showAndWait();
        try {
            Model.doAddLawyerToCase(element.getId(), controller.getLawyerId());
            doShowCase(element);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void onSelectCases(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("Views/SelectedCases.fxml"));
        Parent page = loader.load();
        Stage addStage = new Stage();
        addStage.setTitle("Отобрать дела за выбранный период");
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.initOwner(Main.getPrimaryStage());
        Scene scene = new Scene(page);
        addStage.setScene(scene);
        SelectedCases controller = loader.getController();
        controller.setAddStage(addStage);
        addStage.setResizable(false);
        addStage.showAndWait();
    }

    BackgroundInformationType type;

    public void onEditBackgroundInformationType(ActionEvent actionEvent) {
        String rate = tfPremiumRate.getText();
        if (!rate.matches("(\\d)*")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Стоимость должна состоять из цифр!");
            alert.setResizable(false);
            alert.showAndWait();
            return;
        }
        int premiumRate = Integer.parseInt(rate);
        if (premiumRate <= 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Указана некорректная стоимость!");
            alert.setResizable(false);
            alert.showAndWait();
            return;
        }
        try {
            this.type.setPremiumRate(premiumRate);
            Model.doEditBackgroundInformation(tvBackgroundInformation.getSelectionModel().getSelectedItem().getId(), premiumRate);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}