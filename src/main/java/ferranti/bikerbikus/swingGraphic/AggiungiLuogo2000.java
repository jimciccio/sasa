package ferranti.bikerbikus.swingGraphic;

import ferranti.bikerbikus.controllers1.ModificaLuogoController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.utils.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AggiungiLuogo2000 extends ModificaLuogoController1 {
    private JPanel panel1;
    private JLabel nomeJLabel;
    private JButton backButton;
    private JButton addButton;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField difficultyField;


    JFrame frame;


    public AggiungiLuogo2000() {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Recensioni2000().mostra(frame);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(checkFields()){

                    onActionConferma(nameField.getText(), descriptionField.getText(), difficultyField.getText());
                    JOptionPane.showMessageDialog(null,"Luogo creato con successo!");
                    new Recensioni2000().mostra(frame);

                }else{
                    JOptionPane.showMessageDialog(null,"Inserisci tutti i valori!");
                }
            }
        });
    }

    public void mostra(JFrame frame){


        this.frame=frame;

        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);


        nomeJLabel.setText(
                Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));


    }

    public boolean checkFields(){

        if( nameField.getText().equals("") || descriptionField.getText().equals("") || difficultyField.getText().equals("")){

            return false;
        }else{
            return true;
        }
    }


}
