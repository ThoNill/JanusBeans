package org.janus.value;

import java.io.Serializable;
import java.util.Collection;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.janus.data.DataContext;

/**
 * Führt eine Wertberechnung mit einem Skript aus.
 * 
 * Beispiel:
 * 
 * "  ${a} ${b} "
 * 
 * wir zunächst durch
 * 
 * " xvar1 + xvar2 " 
 * 
 * ersetzt
 * 
 * 
 * 
 * @author Thomas Nill
 *
 */
public class ScriptValue extends TextDataValue {

	private static final long serialVersionUID = -6311431438777125283L;
	String script;
	String originalScript;

	public ScriptValue(String script) {
		super(script);
		this.script = script;
		this.script = prepareText(script); // Ersetzung von ${...} durch xvar1, ...
	}

	@Override
	public String replaceWith(String name, int i) {
		return "xvar" + i;
	}

	@Override
	public Collection<RefDataValue> getDataValues(String stmt) {
		return getNonConstantDataValues(stmt);
	}

	@Override
	public Serializable getObject(DataContext context) {
		
		ScriptEngineManager mgr1 = new ScriptEngineManager();
		ScriptEngine jsEngine = mgr1.getEngineByName("JavaScript");
		try {
			einsetzenDerVariablenWerte(jsEngine,context);
			Object r = jsEngine.eval(script);
			return (Serializable) r;
		} catch (ScriptException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	protected void einsetzenDerVariablenWerte(ScriptEngine jsEngine,DataContext context) {
		int i = 0;
		for (RefDataValue v : getValues()) {
			jsEngine.put("xvar" + i, v.getObject(context));
			i++;
		}
	}
}
