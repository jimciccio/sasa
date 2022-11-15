package ferranti.bikerbikus.swingGraphic;

import ferranti.bikerbikus.controllers1.RecensioniController1;
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

public class Recensioni2000 extends RecensioniController1 {
    private JPanel panel1;
    private JButton backButton;
    private JTable table1;
    private JTable table2;
    private JLabel nomeJLabel;
    private JButton displayButton;
    private JButton addReviewButton;
    private JButton modifyReviewsButton;
    private JButton modifyPlaceButton;
    private JButton addPlaceButton;
    private static JDialog d;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE; // example '2011-12-03'



    Action delete;

    JButton button = new JButton();

    String columnsPlace[] = {"Nome", "Descrizione", "Difficoltà", "Valutazione"};
    String columnsReview[] = {"Utente", "Recensione", "Data", "Valutazione"};

    DefaultTableModel modelPlace = new DefaultTableModel(columnsPlace, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3;
        }
    };

    DefaultTableModel modelReview = new DefaultTableModel(columnsReview, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3;
        }
    };
    String months[] = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};

    DefaultComboBoxModel modelCombo = new DefaultComboBoxModel(months);
    JFrame frame;

    public Recensioni2000() {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Escursioni2000().mostra(frame);
            }
        });

        addReviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogExample(table1.getSelectedRow());
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setValue2(table1.getSelectedRow());
            }
        });

        modifyReviewsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ModificaRecensioni2000().mostra(frame);
            }
        });

        addPlaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AggiungiLuogo2000().mostra(frame);
            }
        });

        modifyPlaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ModificaLuogo2000().mostra(frame);
            }
        });
    }

    public void mostra(JFrame frame){

        this.frame=frame;
        table1.setModel(modelPlace);
        table2.setModel(modelReview);

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
            loadLuoghi();
            setValue(delete);
    }

    public void setValue(Action action1){
        deleteAllRows(modelPlace);

        for (int i = 0; i < RecensioniController1.luoghi.size(); i++) {

            modelPlace.addRow(new Object[]{RecensioniController1.luoghi.get(i).getNome(), RecensioniController1.luoghi.get(i).getDescrizione(),
                    RecensioniController1.luoghi.get(i).getDifficolta(), RecensioniController1.luoghi.get(i).getValutazioneMedia()});

        }
        table1.setRowSelectionInterval(0, 0);
    }

    public void setValue2(int counterListPlace){
        deleteAllRows(modelReview);

        loadRecensioni(RecensioniController1.luoghi.get(counterListPlace).getId());

        for (int i = 0; i < RecensioniController1.recensioni.size(); i++) {

            modelReview.addRow(new Object[]{RecensioniController1.recensioni.get(i).getUtente().getNome(), RecensioniController1.recensioni.get(i).getRecensioneString(),
                    RecensioniController1.recensioni.get(i).getData(), RecensioniController1.recensioni.get(i).getValutazione()});
        }
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
        d = new JDialog(f , "Add a review to "+RecensioniController1.luoghi.get(id).getNome(), true);
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


        btnOk = new JButton("Ok");

        btnOk.addActionListener (new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                    if (dateField.getText().equals("") || descriptionField.getText().equals("") || starField.getText().equals("")) {

                        JOptionPane.showMessageDialog(null, "Inserisci tutti i dati!");

                    } else {
                        if(checkDate(dateField.getText())) {
                            LocalDate localDate = LocalDate.parse(dateField.getText(), dateFormatter);

                            recensisciLuogo(RecensioniController1.luoghi.get(id).getId(), descriptionField.getText(), LocalDate.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()), Double.valueOf(starField.getText()));
                            setValue2(id);
                            d.setVisible(false);
                            d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
                            JOptionPane.showMessageDialog(null, "Recensione inserita");
                        }else{
                            JOptionPane.showMessageDialog(null," Data non valida!");
                        }
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
