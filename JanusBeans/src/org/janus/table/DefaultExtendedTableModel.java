package org.janus.table;

import java.util.Collections;

import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import org.janus.free.Removable;

public class DefaultExtendedTableModel extends DefaultTableModel implements
		ExtendedTableModel, Removable {
	
	private static long sid=1;
	private long id;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4304583020443491079L;
	private transient VectorComperator comperator = null;
	private int current = 0;
	private long generation=1;

	public DefaultExtendedTableModel(String h[], int c) {
		super(h, c);
		initId();
		current = 0;
	}

	public DefaultExtendedTableModel() {
		super();
		initId();
		current = 0;
	}

	/**
	 * @param args
	 */
	@Override
	public int getCurrentRow() {
		return current;
	}

	@Override
	public void setCurrentRow(int current) {
		this.current = current;
	}

	@Override
	public boolean moreData() {
		return false;
	}

	@Override
	public boolean initData() {
		return true;
	}


	@Override
	public int find(Object obj, int column) {

		return TableSearch.find(this, obj, column);
	}

	@Override
	public void sort(boolean ascending, int column) {
		if (comperator == null) {
			comperator = new VectorComperator();
		}
		;
		comperator.setAscending(ascending);
		comperator.setColumn(column);
		Collections.sort(dataVector, comperator);
		fireTableChanged(new TableModelEvent(this));
	}

	public VectorComperator getComperator() {
		return comperator;
	}

	public void setComperator(VectorComperator comperator) {
		if (comperator == null) {
			throw new NullPointerException();
		}
		this.comperator = comperator;
	}

	@Override
	public void removeData() {
		int anz = getRowCount() - 1;
			while (anz > 0) {
			removeRow(anz);
			anz--;
		}
		setCurrentRow(0);
		setRowCount(0);
	}

	@Override
	public void remove() {
		removeData();
	}


	protected void incGeneration() {
		generation++;
		
	}

	@Override
	public long getGeneration() {
		
		return generation;
	}

	private synchronized void initId() {
		id = sid;
		sid++;
	}
	
	@Override
	public long getId() {
		return id ;
	}

}
