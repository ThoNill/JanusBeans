package org.janus.transformation;



import java.util.Vector;

import org.janus.actions.Action;
import org.janus.data.DataContext;
import org.janus.data.DefaultClassFactory;
import org.janus.dict.actions.ActionDictionary;

/**
 * 
 * Führt eine Action auf einer Reihe von Objekten aus.
 * @author javaman
 *
 * @param <K>
 * @param <O>
 */
public abstract class ObjectSequence<K, O> implements Action  {

	private static final long serialVersionUID = -2425397182441185798L;
	protected Vector<O> values;
	private K action = null;
	private String classname = null;
	protected String nameList = null;
	private ActionDictionary model;

	public ObjectSequence(ActionDictionary model) {
		super();
		this.model = model;
	}

	public void setNameList(String nameList) {
		this.nameList = nameList;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public K getAction() {
		if (action == null && classname != null) {
			action = (K) DefaultClassFactory.FACTORY.getInstance(classname);
		}
		return action;
	}

	@Override
	public void perform(DataContext data) {
		K action = getAction();
		for (O a : getValues()) {
			performWith(data, action, a);
		}
	}

	public abstract void performWith(DataContext data, K k, O o);

	public Vector<O> getValues() {
		if (values == null && nameList != null) {
			String names[] = nameList.split(" *, *");
			values = new Vector<O>(names.length);
			for (int i = 0; i < names.length; i++) {
				O o = getObjectFromName(names[i]);
				values.addElement(o);
			}
		}
		return values;
	}

	private O getObjectFromName(String name) {
		return (O) model.getAction(name);
	}
}
