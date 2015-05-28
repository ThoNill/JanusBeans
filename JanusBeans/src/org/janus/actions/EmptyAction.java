package org.janus.actions;

import java.io.Serializable;

import org.janus.data.DataContext;
import org.janus.data.DataDescription;

public class EmptyAction implements Action, Serializable {
	private static final long serialVersionUID = 6157053819274744143L;
	
	
	public EmptyAction() {
		super();
	}

	@Override
	public void perform(DataContext data) {
	}

	@Override
	public void configure(DataDescription description) {
	}



}
