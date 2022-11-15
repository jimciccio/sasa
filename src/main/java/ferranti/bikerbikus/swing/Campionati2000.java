package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.controllers1.CampionatiController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public class Campionati2000 extends CampionatiController1 {
    private JPanel panel1;
    private JButton backButton;
    private JButton addSeason;
    private JTable table1;
    private JLabel nomeJLabel;

    Action join;


    JButton button = new JButton();

    String[] columns = {"Campionato", "Stagione", "Data Inizio", "Data Fine", "Dettagli"};
    DefaultTableModel model = new DefaultTableModel(columns, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4;
        }
    };

    JFrame frame;


    public Campionati2000() {

        backButton.addActionListener(e -> new Homepage2000().mostra(frame));


        addSeason.addActionListener(e -> new AggiungiStagione2000().mostra(frame));

        join = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.valueOf( e.getActionCommand() );

                new Stagione2000(CampionatiController1.stagioni.get(modelRow)).mostra(frame);

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

        nomeJLabel.setText(
                Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));

        item();
    }

    public void item(){

            loadStagioni();
            setValue(join);
    }


    public void setValue(Action action1){
        deleteAllRows(model);

        for (int i = 0; i < CampionatiController1.stagioni.size(); i++) {

            model.addRow(new Object[]{CampionatiController1.stagioni.get(i).getCampionato().getNome(), CampionatiController1.stagioni.get(i).getNome(), CampionatiController1.stagioni.get(i).getDataInizio(),
                    CampionatiController1.stagioni.get(i).getDataFine(), CampionatiController1.stagioni.get(i).getId()});
        }

        ButtonColumn buttonColumn = new ButtonColumn(table1, action1, 4, 1, 0);
        buttonColumn.setMnemonic(KeyEvent.VK_D);
    }


    public static void deleteAllRows(final DefaultTableModel model) {
        for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
            model.removeRow(i);
        }
    }
}