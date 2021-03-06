package org.janus.table;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.janus.actions.Action;
import org.janus.actions.DefaultValue;
import org.janus.actions.ReferenzToValue;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;
import org.janus.dict.actions.ActionDictionary;
import org.janus.dict.actions.NamedActionValue;
import org.janus.dict.actions.PageAction;
import org.janus.single.ObjectCreator;

public class DefaultExtendedTableWrapper extends DefaultValue implements
        Action, ObjectCreator {
    private NamedActionValue currentRow;
    private NamedActionValue currentColumn;
    private NamedActionValue rowCount;
    private NamedActionValue columnCount;
    private NamedActionValue table;
    private NamedActionValue currentValue;
    private String name;
    private ActionDictionary model;

    private List<DefaultTableColumn> columns = new ArrayList<DefaultTableColumn>();

    public DefaultExtendedTableWrapper(String name, ActionDictionary dict) {
        super(null);
        this.name = name;
        setModel(dict);
        setCreator(this);
    }

    public void setModel(ActionDictionary model) {
        this.model = model;
        table = model.addAction(name, this);
        rowCount = model.addAction(name + ".rowCount", new ReferenzToValue(
                this, new RowCount()));
        columnCount = model.addAction(name + ".columnCount",
                new ReferenzToValue(this, new ColumnCount()));
        currentRow = model.addAction(name + ".currentRow", new ReferenzToValue(
                this, new CurrentRow()));
        currentColumn = model.addAction(name + ".currentColumn",
                new ReferenzToValue(this, new CurrentColumn()));
        currentValue = model.addAction(name + ".cellValue",
                new ReferenzToValue(this, new CellValue()));
    }

    public void addColumn(String name) {
        DefaultTableColumn c = new DefaultTableColumn(this, columns.size(),
                name);
        addColumn(c);
    }

    public void addColumn(DefaultTableColumn c) {
        columns.add(c);
        NamedActionValue av = model
                .addAction(name + "." + c.getColumnName(), c);
        table.addActionListener(av);
        currentRow.addActionListener(av);
    //    currentColumn.addActionListener(av);
    //    currentValue.addActionListener(av);
    }

    private static final long serialVersionUID = 6709605480024566081L;

    public ExtendedTableModel createTable() {
        String[] cols = createColumnNames();
        return new DefaultExtendedTableModel(cols, 0);
    }

    @Override
    public Serializable create() {
        return createTable();
    }

    protected String[] createColumnNames() {
        int size = columns.size();
        String cols[] = new String[size];
        int i = 0;
        for (DefaultTableColumn c : columns) {
            cols[i] = c.getColumnName();
            i++;
        }
        return cols;
    }

    public ExtendedTableModel getTable(DataContext data) {
        return (ExtendedTableModel) table.getObject(data);
    }

    public NamedActionValue getCurrentRow() {
        return currentRow;
    }

    public NamedActionValue getCurrentColumn() {
        return currentColumn;
    }

    public PageAction getTable() {
        return table;
    }

    public int getCurrentRow(DataContext data) {
        return getTable(data).getCurrentRow();
    }

    public void setCurrentRow(DataContext data, int current) {
        currentRow.setObject(data, current);
        currentValue.fireActionIsPerformed(data);
    }

    public int getCurrentColumn(DataContext data) {
        return getTable(data).getCurrentColumn();
    }

    public void setCurrentColumn(DataContext data, int current) {
        currentColumn.setObject(data, current);
        currentValue.fireActionIsPerformed(data);
    }

    public NamedActionValue getCurrentValue() {
        return currentValue;
    }

    
    public PageAction getRowCount() {
        return rowCount;
    }

    public boolean moreData(DataContext data) {
        boolean more = getTable(data).moreData();
        if (more) {
            table.fireActionIsPerformed(data);
            rowCount.fireActionIsPerformed(data);
        }
        return more;
    }

    public void removeData(DataContext data) {
        getTable(data).removeData();
    }

    public int find(DataContext data, Object obj, int column) {
        return getTable(data).find(obj, column);
    }

    public void sort(DataContext data, boolean ascending, int column) {
        getTable(data).sort(ascending, column);
    }

    public Class<?> getColumnClass(int arg0) {
        return String.class;
    }

    public PageAction getColumnCount() {
        return columnCount;
    }

    public String getColumnName(int arg0) {
        return columns.get(arg0).getColumnName();
    }

    public Serializable getValueAt(DataContext data, int arg0, int arg1) {
        return (Serializable) getTable(data).getValueAt(arg0, arg1);
    }

    public Serializable getValueAt(DataContext data, int arg1) {
        ExtendedTableModel tm = getTable(data);
        return (Serializable) tm.getValueAt(tm.getCurrentRow(), arg1);
    }

    public Serializable getValueAt(DataContext data) {
        ExtendedTableModel tm = getTable(data);
        return (Serializable) tm.getValueAt(tm.getCurrentRow(), tm.getCurrentColumn());
    }

    
    public boolean isCellEditable(int arg0, int arg1) {
        return false;
    }

    public void setValueAt(DataContext data, Object arg0, int arg1, int arg2) {
        getTable(data).setValueAt(arg0, arg1, arg2);
    }

    public DefaultTableColumn getColumn(String name) {
        for (DefaultTableColumn column : columns) {
            if (name.equals(column.getColumnName())) {
                return column;
            }
        }
        return null;
    }

    @Override
    public void configure(DataDescription description) {

    }

    @Override
    public void perform(DataContext context) {
        getTable(context).initData();
        currentRow.setObject(context, "0");
        currentColumn.setObject(context, "0");
        rowCount.fireActionIsPerformed(context);
        columnCount.fireActionIsPerformed(context);
        currentValue.fireActionIsPerformed(context);
    }

    public String getName() {
        return name;
    }

    public List<DefaultTableColumn> getColumns() {
        return columns;
    }

    @Override
    public void setObject(DataContext ctx, Serializable value) {
        if (value instanceof ExtendedTableModel) {
            super.setObject(ctx, value);
        } else if (value instanceof SelectRowHint) {
            setCurrentRow(ctx, ((SelectRowHint) value).getRow());
        } else if (value instanceof SearchValueInColumnHint) {
            SearchValueInColumnHint hint = ((SearchValueInColumnHint) value);
            find(ctx, hint.getValue(), hint.getColumn());
        } else {
            throw new IllegalArgumentException("set= "
                    + value.getClass().getSimpleName());
        }
    }

}
