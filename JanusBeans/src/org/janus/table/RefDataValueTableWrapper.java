package org.janus.table;

import java.io.Serializable;

import javax.swing.table.TableModel;

import org.janus.data.DataContext;
import org.janus.dict.actions.ActionDictionary;
import org.janus.helper.DebugAssistent;
import org.janus.value.RefDataValue;

public class RefDataValueTableWrapper extends DefaultExtendedTableWrapper {
    private RefDataValue ref;

    public RefDataValueTableWrapper(String name, ActionDictionary dict,
            RefDataValue ref) {
        super(name, dict);
        this.ref = ref;
        ref.setModel(dict);
    }

    @Override
    public ExtendedTableModel getTable(DataContext data) {
        return (ExtendedTableModel) ref.getObject(data);
    }

    @Override
    public Serializable getObject(DataContext ctx) {
        return (ExtendedTableModel) ref.getObject(ctx);
    }

}
