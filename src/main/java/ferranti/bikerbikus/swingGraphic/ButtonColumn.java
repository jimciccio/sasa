package ferranti.bikerbikus.swingGraphic;

import ferranti.bikerbikus.data.UserData;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;


public class ButtonColumn extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener
{
    private JTable table;
    private Action action;
    private int mnemonic;
    private Border originalBorder;
    private Border focusBorder;

    private JButton renderButton;
    private JButton editButton;
    private Object editorValue;
    private boolean isButtonColumnEditor;
    private int type;
    private int buyRent;


    public ButtonColumn(JTable table, Action action, int column, int type, int buyRent )
    {
        this.table = table;
        this.action = action;
        this.type = type;
        this.buyRent = buyRent;

        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted( false );
        editButton.addActionListener( this );
        originalBorder = editButton.getBorder();
        setFocusBorder( new LineBorder(Color.BLUE) );

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer( this );
        columnModel.getColumn(column).setCellEditor( this );
        table.addMouseListener( this );
    }


    public Border getFocusBorder()
    {
        return focusBorder;
    }


    public void setFocusBorder(Border focusBorder)
    {
        this.focusBorder = focusBorder;
        editButton.setBorder( focusBorder );
    }

    public int getMnemonic()
    {
        return mnemonic;
    }


    public void setMnemonic(int mnemonic)
    {
        this.mnemonic = mnemonic;
        renderButton.setMnemonic(mnemonic);
        editButton.setMnemonic(mnemonic);
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column)
    {
        if (value == null)
        {
            editButton.setText( "" );
            editButton.setIcon( null );
        }else {



            if(type == 0){                              // Lezioni
                if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
                    editButton.setText("Elimina");
                } else {
                    editButton.setText("Prenota");
                }
            }else if(type == 1){                        // Campionati - Stagioni
                editButton.setText("Dettagli");
            }else if(type == 2){
                editButton.setText("Modifica");       // Modifica Bicicletta
            }else if(type == 3){
                editButton.setText("Prenota");        // Gare
            }else if(type == 4){
                editButton.setText("Promuovi");       // Gestisci user

            }else if(type == 5){                      // Shop
                if(buyRent==0){
                    editButton.setText("Compra");
                }else{
                    editButton.setText("Noleggia");
                }
            }
        }

        this.editorValue = value;
        return editButton;
    }

    @Override
    public Object getCellEditorValue()
    {
        return editorValue;
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        if (isSelected)
        {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        }
        else
        {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        if (hasFocus)
        {
            renderButton.setBorder( focusBorder );
        }
        else
        {
            renderButton.setBorder( originalBorder );
        }

        if (value == null)
        {
            renderButton.setText( "" );
            renderButton.setIcon( null );
        }else {


            if(type == 0){                              // Lezioni
                if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
                    renderButton.setText("Elimina");
                } else {
                    renderButton.setText("Prenota");
                }
            }else if(type == 1){                        // Campionati - Stagioni
                renderButton.setText("Dettagli");
            }else if(type == 2){
                renderButton.setText("Modifica");       // Modifica Bicicletta
            }else if(type == 3){
                renderButton.setText("Prenota");        // Gare
            }else if(type == 4){
                renderButton.setText("Promuovi");       // Gestisci user

            }else if(type == 5){                      // Shop
                if(buyRent==0){
                    renderButton.setText("Compra");
                }else{
                    renderButton.setText("Noleggia");
                }
            }

        }

        return renderButton;
    }

    //
//  Implement ActionListener interface
//
    /*
     *	The button has been pressed. Stop editing and invoke the custom Action
     */
    public void actionPerformed(ActionEvent e)
    {
        int row = table.convertRowIndexToModel( table.getEditingRow() );
        fireEditingStopped();

        //  Invoke the Action

        ActionEvent event = new ActionEvent(
                table,
                ActionEvent.ACTION_PERFORMED,
                "" + row);
        action.actionPerformed(event);
    }


    public void mousePressed(MouseEvent e)
    {
        if (table.isEditing()
                &&  table.getCellEditor() == this)
            isButtonColumnEditor = true;
    }

    public void mouseReleased(MouseEvent e)
    {
        if (isButtonColumnEditor
                &&  table.isEditing())
            table.getCellEditor().stopCellEditing();

        isButtonColumnEditor = false;
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}