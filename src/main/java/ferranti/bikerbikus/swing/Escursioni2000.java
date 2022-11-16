package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.controllers1.EscursioniController1;
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

public class Escursioni2000 extends EscursioniController1 {
    private JPanel panel1;
    private JButton backButton;
    private JTable table1;
    private JButton reviewButton;
    private JButton addExcursionButton;
    private JComboBox<String> monthBox;
    private JTextField yearsTextField;
    private JButton searchButton;
    private JLabel monthLabel;
    private JLabel yearLabel;
    private JLabel nomeJLabel;


    Action delete;
    Action join;

    JButton button = new JButton();

    String[] columns = {"Giorno", "Ora", "Luogo", "Difficoltà", "Accompagnatore", "Prenotazione"};
    DefaultTableModel model = new DefaultTableModel(columns, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 5;
        }
    };
    String[] months = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};

    DefaultComboBoxModel<String> modelCombo = new DefaultComboBoxModel<>(months);
    JFrame frame;

    public Escursioni2000() {

        backButton.addActionListener(e -> new Homepage2000().mostra(frame));

        searchButton.addActionListener(e -> search());

        addExcursionButton.addActionListener(e -> new AggiungiEscursioni2000().mostra(frame));

        reviewButton.addActionListener(e -> new Recensioni2000().mostra(frame));

        delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.parseInt( e.getActionCommand() );

                if(EscursioniController1.escursioni.get(modelRow).getData().isBefore(LocalDateTime.now())){
                    button.setEnabled(false);
                    JOptionPane.showMessageDialog(null,"L'escursione è scaduta");
                }else{
                    button.setEnabled(true);
                    if(eliminaEscursione(EscursioniController1.escursioni.get(modelRow).getId())){
                        JOptionPane.showMessageDialog(null,"Escursione eliminata con successo! Gli utenti che hanno prenotato l'escursione saranno avvisati.");
                        model.setRowCount(0);
                        item();
                    }else{
                        JOptionPane.showMessageDialog(null,"Non è stato possibile eliminare l'escursione.");
                    }
                }
            }
        };

        join = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.parseInt( e.getActionCommand() );

                if(EscursioniController1.escursioni.get(modelRow).getData().isBefore(LocalDateTime.now())){
                    button.setEnabled(false);
                    JOptionPane.showMessageDialog(null,"L'escursione è scaduta");
                }else{
                    button.setEnabled(true);
                    if(prenotaEscursione(EscursioniController1.escursioni.get(modelRow).getId())){
                        JOptionPane.showMessageDialog(null,"Escursione prenotata con successo! Gli utenti che hanno prenotato la lezione saranno avvisati.");
                        model.setRowCount(0);
                        item();
                    }else{
                        JOptionPane.showMessageDialog(null,"Non è stato possibile prenotare l'escursione.");
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
            loadEscursioni();
            setValue(join, delete);
    }

    public void search(){

        if(isNumber(yearsTextField.getText())){
            onActionSpecificMonth(monthBox.getSelectedIndex()+1,Integer.parseInt(yearsTextField.getText()));
                setValue(join, delete);

        }else{
            JOptionPane.showMessageDialog(null,"La data inserita non è valida!");
        }
    }

    public void setValue(Action action1, Action action2){
        deleteAllRows(model);
        monthLabel.setText(Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
        yearLabel.setText(Integer.toString(getCurrentYearMonth().getYear()));


        for (int i = 0; i < EscursioniController1.escursioni.size(); i++) {

            model.addRow(new Object[]{EscursioniController1.escursioni.get(i).getData().getDayOfMonth(), Utils.formatTime(EscursioniController1.escursioni.get(i).getData().getHour(), EscursioniController1.escursioni.get(i).getData().getMinute()),
                    EscursioniController1.escursioni.get(i).getLuogo().getNome(), EscursioniController1.escursioni.get(i).getLuogo().getDifficolta(), EscursioniController1.escursioni.get(i).getAccompagnatore(), EscursioniController1.escursioni.get(i).getId()});

        }

        ButtonColumnExcursion buttonColumn = new ButtonColumnExcursion(table1, action1, action2, 5, EscursioniController1.escursioni);
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
