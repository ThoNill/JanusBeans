package org.janus.bean;

import java.io.Serializable;

import org.janus.actions.DefaultValue;
import org.janus.data.DefaultClassFactory;
import org.janus.single.ObjectCreator;

/*
 * Ein DatenWert im Context, der ein serialisierbares Objekt (Bean) ist
 * 
 */
public class BeanDataValue extends DefaultValue implements ObjectCreator {
	private static final long serialVersionUID = -222723458893520395L;
	String classname;

	public BeanDataValue() {
		super(null);
		setCreator(this);
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	@Override
	public Serializable create() {
		if (classname == null) {
			throw new IllegalArgumentException("Kein Classname in gesetzt");
		}
		return (Serializable) DefaultClassFactory.FACTORY.createAndCheckInstance(classname,
				Serializable.class);
	}

	

}
