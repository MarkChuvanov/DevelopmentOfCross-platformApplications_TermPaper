package sample;

import sample.Models.*;
import sample.ViewModels.ActiveLawyer;
import sample.ViewModels.StockOfActivity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private final static String DB_URL = "jdbc:mysql://localhost:3306/LawFirm";
    private final static String LOGIN = "root";
    private final static String PASSWORD = "root";

    static Connection connection;

    public static void setConnectionToDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
    }

    public static boolean isUniqueNumber(String number) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cases WHERE number = ?");
        preparedStatement.setString(1, number);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return false;
        }
        else{
            return true;
        }
    }

    public static int getClientId(String passportSeries, String passportNumber) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM clients WHERE PassportSeries = ? AND PassportNumber = ?");
        preparedStatement.setString(1, passportSeries);
        preparedStatement.setString(2, passportNumber);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt("id");
        }
        else{
            return -1;
        }
    }

    public static void doAddNewCase(Case newCase, int lawyerId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT cases (number, clientId, openingDate, closingDate, status, caseResultId, costs) VALUES (?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, newCase.getNumber());
        preparedStatement.setInt(2, newCase.getClientId());
        java.sql.Date date = new Date(System.currentTimeMillis());
        preparedStatement.setDate(3, date);
        preparedStatement.setDate(4, null);
        preparedStatement.setInt(5, 1);
        PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT id FROM CaseResults WHERE name = ?");
        preparedStatement1.setString(1, newCase.getCaseResult());
        ResultSet resultSet = preparedStatement1.executeQuery();
        resultSet.next();
        preparedStatement.setInt(6, resultSet.getInt(1));
        preparedStatement.setInt(7, newCase.getCosts());
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("SELECT id FROM cases WHERE number = ?");
        preparedStatement.setString(1, newCase.getNumber());
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        newCase.setId(resultSet.getInt("id"));

        PreparedStatement statement = connection.prepareStatement("INSERT caseLawyers (caseId, lawyerId) VALUES (?, ?)");
        statement.setInt(1, newCase.getId());
        statement.setInt(2, lawyerId);
        statement.executeUpdate();

        newCase.setOpeningDateProperty(new Date(System.currentTimeMillis()));
        newCase.setStatus("В работе");
    }

    public static ArrayList<Case> getCases() throws SQLException {
        ArrayList<Case> cases = new ArrayList<Case>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM cases");
        while (resultSet.next()){
            Case element = new Case();
            element.setId(resultSet.getInt("id"));
            element.setNumber(resultSet.getString("number"));
            element.setClientId(resultSet.getInt("clientId"));
            element.setOpeningDate(resultSet.getDate("openingDate"));
            element.setOpeningDateProperty(element.getOpeningDate());
            element.setClosingDate(resultSet.getDate("closingDate"));
            element.setClosingDateProperty(element.getClosingDate());
            if (resultSet.getInt("status") == 0){
                element.setStatus("Закрыто");
            }
            else{
                element.setStatus("В работе");
            }
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM CaseResults WHERE id = ?");
            preparedStatement.setInt(1, resultSet.getInt("caseResultId"));
            ResultSet resultSet1 = preparedStatement.executeQuery();
            resultSet1.next();
            element.setCaseResult(resultSet1.getString("name"));
            element.setCosts(resultSet.getInt("costs"));
            cases.add(element);
        }
        return cases;
    }

    public static ArrayList<String> getCaseResults() throws SQLException {
        ArrayList<String> results = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT name FROM CaseResults");
        while (resultSet.next()){
            results.add(resultSet.getString("name"));
        }
        return results;
    }

    public static ArrayList<String> getCaseLawyers(int caseId) throws SQLException {
        ArrayList<String> caseLawyers = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT CONCAT (Lawyers.surname, ' ', Lawyers.name, ' ', Lawyers.patronymic) FROM CaseLawyers, Cases, Lawyers WHERE Cases.Id = ? AND Cases.Id = CaseLawyers.CaseId AND Lawyers.Id = CaseLawyers.LawyerId");
        preparedStatement.setInt(1, caseId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            caseLawyers.add(resultSet.getString(1));
        }
        return caseLawyers;
    }

    public static ArrayList<CaseLegalProcedure> getCaseLegalProcedures(int caseId) throws SQLException {
        ArrayList<CaseLegalProcedure> caseLegalProcedures = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CaseLegalProcedures WHERE caseId = ?");
        preparedStatement.setInt(1, caseId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            CaseLegalProcedure caseLegalProcedure = new CaseLegalProcedure();
            caseLegalProcedure.setId(resultSet.getInt("id"));
            caseLegalProcedure.setCaseId(resultSet.getInt("caseId"));
            caseLegalProcedure.setLegalProcedureId(resultSet.getInt("legalProcedureId"));
            caseLegalProcedure.setDate(resultSet.getDate("date"));
            caseLegalProcedure.setDateProperty(caseLegalProcedure.getDate());
            caseLegalProcedure.setResult(resultSet.getString("result"));
            caseLegalProcedures.add(caseLegalProcedure);
        }
        return caseLegalProcedures;
    }

    public static ArrayList<Client> getClients() throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Clients");
        while (resultSet.next()){
            Client client = new Client();
            client.setId(resultSet.getInt("id"));
            client.setSurname(resultSet.getString("surname"));
            client.setName(resultSet.getString("name"));
            client.setPatronymic(resultSet.getString("patronymic"));
            client.setPassportSeries(resultSet.getString("passportSeries"));
            client.setPassportNumber(resultSet.getString("passportNumber"));
            client.setDateOfBirth(resultSet.getDate("dateOfBirth"));
            client.setDateOfBirthProperty(client.getDateOfBirth());
            client.setPlaceOfResidence(resultSet.getString("placeOfResidence"));
            clients.add(client);
        }
        return clients;
    }

    public static ArrayList<Case> getClientCases(int clientId) throws SQLException {
        ArrayList<Case> cases = new ArrayList<Case>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM cases WHERE clientId = ?");
        statement.setInt(1, clientId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            Case element = new Case();
            element.setId(resultSet.getInt("id"));
            element.setNumber(resultSet.getString("number"));
            element.setClientId(resultSet.getInt("clientId"));
            element.setOpeningDate(resultSet.getDate("openingDate"));
            element.setOpeningDateProperty(element.getOpeningDate());
            element.setClosingDate(resultSet.getDate("closingDate"));
            element.setClosingDateProperty(element.getClosingDate());
            if (resultSet.getInt("status") == 0){
                element.setStatus("Закрыто");
            }
            else{
                element.setStatus("В работе");
            }
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM CaseResults WHERE id = ?");
            preparedStatement.setInt(1, resultSet.getInt("caseResultId"));
            ResultSet resultSet1 = preparedStatement.executeQuery();
            resultSet1.next();
            element.setCaseResult(resultSet1.getString("name"));
            element.setCosts(resultSet.getInt("costs"));
            cases.add(element);
        }
        return cases;
    }

    public static ArrayList<LegalProcedure> getLegalProcedures() throws SQLException {
        ArrayList<LegalProcedure> legalProcedures = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM LegalProcedures");
        while (resultSet.next()){
            LegalProcedure legalProcedure = new LegalProcedure();
            legalProcedure.setId(resultSet.getInt("id"));
            legalProcedure.setName(resultSet.getString("name"));
            legalProcedure.setCosts(resultSet.getInt("costs"));
            legalProcedures.add(legalProcedure);
        }
        return legalProcedures;
    }

    public static ArrayList<Lawyer> getLawyers() throws SQLException {
        ArrayList<Lawyer> lawyers = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Lawyers");
        while (resultSet.next()){
            Lawyer lawyer = new Lawyer();
            lawyer.setId(resultSet.getInt("id"));
            lawyer.setSurname(resultSet.getString("surname"));
            lawyer.setName(resultSet.getString("name"));
            lawyer.setPatronymic(resultSet.getString("patronymic"));
            int i = resultSet.getInt("status");
            if (i == 1){
                lawyer.setWorked(true);
            }
            else{
                lawyer.setWorked(false);
            }
            lawyer.setStartDate(resultSet.getDate("startDate"));
            lawyer.setLengthOfService(lawyer.getStartDate());
            lawyers.add(lawyer);
        }
        return lawyers;
    }

    public static ArrayList<Case> getLawyerCases(int lawyerId) throws SQLException {
        ArrayList<Case> cases = new ArrayList<Case>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM cases, caseLawyers WHERE cases.id = caseLawyers.caseId AND caseLawyers.LawyerId = ?");
        statement.setInt(1, lawyerId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            Case element = new Case();
            element.setId(resultSet.getInt("id"));
            element.setNumber(resultSet.getString("number"));
            element.setClientId(resultSet.getInt("clientId"));
            element.setOpeningDate(resultSet.getDate("openingDate"));
            element.setOpeningDateProperty(element.getOpeningDate());
            element.setClosingDate(resultSet.getDate("closingDate"));
            element.setClosingDateProperty(element.getClosingDate());
            if (resultSet.getInt("status") == 0){
                element.setStatus("Закрыто");
            }
            else{
                element.setStatus("В работе");
            }
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM CaseResults WHERE id = ?");
            preparedStatement.setInt(1, resultSet.getInt("caseResultId"));
            ResultSet resultSet1 = preparedStatement.executeQuery();
            resultSet1.next();
            element.setCaseResult(resultSet1.getString("name"));
            element.setCosts(resultSet.getInt("costs"));
            cases.add(element);
        }
        return cases;
    }

    public static ArrayList<StockOfActivity> getStockOfActivity(int lawyerId) throws SQLException {
        ArrayList<StockOfActivity> stocks = new ArrayList<>();

        StockOfActivity stockOfActivity = new StockOfActivity();
        stockOfActivity.setIndicator("Общее количество дел");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(LawyerId) FROM caseLawyers WHERE LawyerId = ?");
        preparedStatement.setInt(1, lawyerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        stockOfActivity.setValue(resultSet.getInt(1));
        stocks.add(stockOfActivity);

        StockOfActivity stockOfActivity1 = new StockOfActivity();
        stockOfActivity1.setIndicator("Количество закрытых дел");
        preparedStatement = connection.prepareStatement("SELECT count(LawyerId) FROM CaseLawyers WHERE CaseLawyers.LawyerId = ? AND (CaseLawyers.CaseId IN (SELECT Id FROM Cases WHERE status = 0))");
        preparedStatement.setInt(1, lawyerId);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        stockOfActivity1.setValue(resultSet.getInt(1));
        stocks.add(stockOfActivity1);

        StockOfActivity stockOfActivity2 = new StockOfActivity();
        stockOfActivity2.setIndicator("Процент выигранных дел");
        preparedStatement = connection.prepareStatement("SELECT count(LawyerId) FROM CaseLawyers WHERE CaseLawyers.LawyerId = ? AND (CaseLawyers.CaseId IN (SELECT Id FROM Cases WHERE CaseResultId = 1))");
        preparedStatement.setInt(1, lawyerId);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        stockOfActivity2.setValue((double)resultSet.getInt(1) / stockOfActivity1.getValue() * 100);
        stocks.add(stockOfActivity2);
        return stocks;
    }

    public static void doEditDataAboutCase(Case element, int caseResultId) throws SQLException {
        if (caseResultId + 1 != 4){
            int costs = getCaseCosts(element, caseResultId + 1);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cases SET status = 0, caseResultId = ?, costs = ?, closingDate = ? WHERE id = ?");
            preparedStatement.setInt(1, caseResultId + 1);
            preparedStatement.setInt(2, costs);
            preparedStatement.setDate(3, new Date(System.currentTimeMillis()));
            preparedStatement.setInt(4, element.getId());
            preparedStatement.executeUpdate();
            element.setCosts(costs);
            element.setStatus("Закрыто");
            preparedStatement = connection.prepareStatement("SELECT name FROM caseResults WHERE id = ?");
            preparedStatement.setInt(1, caseResultId + 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            element.setCaseResult(resultSet.getString("name"));
            element.setClosingDate(new Date(System.currentTimeMillis()));
            element.setClosingDateProperty(element.getClosingDate());
        }
    }

    private static int getCaseCosts(Case element, int caseResultId) throws SQLException {
        int costs = 0;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT premiumRate FROM backgroundInformation WHERE caseResultId = ?");
        preparedStatement.setInt(1, caseResultId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int rate = resultSet.getInt(1);
        switch (caseResultId){
            case 1:{
                costs = getCostsAllCaseProcedures(element.getId()) + rate;
                break;
            }
            case 2:{
                costs = getCostsAllCaseProcedures(element.getId());
                break;
            }
            case 3:{
                costs = rate;
                break;
            }
        }
        return costs;
    }

    private static int getCostsAllCaseProcedures(int caseId) throws SQLException {
        int costs = 0;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT Costs FROM LegalProcedures WHERE Id IN (SELECT legalProcedureId FROM CaseLegalProcedures WHERE CaseId = ?)");
        preparedStatement.setInt(1, caseId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            costs += resultSet.getInt(1);
        }
        return costs;
    }

    public static void doEditDataAboutClient(Client client) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE clients SET surname = ?, name = ?, patronymic = ?, passportSeries = ?, passportNumber = ?, placeOfResidence = ? WHERE id = ?");
        preparedStatement.setString(1, client.getSurname());
        preparedStatement.setString(2, client.getName());
        preparedStatement.setString(3, client.getPatronymic());
        preparedStatement.setString(4, client.getPassportSeries());
        preparedStatement.setString(5, client.getPassportNumber());
        preparedStatement.setString(6, client.getPlaceOfResidence());
        preparedStatement.setInt(7, client.getId());
        preparedStatement.executeUpdate();
    }

    public static void doEditDataAboutLawyer(Lawyer lawyer, int lawyerStatus) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE lawyers SET surname = ?, name = ?, status = ?, patronymic = ? WHERE id = ?");
        preparedStatement.setString(1, lawyer.getSurname());
        preparedStatement.setString(2, lawyer.getName());
        preparedStatement.setString(4, lawyer.getPatronymic());
        if (lawyerStatus != 0){
            lawyer.setWorked(false);
            preparedStatement.setInt(3, 0);
        }
        preparedStatement.setInt(5, lawyer.getId());
        preparedStatement.executeUpdate();
    }

    public static void doEditDataAboutLegalProcedure(LegalProcedure legalProcedure) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE lawyers SET name = ?, costs = ? WHERE id = ?");
        preparedStatement.setString(1, legalProcedure.getName());
        preparedStatement.setInt(2, legalProcedure.getCosts());
        preparedStatement.setInt(3, legalProcedure.getId());
        preparedStatement.executeUpdate();
    }

    public static void doAddNewLegalProcedure(LegalProcedure legalProcedure) throws SQLException {
        if (legalProcedure.getName().equals("")){
            return;
        }
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT legalProcedures (name, costs) VALUES (?, ?)");
        preparedStatement.setString(1, legalProcedure.getName());
        preparedStatement.setInt(2, legalProcedure.getCosts());
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("SELECT max(id) FROM legalProcedures");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        legalProcedure.setId(resultSet.getInt(1));
    }

    public static void doAddNewLawyer(Lawyer lawyer) throws SQLException {
        if (lawyer.getSurname().equals("")){
            return;
        }
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT lawyers (surname, name, patronymic, startDate, status) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, lawyer.getSurname());
        preparedStatement.setString(2, lawyer.getName());
        preparedStatement.setString(3, lawyer.getPatronymic());
        preparedStatement.setDate(4, new Date(System.currentTimeMillis()));
        preparedStatement.setInt(5, 1);
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("SELECT max(id) FROM lawyers");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        lawyer.setId(resultSet.getInt(1));
        lawyer.setLengthOfService(new Date(System.currentTimeMillis()));
    }

    public static void doAddNewClient(Client client) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT clients (surname, name, patronymic, passportSeries, passportNumber, dateOfBirth, placeOfResidence) VALUES (?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, client.getSurname());
        preparedStatement.setString(2, client.getName());
        preparedStatement.setString(3, client.getPatronymic());
        preparedStatement.setString(4, client.getPassportSeries());
        preparedStatement.setString(5, client.getPassportNumber());
        preparedStatement.setDate(6, client.getDateOfBirth());
        preparedStatement.setString(7, client.getPlaceOfResidence());
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("SELECT max(id) FROM clients");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        client.setId(resultSet.getInt(1));
        client.setDateOfBirthProperty(client.getDateOfBirth());
    }

    public static ArrayList<ActiveLawyer> getActiveLawyers() throws SQLException {
        ArrayList<ActiveLawyer> activeLawyers = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id, CONCAT (surname, ' ', name, ' ', patronymic) FROM lawyers WHERE status = 1");
        while (resultSet.next()){
            ActiveLawyer activeLawyer = new ActiveLawyer();
            activeLawyer.setId(resultSet.getInt(1));
            activeLawyer.setFullName(resultSet.getString(2));
            activeLawyers.add(activeLawyer);
        }
        return activeLawyers;
    }

    public static void doAddNewCaseLegalProcedure(CaseLegalProcedure procedure, int caseId) throws SQLException {
        if (procedure.getResult().equals("")){
            return;
        }
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT caseLegalProcedures (caseId, legalProcedureId, date, result) VALUES (?, ?, ?, ?)");
        preparedStatement.setInt(1, caseId);
        preparedStatement.setInt(2, procedure.getLegalProcedureId());
        preparedStatement.setDate(3, new Date(System.currentTimeMillis()));
        preparedStatement.setString(4, procedure.getResult());
        preparedStatement.executeUpdate();
    }

    public static ArrayList<ActiveLawyer> getAdditionalLawyers(int caseId) throws SQLException {
        ArrayList<ActiveLawyer> additionalLawyers = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT Id, CONCAT (Surname, ' ', Name, ' ', Patronymic) FROM Lawyers WHERE Status = 1 AND Id NOT IN (SELECT LawyerId FROM CaseLawyers WHERE CaseId = ?)");
        statement.setInt(1, caseId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            ActiveLawyer activeLawyer = new ActiveLawyer();
            activeLawyer.setId(resultSet.getInt(1));
            activeLawyer.setFullName(resultSet.getString(2));
            additionalLawyers.add(activeLawyer);
        }
        return additionalLawyers;
    }

    public static void doAddLawyerToCase(int caseId, int lawyerId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT caseLawyers (caseId, lawyerId) VALUES (?, ?)");
        statement.setInt(1, caseId);
        statement.setInt(2, lawyerId);
        statement.executeUpdate();
    }

    public static ArrayList<Case> getSelectedCases(Date startDate, Date endDate) throws SQLException {
        ArrayList<Case> selectedCases= new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT Number, Status, caseResultId, Costs FROM Cases WHERE OpeningDate >= ? AND ClosingDate <= ?");
        preparedStatement.setDate(1, startDate);
        preparedStatement.setDate(2, endDate);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Case element = new Case();
            element.setNumber(resultSet.getString("Number"));
            int status = resultSet.getInt("status");
            if (status == 1){
                element.setStatus("В работе");
            }
            else{
                element.setStatus("Закрыто");
            }
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT name FROM CaseResults WHERE id = ?");
            preparedStatement1.setInt(1, resultSet.getInt("caseResultId"));
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            resultSet1.next();
            element.setCaseResult(resultSet1.getString("name"));
            element.setCosts(resultSet.getInt("costs"));
            selectedCases.add(element);
        }
        return selectedCases;
    }

    public static ArrayList<BackgroundInformationType> getBackgroundInformation() throws SQLException {
        ArrayList<BackgroundInformationType> backgroundInformation = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM backgroundInformation");
        while (resultSet.next()){
            BackgroundInformationType type = new BackgroundInformationType();
            type.setId(resultSet.getInt(1));
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM caseResults WHERE id = ?");
            preparedStatement.setInt(1, resultSet.getInt(2));
            ResultSet resultSet1 = preparedStatement.executeQuery();
            resultSet1.next();
            type.setCaseResultType(resultSet1.getString(1));
            type.setPremiumRate(resultSet.getInt(3));
            backgroundInformation.add(type);
        }
        return backgroundInformation;
    }

    public static void doEditBackgroundInformation(int typeId, int rate) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE backgroundInformation SET premiumRate = ? WHERE id = ?");
        preparedStatement.setInt(1, rate);
        preparedStatement.setInt(2, typeId);
        preparedStatement.executeUpdate();
    }
}