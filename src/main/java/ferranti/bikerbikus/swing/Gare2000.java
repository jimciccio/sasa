package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.controllers1.GareController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.utils.Utils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class Gare2000 extends GareController1 {
    private JPanel panel1;
    private JButton backButton;
    private JButton addRacesButton;
    private JLabel nomeJLabel;
    private JLabel monthLabel;
    private JLabel yearLabel;
    private JTable table1;
    private JComboBox<String> monthBox;
    private JTextField yearsTextField;
    private JButton searchButton;

    Action join;

    JButton button = new JButton();

    String[] columns = {"Giorno", "Ora", "Campionato", "Prenotazione"};
    DefaultTableModel model = new DefaultTableModel(columns, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3;
        }
    };
    String[] months = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};

    DefaultComboBoxModel<String> modelCombo = new DefaultComboBoxModel<>(months);
    JFrame frame;

    public Gare2000() {

        backButton.addActionListener(e -> new Homepage2000().mostra(frame));

        searchButton.addActionListener(e -> search());

        addRacesButton.addActionListener(e -> new AggiungiGara2000().mostra(frame));

        join = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.parseInt( e.getActionCommand() );

                if(GareController1.gare.get(modelRow).getData().isBefore(LocalDateTime.now())){
                    button.setEnabled(false);
                    JOptionPane.showMessageDialog(null,"La gara è scaduta");

                }else{
                    button.setEnabled(true);
                    if(prenotaGara(GareController1.gare.get(modelRow).getId())){
                        JOptionPane.showMessageDialog(null,"Gara prenotata con successo!");
                        model.setRowCount(0);
                        item();
                    }else{
                        JOptionPane.showMessageDialog(null,"Non è stato possibile prenotare la gara.");
                    }
                }
            }
        };
    }
    public void mostra(JFrame frame){
        this.frame=frame;
        table1.setModel(model);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
        nomeJLabel.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        monthBox.setModel(modelCombo);
        monthBox.setSelectedItem(Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
        yearsTextField.setText(Integer.toString(getCurrentYearMonth().getYear()));
        item();
    }

    public void item(){
            super.loadGare();
            setValue();
    }

    public void search(){

        if(isNumber(yearsTextField.getText())){
            onActionSpecificMonth(monthBox.getSelectedIndex()+1,Integer.parseInt(yearsTextField.getText()));
            item();
        }else{
            JOptionPane.showMessageDialog(null,"La data inserita non è valida!");
        }
    }

    public void setValue(){
        deleteAllRows(model);
        monthLabel.setText(Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
        yearLabel.setText(Integer.toString(getCurrentYearMonth().getYear()));

        for (int i = 0; i < GareController1.gare.size(); i++) {

            model.addRow(new Object[]{Utils.uppercase(GareController1.gare.get(i).getData().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault())) + " "
                    + GareController1.gare.get(i).getData().getDayOfMonth(), Utils.formatTime(GareController1.gare.get(i).getData().getHour(), GareController1.gare.get(i).getData().getMinute()),
                    GareController1.gare.get(i).getStagione(), GareController1.gare.get(i).getId()});
        }

        ButtonColumn buttonColumn = new ButtonColumn(table1, join, 3, 3, 0);
        buttonColumn.setMnemonic(KeyEvent.VK_D);
    }

    static boolean isNumber(String s)
    {
        for (int i = 0; i < s.length(); i++)
            if (!Character.isDigit(s.charAt(i)))
                return false;
        return true;
    }

    public static void deleteAllRows(final DefaultTableModel model) {
        for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
            model.removeRow(i);
        }
    }
}