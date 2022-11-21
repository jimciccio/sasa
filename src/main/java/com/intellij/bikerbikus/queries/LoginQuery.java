package com.intellij.bikerbikus.queries;

import com.intellij.bikerbikus.data.UserData;
import com.intellij.bikerbikus.models.TipoUtente;
import com.intellij.bikerbikus.models.Utente;
import com.intellij.bikerbikus.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import java.sql.*;

public class LoginQuery {

	LoginQuery() {
		throw new IllegalStateException("Utility class");
	}

	public static boolean execute(String email, String password) {
		String sql = "SELECT u.*, tu.Nome FROM Utente u LEFT JOIN TipoUtente tu ON tu.Id = u.TipoUtente WHERE email = ? AND password = ?;";
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Utente utente = new Utente();
					utente.setId(resultSet.getInt(1));
					utente.setEmail(resultSet.getString(2));
					utente.setPassword(resultSet.getString(3));
					utente.setNome(resultSet.getString(4));
					utente.setCognome(resultSet.getString(5));
					utente.setTipoUtente(new TipoUtente(resultSet.getInt(6), resultSet.getString(7)));
					UserData.getInstance().setUser(utente);
					return true;
				}
			}
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return false;
	}
}