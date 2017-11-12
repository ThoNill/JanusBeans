package org.janus.table;

import java.io.Serializable;

import javax.swing.table.TableModel;

public interface ExtendedTableModel extends TableModel, Serializable {
	
    long getId();
	
	 long getGeneration();

	 int getCurrentRow();
	 void setCurrentRow(int current);

     int getCurrentColumn();
	 void setCurrentColumn(int current);

	 
	 boolean moreData();

	 boolean initData();

	 void removeData();

	 int find(Object obj, int column);

	 void sort(boolean ascending, int column);

}
