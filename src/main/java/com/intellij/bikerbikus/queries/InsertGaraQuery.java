package com.intellij.bikerbikus.queries;

import com.intellij.bikerbikus.models.Gara;
import com.intellij.bikerbikus.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import java.sql.*;

public class InsertGaraQuery {

	private InsertGaraQuery() {
		throw new IllegalStateException("Utility class");
	}

	public static boolean execute(Gara gara) {
		String sql = "INSERT INTO Gara(Id, Stagione, Data) VALUES ((SELECT COALESCE(MAX(Id) + 1, 1) FROM Gara g2),?,?);";
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, gara.getStagione().getId());
			preparedStatement.setTimestamp(2, Timestamp.valueOf(gara.getData()));
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return false;
	}
}