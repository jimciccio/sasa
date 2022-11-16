package ferranti.bikerbikus.swing;

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

    private JButton renderButton1;
    private JButton editButton1;
    private Object editorValue1;
    private boolean isButtonColumnEditor;
    private int type;
    private int buyRent;
    
    public ButtonColumn(JTable table, Action action, int column, int type, int buyRent )
    {
        this.table = table;
        this.action = action;
        this.type = type;
        this.buyRent = buyRent;

        renderButton1 = new JButton();
        editButton1 = new JButton();
        editButton1.setFocusPainted( false );
        editButton1.addActionListener( this );
        originalBorder = editButton1.getBorder();
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
        editButton1.setBorder( focusBorder );
    }

    public int getMnemonic()
    {
        return mnemonic;
    }


    public void setMnemonic(int mnemonic)
    {
        this.mnemonic = mnemonic;
        renderButton1.setMnemonic(mnemonic);
        editButton1.setMnemonic(mnemonic);
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column)
    {
        if (value == null)
        {
            editButton1.setText( "" );
            editButton1.setIcon( null );
        }else {
            if(type == 0){                              // Lezioni
                if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
                    editButton1.setText("Elimina");
                } else {
                    editButton1.setText("Prenota");
                }
            }else if(type == 1){                        // Campionati - Stagioni
                editButton1.setText("Dettagli");
            }else if(type == 2){
                editButton1.setText("Modifica");       // Modifica Bicicletta
            }else if(type == 3){
                editButton1.setText("Prenota");        // Gare
            }else if(type == 4){
                editButton1.setText("Promuovi");       // Gestisci user

            }else if(type == 5){                      // Shop
                if(buyRent==0){
                    editButton1.setText("Compra");
                }else{
                    editButton1.setText("Noleggia");
                }
            }
        }
        this.editorValue1 = value;
        return editButton1;
    }

    @Override
    public Object getCellEditorValue()
    {
        return editorValue1;
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        if (isSelected)
        {
            renderButton1.setForeground(table.getSelectionForeground());
            renderButton1.setBackground(table.getSelectionBackground());
        }
        else
        {
            renderButton1.setForeground(table.getForeground());
            renderButton1.setBackground(UIManager.getColor("Button.background"));
        }

        if (hasFocus)
        {
            renderButton1.setBorder( focusBorder );
        }
        else
        {
            renderButton1.setBorder( originalBorder );
        }

        if (value == null)
        {
            renderButton1.setText( "" );
            renderButton1.setIcon( null );
        }else {

            if(type == 0){                              // Lezioni
                if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
                    renderButton1.setText("Elimina");
                } else {
                    renderButton1.setText("Prenota");
                }
            }else if(type == 1){                        // Campionati - Stagioni
                renderButton1.setText("Dettagli");
            }else if(type == 2){
                renderButton1.setText("Modifica");       // Modifica Bicicletta
            }else if(type == 3){
                renderButton1.setText("Prenota");        // Gare
            }else if(type == 4){
                renderButton1.setText("Promuovi");       // Gestisci user

            }else if(type == 5){                      // Shop
                if(buyRent==0){
                    renderButton1.setText("Compra");
                }else{
                    renderButton1.setText("Noleggia");
                }
            }
        }
        return renderButton1;
    }

    public void actionPerformed(ActionEvent e)
    {
        int row = table.convertRowIndexToModel( table.getEditingRow() );
        fireEditingStopped();

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