package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.controllers1.AggiungiLezioneController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Lezione;
import ferranti.bikerbikus.models.TipoLezione;
import ferranti.bikerbikus.models.Utente;
import ferranti.bikerbikus.utils.Utils;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class AggiungiLezioni2000 extends AggiungiLezioneController1 {
    private JPanel panel1;
    private JButton backButton;
    private JButton addButton;
    private JLabel nomeJLabel;
    private JComboBox<TipoLezione> typeCombo;
    private JCheckBox privateCheck;
    private JComboBox<Utente> masterCombo;
    private JComboBox<LocalTime> hourCombo;
    private JTextField dateField;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    JFrame frame;

    DefaultComboBoxModel<LocalTime> modelHour = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<TipoLezione> modelType = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<Utente> modelMaster = new DefaultComboBoxModel<>();

    public AggiungiLezioni2000() {

        backButton.addActionListener(e -> new Lezioni2000().mostra(frame));

        addButton.addActionListener(e -> {

            if(checkFields()){

                LocalTime hours= (LocalTime) hourCombo.getModel().getSelectedItem();
                LocalDate localDate = LocalDate.parse(dateField.getText(), dateFormatter);


                int hoursInt = hours.getHour();
                int minute = hours.getMinute();
                TipoLezione tipo = (TipoLezione) typeCombo.getSelectedItem();
                Utente master = (Utente) masterCombo.getSelectedItem();

                Lezione lezione = new Lezione();

                lezione.setData(LocalDateTime.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(), hoursInt,minute));

                lezione.setTipo(tipo);
                lezione.setPrivata(privateCheck.isSelected());
                lezione.setMaestro(master);

                if (AggiungiLezioneController1.onActionConferma(lezione)) {
                    JOptionPane.showMessageDialog(null,"Lezione creata con successo!");
                    new Lezioni2000().mostra(frame);
                } else {
                    JOptionPane.showMessageDialog(null,"Non è stato possibile inserire la lezione");
                }
            }
        });
    }

    public void mostra(JFrame frame){


        this.frame=frame;

        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);


        nomeJLabel.setText(
                Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));

        loadTipi();
        loadOrari();
        loadMaestri();
        modelHour.addAll(AggiungiLezioneController1.orari);
        modelMaster.addAll(AggiungiLezioneController1.maestri);
        modelType.addAll(AggiungiLezioneController1.tipi);

        hourCombo.setModel(modelHour);
        masterCombo.setModel(modelMaster);
        typeCombo.setModel(modelType);
    }

    public boolean checkFields(){

        if( masterCombo.getSelectedItem() == null ||
                hourCombo.getSelectedItem() == null || typeCombo.getSelectedItem() == null || dateField.getText().equals("")){
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
