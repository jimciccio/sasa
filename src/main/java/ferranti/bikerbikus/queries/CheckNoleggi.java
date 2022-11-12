package ferranti.bikerbikus.queries;

import ferranti.bikerbikus.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.sql.*;
import java.time.LocalDateTime;

public class CheckNoleggi {

    private CheckNoleggi() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean checkNoleggi() {
        String sql = "UPDATE BicicletteNoleggiabili i LEFT JOIN BicicletteNoleggiate e ON i.Id = e.idBicicletta SET noleggiabile = 1 WHERE dataFineNoleggio < ?";
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return false;
    }
}