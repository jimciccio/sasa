package ferranti.bikerbikus.queries;

import ferranti.bikerbikus.models.Bicicletta;
import ferranti.bikerbikus.models.BiciclettaNoleggio;
import ferranti.bikerbikus.models.BiciclettaVendita;
import ferranti.bikerbikus.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AggiungiBiciclettaQuery {

    private AggiungiBiciclettaQuery() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean insertBiciclettaComprabile(BiciclettaVendita biciclettaVendita) {
        String sql = "INSERT INTO BicicletteComprabili(Id, Modello, Caratteristiche, Prezzo, Disponibili) VALUES ((SELECT COALESCE(MAX(Id) + 1, 1) FROM BicicletteComprabili e2),?,?,?,?);";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, biciclettaVendita.getModello());
            preparedStatement.setString(2, biciclettaVendita.getCaratteristiche());
            preparedStatement.setInt(3, biciclettaVendita.getPrezzo());
            preparedStatement.setInt(4, biciclettaVendita.getDisponibili());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }
    public static boolean insertBiciclettaNoleggiabile(BiciclettaNoleggio biciclettaNoleggio) {
        String sql = "INSERT INTO BicicletteNoleggiabili(Id, Modello, Caratteristiche, Prezzo, Noleggiabile, Manutenzione) VALUES ((SELECT COALESCE(MAX(Id) + 1, 1) FROM BicicletteNoleggiabili e2),?,?,?,?,?);";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, biciclettaNoleggio.getModello());
            preparedStatement.setString(2, biciclettaNoleggio.getCaratteristiche());
            preparedStatement.setInt(3, biciclettaNoleggio.getPrezzo());
            preparedStatement.setInt(4, 1);
            preparedStatement.setInt(5,0);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }

    public static List<BiciclettaVendita> findBicicletteComprabili() {
        String sql = "SELECT Id, Modello, Caratteristiche, Prezzo, Disponibili FROM BicicletteComprabili";
        List<BiciclettaVendita> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BiciclettaVendita biciclettaVendita = new BiciclettaVendita();
                    biciclettaVendita.setModello(resultSet.getString(2));
                    biciclettaVendita.setId(resultSet.getInt(1));
                    biciclettaVendita.setCaratteristiche(resultSet.getString(3));
                    biciclettaVendita.setPrezzo(resultSet.getInt(4));
                    biciclettaVendita.setDisponibili(resultSet.getInt(5));
                    result.add(biciclettaVendita);
                }
            }
        } catch (
                SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return result;
    }

    public static List<BiciclettaNoleggio> findBicicletteNoleggiabili() {
        String sql = "SELECT Id, Modello, Caratteristiche, Prezzo, Noleggiabile, Manutenzione FROM BicicletteNoleggiabili";
        List<BiciclettaNoleggio> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BiciclettaNoleggio biciclettaNoleggio = new BiciclettaNoleggio();
                    biciclettaNoleggio.setId(resultSet.getInt(1));
                    biciclettaNoleggio.setCaratteristiche(resultSet.getString(3));
                    biciclettaNoleggio.setModello(resultSet.getString(2));
                    biciclettaNoleggio.setPrezzo(resultSet.getInt(4));
                    biciclettaNoleggio.setNoleggiabile(resultSet.getBoolean(5) ? 1 : 0);
                    biciclettaNoleggio.setManutenzione(resultSet.getInt(6));
                    result.add(biciclettaNoleggio);
                }
            }
        } catch (
                SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return result;
    }

    public static boolean updateBiciclettaComprabile(int idBici, String modello, String caratteristiche, int prezzo, int disponibili) {
        String sql = "UPDATE BicicletteComprabili SET modello = ?, caratteristiche = ?, prezzo= ?, disponibili = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, modello);
            preparedStatement.setString(2, caratteristiche);
            preparedStatement.setInt(3, prezzo);
            preparedStatement.setInt(4, disponibili);
            preparedStatement.setInt(5, idBici);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }

    public static boolean updateBiciclettaNoleggiabile(int idBici, String modello, String caratteristiche, int prezzo, Boolean manutenzione) {
        String sql = "UPDATE BicicletteNoleggiabili SET modello = ?, caratteristiche = ?, prezzo = ?, manutenzione = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, modello);
            preparedStatement.setString(2, caratteristiche);
            preparedStatement.setInt(3, prezzo);
            preparedStatement.setBoolean(4, manutenzione);
            preparedStatement.setInt(5, idBici);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }

    public static boolean updateBiciclettaNoleggiate(int idBici, String modello, String caratteristiche) {
        String sql = "UPDATE BicicletteNoleggiate SET modello = ?, caratteristiche = ? WHERE idBicicletta = ?";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, modello);
            preparedStatement.setString(2, caratteristiche);
            preparedStatement.setInt(3, idBici);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }

    public static Boolean checkBiciNuova(Bicicletta bicicletta) {
        String sql = "SELECT Modello FROM BicicletteComprabili";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    if(bicicletta.getModello().equals(resultSet.getString(1))){
                        return true;
                    }
                }
            }
        } catch (
                SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }
}