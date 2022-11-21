package com.intellij.bikerbikus.queries;

import com.intellij.bikerbikus.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import java.sql.*;
import java.time.LocalDate;

public class InsertRecensioneQuery {

    private InsertRecensioneQuery() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean insertRecensione(int idLuogo, int idUtente, String recensione, LocalDate date, Double valutazione) {
        String sql = "INSERT INTO RecensioneLuogo(Id, IdLuogo, IdUtente, Recensione, Data, Valutazione) VALUES ((SELECT COALESCE(MAX(Id) + 1, 1) FROM RecensioneLuogo e2),?,?,?,?,?);";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idLuogo);
            preparedStatement.setInt(2, idUtente);
            preparedStatement.setString(3, recensione);
            preparedStatement.setDate(4, Date.valueOf(date));
            preparedStatement.setDouble(5, valutazione);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }
}