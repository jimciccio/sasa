package ferranti.bikerbikus.swingGraphic;

import ferranti.bikerbikus.controllers1.AggiungiGaraController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.*;
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

public class AggiungiGara2000 extends AggiungiGaraController1 {
    private JPanel panel1;
    private JButton backButton;
    private JButton addButton;
    private JLabel nomeJLabel;
    private JComboBox hourCombo;
    private JComboBox championCombo;
    private JPanel pickerPanel;

    JDatePickerImpl datePicker;

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

                    int hoursInt = hours.getHour();
                    int minute = hours.getMinute();

                    Stagione stagione = (Stagione) championCombo.getSelectedItem();


                    Gara gara = new Gara();
                    gara.setData(LocalDateTime.of(datePicker.getModel().getYear(), datePicker.getModel().getMonth()+1, datePicker.getModel().getDay(), hoursInt,minute));
                    gara.setStagione(stagione);


                    if (AggiungiGaraController1.onActionConferma(gara)) {
                        JOptionPane.showMessageDialog(null,"Gara creata con successo!");
                        new Gare2000().mostra(frame);
                    } else {
                        JOptionPane.showMessageDialog(null,"Non è stato possibile inserire la gara");
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

        loadStagioni();
        loadOrari();
        modelHour.addAll(AggiungiGaraController1.orari);
        modelChampion.addAll(AggiungiGaraController1.stagioni);

        hourCombo.setModel(modelHour);
        championCombo.setModel(modelChampion);
    }

    public boolean checkFields(){

        if( championCombo.getSelectedItem() == null ||
                hourCombo.getSelectedItem() == null || datePicker.getModel().isSelected() == false){

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
