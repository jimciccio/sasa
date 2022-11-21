package com.intellij.bikerbikus.swing;

import com.intellij.bikerbikus.controllers1.ShopController1;
import com.intellij.bikerbikus.utils.Utils;
import com.intellij.bikerbikus.data.UserData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Shop2000 extends ShopController1 {
    private JPanel panel1;
    private JTable table1;
    private JButton backButton;
    private JLabel nomeJLabel;
    private JTable table2;
    private JButton addBikeButton;
    private JButton modifyBikeButton;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    Action buy;
    Action rent;

    JButton button = new JButton();

    String[] columnsBuy = {"Modello", "Caratteristiche", "Prezzo", "Compra"};
    DefaultTableModel modelBuy = new DefaultTableModel(columnsBuy, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3;
        }
    };

    String[] columnsRent = {"Modello", "Caratteristiche", "Prezzo", "Noleggia"};

    DefaultTableModel modelRent = new DefaultTableModel(columnsRent, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3;
        }
    };

    JFrame frame;

    public Shop2000() {

        backButton.addActionListener(e -> new Homepage2000().mostra(frame));

        addBikeButton.addActionListener(e -> new AggiungiBicicletta2000().mostra(frame));

        modifyBikeButton.addActionListener(e -> new ModificaBicicletta2000().mostra(frame));

        buy = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.parseInt( e.getActionCommand() );
                ShopController1.buyBicicletta(modelRow);
                JOptionPane.showMessageDialog(null,"Bicicletta comprata con successo");
            }
        };

        rent = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.parseInt( e.getActionCommand() );
                dialogExample(modelRow);
            }
        };
    }

    public void mostra(JFrame frame){
        this.frame=frame;
        table1.setModel(modelBuy);
        table2.setModel(modelRent);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
        nomeJLabel.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        item();
    }

    public void item(){
            loadBicicletteComprabili();
            loadBicicletteNoleggiabili();
            setValue();
    }

    public void setValue(){
        deleteAllRows(modelBuy);
        deleteAllRows(modelRent);

        for (int i = 0; i < ShopController1.bicicletteVendita.size(); i++) {
            modelBuy.addRow(new Object[]{ShopController1.bicicletteVendita.get(i).getModello(), ShopController1.bicicletteVendita.get(i).getCaratteristiche(),
                    ShopController1.bicicletteVendita.get(i).getPrezzo(), ShopController1.bicicletteVendita.get(i).getId()});
        }

        new ButtonColumn(table1, buy, 3, 5, 0);

        for (int i = 0; i < ShopController1.bicicletteNoleggio.size(); i++) {
            modelRent.addRow(new Object[]{ShopController1.bicicletteNoleggio.get(i).getModello(), ShopController1.bicicletteNoleggio.get(i).getCaratteristiche(),
                    ShopController1.bicicletteNoleggio.get(i).getPrezzo(), ShopController1.bicicletteNoleggio.get(i).getId()});
        }
        new ButtonColumn(table2, rent, 3, 5, 1);
    }

    public static void deleteAllRows(final DefaultTableModel model) {
        for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
            model.removeRow(i);
        }
    }

    public  void dialogExample(int id) {

        JButton btnOk;
        JButton btnCancel1;
        JDialog d;

        JFrame f= new JFrame();
        d = new JDialog(f , "Fino a quando vuoi noleggiare ", true);
        d.setLayout( new FlowLayout() );

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel pickerPanel= new JPanel();
        JTextField dateField = new JTextField();
        dateField.setSize(75,-1);
        pickerPanel.add(dateField);

        gbc.insets = new Insets(2,2,2,2);
        JLabel dateLabel = new JLabel("Data:");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(dateLabel,gbc);

        gbc.gridwidth = 2;
        gbc.gridy = 0;
        gbc.gridx = 1;
        panel.add(dateField,gbc);

        JLabel formatLabel = new JLabel("(yyyy-mm-dd)");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(formatLabel,gbc);

        btnOk = new JButton("Ok");

        btnOk.addActionListener (e -> {
            if(dateField.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Inserisci una data!");
            }else if(checkDate2(dateField.getText())){
                LocalDate localDate = LocalDate.parse(dateField.getText(), dateFormatter);

                rentBicicletta(id, LocalDate.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()));
                setValue();
                d.setVisible(false);
                d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
                JOptionPane.showMessageDialog(null,"Bicicletta noleggiata con successo");
            }else{
                JOptionPane.showMessageDialog(null,"Inserisci una data valida!");
            }
        });


        btnCancel1 = new JButton("Cancel");
        btnCancel1.addActionListener (e -> {
            d.setVisible(false);
            d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
        });

        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(btnOk,gbc);

        gbc.gridy = 2;
        gbc.gridx = 1;
        panel.add(btnCancel1,gbc);
        d.add(panel);
        d.setSize(400,250);
        d.setLocationRelativeTo(null);
        d.setVisible(true);
    }

    public boolean checkDate2(String stringa){
        DateValidatorUsingLocalDate validator = new DateValidatorUsingLocalDate(dateFormatter);
        return validator.isValid(stringa);
    }
}