package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.controllers1.RecensioniController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    Action delete;

    JButton button = new JButton();

    String[] columnsPlace = {"Nome", "Descrizione", "Difficoltà", "Valutazione"};
    String[] columnsReview = {"Utente", "Recensione", "Data", "Valutazione"};

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
    String[] months = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};

    DefaultComboBoxModel<String> modelCombo = new DefaultComboBoxModel<>(months);
    JFrame frame;

    public Recensioni2000() {

        backButton.addActionListener(e -> new Escursioni2000().mostra(frame));

        addReviewButton.addActionListener(e -> dialogExample(table1.getSelectedRow()));

        displayButton.addActionListener(e -> setValue2(table1.getSelectedRow()));

        modifyReviewsButton.addActionListener(e -> new ModificaRecensioni2000().mostra(frame));

        addPlaceButton.addActionListener(e -> new AggiungiLuogo2000().mostra(frame));

        modifyPlaceButton.addActionListener(e -> new ModificaLuogo2000().mostra(frame));
    }

    public void mostra(JFrame frame){
        this.frame=frame;
        table1.setModel(modelPlace);
        table2.setModel(modelReview);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
        nomeJLabel.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        item();
    }

    public void item(){
            loadLuoghi();
            setValue();
    }

    public void setValue(){
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

    public  void dialogExample(int id) {
        JButton btnOk;
        JButton btnCancel;
        JDialog d;

        JFrame f= new JFrame();
        d = new JDialog(f , "Add a review to "+RecensioniController1.luoghi.get(id).getNome(), true);
        d.setLayout( new FlowLayout() );

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        JTextField starField = new JTextField();

        JTextArea descriptionField = new JTextArea(8,20);
        descriptionField.setLineWrap(true);

        JPanel pickerPanel= new JPanel();
        JTextField dateField = new JTextField();
        dateField.setSize(75,-1);
        pickerPanel.add(dateField);

        gbc1.insets = new Insets(2,2,2,2);

        JLabel descriptionLabel = new JLabel("Description:");
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        panel.add(descriptionLabel,gbc1);

        gbc1.gridwidth = 2;
        gbc1.gridx = 1;
        gbc1.gridy = 0;
        panel.add(new JScrollPane(descriptionField),gbc1);

        JLabel dateLabel = new JLabel("Date:");
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.gridx = 0;
        gbc1.gridy = 1;
        panel.add(dateLabel,gbc1);

        gbc1.gridwidth = 2;
        gbc1.gridx = 1;
        gbc1.gridy = 1;
        panel.add(dateField,gbc1);

        JLabel formatLabel = new JLabel("(yyyy-mm-dd)");
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.gridx = 2;
        gbc1.gridy = 1;
        panel.add(formatLabel,gbc1);

        JLabel starLabel = new JLabel("Rate:");
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.gridx = 0;
        gbc1.gridy = 2;
        panel.add(starLabel,gbc1);

        gbc1.gridwidth = 2;
        gbc1.gridx = 1;
        gbc1.gridy = 2;
        panel.add(starField,gbc1);

        btnOk = new JButton("Ok");

        btnOk.addActionListener (e -> {
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
            });
        gbc1.gridwidth = 1;
        gbc1.gridx = 0;
        gbc1.gridy = 3;
        panel.add(btnOk,gbc1);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener (e -> {
            d.setVisible(false);
            d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
        });

        gbc1.gridx = 1;
        gbc1.gridy = 3;
        panel.add(btnCancel,gbc1);
        d.add(panel);
        d.setSize(500,300);
        d.setLocationRelativeTo(null);
        d.setVisible(true);
    }

    public boolean checkDate(String stringa){
        DateValidatorUsingLocalDate validator = new DateValidatorUsingLocalDate(dateFormatter);
        return validator.isValid(stringa);
    }
}