package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.utils.Utils;
import javax.swing.*;
import java.awt.*;

public class Homepage2000 {
    private JButton lezioniButton;
    private JButton shopButton;
    private JLabel nomeJLabel;
    private JPanel mainPanel;
    private JButton campionatoButton;
    private JButton gareButton;
    private JButton profileButton;
    private JButton excursionButton;

    JFrame frame;

    public Homepage2000() {

        lezioniButton.addActionListener(e -> new Lezioni2000().mostra(frame));

        gareButton.addActionListener(e -> new Gare2000().mostra(frame));

        campionatoButton.addActionListener(e -> new Campionati2000().mostra(frame));

        shopButton.addActionListener(e -> new Shop2000().mostra(frame));

        excursionButton.addActionListener(e -> new Escursioni2000().mostra(frame));

        profileButton.addActionListener(e -> new Profile2000().mostra(frame));
    }

    public void mostra(JFrame frame){
        this.frame=frame;
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);

        frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);

        nomeJLabel.setText(
                Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));

    }
}