package ferranti.bikerbikus.queries;

import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.*;
import ferranti.bikerbikus.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.sql.*;


public class RegisterQuery {

    private RegisterQuery() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean execute(String nome, String cognome, String email, String password, int counter) {
        String sql = "INSERT INTO Utente(Id, Email, Password, Nome, Cognome, TipoUtente) VALUES ((SELECT MAX(Id) + 1 FROM Utente l2),?,?,?,?,?);";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, nome);
            preparedStatement.setString(4, cognome);
            preparedStatement.setInt(5,1);
            preparedStatement.executeUpdate();
            Utente utente = new Utente();
            utente.setId(counter);
            utente.setEmail(email);
            utente.setPassword(password);
            utente.setNome(nome);
            utente.setCognome(cognome);
            TipoUtente ut = new TipoUtente(1,"Allievo");
            utente.setTipoUtente(ut);
            UserData.getInstance().setUser(utente);
            return true;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }


    public static int countUsers() {
        String sql = "SELECT MAX(Id) + 1  from Utente";
        int result = 0;
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    result= resultSet.getInt(1);
                }
            }
        } catch (

                SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return result;
    }

}