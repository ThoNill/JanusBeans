package org.janus.value;

import java.io.Serializable;
import java.util.Collection;

import org.janus.actions.ReadValue;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;
import org.janus.dict.actions.ActionDictionary;

/*
 * Setzt die Werte von Variablen der Form ${name} in einen Text ein
 */
public class TextDataValue extends VariableNamesInAText implements ReadValue {

	private Collection<RefDataValue> values;

	public Collection<RefDataValue> getValues() {
		return values;
	}

	private static final long serialVersionUID = -6080465576634623265L;

	private DataDescription model;

	public TextDataValue(String text) {
		values = getDataValues(text);
	}

	
	public DataDescription getModel() {
		return model;
	}

	
	public void setDataDescription(DataDescription model) {
		this.model = model;
		for (RefDataValue v : values) {
			v.setModel((ActionDictionary)model);
		}
	}

	@Override
	public Serializable getObject(DataContext data) {
		if (this.model == null) {
			setModel(data.getDataDescription());
		};
		StringBuilder builder = new StringBuilder();
		for (RefDataValue v : values) {
			builder.append(	getStringValue(data, v)	);
		}

		return builder.toString();
	}

	private void setModel(DataDescription dataDescription) {
		this.model = dataDescription;
		
	}


	private String getStringValue(DataContext data, RefDataValue v) {
		Object o = v.getObject(data);
		return (o == null) ? "" : o.toString();
	}



}
