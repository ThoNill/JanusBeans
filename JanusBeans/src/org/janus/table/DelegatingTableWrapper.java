package org.janus.table;

import javax.swing.table.TableModel;

import org.janus.dict.actions.ActionDictionary;

public class DelegatingTableWrapper extends DefaultExtendedTableWrapper{
	private TableModel tm;
	
	public DelegatingTableWrapper(String name, ActionDictionary dict,TableModel tm) {
		super(name, dict);
		this.tm = tm;
		
	}

	@Override
	public ExtendedTableModel createTable() {
		return new DelegatingExtendedTableModel(tm);
	}

}
