package org.janus.table;

import java.util.Comparator;
import java.util.Vector;

public class VectorComperator implements Comparator {
	private boolean ascending;
	private int column;

	public VectorComperator() {
		this(true, 0);
	}

	public VectorComperator(boolean ascending, int column) {
		this.ascending = ascending;
		this.column = column;
	}

	@Override
	public int compare(Object arg0, Object arg1) {

		if (arg0 != null && arg1 != null && arg0 instanceof Vector
				&& arg1 instanceof Vector) {
			if (column < ((Vector) arg0).size()) {
				arg0 = ((Vector) arg0).elementAt(column);
			} else {
				arg0 = null;
			}
			;
			if (column < ((Vector) arg1).size()) {
				arg1 = ((Vector) arg1).elementAt(column);
			} else {
				arg1 = null;
				;
			}

		}

		if (arg0 == null) {
			if (arg1 == null)
				return 0;
			return (ascending) ? -1 : 1;
		}

		String s0 = arg0.toString();
		String s1 = arg1.toString();
		return ((ascending) ? s0.compareTo(s1) : -s0.compareTo(s1));
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	public void setColumn(int column) {
		this.column = column;
	}

}
