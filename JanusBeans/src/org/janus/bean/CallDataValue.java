package org.janus.bean;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.ConvertingWrapDynaBean;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.janus.actions.EmptyAction;
import org.janus.actions.ReadValue;
import org.janus.data.DataContext;
import org.janus.value.RefDataValue;
import org.janus.value.Setter;
import org.janus.value.SetterSet;
import org.janus.value.TextDataValue;

/**
 * 
 * Aufruf einer Funktion commad eines Objektes, zuvor werden Daten auf dem Object gesetzt
 * 
 * @author Thomas Nill
 * 
 * 
 */
public class CallDataValue extends EmptyAction {
	private static final Logger LOG = LogManager.getLogger(CallDataValue.class);

	BeanDataValue value;
	String command;
	SetterSet setters = new SetterSet();

	public CallDataValue(BeanDataValue value, String command) {
		super();
		this.value = value;
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public ReadValue getBean() {
		return value;
	}

	public Serializable getBeanValue(DataContext data) {
		return value.getObject(data);
	}


	public void addSetterValue(String target, String source) {
		RefDataValue value = new RefDataValue();
		value.setSourceName(source);
		addSetter(target, value);
	}

	public void addSetterConstant(String target, String constant) {
		RefDataValue value = new RefDataValue();
		value.setConstant(constant);
		addSetter(target, value);
	}

	public void addSetterText(String target, String text) {
		TextDataValue value = new TextDataValue(text);
		addSetter(target, value);
	}

	private void addSetter(String target,ReadValue value) {
		Setter s = new Setter();
		s.setTargetName(target);
		s.setValue(value);
		addSetter(s);
	}


	

	@Override
	public void perform(DataContext data) {
		Object obj = setzenVonProperties(data);
		comandAufrufen(obj);
	}

	protected void comandAufrufen(Object obj) {
		try {
			MethodUtils.invokeMethod(obj, command, null);
		} catch (NoSuchMethodException e) {
			LOG.error("Keine Methode {} {}",command,e);
		} catch (IllegalAccessException e) {
			LOG.error("Illegaler Zugriff {} {}",command,e);
		} catch (InvocationTargetException e) {
			LOG.error("Objekt passt nicht zu {} {}",command,e);
		}
	}

	protected Object setzenVonProperties(DataContext data) {
		Object obj = value.getObject(data);
		ConvertingWrapDynaBean dynBean = new ConvertingWrapDynaBean(obj);
		setters.setBean(data, dynBean);
		return obj;
	}

	public void addSetter(Setter s) {
		setters.addElement(s);
		
	}

}
