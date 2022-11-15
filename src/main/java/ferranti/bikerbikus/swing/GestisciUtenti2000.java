package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.controllers1.ManageUsersController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class GestisciUtenti2000 extends ManageUsersController1 {
    private JPanel panel1;
    private JLabel nomeJLabel;
    private JButton backButton;
    private JTable table1;

    Action upgrade;

    JButton button = new JButton();

    String[] columns = {"Nome", "Cognome", "Email", "Tipo", "Promuovi"};
    DefaultTableModel model = new DefaultTableModel(columns, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4;
        }
    };

    JFrame frame;

    public GestisciUtenti2000() {

        backButton.addActionListener(e -> new Homepage2000().mostra(frame));

        upgrade = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.parseInt( e.getActionCommand() );
                upgradeUser(ManageUsersController1.utenti.get(modelRow).getId());
                item();
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

            loadUtenti();
            setValue();
    }

    public void setValue(){
        deleteAllRows(model);

        for (int i = 0; i < ManageUsersController1.utenti.size(); i++) {

            model.addRow(new Object[]{ManageUsersController1.utenti.get(i).getNome(), ManageUsersController1.utenti.get(i).getCognome(),
                    ManageUsersController1.utenti.get(i).getEmail(), ManageUsersController1.utenti.get(i).getTipoUtente(), ManageUsersController1.utenti.get(i).getId()});

        }

        ButtonColumn buttonColumn = new ButtonColumn(table1, upgrade, 4,4, 0);
        buttonColumn.setMnemonic(KeyEvent.VK_D);
    }

    public static void deleteAllRows(final DefaultTableModel model) {
        for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
            model.removeRow(i);
        }
    }
}