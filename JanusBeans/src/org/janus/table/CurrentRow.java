package org.janus.table;

import java.io.Serializable;

import org.janus.single.ObjectRead;
import org.janus.single.ObjectWrite;

public class CurrentRow implements ObjectRead, ObjectWrite {

	@Override
	public Serializable getValue(Serializable tm) {
		return ((ExtendedTableModel) tm).getCurrentRow();
	}

	@Override
	public void setValue(Serializable obj, Serializable value) {
		ExtendedTableModel tm = (ExtendedTableModel) obj;
		int row = Integer.parseInt(value.toString());
		if (tm.getRowCount() > row) {
			tm.setCurrentRow(row);
		}

	}

}
