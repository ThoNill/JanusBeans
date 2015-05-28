package org.janus.value;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;

import org.apache.commons.beanutils.DynaBean;
import org.janus.actions.ReadValue;
import org.janus.data.DataContext;

/*
 * Setzt den Wert einer Hashtable usw, auf eine Konstante oder einen Wert
 * 
 * 
 */
public class Setter implements ReadValue {

	private String targetName = null; // Name dessen, dessen Wert gesetzt wird
	private ReadValue value;

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	@Override
	public Serializable getObject(DataContext data) {
		return value.getObject(data);
	}

	public void setValue(ReadValue value) {
		this.value = value;
	}

	// Wert einer Hashtable setzen
	public void setHashtable(DataContext data, Hashtable<String, Object> h) {
		if (targetName != null) {
			h.put(targetName, getObject(data));
		}
	}

	// in einer Query ein ? ersetzen
	public String replace(DataContext data, String in) {
		Serializable obj = getObject(data);
		return in.replaceFirst("\\?", obj.toString());
	}

	// in einem prepaed Statement einen Wert setzen
	public void setStatement(DataContext data, PreparedStatement stmt, int pos)
			throws SQLException {
		Object obj = getObject(data);
		stmt.setObject(pos, obj);
	}

	// Ein Attriobut eines Beans setzen
	public void setBean(DataContext data, DynaBean bean) {
		Object obj = getObject(data);
		bean.set(targetName, obj);
	}



}
