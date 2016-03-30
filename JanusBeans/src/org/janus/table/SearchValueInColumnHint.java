package org.janus.table;

import java.io.Serializable;

public class SearchValueInColumnHint implements Serializable {

	private static final long serialVersionUID = 7765729308888601927L;
	private final int column;
	private final Serializable value;

	public SearchValueInColumnHint(int column, Serializable value) {
		super();
		this.column = column;
		this.value = value;
	}
	
	public SearchValueInColumnHint(Serializable value) {
		this(0,value);
	}

	public int getColumn() {
		return column;
	}

	public Serializable getValue() {
		return value;
	}
}
