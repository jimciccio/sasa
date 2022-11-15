package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.controllers1.AggiungiGaraController1;
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

public class AggiungiGara2000 extends AggiungiGaraController1 {
    private JPanel panel1;
    private JButton backButton;
    private JButton addButton;
    private JLabel nomeJLabel;
    private JComboBox hourCombo;
    private JComboBox championCombo;
    private JPanel pickerPanel;
    private JTextField dateField;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;


    JFrame frame;

    DefaultComboBoxModel modelHour = new DefaultComboBoxModel();
    DefaultComboBoxModel modelChampion = new DefaultComboBoxModel();

    public AggiungiGara2000() {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Gare2000().mostra(frame);
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

                    Stagione stagione = (Stagione) championCombo.getSelectedItem();


                    Gara gara = new Gara();


                    gara.setData(LocalDateTime.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(), hoursInt,minute));

                    gara.setStagione(stagione);


                    if (AggiungiGaraController1.onActionConferma(gara)) {
                        JOptionPane.showMessageDialog(null,"Gara creata con successo!");
                        new Gare2000().mostra(frame);
                    } else {
                        JOptionPane.showMessageDialog(null,"Non è stato possibile inserire la gara");
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

        loadStagioni();
        loadOrari();
        modelHour.addAll(AggiungiGaraController1.orari);
        modelChampion.addAll(AggiungiGaraController1.stagioni);

        hourCombo.setModel(modelHour);
        championCombo.setModel(modelChampion);
    }

    public boolean checkFields(){

            if (championCombo.getSelectedItem() == null ||
                    hourCombo.getSelectedItem() == null || dateField.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Inserisci tutti i valori!");
                return false;
            } else {
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
