package ferranti.bikerbikus.queries;

import ferranti.bikerbikus.models.*;
import ferranti.bikerbikus.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecensioneQuery {

    private RecensioneQuery() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Recensione> findRecensioniLuogo(int idLuogo) {
        String sql = "SELECT r.Id, r.IdLuogo, r.IdUtente, u.Nome, r.Recensione, r.Data, r.Valutazione FROM RecensioneLuogo r LEFT JOIN Utente u ON u.Id = r.IdUtente WHERE r.IdLuogo = ? ORDER BY Data";
        List<Recensione> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idLuogo);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Recensione recensione = new Recensione();
                    recensione.setId(resultSet.getInt(1));
                    recensione.setIdLuogo(resultSet.getInt(2));
                    recensione.setIdUtente(resultSet.getInt(3));
                    Utente utente = new Utente();
                    utente.setNome(resultSet.getString(4));
                    recensione.setUtente(utente);
                    recensione.setRecensioneString(resultSet.getString(5));
                    recensione.setData(resultSet.getTimestamp(6).toLocalDateTime().toLocalDate());
                    recensione.setValutazione(resultSet.getDouble(7));
                    result.add(recensione);
                }
            }
        } catch (SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return result;
    }

    public static List<Recensione> findRecensioniUtente(int idUtente) {
        String sql = "SELECT r.Id, r.IdLuogo, l.Nome, r.Recensione, r.Data, r.Valutazione FROM RecensioneLuogo r LEFT JOIN Luogo l ON l.Id = r.IdLuogo WHERE r.IdUtente = ? ORDER BY Data";
        List<Recensione> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idUtente);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Recensione recensione = new Recensione();
                    recensione.setId(resultSet.getInt(1));
                    recensione.setIdLuogo(resultSet.getInt(2));
                    Luoghi luogo = new Luoghi();
                    luogo.setNome(resultSet.getString(3));
                    recensione.setLuogo(luogo);
                    recensione.setRecensioneString(resultSet.getString(4));
                    recensione.setData(resultSet.getTimestamp(5).toLocalDateTime().toLocalDate());
                    recensione.setValutazione(resultSet.getDouble(6));
                    result.add(recensione);
                }
            }
        } catch (SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return result;
    }

    public static boolean updateRecensione(int idRecensione, String recensione, LocalDate data, double valutazione) {
        String sql = "UPDATE RecensioneLuogo SET  Recensione = ?, Data = ?, Valutazione = ?  WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, recensione);
            preparedStatement.setDate(2, Date.valueOf(data));
            preparedStatement.setDouble(3, valutazione);
            preparedStatement.setInt(4, idRecensione);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }

    public static boolean deleteRecensione(int idRecensione) {
        String sql = "DELETE FROM RecensioneLuogo WHERE Id = ?";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idRecensione);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }
}