package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.controllers1.LoginController1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class Login2000 extends JFrame{

    private JPanel mainPanel;
    private JButton loginJButton;
    private JPasswordField passwordJField;
    private JTextField emailJField;
    private JButton registerJButton;

    JFrame frame;

    public Login2000() {

        loginJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( LoginController1.onActionLogin(emailJField.getText(), String.valueOf(passwordJField.getPassword()))){
                    System.out.println("funge");
                    new Homepage2000().mostra(frame);
                }else{
                    showMessageDialog(null, "Qualcosa è andato storto");
                }
            }
        });

        registerJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register2000().mostra(frame);
            }
        });
    }

    public void mostra(JFrame frame){
        this.frame=frame;
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        registerJButton.setOpaque(false);
        registerJButton.setBackground(Color.WHITE);
        registerJButton.setToolTipText("Register");
    }
}