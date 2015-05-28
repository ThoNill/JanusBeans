package org.janus.datatype;

import java.io.Serializable;

import org.janus.single.ObjectCreator;

public class DefaultData implements ObjectCreator {
	Serializable defValue;
	
	public DefaultData(Serializable defValue) {
		this.defValue = defValue;
	}

	@Override
	public Serializable create() {
		return defValue;
	}

}
