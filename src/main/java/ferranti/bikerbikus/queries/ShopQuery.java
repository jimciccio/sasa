package ferranti.bikerbikus.queries;

import ferranti.bikerbikus.models.BiciclettaNoleggio;
import ferranti.bikerbikus.models.BiciclettaVendita;
import ferranti.bikerbikus.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShopQuery {

    private ShopQuery() {
        throw new IllegalStateException("Utility class");
    }

    public static List<BiciclettaVendita> findBicicletteComprabili() {
        String sql = "SELECT Id, Modello, Caratteristiche, Prezzo FROM BicicletteComprabili WHERE Disponibili > 0";
        List<BiciclettaVendita> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BiciclettaVendita biciclettaVendita = new BiciclettaVendita();
                    biciclettaVendita.setId(resultSet.getInt(1));
                    biciclettaVendita.setCaratteristiche(resultSet.getString(3));
                    biciclettaVendita.setPrezzo(resultSet.getInt(4));
                    biciclettaVendita.setModello(resultSet.getString(2));
                    result.add(biciclettaVendita);
                }
            }
        } catch (
                SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return result;
    }

    public static List<BiciclettaNoleggio> findBicicletteNoleggiabili() {
        List<BiciclettaNoleggio> result = new ArrayList<>();
        String sql = "SELECT Id, Modello, Caratteristiche, Prezzo FROM BicicletteNoleggiabili WHERE noleggiabile != 0 AND manutenzione != 1";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BiciclettaNoleggio biciclettaNoleggio = new BiciclettaNoleggio();
                    biciclettaNoleggio.setId(resultSet.getInt(1));
                    biciclettaNoleggio.setModello(resultSet.getString(2));
                    biciclettaNoleggio.setCaratteristiche(resultSet.getString(3));
                    biciclettaNoleggio.setPrezzo(resultSet.getInt(4));
                    result.add(biciclettaNoleggio);
                }
            }
        } catch (
                SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return result;
    }

    public static boolean insertBiciclettaComprata(BiciclettaVendita biciclettaVendita, int idUser) {
        String sql = "INSERT INTO BicicletteComprate(IdAcquisto, IdBicicletta, IdUtente, Modello, Caratteristiche, DataAcquisto, Prezzo) VALUES ((SELECT COALESCE(MAX(IdAcquisto) + 1, 1) FROM BicicletteComprate e2),?,?,?,?,?,?);";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, biciclettaVendita.getId());
            preparedStatement.setInt(2, idUser);
            preparedStatement.setString(3, biciclettaVendita.getModello());
            preparedStatement.setString(4, biciclettaVendita.getCaratteristiche());
            preparedStatement.setTimestamp(5, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(6, biciclettaVendita.getPrezzo());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }

    public static boolean insertBiciclettaNoleggiata(BiciclettaNoleggio biciclettaNoleggio, int idUser, int finalPrice, LocalDate endNoleggio) {
        String sql = "INSERT INTO BicicletteNoleggiate(IdNoleggio, IdBicicletta, IdUtente, Modello, Caratteristiche, DataInizioNoleggio, DataFineNoleggio, Prezzo, PrezzoFinale) VALUES ((SELECT COALESCE(MAX(IdNoleggio) + 1, 1) FROM BicicletteNoleggiate e2),?,?,?,?,?,?,?,?);";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, biciclettaNoleggio.getId());
            preparedStatement.setInt(2, idUser);
            preparedStatement.setString(3, biciclettaNoleggio.getModello());
            preparedStatement.setString(4, biciclettaNoleggio.getCaratteristiche());
            preparedStatement.setTimestamp(5, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp(6, java.sql.Timestamp.valueOf(endNoleggio.atTime(23, 59,59)));
            preparedStatement.setInt(7, biciclettaNoleggio.getPrezzo());
            preparedStatement.setInt(8, finalPrice);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }

    public static boolean updateBiciclettaComprabile(int idBicicletta) {
        String sql = "UPDATE BicicletteComprabili SET disponibili = disponibili -1 WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idBicicletta);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }

    public static boolean updateBiciclettaNoleggiabile(int idBicicletta) {
        String sql = "UPDATE BicicletteNoleggiabili SET noleggiabile = 0 WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idBicicletta);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }
}