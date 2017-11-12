package org.janus.table;

import java.io.Serializable;

import org.janus.single.ObjectRead;
import org.janus.single.ObjectWrite;

public class CellValue implements ObjectRead, ObjectWrite {

    @Override
    public Serializable getValue(Serializable v) {
        ExtendedTableModel tm = (ExtendedTableModel) v;
        if (tm.getColumnCount() > tm.getCurrentColumn()
                && tm.getRowCount() > tm.getCurrentRow()) {
            Serializable s = (Serializable) tm.getValueAt(tm.getCurrentRow(),
                    tm.getCurrentColumn());
            return s;
        }
        return "";
    }

    @Override
    public void setValue(Serializable obj, Serializable value) {
        ExtendedTableModel tm = (ExtendedTableModel) obj;
        if (tm.getColumnCount() > tm.getCurrentColumn()
                && tm.getRowCount() > tm.getCurrentRow()) {
            tm.setValueAt(value,tm.getCurrentRow(),tm.getCurrentColumn());
        } 
 
    }

}
