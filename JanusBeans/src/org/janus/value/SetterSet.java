package org.janus.value;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.janus.data.DataContext;

/*
 * Setzt mehrere Werte
 * 
 */
public class SetterSet extends ArrayList<Setter> {
	private static final Logger LOG = LogManager.getLogger(SetterSet.class);

	
	// Wert einer HashMap setzen
	public void setHashMap(DataContext data, HashMap<String, Object> h) {
		LOG.debug("Setze HashMap");
		for (Setter s : this) {
			s.setHashMap(data, h);
		}
	}

	// in einer Query ein ? ersetzen
	public String replace(DataContext data, String in) {
		LOG.debug("replace");
		String erg = in;
		for (Setter s : this) {
			erg = s.replace(data, erg);
		}
		return erg;
	}

	// in einem prepaed Statement einen Wert setzen
	public void setStatement(DataContext data, PreparedStatement stmt)
			throws SQLException {
		LOG.debug("Setze Statement");
		int pos = 1;
		for (Setter s : this) {
			s.setStatement(data, stmt, pos);
			pos++;
		}
	}

	// Ein Attriobut eines Beans setzen
	public void setBean(DataContext data, DynaBean bean) {
		LOG.debug("Setze Bean");
		for (Setter s : this) {
			s.setBean(data, bean);
		}
	}

	

}
