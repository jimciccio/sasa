package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.controllers1.AggiungiBiciclettaController1;
import ferranti.bikerbikus.data.UserData;

import ferranti.bikerbikus.models.BiciclettaNoleggio;
import ferranti.bikerbikus.models.BiciclettaVendita;
import ferranti.bikerbikus.utils.Utils;


import javax.swing.*;
import java.awt.*;


public class AggiungiBicicletta2000 extends AggiungiBiciclettaController1 {
    private JPanel panel1;
    private JButton backButton;
    private JLabel nomeJLabel;
    private JButton addButton;
    private JTextField modelTxt;
    private JTextField carTxt;
    private JTextField priceTxt;
    private JTextField quantityTxt;
    private JCheckBox rentableCheck;

        JFrame frame;

        public AggiungiBicicletta2000() {

            backButton.addActionListener(e -> new Shop2000().mostra(frame));

            addButton.addActionListener(e -> {
                if(checkFields()){
                    if(rentableCheck.isSelected()){
                        BiciclettaNoleggio biciclettaNoleggio = new BiciclettaNoleggio();
                        biciclettaNoleggio.setModello(modelTxt.getText());
                        biciclettaNoleggio.setCaratteristiche(carTxt.getText());
                        biciclettaNoleggio.setPrezzo(Integer.parseInt(priceTxt.getText()));
                        biciclettaNoleggio.setNoleggiabile(1);
                        onActionConfermaNoleggiabile(biciclettaNoleggio);
                        JOptionPane.showMessageDialog(null,"La bicicletta noleggiabile è stata inserita!");
                        new Shop2000().mostra(frame);
                    }else{
                        BiciclettaVendita biciclettaVendita = new BiciclettaVendita();
                        biciclettaVendita.setModello(modelTxt.getText());
                        biciclettaVendita.setCaratteristiche(carTxt.getText());
                        biciclettaVendita.setPrezzo(Integer.parseInt(priceTxt.getText()));
                        biciclettaVendita.setDisponibili(Integer.parseInt(quantityTxt.getText()));

                        if(checkBiciclettaNuova(biciclettaVendita)){
                            JOptionPane.showMessageDialog(null,"La bicicletta è gia esistente!");
                        }else{
                            onActionConfermaNuova(biciclettaVendita);
                            JOptionPane.showMessageDialog(null,"La nuova bicicletta è stata inserita!");
                            new Shop2000().mostra(frame);
                        }
                    }

                }else{
                    JOptionPane.showMessageDialog(null,"Inserisci tutti i valori!");
                }
            });
        }

        public boolean checkFields(){

            if(rentableCheck.isSelected()){
                return !modelTxt.getText().equals("") && !carTxt.getText().equals("") && !priceTxt.getText().equals("");
            }else{
                return !modelTxt.getText().equals("") && !carTxt.getText().equals("") && !priceTxt.getText().equals("")
                        && !quantityTxt.getText().equals("");
            }
        }

    public void mostra(JFrame frame){
        this.frame=frame;
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
        nomeJLabel.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
    }
}
