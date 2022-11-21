package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.controllers1.StagioneController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Stagione;
import ferranti.bikerbikus.utils.Utils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Stagione2000 extends StagioneController1 {

    private Stagione stagione;
    private JPanel panel1;
    private JButton backButton;
    private JLabel nomeJLabel;
    private JLabel seasonName;
    private JTable table1;
    private JTable table2;
    String surname = "Cognome";

    Action join;

    JButton button = new JButton();

    String[] columnsRank = {"#", "Nome", surname, "Gare", "Punti"};
    DefaultTableModel modelRank = new DefaultTableModel(columnsRank, 0){
        @Override
        public boolean isCellEditable(int row, int column) {return column == 4;}
    };

    String[] columnsRace = {"Data", "Partecipanti", "Nome", surname, "Dettagli"};

    DefaultTableModel modelRace = new DefaultTableModel(columnsRace, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4;
        }
    };

    JFrame frame;

    public Stagione2000(Stagione stagione) {

        this.stagione = stagione;
        backButton.addActionListener(e -> new Campionati2000().mostra(frame));

        join = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.parseInt( e.getActionCommand() );
                dialogExample(StagioneController1.gare.get(modelRow).getId(), modelRow);
            }
        };
    }

    public void mostra(JFrame frame){
        this.frame=frame;
        table1.setModel(modelRank);
        table2.setModel(modelRace);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
        seasonName.setText("Campionato " + stagione.getCampionato().getNome() + " - Stagione " + stagione.getNome());
        nomeJLabel.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        item();
    }

    public void item(){
      loadStagione(stagione);
      loadClassifica(stagione);
      setValue(join);
    }

    public void setValue(Action action1){
        deleteAllRows(modelRace);
        deleteAllRows(modelRank);
        for (int i = 0; i < StagioneController1.gare.size(); i++) {
            modelRace.addRow(new Object[]{Utils.formatTimeDayMonthYear(StagioneController1.gare.get(i).getData().getDayOfMonth(), StagioneController1.gare.get(i).getData().getMonthValue(), StagioneController1.gare.get(i).getData().getYear()), StagioneController1.gare.get(i).getPartecipanti(),
                    StagioneController1.gare.get(i).getNomeVincitore(), StagioneController1.gare.get(i).getCognomeVincitore(), StagioneController1.gare.get(i).getId()});
        }
        new ButtonColumn(table2, action1, 4, 1, 0);
        for (int i = 0; i < StagioneController1.utente.size(); i++) {
            modelRank.addRow(new Object[]{StagioneController1.utente.get(i).getPosizioneFinale(), StagioneController1.utente.get(i).getNome(),
                    StagioneController1.utente.get(i).getCognome(), StagioneController1.utente.get(i).getGare(), StagioneController1.utente.get(i).getPunteggio()});
        }
    }

    public static void deleteAllRows(final DefaultTableModel model) {
        for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
            model.removeRow(i);
        }
    }

    public static void dialogExample(int id, int counter) {
        JFrame f= new JFrame();
        JDialog d;

        d = new JDialog(f , "Gara del "+ Utils.formatTimeDayMonthYear(StagioneController1.gare.get(counter).getData().getDayOfMonth(), StagioneController1.gare.get(counter).getData().getMonthValue(), StagioneController1.gare.get(counter).getData().getYear()), true);
        d.setLayout( new FlowLayout() );

        JTable tab = new JTable();
        JScrollPane scroll = new JScrollPane(tab);

        String[] columnsRace = {"Nome", "Cognome", "Posizione", "Ps1", "Ps2", "Ps3", "Finale", "Punti"};

        DefaultTableModel mod = new DefaultTableModel(columnsRace, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7;
            }
        };
        tab.setModel(mod);

        if(StagioneController1.loadClassificaGara(id)){
            for (int i = 0; i < StagioneController1.utenteGara.size(); i++) {
                mod.addRow(new Object[]{StagioneController1.utenteGara.get(i).getNome(), StagioneController1.utenteGara.get(i).getCognome(),
                        StagioneController1.utenteGara.get(i).getPosizioneGara(), StagioneController1.utenteGara.get(i).getPs1(), StagioneController1.utenteGara.get(i).getPs2(),
                        StagioneController1.utenteGara.get(i).getPs3(), StagioneController1.utenteGara.get(i).getTempo(), StagioneController1.utenteGara.get(i).getPunteggio()});
            }
            tab.setAutoCreateRowSorter(true);
        }
        d.add(scroll);
        d.setSize(600,600);
        d.setLocationRelativeTo(null);
        d.setVisible(true);
    }
}