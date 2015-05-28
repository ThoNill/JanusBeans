package org.janus.table;

import java.io.Serializable;

import org.janus.single.ObjectRead;


public class ColumnCount implements ObjectRead {

	@Override
	public Serializable getValue(Serializable obj) {
		if (obj == null) return 0;
		
		return ((ExtendedTableModel)obj).getColumnCount();
	}


}
