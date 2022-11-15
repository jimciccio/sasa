package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.controllers1.AreaPersonaleController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.utils.Utils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class Profile2000 extends AreaPersonaleController1 {
    private JPanel panel1;
    private JLabel nomeJLabel;
    private JButton backButton;
    private JComboBox<String> typeCombo;
    private JButton displayButton;
    private JButton cancelButton;
    private JTable table1;
    private JButton logOutButton;
    private JButton manageUsersButton;
    String day = "Giorno";
    String status = "Status";
    String lezioni = "Lezioni";



    DefaultComboBoxModel<String> modelType = new DefaultComboBoxModel<>();

    String[] columnsLesson = {day, "Ora", "Tipo", "Privata", "Maestro", status};
    String[] columnsRace = {day, "Ora", "Nome", "Stagione"};
    String[] columnsExcursion = {day, "Ora", "Luogo", "Difficoltà", "Accompagnatore", status};
    String[] columnsBuy = {"Modello", "Caratteristiche", "Data Acquisto", "Prezzo"};
    String[] columnsRent = {"Modello", "Caratteristiche", "Inizio Noleggio", "Fine Noleggio", "Prezzo al Giorno", "Prezzo Totale", status};
    String[] columnTypeA = {lezioni, "Gare", "Escursioni", "Bici Comprate", "Bici Noleggiate"};
    String[] columnTypeB = {"Gare", "Escursioni", "Bici Comprate", "Bici Noleggiate"};

    DefaultTableModel modelLesson = new DefaultTableModel(columnsLesson, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4;
        }
    };

    DefaultTableModel modelRace = new DefaultTableModel(columnsRace, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3;
        }
    };

    DefaultTableModel modelExcursion = new DefaultTableModel(columnsExcursion, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4;
        }
    };

    DefaultTableModel modelBuy = new DefaultTableModel(columnsBuy, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3;
        }
    };

    DefaultTableModel modelRent = new DefaultTableModel(columnsRent, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 5;
        }
    };

    JFrame frame;

    public Profile2000() {

        backButton.addActionListener(e -> new Homepage2000().mostra(frame));

        displayButton.addActionListener(e -> item((String) typeCombo.getSelectedItem()));

        cancelButton.addActionListener(e -> {
            String tipo = (String) typeCombo.getSelectedItem();

            if(table1.getSelectedRow() != -1){
                switch (tipo){
                    case "Lezioni" :{
                        if(!AreaPersonaleController1.lezioni.get(table1.getSelectedRow()).getData().isBefore(LocalDateTime.now())){
                            AreaPersonaleController1.disdiciLezione(AreaPersonaleController1.lezioni.get(table1.getSelectedRow()).getId());
                            JOptionPane.showMessageDialog(null," La prenotazione della lezione è stata annullata");
                            setValueLesson();
                            break;
                        }else{
                            JOptionPane.showMessageDialog(null," Non puoi disdire una lezione terminata");
                            break;
                        }
                    }
                    case "Gare" :{
                        JOptionPane.showMessageDialog(null," Non puoi disdire una gara");
                        break;
                    }
                    case "Escursioni" :{
                        if(!AreaPersonaleController1.escursioni.get(table1.getSelectedRow()).getData().isBefore(LocalDateTime.now())){
                            AreaPersonaleController1.disdiciEscursione(AreaPersonaleController1.escursioni.get(table1.getSelectedRow()).getId());
                            JOptionPane.showMessageDialog(null," La prenotazione dell'escursione è stata annullata");
                            setValueExcursion();
                            break;
                        }else{
                            JOptionPane.showMessageDialog(null," Non puoi disdire una escursione terminata");
                            break;
                        }
                    }
                    case "Bici Noleggiate" :{
                        if(!AreaPersonaleController1.bicicletteNoleggiate.get(table1.getSelectedRow()).getFineNoleggio().isBefore(LocalDateTime.now())){
                            AreaPersonaleController1.disdiciNoleggio(AreaPersonaleController1.bicicletteNoleggiate.get(table1.getSelectedRow()).getIdNoleggio());
                            JOptionPane.showMessageDialog(null," Il noleggio è stato interotto");
                            setValueRent();
                            break;
                        }else{
                            JOptionPane.showMessageDialog(null," Non puoi disdire un noleggio terminato");
                            break;
                        }
                    }
                    case "Bici Comprate" :{
                        JOptionPane.showMessageDialog(null," Non puoi disdire una bici comprata");
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + tipo);
                }
            }else{
                JOptionPane.showMessageDialog(null," Selezione cosa disdire! ");
            }
        });

        logOutButton.addActionListener(e -> new Login2000().mostra(frame));
        manageUsersButton.addActionListener(e -> new GestisciUtenti2000().mostra(frame));
    }

    public void mostra(JFrame frame){

        this.frame=frame;

        if(UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()){
            table1.setModel(modelRace);
            modelType.addAll(List.of(columnTypeB));

        }else{
            table1.setModel(modelLesson);
            modelType.addAll(List.of(columnTypeA));
        }

        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);

        nomeJLabel.setText(
                Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));


        typeCombo.setModel(modelType);
        typeCombo.setSelectedItem(modelType.getElementAt(0));
        item((String) typeCombo.getSelectedItem());

    }

    public void item(String name){

        switch (name){
            case "Lezioni" :{
                showLezioni();
                setValueLesson();
                break;
            }
            case "Gare" :{
                showGare();
                setValueRace();
                break;
            }
            case "Escursioni" :{
                showEscursioni();
                setValueExcursion();
                break;
            }
            case "Bici Comprate" :{
                showBiciComprate();
                setValueBuy();
                break;
            }
            case "Bici Noleggiate" :{
                showBiciNoleggiate();
                setValueRent();
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + name);
        }
    }

    public void setValueRace(){
        deleteAllRows(modelRace);
        table1.setModel(modelRace);

        for (int i = 0; i < AreaPersonaleController1.gare.size(); i++) {

            modelRace.addRow(new Object[]{Utils.formatTimeDayMonthYear(AreaPersonaleController1.gare.get(i).getData().getDayOfMonth(), AreaPersonaleController1.gare.get(i).getData().getMonthValue(), AreaPersonaleController1.gare.get(i).getData().getYear()),
                    AreaPersonaleController1.gare.get(i).getData().getHour()+AreaPersonaleController1.gare.get(i).getData().getMinute(),
                    AreaPersonaleController1.gare.get(i).getStagione().getNome(), AreaPersonaleController1.gare.get(i).getStagione().getCampionato()});

        }
    }

    public void setValueLesson(){
        deleteAllRows(modelLesson);
        table1.setModel(modelLesson);

        for (int i = 0; i < AreaPersonaleController1.lezioni.size(); i++) {

            modelLesson.addRow(new Object[]{Utils.formatTimeDayMonthYear(AreaPersonaleController1.lezioni.get(i).getData().getDayOfMonth(), AreaPersonaleController1.lezioni.get(i).getData().getMonthValue(), AreaPersonaleController1.lezioni.get(i).getData().getYear()),
                    AreaPersonaleController1.lezioni.get(i).getData().getHour()+AreaPersonaleController1.lezioni.get(i).getData().getMinute(),
                    AreaPersonaleController1.lezioni.get(i).getTipo(), Boolean.TRUE.equals(AreaPersonaleController1.lezioni.get(i).isPrivata()) ? "Si" : "No", AreaPersonaleController1.lezioni.get(i).getMaestro(), AreaPersonaleController1.lezioni.get(i).getData().isBefore(LocalDateTime.now()) ? "Terminated" : "Active"});
        }
    }

    public void setValueExcursion(){
        deleteAllRows(modelExcursion);
        table1.setModel(modelExcursion);

        for (int i = 0; i < AreaPersonaleController1.escursioni.size(); i++) {

            modelExcursion.addRow(new Object[]{Utils.formatTimeDayMonthYear(AreaPersonaleController1.escursioni.get(i).getData().getDayOfMonth(), AreaPersonaleController1.escursioni.get(i).getData().getMonthValue(), AreaPersonaleController1.escursioni.get(i).getData().getYear()),
                    Utils.formatTime(AreaPersonaleController1.escursioni.get(i).getData().getHour(), AreaPersonaleController1.escursioni.get(i).getData().getMinute()),
                    AreaPersonaleController1.escursioni.get(i).getLuogo().getNome(), AreaPersonaleController1.escursioni.get(i).getLuogo().getDifficolta(), AreaPersonaleController1.escursioni.get(i).getAccompagnatore(), AreaPersonaleController1.escursioni.get(i).getData().isBefore(LocalDateTime.now()) ? "Terminated" : "Active"});
        }
    }

    public void setValueBuy(){
        deleteAllRows(modelBuy);
        table1.setModel(modelBuy);

        for (int i = 0; i < AreaPersonaleController1.bicicletteComprate.size(); i++) {

            modelBuy.addRow(new Object[]{AreaPersonaleController1.bicicletteComprate.get(i).getModello(), AreaPersonaleController1.bicicletteComprate.get(i).getCaratteristiche(),
                    Utils.formatTimeDayMonthYear(AreaPersonaleController1.bicicletteComprate.get(i).getDataAcquisto().getDayOfMonth(), AreaPersonaleController1.bicicletteComprate.get(i).getDataAcquisto().getMonthValue(), AreaPersonaleController1.bicicletteComprate.get(i).getDataAcquisto().getYear()),
                    AreaPersonaleController1.bicicletteComprate.get(i).getPrezzo()});
        }
    }

    public void setValueRent(){
        deleteAllRows(modelRent);
        table1.setModel(modelRent);

        for (int i = 0; i < AreaPersonaleController1.bicicletteNoleggiate.size(); i++) {

            modelRent.addRow(new Object[]{AreaPersonaleController1.bicicletteNoleggiate.get(i).getModello(), AreaPersonaleController1.bicicletteNoleggiate.get(i).getCaratteristiche(),
                    Utils.formatTimeDayMonthYear(AreaPersonaleController1.bicicletteNoleggiate.get(i).getInizioNoleggio().getDayOfMonth(), AreaPersonaleController1.bicicletteNoleggiate.get(i).getInizioNoleggio().getMonthValue(), AreaPersonaleController1.bicicletteNoleggiate.get(i).getInizioNoleggio().getYear()),
                    Utils.formatTimeDayMonthYear(AreaPersonaleController1.bicicletteNoleggiate.get(i).getFineNoleggio().getDayOfMonth(), AreaPersonaleController1.bicicletteNoleggiate.get(i).getFineNoleggio().getMonthValue(), AreaPersonaleController1.bicicletteNoleggiate.get(i).getFineNoleggio().getYear()),
                    AreaPersonaleController1.bicicletteNoleggiate.get(i).getPrezzo(), AreaPersonaleController1.bicicletteNoleggiate.get(i).getPrezzoFinale(), AreaPersonaleController1.bicicletteNoleggiate.get(i).getFineNoleggio().isBefore(LocalDateTime.now()) ? "Terminated" : "Active"});
        }
    }

    public static void deleteAllRows(final DefaultTableModel model) {
        for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
            model.removeRow(i);
        }
    }
}