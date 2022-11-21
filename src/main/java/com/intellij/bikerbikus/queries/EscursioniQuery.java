package com.intellij.bikerbikus.queries;

import com.intellij.bikerbikus.models.Luoghi;
import com.intellij.bikerbikus.models.Escursione;
import com.intellij.bikerbikus.models.Utente;
import com.intellij.bikerbikus.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import java.sql.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class EscursioniQuery {

	private EscursioniQuery() {
		throw new IllegalStateException("Utility class");
	}

	public static List<Escursione> execute(YearMonth yearMonth, int userId) {
		String sql = "SELECT e.Id, e.Data, e.Luogo, l.Nome, l.Difficolta, u.Nome AS NomeAccompagnatore, u.Cognome AS CognomeAccompagnatore, u.Id FROM Escursione e LEFT JOIN Utente u ON u.Id = e.Accompagnatore JOIN Luogo l ON e.Luogo = l.Id WHERE MONTH(Data) = ? AND YEAR(Data) = ? AND (?, e.Id) NOT IN (SELECT pe.Utente, pe.Escursione FROM PrenotazioneEscursione pe) AND e.Eliminata = 0 ORDER BY e.Data";
		List<Escursione> result = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, yearMonth.getMonthValue());
			preparedStatement.setInt(2, yearMonth.getYear());
			preparedStatement.setInt(3, userId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Escursione escursione = new Escursione();
					escursione.setId(resultSet.getInt(1));
					escursione.setData(resultSet.getTimestamp(2).toLocalDateTime());
					Luoghi luogo = new Luoghi();
					luogo.setId(resultSet.getInt(3));
					luogo.setDifficolta(resultSet.getInt(5));
					luogo.setNome(resultSet.getString(4));
					escursione.setLuogo(luogo);
					Utente accompagnatore = new Utente();
					accompagnatore.setNome(resultSet.getString(6));
					accompagnatore.setCognome(resultSet.getString(7));
					accompagnatore.setId(resultSet.getInt(8));
					escursione.setAccompagnatore(accompagnatore);
					result.add(escursione);
				}
			}
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return result;
	}

	public static boolean eliminaEscursione(int idEscursione) {
		String sql = "UPDATE Escursione SET Eliminata = 1 WHERE Id=?;";
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
			 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, idEscursione);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return false;
	}
}