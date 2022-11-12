package ferranti.bikerbikus.swingGraphic;

import ferranti.bikerbikus.controllers1.ShopController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.utils.Utils;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.Properties;


public class Shop2000 extends ShopController1 {
    private JPanel panel1;
    private JTable table1;
    private JButton backButton;
    private JLabel nomeJLabel;
    private JTable table2;
    private JButton addBikeButton;
    private JButton modifyBikeButton;
    private static JDialog d;

    Action buy;
    Action rent;

    JButton button = new JButton();

    String columnsBuy[] = {"Modello", "Caratteristiche", "Prezzo", "Compra"};
    DefaultTableModel modelBuy = new DefaultTableModel(columnsBuy, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3;
        }
    };

    String columnsRent[] = {"Modello", "Caratteristiche", "Prezzo", "Noleggia"};

    DefaultTableModel modelRent = new DefaultTableModel(columnsRent, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 3;
        }
    };

    JFrame frame;


    public Shop2000() {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Homepage2000().mostra(frame);
            }
        });

        addBikeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AggiungiBicicletta2000().mostra(frame);
            }
        });

        modifyBikeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ModificaBicicletta2000().mostra(frame);
            }
        });

        buy = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.valueOf( e.getActionCommand() );
                ShopController1.buyBicicletta(modelRow);
            }
        };

        rent = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                int modelRow = Integer.valueOf( e.getActionCommand() );
                //ShopController1.rentBicicletta(modelRow, );
                DialogExample(modelRow);

            }
        };
    }

    public void mostra(JFrame frame){

        this.frame=frame;
        table1.setModel(modelBuy);
        table2.setModel(modelRent);

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

            loadBicicletteComprabili();
            loadBicicletteNoleggiabili();

            setValue();
    }


    public void setValue(){
        deleteAllRows(modelBuy);
        deleteAllRows(modelRent);

        for (int i = 0; i < ShopController1.bicicletteVendita.size(); i++) {

            modelBuy.addRow(new Object[]{ShopController1.bicicletteVendita.get(i).getModello(), ShopController1.bicicletteVendita.get(i).getCaratteristiche(),
                    ShopController1.bicicletteVendita.get(i).getPrezzo(), ShopController1.bicicletteVendita.get(i).getId()});
        }

        ButtonColumn buttonColumnBuy = new ButtonColumn(table1, buy, 3, 5, 0);
        buttonColumnBuy.setMnemonic(KeyEvent.VK_D);

        for (int i = 0; i < ShopController1.bicicletteNoleggio.size(); i++) {

            modelRent.addRow(new Object[]{ShopController1.bicicletteNoleggio.get(i).getModello(), ShopController1.bicicletteNoleggio.get(i).getCaratteristiche(),
                    ShopController1.bicicletteNoleggio.get(i).getPrezzo(), ShopController1.bicicletteNoleggio.get(i).getId()});

        }

        ButtonColumn buttonColumnRent = new ButtonColumn(table2, rent, 3, 5, 1);
        buttonColumnRent.setMnemonic(KeyEvent.VK_D);

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
        d = new JDialog(f , "Fino a quando vuoi noleggiare ", true);
        d.setLayout( new FlowLayout() );

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel pickerPanel= new JPanel();
        UtilDateModel model = new UtilDateModel();
        JDatePickerImpl datePicker;

        Properties p = new Properties();

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBounds(220,350,120,30);

        pickerPanel.add(datePicker);

        gbc.insets = new Insets(2,2,2,2);
        JLabel dateLabel = new JLabel("Data:");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(dateLabel,gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(pickerPanel,gbc);

        btnOk = new JButton("Ok");

        btnOk.addActionListener (new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                if(datePicker.getModel().isSelected()){
                    rentBicicletta(id, LocalDate.of(datePicker.getModel().getYear(), datePicker.getModel().getMonth()+1, datePicker.getModel().getDay()));
                    setValue();
                    d.setVisible(false);
                    d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
                }else{
                    JOptionPane.showMessageDialog(null,"Inserisci una data!");
                }
            }
        });
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
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
        gbc.gridy = 2;
        panel.add(btnCancel,gbc);
        d.add(panel);
        d.setSize(400,250);
        d.setLocationRelativeTo(null);

        d.setVisible(true);

    }
}
