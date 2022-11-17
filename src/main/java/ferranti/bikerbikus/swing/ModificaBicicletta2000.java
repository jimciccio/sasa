package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.controllers1.ModificaBiciclettaController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class ModificaBicicletta2000 extends ModificaBiciclettaController1 {
    private JPanel panel1;
    private JButton backButton;
    private JLabel nomeJLabel;
    private JTable table1;
    private JTable table2;

    Action modifyBuyable;
    Action modifyRentable;

    JButton button = new JButton();

    String[] columnsBuyable = {"Modello", "Caratteristiche", "Prezzo", "Disponibili", "Modifica"};
    DefaultTableModel modelBuyable = new DefaultTableModel(columnsBuyable, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4;
        }
    };

    String[] columnsRentable = {"Modello", "Caratteristiche", "Prezzo", "Prenotazione", "Modifica"};

    DefaultTableModel modelRentable = new DefaultTableModel(columnsRentable, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4;
        }
    };

    JFrame frame;

    public ModificaBicicletta2000() {

        backButton.addActionListener(e -> new Shop2000().mostra(frame));

        modifyBuyable = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.parseInt( e.getActionCommand() );
                dialogExample(modelRow,0);
            }
        };

        modifyRentable = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.parseInt( e.getActionCommand() );
                dialogExample(modelRow,1);
            }
        };
    }

    public void mostra(JFrame frame){
        this.frame=frame;
        table1.setModel(modelBuyable);
        table2.setModel(modelRentable);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
        nomeJLabel.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        item();
    }

    public void item(){
        loadModelli();
        loadModelliNoleggiabili();
        setValue();
    }

    public void setValue(){
        deleteAllRows(modelBuyable);
        deleteAllRows(modelRentable);

        for (int i = 0; i < ModificaBiciclettaController1.bicicletteVendita.size(); i++) {
            modelBuyable.addRow(new Object[]{ModificaBiciclettaController1.bicicletteVendita.get(i).getModello(), ModificaBiciclettaController1.bicicletteVendita.get(i).getCaratteristiche(),
                    ModificaBiciclettaController1.bicicletteVendita.get(i).getPrezzo(), ModificaBiciclettaController1.bicicletteVendita.get(i).getDisponibili(), ModificaBiciclettaController1.bicicletteVendita.get(i).getId()});
        }
        new ButtonColumn(table1, modifyBuyable, 4, 2, 0);

        for (int i = 0; i < ModificaBiciclettaController1.bicicletteNoleggiate.size(); i++) {

            modelRentable.addRow(new Object[]{ModificaBiciclettaController1.bicicletteNoleggiate.get(i).getModello(), ModificaBiciclettaController1.bicicletteNoleggiate.get(i).getCaratteristiche(),
                    ModificaBiciclettaController1.bicicletteNoleggiate.get(i).getPrezzo(), ModificaBiciclettaController1.bicicletteNoleggiate.get(i).getStatus(), ModificaBiciclettaController1.bicicletteNoleggiate.get(i).getId()});
        }
        new ButtonColumn(table2, modifyRentable, 4, 2, 0);
    }

    public static void deleteAllRows(final DefaultTableModel model) {
        for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
            model.removeRow(i);
        }
    }

    public void dialogExample(int id, int counter) {
        JButton btnOk;
        JButton btnCancel;
        JTextField modBox;
        JTextField carBox;
        JTextField prezzoBox;
        JTextField dispBox;
        JDialog d;
        JCheckBox manuCheck = new JCheckBox();

        JFrame f= new JFrame();
        d = new JDialog(f , "Modifica bicicletta ", true);
        d.setLayout( new FlowLayout() );

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(2,2,2,2);
        JLabel modLabel = new JLabel("Modello:");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(modLabel,gbc);

        JLabel carLabel = new JLabel("Caratteristiche:");
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(carLabel,gbc);

        JLabel prezzoLabel = new JLabel("Prezzo:");
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(prezzoLabel,gbc);

        dispBox = new JTextField(30);
        if(counter==0){
            modBox = new JTextField(30);
            gbc.gridwidth = 2;
            gbc.gridx = 1;
            gbc.gridy = 0;
            panel.add(modBox,gbc);
            modBox.setText(ModificaBiciclettaController1.bicicletteVendita.get(id).getModello());

            carBox = new JTextField(30);
            gbc.gridwidth = 2;
            gbc.gridx = 1;
            gbc.gridy = 1;
            panel.add(carBox,gbc);
            carBox.setText(ModificaBiciclettaController1.bicicletteVendita.get(id).getCaratteristiche());

            prezzoBox = new JTextField(30);
            gbc.gridwidth = 2;
            gbc.gridx = 1;
            gbc.gridy = 2;
            panel.add(prezzoBox,gbc);
            prezzoBox.setText(Integer.toString(ModificaBiciclettaController1.bicicletteVendita.get(id).getPrezzo()));

            JLabel dispLabel = new JLabel("Disponibili:");
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = 3;
            panel.add(dispLabel,gbc);

            gbc.gridwidth = 2;
            gbc.gridx = 1;
            gbc.gridy = 3;
            panel.add(dispBox,gbc);
            dispBox.setText(Integer.toString(ModificaBiciclettaController1.bicicletteVendita.get(id).getDisponibili()));

        }else{
            modBox = new JTextField(30);
            gbc.gridwidth = 2;
            gbc.gridx = 1;
            gbc.gridy = 0;
            panel.add(modBox,gbc);
            modBox.setText(ModificaBiciclettaController1.bicicletteNoleggiate.get(id).getModello());

            carBox = new JTextField(30);
            gbc.gridwidth = 2;
            gbc.gridx = 1;
            gbc.gridy = 1;
            panel.add(carBox,gbc);
            carBox.setText(ModificaBiciclettaController1.bicicletteNoleggiate.get(id).getCaratteristiche());

            prezzoBox = new JTextField(30);
            gbc.gridwidth = 2;
            gbc.gridx = 1;
            gbc.gridy = 2;
            panel.add(prezzoBox,gbc);
            prezzoBox.setText(Integer.toString(ModificaBiciclettaController1.bicicletteNoleggiate.get(id).getPrezzo()));

            if(ModificaBiciclettaController1.bicicletteNoleggiate.get(id).getStatus().equals("Noleggiabile")){

                JLabel manutenzioneLabel = new JLabel("Manutenzione:");
                gbc.gridwidth = 1;
                gbc.gridx = 0;
                gbc.gridy = 3;
                panel.add(manutenzioneLabel,gbc);

                gbc.gridwidth = 2;
                gbc.gridx = 1;
                gbc.gridy = 3;
                panel.add(manuCheck,gbc);

            }else if(ModificaBiciclettaController1.bicicletteNoleggiate.get(id).getStatus().equals("Manutenzione")){
                JLabel manutenzioneLabel = new JLabel("Manutenzione:");
                gbc.gridwidth = 1;
                gbc.gridx = 0;
                gbc.gridy = 3;
                panel.add(manutenzioneLabel,gbc);

                manuCheck.setSelected(true);
                gbc.gridwidth = 2;
                gbc.gridx = 1;
                gbc.gridy = 3;
                panel.add(manuCheck,gbc);

            }
        }

        btnOk = new JButton("Ok");

        btnOk.addActionListener (e -> {
            if(counter==0){
                check2(modBox, carBox, prezzoBox, dispBox, d, id);
            }else{
                check3(modBox, carBox, prezzoBox, manuCheck, d, id);
            }
        });

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener (e -> {
            d.setVisible(false);
            d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
        });

        gbc.gridwidth = 1;
        gbc.gridy = 4;
        gbc.gridx = 0;
        panel.add(btnOk,gbc);

        gbc.gridy = 4;
        gbc.gridx = 1;
        panel.add(btnCancel,gbc);
        d.add(panel);
        d.setSize(600,600);
        d.setLocationRelativeTo(null);

        d.setVisible(true);
    }

    public void check2(JTextField modBox, JTextField carBox, JTextField prezzoBox, JTextField dispBox, JDialog d, int id){
        if(modBox.getText().equals(ModificaBiciclettaController1.bicicletteVendita.get(id).getModello()) && carBox.getText().equals(ModificaBiciclettaController1.bicicletteVendita.get(id).getCaratteristiche()) && prezzoBox.getText().equals(Integer.toString(ModificaBiciclettaController1.bicicletteVendita.get(id).getPrezzo())) && dispBox.getText().equals(Integer.toString(ModificaBiciclettaController1.bicicletteVendita.get(id).getDisponibili()))) {
            JOptionPane.showMessageDialog(null,"Modifica almeno un dato!");
        }else{
            ModificaBiciclettaController1.modBiciNuova(ModificaBiciclettaController1.bicicletteVendita.get(id).getId(), modBox.getText(),
                    carBox.getText(), Integer.parseInt(prezzoBox.getText()), Integer.parseInt(dispBox.getText()));
            setValue();
            d.setVisible(false);
            d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void check3(JTextField modBox, JTextField carBox, JTextField prezzoBox, JCheckBox manuCheck, JDialog d, int id){
        int finalCheck = Boolean.TRUE.equals(manuCheck.isSelected()) ? 1 : 0;
        if(modBox.getText().equals(ModificaBiciclettaController1.bicicletteNoleggiate.get(id).getModello()) && carBox.getText().equals(ModificaBiciclettaController1.bicicletteNoleggiate.get(id).getCaratteristiche()) && prezzoBox.getText().equals(Integer.toString(ModificaBiciclettaController1.bicicletteNoleggiate.get(id).getPrezzo())) &&  finalCheck ==  ModificaBiciclettaController1.bicicletteNoleggiate.get(id).getManutenzione()) {
            JOptionPane.showMessageDialog(null,"Modifica almeno un dato!");
        }else{
            ModificaBiciclettaController1.modBiciNoleggiata(ModificaBiciclettaController1.bicicletteNoleggiate.get(id).getId(), modBox.getText(),
                    carBox.getText(), Integer.parseInt(prezzoBox.getText()), manuCheck.isSelected());
            setValue();
            d.setVisible(false);
            d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
        }
    }

}