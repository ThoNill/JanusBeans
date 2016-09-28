package org.janus.table;

import javax.swing.table.TableModel;

public class TableSearch {

	public static int find(TableModel m, Object obj, int column) {
		if (m == null || obj == null) {
			throw new NullPointerException();
		}
		int rowCount = m.getRowCount();
		int i = 0;
		Object comparedObject;

		while (i < rowCount) {
			comparedObject = m.getValueAt(i, column);
			if (obj.equals(comparedObject)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public static int find(ExtendedTableModel m, Object obj, int column) {
		if (m == null || obj == null) {
			throw new NullPointerException();
		}

		int rowCount = m.getRowCount();
		int i = 0;
		Object comparedObject;

		while (i < rowCount) {
			comparedObject = m.getValueAt(i, column);
			if (obj.equals(comparedObject)) {
				return i;
			}
			i++;
			if (i >= rowCount) {
				m.moreData();
				rowCount = m.getRowCount();
			}
		}
		return -1;
	}

}
