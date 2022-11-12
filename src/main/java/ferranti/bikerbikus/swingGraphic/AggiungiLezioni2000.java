package ferranti.bikerbikus.swingGraphic;

import ferranti.bikerbikus.controllers1.AggiungiLezioneController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Lezione;
import ferranti.bikerbikus.models.TipoLezione;
import ferranti.bikerbikus.models.Utente;
import ferranti.bikerbikus.utils.Utils;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Properties;


public class AggiungiLezioni2000 extends AggiungiLezioneController1 {
    private JPanel panel1;
    private JButton backButton;
    private JButton addButton;
    private JLabel nomeJLabel;
    private JComboBox<TipoLezione> typeCombo;
    private JCheckBox privateCheck;
    private JComboBox masterCombo;
    private JComboBox<LocalTime> hourCombo;
    private JPanel pickerPanel;

    JDatePickerImpl datePicker;

    JFrame frame;

    DefaultComboBoxModel modelHour = new DefaultComboBoxModel();
    DefaultComboBoxModel modelType = new DefaultComboBoxModel();
    DefaultComboBoxModel modelMaster = new DefaultComboBoxModel();

    public AggiungiLezioni2000() {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Lezioni2000().mostra(frame);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(checkFields()){

                    LocalTime hours= (LocalTime) hourCombo.getModel().getSelectedItem();

                    int hoursInt = hours.getHour();
                    int minute = hours.getMinute();
                    TipoLezione tipo = (TipoLezione) typeCombo.getSelectedItem();
                    Utente master = (Utente) masterCombo.getSelectedItem();

                    Lezione lezione = new Lezione();
                    lezione.setData(LocalDateTime.of(datePicker.getModel().getYear(), datePicker.getModel().getMonth()+1, datePicker.getModel().getDay(), hoursInt,minute));
                    lezione.setTipo(tipo);
                    lezione.setPrivata(privateCheck.isSelected());
                    lezione.setMaestro(master);

                    if (AggiungiLezioneController1.onActionConferma(lezione)) {
                        JOptionPane.showMessageDialog(null,"Lezione creata con successo!");
                        new Lezioni2000().mostra(frame);
                    } else {
                        JOptionPane.showMessageDialog(null,"Non è stato possibile inserire la lezione");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Inserisci tutti i valori!");
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
                hourCombo.getSelectedItem() == null || typeCombo.getSelectedItem() == null || datePicker.getModel().isSelected() == false){
                System.out.println("date"+"master"+masterCombo.getSelectedItem()+"tipo"+typeCombo.getSelectedItem()+"ora"+hourCombo.getSelectedItem());

            return false;
        }else{
            return true;
        }
    }

    private void createUIComponents() {
        pickerPanel= new JPanel();
        UtilDateModel model = new UtilDateModel();

        Properties p = new Properties();

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBounds(220,350,120,30);


        pickerPanel.add(datePicker);
    }
}
