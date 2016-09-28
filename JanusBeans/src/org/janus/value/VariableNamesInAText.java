package org.janus.value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Isoliert Variablennamen der Form ${nam} in einem Text
 */
public class VariableNamesInAText implements Serializable {
	private static final long serialVersionUID = 8361072560079359625L;
	private static final String TRENNER = "(\\$\\{|\\})";

	public String prepareText(String stmt) {
		String s[] = stmt.split(TRENNER);
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < s.length; i++) {
			if (i % 2 == 0) {
				b.append(s[i]);
			} else {
				b.append(replaceWith(s[i - 1], (i - 1) / 2));
			}
		}
		return b.toString();
	}

	public String replaceWith(String name, int i) {
		return "?";
	}

	public static Collection<String> getVariablen(String stmt) {
		List<String> liste = new ArrayList<String>();
		String s[] = stmt.split(TRENNER);
		for (int i = 1; i < s.length; i += 2) {
			liste.add(s[i]);
		}
		return liste;
	}

	public Collection<RefDataValue> getDataValues(String stmt) {
		List<RefDataValue> liste = new ArrayList<RefDataValue>();
		String s[] = stmt.split(TRENNER);
		for (int i = 0; i < s.length; i++) {
			RefDataValue v = new RefDataValue();
			if (i % 2 == 0) {
				v.setConstant(s[i]);
			} else {
				v.setSourceName(s[i]);
			}
			liste.add(v);
		}
		return liste;
	}

	public static Collection<RefDataValue> getNonConstantDataValues(String stmt) {
		List<RefDataValue> liste = new ArrayList<RefDataValue>();
		String s[] = stmt.split(TRENNER);
		for (int i = 0; i < s.length; i++) {
			if (i % 2 != 0) {
				RefDataValue v = new RefDataValue();
				v.setSourceName(s[i]);
				liste.add(v);
			}
		}
		return liste;
	}

}
