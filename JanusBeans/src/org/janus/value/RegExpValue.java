package org.janus.value;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.janus.data.DataContext;
import org.janus.dict.actions.ActionDictionary;
import org.janus.dict.interfaces.BooleanValue;

/*
 * SBestimmt, ob eine reguläre Expression erfüllt ist
 * 
 * 
 */
public class RegExpValue implements BooleanValue {

	private RefDataValue value;
	private String patternString;
	private String valueName;
	private Pattern pattern;

	private static final long serialVersionUID = -6080465576634623265L;

	public RegExpValue() {
	}

	/*
	 * Pattern p = Pattern.compile("a*b"); Matcher m = p.matcher("aaaaab");
	 * boolean b = m.matches();
	 */

	public String getPatternString() {
		return patternString;
	}

	public void setPatternString(String patternString) {
		this.patternString = patternString;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}


	@Override
	public boolean isOk(DataContext context) {
		synchronized (this) {
			String v = getValue(context);
			if (v == null)
				return false;
			return testPattern(v);
		}
	}

	private boolean testPattern(String text) {
		if (pattern == null) {
			pattern = Pattern.compile(patternString);
		}
		Matcher m = pattern.matcher(text);
		return m.matches();
	}

	private String getValue(DataContext data) {
		if (value == null) {
			value = new RefDataValue();
			value.setSourceName(valueName);
			value.setModel((ActionDictionary)data.getDataDescription());
		}
		Serializable o = value.getObject(data);
		return (o == null) ? null : o.toString();
	}
}
