package ferranti.bikerbikus.queries;

import ferranti.bikerbikus.models.Luoghi;
import ferranti.bikerbikus.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LuoghiQuery {

    private LuoghiQuery() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Luoghi> findLuoghi() {
        String sql = "SELECT DISTINCT l.Id, l.Nome, l.Descrizione, l.Difficolta, l.ValutazioneMedia FROM Luogo l ORDER BY Nome;";
        List<Luoghi> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Luoghi luogo = new Luoghi();
                luogo.setId(resultSet.getInt(1));
                luogo.setNome(resultSet.getString(2));
                luogo.setDescrizione(resultSet.getString(3));
                luogo.setDifficolta(resultSet.getInt(4));
                luogo.setValutazioneMedia(resultSet.getDouble(5));
                result.add(luogo);
            }
        } catch (SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return result;
    }

    public static boolean insertValutazione(int idLuogo) {
        String sql = "UPDATE Luogo l SET l.ValutazioneMedia = (SELECT DISTINCT CAST(COALESCE(AVG(Valutazione),0) AS DECIMAL(10,2)) AS Valutazione FROM RecensioneLuogo d WHERE d.IdLuogo = ?) WHERE l.Id =?;";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idLuogo);
            preparedStatement.setInt(2, idLuogo);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }

    public static boolean insertValutazioneForAll() {
        String sql = "UPDATE Luogo l SET l.ValutazioneMedia = (SELECT DISTINCT CAST(COALESCE(AVG(Valutazione),0) AS DECIMAL(10,2)) AS Valutazione FROM RecensioneLuogo d WHERE d.IdLuogo = l.Id);";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }

    public static boolean updateLuogo(int id, String nome, String descrizione, int difficolta) {
        String sql = "UPDATE Luogo  SET Nome = ?, Descrizione = ?, Difficolta = ? WHERE Id = ?;";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, descrizione);
            preparedStatement.setInt(3, difficolta);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }

    public static boolean insertLuogo(String nome, String descrizione, int difficolta) {
        String sql = "INSERT INTO Luogo(Id, Nome, Descrizione, Difficolta, ValutazioneMedia) VALUES ((SELECT COALESCE(MAX(Id) + 1, 1) FROM Luogo e2),?,?,?,?);";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, descrizione);
            preparedStatement.setInt(3, difficolta);
            preparedStatement.setInt(4, 0);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }
}