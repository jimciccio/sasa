package ferranti.bikerbikus.swingGraphic;

import ferranti.bikerbikus.controllers1.AggiungiStagioneController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Campionato;
import ferranti.bikerbikus.models.Stagione;
import ferranti.bikerbikus.utils.Utils;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Properties;

public class AggiungiStagione2000 extends AggiungiStagioneController1 {
    private JPanel panel1;
    private JButton backButton;
    private JLabel nomeJLabel;
    private JLabel tipoJLabel;
    private JComboBox championshipCombo;
    private JPanel pickerPanelEnd;
    private JPanel pickerPanelStart;
    private JButton addButton;

    JDatePickerImpl datePickerStart;
    JDatePickerImpl datePickerEnd;


    JFrame frame;

    DefaultComboBoxModel modelChampionship = new DefaultComboBoxModel();

    public AggiungiStagione2000() {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Campionati2000().mostra(frame);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(checkFields()){
                    Campionato cam = (Campionato) championshipCombo.getSelectedItem();

                    Stagione stagione = new Stagione();
                    stagione.setNome(Integer.toString(datePickerStart.getModel().getYear()));
                    stagione.setDataInizio(LocalDate.of(datePickerStart.getModel().getYear(), datePickerStart.getModel().getMonth()+1, datePickerStart.getModel().getDay()));
                    stagione.setDataFine(LocalDate.of(datePickerEnd.getModel().getYear(), datePickerEnd.getModel().getMonth()+1, datePickerEnd.getModel().getDay()));
                    stagione.setCampionato(cam);

                    if (AggiungiStagioneController1.onActionConferma(stagione)) {
                        JOptionPane.showMessageDialog(null,"Stagione creata con successo!");
                        new Campionati2000().mostra(frame);
                    } else {
                        JOptionPane.showMessageDialog(null,"Non è stato possibile creare la stagione");
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

        loadCampionati();
        modelChampionship.addAll(AggiungiStagioneController1.campionati);
        championshipCombo.setModel(modelChampionship);

    }

    public boolean checkFields(){

        if( championshipCombo.getSelectedItem() == null || datePickerStart.getModel().isSelected() == false || datePickerEnd.getModel().isSelected() == false){

            return false;
        }else{
            return true;
        }
    }

    private void createUIComponents() {

        pickerPanelStart= new JPanel();
        UtilDateModel modelStart = new UtilDateModel();

        Properties p = new Properties();

        JDatePanelImpl datePanelStart = new JDatePanelImpl(modelStart, p);
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePickerStart = new JDatePickerImpl(datePanelStart, new DateLabelFormatter());
        datePickerStart.setBounds(220,350,120,30);

        pickerPanelStart.add(datePickerStart);




        pickerPanelEnd= new JPanel();
        UtilDateModel modelEnd = new UtilDateModel();


        JDatePanelImpl datePanelEnd = new JDatePanelImpl(modelEnd, p);
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePickerEnd = new JDatePickerImpl(datePanelEnd, new DateLabelFormatter());
        datePickerEnd.setBounds(220,350,120,30);

        pickerPanelEnd.add(datePickerEnd);

    }
}
