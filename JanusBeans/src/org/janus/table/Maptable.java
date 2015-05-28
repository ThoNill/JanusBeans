package org.janus.table;

import javax.swing.table.DefaultTableModel;

import org.janus.actions.DataValue;
import org.janus.dict.actions.ActionDictionary;

public class Maptable extends DefaultExtendedTableWrapper implements DataValue {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3470147366503637121L;
	DefaultTableModel table = new DefaultTableModel(new String[] { "value",
			"text" }, 0);

	public Maptable(String name,ActionDictionary dict) {
		super(name,dict);
		addColumn("value");
		addColumn("text");
	}

	public void addEntry(String value, String text) {
		int r = table.getRowCount();
		table.setRowCount(r + 1);
		table.setValueAt(value, r, 0);
		table.setValueAt(text, r, 1);
	}

	@Override
	public ExtendedTableModel createTable() {
		String[] cols = createColumnNames();
		DefaultExtendedTableModel emodel = new DefaultExtendedTableModel(cols,
				0);
		emodel.setRowCount(table.getRowCount());
		for (int c = 0; c < 2; c++) {
			for (int r = 0; r < table.getRowCount(); r++) {
				emodel.setValueAt(table.getValueAt(r, c), r, c);
			}
		}
		return emodel;
	}

}
