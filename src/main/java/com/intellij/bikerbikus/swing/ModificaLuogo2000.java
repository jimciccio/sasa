package com.intellij.bikerbikus.swing;

import com.intellij.bikerbikus.controllers1.ModificaLuogoController1;
import com.intellij.bikerbikus.data.UserData;
import com.intellij.bikerbikus.utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowEvent;

public class ModificaLuogo2000 extends ModificaLuogoController1 {
    private JPanel panel1;
    private JTable table1;
    private JButton backButton;
    private JLabel nomeJLabel;
    private JButton modifyButton;


    JButton button = new JButton();

    String[] columnsPlace = {"Nome", "Descrizione", "Difficoltà", "Valutazione"};

    DefaultTableModel modelPlace = new DefaultTableModel(columnsPlace, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3;
        }
    };

    JFrame frame;

    public ModificaLuogo2000() {

        backButton.addActionListener(e -> new Recensioni2000().mostra(frame));

        modifyButton.addActionListener(e -> dialogExample(table1.getSelectedRow()));
    }

    public void mostra(JFrame frame){
        this.frame=frame;
        table1.setModel(modelPlace);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
        nomeJLabel.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        frame.pack();
        item();
    }

    public void item(){
        loadLuoghi();
        setValue();
    }

    public void setValue(){
        deleteAllRows(modelPlace);

        for (int i = 0; i < ModificaLuogoController1.luoghi.size(); i++) {

            modelPlace.addRow(new Object[]{ModificaLuogoController1.luoghi.get(i).getNome(), ModificaLuogoController1.luoghi.get(i).getDescrizione(),
                    ModificaLuogoController1.luoghi.get(i).getDifficolta(), ModificaLuogoController1.luoghi.get(i).getValutazioneMedia()});
        }
        table1.setRowSelectionInterval(0, 0);
    }




    public  void dialogExample(int id) {
        JButton btnOk;
        JButton btnCancel2;
        JDialog d;

        JFrame f= new JFrame();
        d = new JDialog(f , "Modify "+ModificaLuogoController1.luoghi.get(id).getNome(), true);
        d.setLayout( new FlowLayout() );

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField descriptionField = new JTextField();
        JTextField nameField = new JTextField();

        JTextField difficultyField = new JTextField();

        gbc.insets = new Insets(2,2,2,2);

        JLabel nameLabel = new JLabel("Name:");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel,gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(nameField,gbc);

        JLabel descriptionLabel = new JLabel("Description:");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(descriptionLabel,gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(descriptionField,gbc);

        JLabel difficultiLabel = new JLabel("Difficulty:");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(difficultiLabel,gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(difficultyField,gbc);

        nameField.setText(ModificaLuogoController1.luoghi.get(id).getNome());
        descriptionField.setText(ModificaLuogoController1.luoghi.get(id).getDescrizione());
        difficultyField.setText(Integer.toString(ModificaLuogoController1.luoghi.get(id).getDifficolta()));

        btnOk = new JButton("Ok");

        btnOk.addActionListener (e -> {

            if( nameField.getText().equals(ModificaLuogoController1.luoghi.get(id).getNome()) && descriptionField.getText().equals(ModificaLuogoController1.luoghi.get(id).getDescrizione()) &&  difficultyField.getText().equals(Integer.toString(ModificaLuogoController1.luoghi.get(id).getDifficolta()))){

                JOptionPane.showMessageDialog(null,"Modifica almeno un dato!");
            }else{
                modLuogo(ModificaLuogoController1.luoghi.get(id).getId(), nameField.getText(), descriptionField.getText(), Integer.parseInt(difficultyField.getText()));
                setValue();
                d.setVisible(false);
                d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
                JOptionPane.showMessageDialog(null," Modifica effettuata");
            }
        });
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(btnOk,gbc);

        btnCancel2 = new JButton("Cancel");
        btnCancel2.addActionListener (e -> {
            d.setVisible(false);
            d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
        });

        gbc.gridx = 2;
        gbc.gridy = 3;
        panel.add(btnCancel2,gbc);
        d.add(panel);
        d.setSize(500,300);
        d.setLocationRelativeTo(null);

        d.setVisible(true);
    }

    public static void deleteAllRows(final DefaultTableModel model) {
        for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
            model.removeRow(i);
        }
    }
}