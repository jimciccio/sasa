package ferranti.bikerbikus.swingGraphic;

import ferranti.bikerbikus.controllers1.ModificaRecensioneController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.utils.Utils;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.Properties;

public class ModificaRecensioni2000 extends ModificaRecensioneController1 {
    private JPanel panel1;
    private JButton backButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JTable table1;
    private JLabel nomeJLabel;
    private static JDialog d;

    JButton button = new JButton();

    String columnsPlace[] = {"Luogo", "Recensione", "Data", "Valutazione"};

    DefaultTableModel modelPlace = new DefaultTableModel(columnsPlace, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3;
        }
    };

    JFrame frame;


    public ModificaRecensioni2000() {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Recensioni2000().mostra(frame);
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                DialogExample(table1.getSelectedRow());
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                onActionElimina(ModificaRecensioneController1.recensioni.get(table1.getSelectedRow()).getId());
                JOptionPane.showMessageDialog(null,"Recensione eliminata con successo.");
                setValue();

            }
        });
    }

    public void mostra(JFrame frame){

        this.frame=frame;
        table1.setModel(modelPlace);


        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);


        nomeJLabel.setText(
                Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));



        item();

    }

    public void item(){

        loadRecensioni();
        setValue();

    }

    public void setValue(){
        deleteAllRows(modelPlace);



        for (int i = 0; i < ModificaRecensioneController1.recensioni.size(); i++) {

            modelPlace.addRow(new Object[]{ModificaRecensioneController1.recensioni.get(i).getLuogo().getNome(), ModificaRecensioneController1.recensioni.get(i).getRecensioneString(),
                    ModificaRecensioneController1.recensioni.get(i).getData(), ModificaRecensioneController1.recensioni.get(i).getValutazione()});

        }
        table1.setRowSelectionInterval(0, 0);

    }



    public static void deleteAllRows(final DefaultTableModel model) {
        for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
            model.removeRow(i);
        }
    }



    public  void DialogExample(int id) {
        JButton btnOk;
        JButton btnCancel;


        JFrame f= new JFrame();
        d = new JDialog(f , "Modify "+ModificaRecensioneController1.recensioni.get(id).getLuogo().getNome(), true);
        d.setLayout( new FlowLayout() );

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField descriptionField = new JTextField();
        JTextField starField = new JTextField();


        JPanel pickerPanel= new JPanel();
        UtilDateModel model = new UtilDateModel();
        JDatePickerImpl datePicker;

        Properties p = new Properties();

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBounds(220,350,120,30);

        pickerPanel.add(datePicker);


        gbc.insets = new Insets(2,2,2,2);

        JLabel descriptionLabel = new JLabel("Description:");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(descriptionLabel,gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(descriptionField,gbc);

        JLabel dateLabel = new JLabel("Date:");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(dateLabel,gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(datePicker,gbc);

        JLabel starLabel = new JLabel("Rate:");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(starLabel,gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(starField,gbc);

        datePicker.getModel().setDate(ModificaRecensioneController1.recensioni.get(id).getData().getYear(), ModificaRecensioneController1.recensioni.get(id).getData().getMonthValue()-1, ModificaRecensioneController1.recensioni.get(id).getData().getDayOfMonth());
        datePicker.getModel().setSelected(true);

        descriptionField.setText(ModificaRecensioneController1.recensioni.get(id).getRecensioneString());
        starField.setText(ModificaRecensioneController1.recensioni.get(id).getValutazione().toString());

        btnOk = new JButton("Ok");


        btnOk.addActionListener (new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                System.out.println("data nuova"+ starField.getText()+ " data vecchia"+ModificaRecensioneController1.recensioni.get(id).getValutazione().toString());

                if(LocalDate.of(datePicker.getModel().getYear(), datePicker.getModel().getMonth()+1, datePicker.getModel().getDay()).equals(ModificaRecensioneController1.recensioni.get(id).getData())  && descriptionField.getText().equals(ModificaRecensioneController1.recensioni.get(id).getRecensioneString()) &&  starField.getText().equals(ModificaRecensioneController1.recensioni.get(id).getValutazione().toString())){

                    JOptionPane.showMessageDialog(null,"Modifica almeno un dato!");

                }else{
                    onActionModifica(ModificaRecensioneController1.recensioni.get(id).getId(), descriptionField.getText(),  LocalDate.of(datePicker.getModel().getYear(), datePicker.getModel().getMonth()+1, datePicker.getModel().getDay()), Double.valueOf(starField.getText()));
                    setValue();
                    d.setVisible(false);
                    d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
                    JOptionPane.showMessageDialog(null," Modifica effettuata");
                }
            }
        });
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(btnOk,gbc);


        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                d.setVisible(false);
                d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(btnCancel,gbc);
        d.add(panel);
        d.setSize(500,300);
        d.setLocationRelativeTo(null);

        d.setVisible(true);
    }
}