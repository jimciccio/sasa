package ferranti.bikerbikus.swing;

import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Escursione;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;

public class ButtonColumnExcursion extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor, ActionListener
{
    private JTable table;
    private transient Action join;
    private transient Action delete;
    private JButton renderButton;
    private JButton editButton;
    private transient Object editorValue;
    transient List<Escursione> escursioni;

    public ButtonColumnExcursion(JTable table, Action join, Action delete, int column, List<Escursione> escursioni)
    {
        this.table = table;
        this.join = join;
        this.delete = delete;
        this.escursioni = escursioni;
        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted( false );
        editButton.addActionListener( this );

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer( this );
        columnModel.getColumn(column).setCellEditor( this );
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
        if(table.getValueAt(row, 4).toString().equals(UserData.getInstance().getUser().getNome()+ " " + UserData.getInstance().getUser().getCognome())){
            delete.actionPerformed(event);
        }else{
            join.actionPerformed(event);
        }
    }
}