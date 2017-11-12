package org.janus.table;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class DelegatingExtendedTableModel implements ExtendedTableModel{
	private TableModel tm;
	private int currentRow;
	private int currentColumn;

	public DelegatingExtendedTableModel(TableModel tm) {
		super();
		this.tm = tm;
	}

	@Override
	public int getRowCount() {
		return tm.getRowCount();
	}

	@Override
	public int getColumnCount() {
		return tm.getColumnCount();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return tm.getColumnName(columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return tm.getColumnClass(columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return tm.isCellEditable(rowIndex, columnIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
	    if (rowIndex <0) {
	        rowIndex = 0;
	    }
		return tm.getValueAt(rowIndex, columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		tm.setValueAt(aValue, rowIndex, columnIndex);
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		tm.addTableModelListener(l);
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		tm.removeTableModelListener(l);
	}

	@Override
	public long getId() {
		return 0;
	}

	@Override
	public long getGeneration() {
		return 0;
	}

	@Override
	public int getCurrentRow() {
		return currentRow;
	}

	@Override
	public void setCurrentRow(int current) {
		currentRow = current;
		
	}

	@Override
	public boolean moreData() {
		return false;
	}

	@Override
	public boolean initData() {
		return false;
	}

	@Override
	public void removeData() {
	}

	@Override
	public int find(Object obj, int column) {
		return TableSearch.find(this, obj, column);
	}

	@Override
	public void sort(boolean ascending, int column) {
	
	}

    @Override
    public int getCurrentColumn() {
        return currentColumn;
    }

    @Override
    public void setCurrentColumn(int current) {
        this.currentColumn = current;
        
    }
	
	

}
