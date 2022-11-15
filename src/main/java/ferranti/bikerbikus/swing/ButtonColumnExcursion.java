package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Escursione;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.ArrayList;
import java.util.List;


/**
 *  The ButtonColumn class provides a renderer and an editor that looks like a
 *  JButton. The renderer and editor will then be used for a specified column
 *  in the table. The TableModel will contain the String to be displayed on
 *  the button.
 *
 *  The button can be invoked by a mouse click or by pressing the space bar
 *  when the cell has focus. Optionally a mnemonic can be set to invoke the
 *  button. When the button is invoked the provided Action is invoked. The
 *  source of the Action will be the table. The action command will contain
 *  the model row number of the button that was clicked.
 *
 */
public class ButtonColumnExcursion extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener
{
    private JTable table;
    private Action join;
    private Action delete;

    private int mnemonic;
    private Border originalBorder;
    private Border focusBorder;

    private JButton renderButton;
    private JButton editButton;
    private Object editorValue;
    private boolean isButtonColumnEditor;
    List<Escursione> escursioni = new ArrayList<>();

    public ButtonColumnExcursion(JTable table, Action join, Action delete, int column, List escursioni)
    {
        this.table = table;
        this.join = join;
        this.delete = delete;

        this.escursioni = escursioni;

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


                if (escursioni.get(row).getAccompagnatore().getId() == UserData.getInstance().getUser().getId()) {
                    editButton.setText("Elimina");

                } else {
                    editButton.setText("Prenota");

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


                if (escursioni.get(row).getAccompagnatore().getId() == UserData.getInstance().getUser().getId()) {
                    renderButton.setText("Elimina");

                } else {
                    renderButton.setText("Prenota");

                }



        return renderButton;
    }


    public void actionPerformed(ActionEvent e)
    {
        int row = table.convertRowIndexToModel( table.getEditingRow() );
        fireEditingStopped();


        ActionEvent event = new ActionEvent(
                table,
                ActionEvent.ACTION_PERFORMED,
                "" + row);

        System.out.println("prima prima ???"+UserData.getInstance().getUser().getNome()+ " " + UserData.getInstance().getUser().getCognome());

        if(table.getValueAt(row, 2).toString().equals(UserData.getInstance().getUser().getNome()+ " " + UserData.getInstance().getUser().getCognome())){
            delete.actionPerformed(event);

        }else{
            join.actionPerformed(event);
        }
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