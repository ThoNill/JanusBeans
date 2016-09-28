package org.janus.table;

import java.util.Comparator;
import java.util.List;

public class ListComperator implements Comparator {
    private boolean ascending;
    private int column;

    public ListComperator() {
        this(true, 0);
    }

    public ListComperator(boolean ascending, int column) {
        this.ascending = ascending;
        this.column = column;
    }

    @Override
    public int compare(Object argA, Object argB) {
        Object a = argA;
        Object b = argB;
        if (a != null && b != null && a instanceof List
                && b instanceof List) {
            if (column < ((List) a).size()) {
                a = ((List) a).get(column);
            } else {
                a = null;
            }
            if (column < ((List) b).size()) {
                b = ((List) b).get(column);
            } else {
                b = null;
            }

        }

        if (a == null || b == null) {
            if (b == null && a == null)
                return 0;
            return ascending ? -1 : 1;
        }

        String s0 = a.toString();
        String s1 = b.toString();
        int compareValue = s0.compareTo(s1);
        return ascending ? compareValue : -compareValue;
        }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public void setColumn(int column) {
        this.column = column;
    }

}
