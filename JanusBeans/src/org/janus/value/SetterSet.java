package org.janus.value;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.beanutils.DynaBean;
import org.janus.data.DataContext;

/*
 * Setzt mehrere Werte
 * 
 */
public class SetterSet extends Vector<Setter> {

	
	// Wert einer Hashtable setzen
	public void setHashtable(DataContext data, Hashtable<String, Object> h) {
		for (Setter s : this) {
			s.setHashtable(data, h);
		}
	}

	// in einer Query ein ? ersetzen
	public String replace(DataContext data, String in) {
		String erg = in;
		for (Setter s : this) {
			erg = s.replace(data, erg);
		}
		return erg;
	}

	// in einem prepaed Statement einen Wert setzen
	public void setStatement(DataContext data, PreparedStatement stmt)
			throws SQLException {
		int pos = 1;
		for (Setter s : this) {
			s.setStatement(data, stmt, pos);
			pos++;
		}
	}

	// Ein Attriobut eines Beans setzen
	public void setBean(DataContext data, DynaBean bean) {
		for (Setter s : this) {
			s.setBean(data, bean);
		}
	}

	

}
