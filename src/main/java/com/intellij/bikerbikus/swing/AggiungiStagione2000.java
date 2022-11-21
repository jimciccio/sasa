package com.intellij.bikerbikus.swing;

import com.intellij.bikerbikus.controllers1.AggiungiStagioneController1;
import com.intellij.bikerbikus.data.UserData;
import com.intellij.bikerbikus.models.Campionato;
import com.intellij.bikerbikus.models.Stagione;
import com.intellij.bikerbikus.utils.Utils;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AggiungiStagione2000 extends AggiungiStagioneController1 {
    private JPanel panel1;
    private JButton backButton;
    private JLabel nomeJLabel;
    private JComboBox<Campionato> championshipCombo;
    private JButton addButton;
    private JTextField dateFieldStart;
    private JTextField dateFieldEnd;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    JFrame frame;

    DefaultComboBoxModel<Campionato> modelChampionship = new DefaultComboBoxModel<>();

    public AggiungiStagione2000() {

        backButton.addActionListener(e -> new Campionati2000().mostra(frame));

        addButton.addActionListener(e -> {

            if(checkFields()){
                Campionato cam = (Campionato) championshipCombo.getSelectedItem();
                LocalDate localDateStart = LocalDate.parse(dateFieldStart.getText(), dateFormatter);
                LocalDate localDateEnd = LocalDate.parse(dateFieldEnd.getText(), dateFormatter);

                Stagione stagione = new Stagione();
                stagione.setNome(Integer.toString(localDateStart.getYear()));
                stagione.setDataInizio(LocalDate.of(localDateStart.getYear(), localDateStart.getMonthValue(), localDateStart.getDayOfMonth()));
                stagione.setDataFine(LocalDate.of(localDateEnd.getYear(), localDateEnd.getMonthValue(), localDateEnd.getDayOfMonth()));
                stagione.setCampionato(cam);

                if (AggiungiStagioneController1.onActionConferma(stagione)) {
                    JOptionPane.showMessageDialog(null,"Stagione creata con successo!");
                    new Campionati2000().mostra(frame);
                } else {
                    JOptionPane.showMessageDialog(null,"Non è stato possibile creare la stagione");
                }
            }
        });
    }

    public boolean checkFields(){

        if( championshipCombo.getSelectedItem() == null ||
                dateFieldStart.getText().equals("") || dateFieldEnd.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Inserisci tutti i valori!");
            return false;
        }else{
            if(checkDate(dateFieldStart.getText()) && checkDate(dateFieldEnd.getText())){
                return true;
            }else{
                JOptionPane.showMessageDialog(null,"La data inserita è errata");
                return false;
            }
        }
    }

    public boolean checkDate(String stringa){
        DateValidatorUsingLocalDate validator = new DateValidatorUsingLocalDate(dateFormatter);
        return validator.isValid(stringa);
    }

    public void mostra(JFrame frame){
        this.frame=frame;
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
        nomeJLabel.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        loadCampionati();
        modelChampionship.addAll(AggiungiStagioneController1.campionati);
        championshipCombo.setModel(modelChampionship);
    }
}