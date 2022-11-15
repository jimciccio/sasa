package ferranti.bikerbikus.swingGraphic;

import ferranti.bikerbikus.controllers1.ModificaRecensioneController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.utils.Utils;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class ModificaRecensioni2000 extends ModificaRecensioneController1 {
    private JPanel panel1;
    private JButton backButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JTable table1;
    private JLabel nomeJLabel;
    private static JDialog d;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE; // example '2011-12-03'


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
        JTextField starField = new JTextField();

        JTextArea descriptionField = new JTextArea(8,20);
        descriptionField.setLineWrap(true);

        JPanel pickerPanel= new JPanel();
        JTextField dateField = new JTextField();
        dateField.setSize(75,-1);
        pickerPanel.add(dateField);


        gbc.insets = new Insets(2,2,2,2);

        JLabel descriptionLabel = new JLabel("Description:");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(descriptionLabel,gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(new JScrollPane(descriptionField),gbc);

        JLabel dateLabel = new JLabel("Date:");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(dateLabel,gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(dateField,gbc);

        JLabel formatLabel = new JLabel("(yyyy-mm-dd)");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(formatLabel,gbc);

        JLabel starLabel = new JLabel("Rate:");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(starLabel,gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(starField,gbc);

        dateField.setText(ModificaRecensioneController1.recensioni.get(id).getData().toString());

        descriptionField.setText(ModificaRecensioneController1.recensioni.get(id).getRecensioneString());
        starField.setText(ModificaRecensioneController1.recensioni.get(id).getValutazione().toString());

        btnOk = new JButton("Ok");


        btnOk.addActionListener (new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                if(checkDate(dateField.getText())){
                    LocalDate localDate = LocalDate.parse(dateField.getText(), dateFormatter);

                    if(localDate.equals(ModificaRecensioneController1.recensioni.get(id).getData())  && descriptionField.getText().equals(ModificaRecensioneController1.recensioni.get(id).getRecensioneString()) &&  starField.getText().equals(ModificaRecensioneController1.recensioni.get(id).getValutazione().toString())){

                        JOptionPane.showMessageDialog(null,"Modifica almeno un dato!");

                    }else{
                            onActionModifica(ModificaRecensioneController1.recensioni.get(id).getId(), descriptionField.getText(),  LocalDate.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()), Double.valueOf(starField.getText()));
                            setValue();
                            d.setVisible(false);
                            d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
                            JOptionPane.showMessageDialog(null," Modifica effettuata");
                        }
                }else{
                    JOptionPane.showMessageDialog(null," Data non valida!");
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

    public boolean checkDate(String stringa){

        DateValidatorUsingLocalDate validator = new DateValidatorUsingLocalDate(dateFormatter);

        if(validator.isValid(stringa) ){
            return true;
        }else{
            return false;
        }
    }
}