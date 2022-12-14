package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.controllers1.RegisterController1;

import javax.swing.*;
import java.awt.*;

public class Register2000 extends RegisterController1 {
    private JPanel panel1;
    private JButton logInButton;
    private JButton signInButton;
    private JTextField nameTxt;
    private JTextField surnameTxt;
    private JTextField emailTxt;
    private JTextField passwordTxt;
    JFrame frame;

    public Register2000() {

        logInButton.addActionListener(e -> new Login2000().mostra(frame));

        signInButton.addActionListener(e -> {
            onActionRegister(nameTxt.getText(), surnameTxt.getText(), emailTxt.getText(), passwordTxt.getText());
            new Homepage2000().mostra(frame);
        });
    }

    public void mostra(JFrame frame){
        this.frame=frame;
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
    }
}