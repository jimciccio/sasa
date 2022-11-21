package com.intellij.bikerbikus.queries;

import com.intellij.bikerbikus.models.Campionato;
import com.intellij.bikerbikus.models.Stagione;
import com.intellij.bikerbikus.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CampionatiQuery {

	private CampionatiQuery() {
		throw new IllegalStateException("Utility class");
	}

	public static List<Stagione> findAll() {
		String sql = "SELECT s.*, c.Nome AS NomeCampionato FROM Stagione s LEFT JOIN Campionato c ON c.Id = s.Campionato ORDER BY c.Id, s.dataInizio";
		List<Stagione> result = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				Stagione stagione = new Stagione();
				stagione.setId(resultSet.getInt(1));
				stagione.setNome(resultSet.getString(2));
				stagione.setDataFine(resultSet.getDate(4).toLocalDate());
				stagione.setDataInizio(resultSet.getDate(3).toLocalDate());
				Campionato campionato = new Campionato();
				campionato.setId(resultSet.getInt(5));
				campionato.setNome(resultSet.getString(6));
				stagione.setCampionato(campionato);
				result.add(stagione);
			}
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return result;
	}

	public static List<Stagione> findCampionatiAperti() {
		String sql = "SELECT s.*, c.Nome AS NomeCampionato FROM Stagione s LEFT JOIN Campionato c ON c.Id = s.Campionato WHERE s.DataFine > CURDATE() ORDER BY c.Id";
		List<Stagione> result = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				Stagione stagione = new Stagione();
				stagione.setId(resultSet.getInt(1));
				stagione.setNome(resultSet.getString(2));
				stagione.setDataInizio(resultSet.getDate(3).toLocalDate());
				stagione.setDataFine(resultSet.getDate(4).toLocalDate());
				Campionato campionato = new Campionato();
				campionato.setId(resultSet.getInt(5));
				campionato.setNome(resultSet.getString(6));
				stagione.setCampionato(campionato);
				result.add(stagione);
			}
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return result;
	}

	public static List<Campionato> findCampionati() {
		String sql = "SELECT Id, Nome FROM Campionato ORDER BY Nome";
		List<Campionato> result = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
			 PreparedStatement preparedStatement = connection.prepareStatement(sql);
			 ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				Campionato campionato = new Campionato();
				campionato.setId(resultSet.getInt(1));
				campionato.setNome(resultSet.getString(2));
				result.add(campionato);
			}
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return result;
	}

	public static boolean insertStagione(Stagione stagione) {
		String sql = "INSERT INTO Stagione(Id, Nome, dataInizio, dataFine, Campionato) VALUES ((SELECT COALESCE(MAX(Id) + 1, 1) FROM Stagione g2),?,?,?,?);";
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
			 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, Integer. parseInt(stagione.getNome()));
			preparedStatement.setDate(2,  Date.valueOf(stagione.getDataInizio()));
			preparedStatement.setDate(3,  Date.valueOf(stagione.getDataFine()));
			preparedStatement.setInt(4, stagione.getCampionato().getId());
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return false;
	}
}