package org.janus.table;

import java.io.Serializable;

import org.janus.actions.Action;
import org.janus.actions.DataFormat;
import org.janus.actions.DataValue;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;

public class DefaultTableColumn implements DataValue , Action {

	private static final long serialVersionUID = -390017133534792797L;

	private String columnName;
	private int index;
	private DefaultExtendedTableWrapper table;
	private DataFormat format;

	public DefaultTableColumn(DefaultExtendedTableWrapper table, int column,
			String name) {
		this.table = table;
		this.columnName = name;
		setIndex(column);
	}

	public Class<?> getColumnClass() {
		return String.class;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0)
			return table.isCellEditable(rowIndex, index);
		return false;
	}

	public Serializable getObject(DataContext data, int rowIndex) {
		return table.getValueAt(data, rowIndex, index);
	}

	@Override
	public Serializable getObject(DataContext data) {
	    return table.getValueAt(data, index);
	}
	

	public void setValueAt(DataContext data, Object aValue, int rowIndex) {
		table.setValueAt(data, aValue, rowIndex, index);
	}

	public void findValue(DataContext data, Serializable obj) {
		int pos = table.find(data, obj, index);
		if (pos >= 0) {
			table.setCurrentRow(data, pos);
			
		}
	}

	public int find(DataContext data, Object obj, int column) {
		return table.find(data, obj, column);
	}

	public void sort(DataContext data, boolean ascending) {
		table.sort(data, ascending, this.index);
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	protected int getIndex() {
		return index;
	}

	protected void setIndex(int index) {
		this.index = index;
	}

	
	@Override
	public void setObject(DataContext ctx, Serializable value) {
		findValue(ctx,value);
	}

	@Override
	public void configure(DataDescription description) {
		
	}

	@Override
	public DataFormat getFormat() {
		return format;
	}

	@Override
	public void setFormat(DataFormat format) {
		this.format = format;
	}

	@Override
	public void perform(DataContext context) {
	}

	



}