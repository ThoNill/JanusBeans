package org.janus.value;


import java.io.Serializable;

import org.apache.commons.beanutils.ConvertingWrapDynaBean;
import org.janus.actions.ReadValue;
import org.janus.data.DataContext;
import org.janus.dict.actions.ActionDictionary;
import org.janus.dict.actions.ActionEventSender;
import org.janus.dict.actions.NamedActionValue;
import org.janus.dict.interfaces.ActionListener;

/*
 * DatenReferenzen können einen konstanten Wert haben oder auf eine Variable oder
 * das Ergebnis eine Funktionsauswertung sein.
 * 
 */
public class RefDataValue extends ActionEventSender implements ReadValue, ActionListener {
	private static final long serialVersionUID = -6080465576634623265L;
	private String sourceName = null; // Name des Quelle für den Wert

	// Der Wert ist entweder ...
	private Serializable constant = null; // eine Konstante
	private transient NamedActionValue sourceValue = null; // oder ein Value
	private String dynaBeanPath; // oder die Auswertung einer Funktion auf einem Bean

	private ActionDictionary model;

	// RhinoConnector rhinoScript = null;

	
	public ActionDictionary getModel() {
		return model;
	}


	public void setModel(ActionDictionary model) {
		if (this.model == null && constant == null && model != null
				&& this.model != model) {
			this.model = model;
			this.sourceValue = null;
			getSourceValue();
		}
	}

	public RefDataValue() {
		super();
	}

	public void setConstant(Serializable constant) {
		this.constant = constant;
	}

	public void setSourceName(String to) {
		this.sourceName = to;
	}

	public void setToValue(NamedActionValue toValue) {
		this.sourceValue = toValue;
	}

	/**
	 * 
	 * Sucht im Model nach einen Bean der mit name1.name2 ... ansprechbar ist.
	 * 
	 * @return
	 */
	public NamedActionValue getSourceValue() {
		if (sourceValue == null && sourceName != null && model != null) {
				String s = sourceName;
				sourceValue = model.getAction(s);
				int ip = sourceName.length();
				while (sourceValue == null) {
					ip = s.lastIndexOf('.', ip);
					s = sourceName.substring(0, ip);
					sourceValue = model.getAction(s);
					if (sourceValue != null) {
						dynaBeanPath = sourceName.substring(ip + 1);
						sourceName = s;
					}
				}
				sourceValue.addActionListener(this);
		}
		return sourceValue;
	}

	/*
	 * Bestimmt den Wert einer Referenz 
	 * 
	 * @see janus.tech.model.DataValue#getValue(janus.tech.model.DataContext)
	 */
	@Override
	public Serializable getObject(DataContext data) {
		if (constant != null)
			return constant;

		if (this.model == null) {
			setModel((ActionDictionary)data.getDataDescription());
		}

		sourceValue = getSourceValue();
		if (sourceValue != null) {
			Serializable bean = sourceValue.getObject(data);
			if (dynaBeanPath != null) {
				ConvertingWrapDynaBean dynBean = new ConvertingWrapDynaBean(bean);
				return (Serializable) dynBean.get(dynaBeanPath);
			}
			return bean;
		}
		return constant;
	}

	@Override
	public void actionPerformed(Object a, DataContext data) {
		fireActionIsPerformed(data);

	}



}
