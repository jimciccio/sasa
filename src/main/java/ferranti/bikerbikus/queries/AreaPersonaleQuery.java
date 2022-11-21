package ferranti.bikerbikus.queries;

import ferranti.bikerbikus.models.*;
import ferranti.bikerbikus.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AreaPersonaleQuery {

	private AreaPersonaleQuery() {
		throw new IllegalStateException("Utility class");
	}

	public static List<Lezione> findLezioni(int idUtente) {
		String sql = "SELECT l.Id, l.Data, l.Privata, u.Nome AS NomeMaestro, u.Cognome AS CognomeMaestro, tl.Id AS TipoLezioneId, tl.Nome AS TipoLezioneNome, l.Eliminata FROM Lezione l LEFT JOIN Utente u ON u.Id = l.Maestro LEFT JOIN TipoLezione tl ON tl.Id = l.TipoLezione WHERE (?, l.Id) IN (SELECT pl.Utente, pl.Lezione FROM PrenotazioneLezione pl) ORDER BY l.Data DESC";
		List<Lezione> result = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, idUtente);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Lezione lezione = new Lezione();
					lezione.setId(resultSet.getInt(1));
					lezione.setData(resultSet.getTimestamp(2).toLocalDateTime());
					lezione.setPrivata(resultSet.getBoolean(3));
					lezione.setEliminata(resultSet.getInt(8));
					Utente maestro = new Utente();
					maestro.setNome(resultSet.getString(4));
					maestro.setCognome(resultSet.getString(5));
					lezione.setMaestro(maestro);
					TipoLezione tipoLezione = new TipoLezione();
					tipoLezione.setId(resultSet.getInt(6));
					tipoLezione.setNome(resultSet.getString(7));
					lezione.setTipo(tipoLezione);
					result.add(lezione);
				}
			}
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return result;
	}

	public static boolean disdiciLezione(int idUtente, int idLezione) {
		String sql = "DELETE FROM PrenotazioneLezione WHERE Utente = ? AND Lezione = ?";
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, idUtente);
			preparedStatement.setInt(2, idLezione);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return false;
	}

	public static List<Gara> findGare(int idUtente) {
		String sql = "SELECT g.Id, g.Data, g.Stagione, c.Id AS Campionato, c.Nome AS NomeCampionato, s.Nome AS NomeStagione FROM Gara g LEFT JOIN Stagione s ON s.Id = g.Stagione LEFT JOIN Campionato c ON c.Id = s.Campionato WHERE (?, g.Id) IN (SELECT pg.Utente, pg.Gara FROM PrenotazioneGara pg) ORDER BY g.Data DESC";
		List<Gara> result = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, idUtente);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Gara gara = new Gara();
					gara.setData(resultSet.getTimestamp(2).toLocalDateTime());
					gara.setId(resultSet.getInt(1));
					Stagione stagione = new Stagione();
					stagione.setId(resultSet.getInt(3));
					Campionato campionato = new Campionato();
					campionato.setNome(resultSet.getString(5));
					campionato.setId(resultSet.getInt(4));
					stagione.setCampionato(campionato);
					stagione.setNome(resultSet.getString(6));
					gara.setStagione(stagione);
					result.add(gara);
				}
			}
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return result;
	}

	public static List<Escursione> findEscursioni(int idUtente) {
		String sql = "SELECT e.Id, e.Data, e.Luogo, l.Nome, l.Difficolta, u.Nome AS NomeAccompagnatore, u.Cognome AS CognomeAccompagnatore FROM Escursione e LEFT JOIN Utente u ON u.Id = e.Accompagnatore JOIN Luogo l ON e.Luogo = l.Id WHERE (?, e.Id) IN (SELECT pe.Utente, pe.Escursione FROM PrenotazioneEscursione pe) AND e.Eliminata = 0 ORDER BY e.Data DESC";
		List<Escursione> result = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, idUtente);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Escursione escursione = new Escursione();
					escursione.setId(resultSet.getInt(1));
					escursione.setData(resultSet.getTimestamp(2).toLocalDateTime());
					Luoghi luogo = new Luoghi();
					luogo.setId(resultSet.getInt(3));
					luogo.setNome(resultSet.getString(4));
					luogo.setDifficolta(resultSet.getInt(5));
					escursione.setLuogo(luogo);
					Utente accompagnatore = new Utente();
					accompagnatore.setNome(resultSet.getString(6));
					accompagnatore.setCognome(resultSet.getString(7));
					escursione.setAccompagnatore(accompagnatore);
					result.add(escursione);
				}
			}
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return result;
	}

	public static boolean disdiciEscursione(int idUtente, int idEscursione) {
		String sql = "DELETE FROM PrenotazioneEscursione WHERE Utente = ? AND Escursione = ?";
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, idUtente);
			preparedStatement.setInt(2, idEscursione);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return false;
	}

	public static List<BiciclettaVendita> findBicicletteComprate(int idUtente) {
		String sql = "SELECT e.idAcquisto, e.idBicicletta, e.idUtente, e.modello, e.caratteristiche, e.dataAcquisto, e.prezzo  FROM BicicletteComprate e WHERE ? = idUtente ORDER BY e.dataAcquisto DESC";
		List<BiciclettaVendita> result = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
			 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, idUtente);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					BiciclettaVendita biciclettaComprata = new BiciclettaVendita();
					biciclettaComprata.setIdAcquisto(resultSet.getInt(1));
					biciclettaComprata.setId(resultSet.getInt(2));
					biciclettaComprata.setIdUtente(resultSet.getInt(3));
					biciclettaComprata.setModello(resultSet.getString(4));
					biciclettaComprata.setCaratteristiche(resultSet.getString(5));
					biciclettaComprata.setDataAcquisto(resultSet.getTimestamp(6).toLocalDateTime());
					biciclettaComprata.setPrezzo(resultSet.getInt(7));
					result.add(biciclettaComprata);
				}
			}
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return result;
	}

	public static List<BiciclettaNoleggio> findBicicletteNoleggiate(int idUtente) {
		String sql = "SELECT e.idNoleggio, e.idBicicletta, e.idUtente, e.modello, e.caratteristiche, e.dataInizioNoleggio, e.dataFineNoleggio, e.prezzo, e.prezzoFinale  FROM BicicletteNoleggiate e WHERE ? = idUtente ORDER BY e.dataFineNoleggio DESC";
		List<BiciclettaNoleggio> result = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
			 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, idUtente);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					BiciclettaNoleggio biciclettaNoleggiata = new BiciclettaNoleggio();
					biciclettaNoleggiata.setIdNoleggio(resultSet.getInt(1));
					biciclettaNoleggiata.setId(resultSet.getInt(2));
					biciclettaNoleggiata.setIdUtente(resultSet.getInt(3));
					biciclettaNoleggiata.setModello(resultSet.getString(4));
					biciclettaNoleggiata.setCaratteristiche(resultSet.getString(5));
					biciclettaNoleggiata.setInizioNoleggio(resultSet.getTimestamp(6).toLocalDateTime());
					biciclettaNoleggiata.setFineNoleggio(resultSet.getTimestamp(7).toLocalDateTime());
					biciclettaNoleggiata.setPrezzo(resultSet.getInt(8));
					biciclettaNoleggiata.setPrezzoFinale(resultSet.getInt(9));
					result.add(biciclettaNoleggiata);
				}
			}
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return result;
	}
	public static boolean disdiciNoleggio(int idUtente, int idNoleggio, LocalDateTime dataFineNoleggio) {
		String sql = "UPDATE BicicletteNoleggiate SET dataFineNoleggio = ? WHERE idUtente = ? AND idNoleggio = ?";
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
			 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(dataFineNoleggio));
			preparedStatement.setInt(2, idUtente);
			preparedStatement.setInt(3, idNoleggio);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return false;
	}

	public static boolean ripristinaNoleggiabilita(int idNoleggio) {
		String sql = "UPDATE BicicletteNoleggiabili SET noleggiabile = 1 WHERE id = (SELECT idBicicletta FROM BicicletteNoleggiate WHERE idNoleggio = ?)";
		try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
			 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, idNoleggio);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
		}
		return false;
	}
}