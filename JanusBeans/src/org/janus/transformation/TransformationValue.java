package org.janus.transformation;

import java.io.Serializable;
import java.util.List;

import org.janus.actions.ReadValue;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;
import org.janus.dict.actions.ActionDictionary;

/**
 * Berechnung einer Funktion aus Werten, die vom Context stammen,
 * speichert das Egebnis im Context ab
 * 
 * @author javaman
 *
 */

public class TransformationValue extends
		ObjectSequence<Transformation, ReadValue> implements ReadValue {

	private static final long serialVersionUID = -2425397182441185798L;
	

	public TransformationValue(ActionDictionary model) {
		super(model);
	}

	@Override
	public Serializable getObject(DataContext data) {
		Transformation action = getAction();
		Serializable[] o = werteInArraySchreiben(data);
		return action.calculate(o);
	}

	private Serializable[] werteInArraySchreiben(DataContext data) {
		List<ReadValue> values = getValues();
		Serializable o[] = new Serializable[values.size()];
		int i = 0;
		for (ReadValue a : getValues()) {
			o[i] = a.getObject(data);
			i++;
		}
		return o;
	}

	@Override
	public void configure(DataDescription description) {
	}

	
	@Override
	public void performWith(DataContext data, Transformation k, ReadValue o) {
		
	}

	
}
