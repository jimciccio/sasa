package ferranti.bikerbikus.swingGraphic;

import ferranti.bikerbikus.controllers1.EscursioniController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JComboBox monthBox;
    private JTextField yearsTextField;
    private JButton searchButton;
    private JLabel monthLabel;
    private JLabel yearLabel;
    private JLabel nomeJLabel;


    Action delete;
    Action join;


    JButton button = new JButton();

    String columns[] = {"Giorno", "Ora", "Luogo", "Difficoltà", "Accompagnatore", "Prenotazione"};
    DefaultTableModel model = new DefaultTableModel(columns, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 5;
        }
    };
    String months[] = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};

    DefaultComboBoxModel modelCombo = new DefaultComboBoxModel(months);
    JFrame frame;

    public Escursioni2000() {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Homepage2000().mostra(frame);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        addExcursionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AggiungiEscursioni2000().mostra(frame);
            }
        });

        reviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Recensioni2000().mostra(frame);
            }
        });



        delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.valueOf( e.getActionCommand() );

                if(Escursioni2000.escursioni.get(modelRow).getData().isBefore(LocalDateTime.now())){
                    button.setEnabled(false);
                    JOptionPane.showMessageDialog(null,"L'escursione è scaduta");
                }else{
                    button.setEnabled(true);
                    System.out.println("pannello"+modelRow);
                    if(eliminaEscursione(Escursioni2000.escursioni.get(modelRow).getId())){
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
                int modelRow = Integer.valueOf( e.getActionCommand() );

                if(Escursioni2000.escursioni.get(modelRow).getData().isBefore(LocalDateTime.now())){
                    button.setEnabled(false);
                    JOptionPane.showMessageDialog(null,"L'escursione è scaduta");

                }else{
                    button.setEnabled(true);
                    if(prenotaEscursione(Escursioni2000.escursioni.get(modelRow).getId())){
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        nomeJLabel.setText(
                Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));


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
            System.out.println("il mese è"+monthBox.getSelectedIndex());
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

            model.addRow(new Object[]{EscursioniController1.escursioni.get(i).getData().getDayOfMonth(), Utils.formatTime(EscursioniController1.escursioni.get(i).getData().getHour(), Escursioni2000.escursioni.get(i).getData().getMinute()),
                    Escursioni2000.escursioni.get(i).getLuogo().getNome(), Escursioni2000.escursioni.get(i).getLuogo().getDifficolta(), Escursioni2000.escursioni.get(i).getAccompagnatore(), Escursioni2000.escursioni.get(i).getId()});

        }



        ButtonColumnExcursion buttonColumn = new ButtonColumnExcursion(table1, action1, action2, 5, EscursioniController1.escursioni);
        buttonColumn.setMnemonic(KeyEvent.VK_D);
    }

    static boolean isNumber(String s)
    {
        for (int i = 0; i < s.length(); i++)
            if (Character.isDigit(s.charAt(i)) == false)
                return false;

        return true;
    }

    public static void deleteAllRows(final DefaultTableModel model) {
        for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
            model.removeRow(i);
        }
    }
}
