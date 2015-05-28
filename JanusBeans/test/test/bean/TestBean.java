package test.bean;

import java.io.Serializable;

import org.janus.dict.helper.ID;

public class TestBean implements Serializable {
	private String a;
	private String b;
	private String c;
	int id;
	
	public TestBean() {
		id = ID.getId();
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public void concat() {
		c = a + b;
	}
}
