package ferranti.bikerbikus.queries;

import ferranti.bikerbikus.models.Lezione;
import ferranti.bikerbikus.models.TipoLezione;
import ferranti.bikerbikus.models.Utente;
import ferranti.bikerbikus.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import java.sql.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class LezioniQuery {

	private LezioniQuery() {
		throw new IllegalStateException("Utility class");
	}

	public static List<Lezione> execute(YearMonth yearMonth, int userId) {
		String sql = "SELECT l.Id, l.Data, l.Privata, u.Nome AS NomeMaestro, u.Cognome AS CognomeMaestro, l.Maestro as IdMaestro, tl.Id AS TipoLezioneId, tl.Nome AS TipoLezioneNome FROM Lezione l LEFT JOIN Utente u ON u.Id = l.Maestro LEFT JOIN TipoLezione tl ON tl.Id = l.TipoLezione WHERE MONTH(Data) = ? AND YEAR(Data) = ? AND (?, l.Id) NOT IN (SELECT pl.Utente, pl.Lezione FROM PrenotazioneLezione pl) AND (l.id) NOT IN (SELECT pl.Lezione FROM PrenotazioneLezione pl JOIN Lezione l ON pl.Lezione = l.Id WHERE l.Privata = 1 ) AND l.Eliminata = 0 ORDER BY l.Data";
		List<Lezione> result = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, yearMonth.getMonthValue());
			preparedStatement.setInt(2, yearMonth.getYear());
			preparedStatement.setInt(3, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Lezione lezione = new Lezione();
					lezione.setData(resultSet.getTimestamp(2).toLocalDateTime());
					lezione.setId(resultSet.getInt(1));
					lezione.setPrivata(resultSet.getBoolean(3));
					Utente maestro = new Utente();
					maestro.setNome(resultSet.getString(4));
					maestro.setCognome(resultSet.getString(5));
					maestro.setId(resultSet.getInt(6));
					lezione.setMaestro(maestro);
					TipoLezione tipoLezione = new TipoLezione();
					tipoLezione.setNome(resultSet.getString(8));
					tipoLezione.setId(resultSet.getInt(7));
					lezione.setTipo(tipoLezione);
					result.add(lezione);
				}
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return result;
	}

	public static List<Lezione> lezioniMaestro(YearMonth yearMonth, int userId) {
		String sql = "SELECT l.Id, l.Data, l.Privata, u.Nome AS NomeMaestro, u.Cognome AS CognomeMaestro, l.Maestro as IdMaestro, tl.Id AS TipoLezioneId, tl.Nome AS TipoLezioneNome FROM Lezione l LEFT JOIN Utente u ON u.Id = l.Maestro LEFT JOIN TipoLezione tl ON tl.Id = l.TipoLezione WHERE MONTH(Data) = ? AND YEAR(Data) = ? AND l.Maestro = ? AND l.Eliminata = 0 ORDER BY l.Data";
		List<Lezione> result = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
			 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, yearMonth.getMonthValue());
			preparedStatement.setInt(2, yearMonth.getYear());
			preparedStatement.setInt(3, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Lezione lezione = new Lezione();
					lezione.setId(resultSet.getInt(1));
					lezione.setData(resultSet.getTimestamp(2).toLocalDateTime());
					lezione.setPrivata(resultSet.getBoolean(3));
					Utente maestro = new Utente();
					maestro.setNome(resultSet.getString(4));
					maestro.setCognome(resultSet.getString(5));
					maestro.setId(resultSet.getInt(6));
					lezione.setMaestro(maestro);
					TipoLezione tipoLezione = new TipoLezione();
					tipoLezione.setId(resultSet.getInt(7));
					tipoLezione.setNome(resultSet.getString(8));
					lezione.setTipo(tipoLezione);
					result.add(lezione);
				}

		} catch (SQLException e) {
			new Alert(AlertType.ERROR, "Qualcosa è andato storto! Riprova più tardi.", ButtonType.OK).show();
			return result;
		}
		return result;
	}

	public static boolean eliminaLezione(int idLezione) {
		String sql = "UPDATE Lezione SET Eliminata = 1 WHERE Id=?;";
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
			 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, idLezione);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return false;
	}
}