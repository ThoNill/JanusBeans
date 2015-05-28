package test.bean;

import java.io.Serializable;

import org.janus.transformation.Transformation;

public class TestTransformation implements Transformation {

	@Override
	public Serializable calculate(Serializable values[]) {
		String erg ="";
		for(int i=0;i< values.length;i++) {
			erg = erg + values[i]+ "%";
		};
		return erg;
	}

}
