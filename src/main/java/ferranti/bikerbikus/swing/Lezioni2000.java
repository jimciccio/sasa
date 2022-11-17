package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.controllers1.LezioniController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.utils.Utils;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class Lezioni2000 extends LezioniController1 {
    private JPanel panel1;
    private JTable table1;
    private JLabel nomeJLabel;
    private JLabel monthLabel;
    private JLabel yearLabel;
    private JButton backButton;
    private JButton addLessonButton;
    private JButton searchButton;
    private JTextField yearsTextField;
    private JComboBox<String> monthBox;

    Action delete;
    Action join;

    JButton button = new JButton();

    String[] columns = {"Giorno", "Ora", "Tipo", "Privata", "Maestro", "Prenotazione"};
    DefaultTableModel model = new DefaultTableModel(columns, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 5;
        }
    };
    String[] months = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};

    DefaultComboBoxModel<String> modelCombo = new DefaultComboBoxModel<>(months);
    JFrame frame;

    public Lezioni2000() {

        backButton.addActionListener(e -> new Homepage2000().mostra(frame));

        searchButton.addActionListener(e -> search());

        addLessonButton.addActionListener(e -> new AggiungiLezioni2000().mostra(frame));

        delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.parseInt( e.getActionCommand() );

                if(LezioniController1.lezioniController.get(modelRow).getData().isBefore(LocalDateTime.now())){
                    button.setEnabled(false);
                    JOptionPane.showMessageDialog(null,"La lezione è scaduta");
                }else{
                    button.setEnabled(true);
                    if(eliminaLezione(LezioniController1.lezioniController.get(modelRow).getId())){
                        JOptionPane.showMessageDialog(null,"Lezione eliminata con successo! Gli utenti che hanno prenotato la lezione saranno avvisati.");
                        model.setRowCount(0);
                        item();
                    }else{
                        JOptionPane.showMessageDialog(null,"Non è stato possibile eliminare la lezione.");
                    }
                }
            }
        };

        join = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.parseInt( e.getActionCommand() );

                if(LezioniController1.lezioniController.get(modelRow).getData().isBefore(LocalDateTime.now())){
                    button.setEnabled(false);
                    JOptionPane.showMessageDialog(null,"La lezione è scaduta");
                }else{
                    button.setEnabled(true);
                    if(prenotaLezione(LezioniController1.lezioniController.get(modelRow).getId())){
                        JOptionPane.showMessageDialog(null,"Lezione prenotata con successo! Gli utenti che hanno prenotato la lezione saranno avvisati.");
                        model.setRowCount(0);
                        item();

                    }else{
                        JOptionPane.showMessageDialog(null,"Non è stato possibile prenotare la lezione.");
                    }
                }
            }
        };
    }



    public void item(){
        if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
            super.loadLezioniMaestro();
            setValue(delete);
        } else {
            super.loadLezioni();
            setValue(join);
        }
    }

    public void search(){
        if(isNumber(yearsTextField.getText())){
            onActionSpecificMonth(monthBox.getSelectedIndex()+1,Integer.parseInt(yearsTextField.getText()));
            if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
                setValue(delete);
            } else {
                setValue(join);
            }
        }else{
            JOptionPane.showMessageDialog(null,"La data inserita non è valida!");
        }
    }

    public void mostra(JFrame frame){
        this.frame=frame;
        table1.setModel(model);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        nomeJLabel.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        monthBox.setModel(modelCombo);
        monthBox.setSelectedItem(Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
        yearsTextField.setText(Integer.toString(getCurrentYearMonth().getYear()));
        item();
    }

    public void setValue(Action action1){
        deleteAllRows(model);
        monthLabel.setText(Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
        yearLabel.setText(Integer.toString(getCurrentYearMonth().getYear()));

        for (int i = 0; i < LezioniController1.lezioniController.size(); i++) {
            model.addRow(new Object[]{LezioniController1.lezioniController.get(i).getData().getDayOfMonth(), Utils.formatTime(LezioniController1.lezioniController.get(i).getData().getHour(), LezioniController1.lezioniController.get(i).getData().getMinute()),
                    LezioniController1.lezioniController.get(i).getTipo(), Boolean.TRUE.equals(LezioniController1.lezioniController.get(i).isPrivata()) ? "Si" : "No", LezioniController1.lezioniController.get(i).getMaestro(), LezioniController1.lezioniController.get(i).getId()});
        }
        new ButtonColumn(table1, action1, 5, 0, 0);
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