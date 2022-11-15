package ferranti.bikerbikus.swingGraphic;

import ferranti.bikerbikus.controllers1.AggiungiEscursioneController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.*;
import ferranti.bikerbikus.utils.Utils;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class AggiungiEscursioni2000 extends AggiungiEscursioneController1 {
    private JPanel panel1;
    private JLabel nomeJLabel;
    private JButton backButton;
    private JButton addButton;
    private JComboBox hourCombo;
    private JComboBox placeCombo;
    private JComboBox companionCombo;
    private JPanel pickerPanel;
    private JTextField dateField;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE; // example '2011-12-03'



    JFrame frame;

    DefaultComboBoxModel modelHour = new DefaultComboBoxModel();
    DefaultComboBoxModel modelPlace = new DefaultComboBoxModel();
    DefaultComboBoxModel modelCompanion = new DefaultComboBoxModel();

    public AggiungiEscursioni2000() {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Escursioni2000().mostra(frame);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(checkFields()){

                    LocalTime hours= (LocalTime) hourCombo.getModel().getSelectedItem();
                    LocalDate localDate = LocalDate.parse(dateField.getText(), dateFormatter);

                    int hoursInt = hours.getHour();
                    int minute = hours.getMinute();
                    Utente companion = (Utente) companionCombo.getSelectedItem();
                    Luoghi luogo = (Luoghi) placeCombo.getSelectedItem();

                    Escursione escursione = new Escursione();


                    escursione.setData(LocalDateTime.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth() , hoursInt,minute));
                    escursione.setLuogo(luogo);
                    escursione.setAccompagnatore(companion);

                    if (AggiungiEscursioni2000.onActionConferma(escursione)) {
                        JOptionPane.showMessageDialog(null,"Escursione creata con successo!");
                        new Escursioni2000().mostra(frame);
                    } else {
                        JOptionPane.showMessageDialog(null,"Non è stato possibile inserire l'escursione");
                    }
                }
            }
        });
    }

    public void mostra(JFrame frame){


        this.frame=frame;

        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        nomeJLabel.setText(
                Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));

        loadAccompagnatori();
        loadOrari();
        loadLuoghi();
        modelHour.addAll(AggiungiEscursioni2000.orari);
        modelPlace.addAll(AggiungiEscursioni2000.luoghi);
        modelCompanion.addAll(AggiungiEscursioni2000.accompagnatori);

        hourCombo.setModel(modelHour);
        placeCombo.setModel(modelPlace);
        companionCombo.setModel(modelCompanion);
    }

    public boolean checkFields(){

            if( companionCombo.getSelectedItem() == null ||
                    placeCombo.getSelectedItem() == null || hourCombo.getSelectedItem() == null || dateField.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Inserisci tutti i valori!");
                return false;
            }else{
                if(checkDate()){
                    return true;
                }else{
                    JOptionPane.showMessageDialog(null,"La data inserita è errata");
                    return false;
                }
            }
    }

    public boolean checkDate(){

        DateValidatorUsingLocalDate validator = new DateValidatorUsingLocalDate(dateFormatter);

        if(validator.isValid(dateField.getText()) ){
            return true;
        }else{
            return false;
        }
    }
}
