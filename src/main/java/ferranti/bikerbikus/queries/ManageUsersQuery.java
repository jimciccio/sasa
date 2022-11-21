package ferranti.bikerbikus.queries;

import ferranti.bikerbikus.models.TipoUtente;
import ferranti.bikerbikus.models.Utente;
import ferranti.bikerbikus.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManageUsersQuery {

    private ManageUsersQuery() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Utente> findAllUsers() {
        String sql = "SELECT u.*, tu.Nome FROM Utente u LEFT JOIN TipoUtente tu ON tu.Id = u.TipoUtente  WHERE u.TipoUtente IN (1,2) ORDER BY TipoUtente;";
        List<Utente> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Utente utente = new Utente();
                utente.setId(resultSet.getInt(1));
                utente.setEmail(resultSet.getString(2));
                utente.setPassword(resultSet.getString(3));
                utente.setNome(resultSet.getString(4));
                utente.setCognome(resultSet.getString(5));
                TipoUtente tipoUtente = new TipoUtente();
                tipoUtente.setId(resultSet.getInt(6));
                tipoUtente.setNome(resultSet.getString(7));
                utente.setTipoUtente(tipoUtente);
                result.add(utente);
            }
        } catch (SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return result;
    }

    public static boolean upgradeUser(int idUtente) {
        String sql = "UPDATE Utente SET  TipoUtente = TipoUtente + 1  WHERE Id = ?";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idUtente);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }
}