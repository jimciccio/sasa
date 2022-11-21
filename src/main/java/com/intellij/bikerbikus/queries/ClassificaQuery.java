package com.intellij.bikerbikus.queries;

import com.intellij.bikerbikus.models.UtenteExtended;
import com.intellij.bikerbikus.utils.Constants;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassificaQuery {

    private ClassificaQuery() {
        throw new IllegalStateException("Utility class");
    }

    public static List<UtenteExtended> execute(int idStagione) {
        String sql = " SELECT DISTINCT Utente, Nome, Cognome FROM PrenotazioneGara p JOIN Gara g ON p.Gara = g.Id JOIN Utente u ON u.id = p.utente WHERE Stagione = ?";
        List<UtenteExtended> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idStagione);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    UtenteExtended utente = new UtenteExtended();
                    utente.setId(resultSet.getInt(1));
                    utente.setNome(resultSet.getString(2));
                    utente.setCognome(resultSet.getString(3));
                    result.add(utente);
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return result;
    }

    public static int userPunteggio(int idStagione, int idUtente, UtenteExtended utente) {
        String sql = " SELECT Posizione FROM PrenotazioneGara JOIN Gara ON Gara = Id WHERE Stagione = ? and Utente = ?";
        int result = 0;
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idStagione);
            preparedStatement.setInt(2, idUtente);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    utente.setGare(utente.getGare()+1);
                    if(resultSet.getInt(1)==1){
                        result= result + 10;
                    }else if(resultSet.getInt(1)==2){
                        result= result + 5;
                    }else if(resultSet.getInt(1)==3){
                        result= result + 3;
                    }else if(resultSet.getInt(1)==0){
                        result= result + 0;
                    }else{
                        result= result + 2;
                    }
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return result;
    }

    public static List<UtenteExtended> classificaGara(int idGara) {
        String sql = " SELECT Utente, Nome, Cognome, Posizione, Ps1, Ps2, Ps3, Tempo FROM PrenotazioneGara p JOIN Gara g ON p.Gara = g.Id JOIN Utente u ON u.id = p.utente WHERE Gara = ?";
        List<UtenteExtended> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idGara);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    UtenteExtended utente = new UtenteExtended();
                    utente.setId(resultSet.getInt(1));
                    utente.setNome(resultSet.getString(2));
                    utente.setCognome(resultSet.getString(3));
                    utente.setPosizioneGara(resultSet.getInt(4));
                    utente.setPs1(resultSet.getString(5));
                    utente.setPs2(resultSet.getString(6));
                    utente.setPs3(resultSet.getString(7));
                    utente.setTempo(resultSet.getString(8));
                    if(resultSet.getInt(4)==1){
                        utente.setPunteggio(10);
                    }else if(resultSet.getInt(4)==2){
                        utente.setPunteggio(5);
                    }else if(resultSet.getInt(4) == 3){
                        utente.setPunteggio(3);
                    }else if(resultSet.getInt(4) == 0){
                        utente.setPunteggio(0);
                    }else{
                        utente.setPunteggio(2);
                    }
                    result.add(utente);
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return result;
    }
}