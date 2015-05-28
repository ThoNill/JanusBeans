package org.janus.value;

import java.io.Serializable;

import org.janus.data.DataContext;
import org.janus.dict.interfaces.BooleanValue;

/*
 * liefert einen boolschen Wert v == "TRUE"
 * 
 */
public class BooleanScriptValue extends ScriptValue implements BooleanValue {

	private static final long serialVersionUID = -6080465576634623265L;

	public BooleanScriptValue(String script) {
		super(script);
	}

	@Override
	public boolean isOk(DataContext context) {
		Serializable v = super.getObject(context);
		if (v == null)
			return false;
		if (v instanceof Boolean) {
			return ((Boolean)v).booleanValue();
		}
		String s = v.toString().toUpperCase();
		return "TRUE".equals(s);
	}

}
