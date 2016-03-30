package org.janus.table;

import java.io.Serializable;

public class SelectRowHint implements Serializable {
	private final int row;

	public SelectRowHint(int row) {
		super();
		this.row = row;
	}
	
	public SelectRowHint(String row) {
		super();
		this.row = Integer.parseInt(row);
	}

	public int getRow() {
		return row;
	}
	
}
