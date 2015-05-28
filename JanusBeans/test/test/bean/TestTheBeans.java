package test.bean;

import java.io.Serializable;

import org.janus.actions.DefaultValue;
import org.janus.bean.BeanDataValue;
import org.janus.bean.CallDataValue;
import org.janus.data.DataContext;
import org.janus.data.DefaultClassFactory;
import org.janus.datatype.DefaultData;
import org.janus.dict.actions.ActionDictionary;
import org.janus.dict.actions.ListeningValue;
import org.janus.dict.actions.NamedActionValue;
import org.janus.dict.actions.PageAction;
import org.janus.dict.actions.PageValue;
import org.janus.dict.interfaces.ActionListener;
import org.janus.value.RefDataValue;
import org.janus.value.VariableNamesInAText;
import org.junit.Assert;
import org.junit.Test;

public class TestTheBeans implements ActionListener {
	int count;

	@Test
	public void classFactory() {
		Object obj = DefaultClassFactory.FACTORY.createAndCheckInstance("test.bean.TestBean",Serializable.class);
		boolean ok = (obj instanceof test.bean.TestBean);
		Assert.assertTrue(ok);
	}

	@Test
	public void helperStringSplitting() {
		HelperGetPrepare("", "");
		HelperGetPrepare(" a  ", " a  ");
		HelperGetPrepare(" a ? ", " a ${b} ");
		HelperGetPrepare(" a ?", " a ${b}");
	}

	private void HelperGetPrepare(String erwartet, String original) {
		VariableNamesInAText h = new VariableNamesInAText();
		String erg = h.prepareText(original);
		Assert.assertEquals(erwartet, erg);
	}

	private void RefTest(String original, String testwert) {
		ActionDictionary m = new ActionDictionary("test");
		PageAction vu = getActionValue(m,original,"");
		

		RefDataValue ref = new RefDataValue();
		ref.setSourceName(testwert);
		ref.setModel(m);

		NamedActionValue v = ref.getSourceValue();
		Assert.assertEquals(vu, v);
	}

	protected PageAction getActionValue( ActionDictionary m,String original,String defValue) {
		DefaultData d = new DefaultData(defValue);
		
		DefaultValue dv = new ListeningValue(d);
		PageAction vu = m.addAction(original,dv);
		return vu;
	}

	@Test
	public void reftest() {
		RefTest("au", "au");
		RefTest("au", "au.bu");
		RefTest("au.z", "au.z.bu");
		RefTest("au.z", "au.z.bu.k.z");
		RefTest("au.z.bu", "au.z.bu.k.z");
	}

	@Test
	public void beanvalue() {
		ActionDictionary m = new ActionDictionary("test");
		BeanDataValue b = new BeanDataValue();
		b.setClassname("test.bean.TestBean");
		PageValue pa = m.addAction("bean",b);
		
		DataContext data = m.createDataContext();
		Object obj = pa.getObject(data);
		boolean ok = (obj instanceof test.bean.TestBean);
		Assert.assertTrue(ok);
	}

	@Test
	public void callexec() {

		ActionDictionary m = new ActionDictionary("test");

		PageValue va = (PageValue) getActionValue(m,"va", "1");
		PageValue vb = (PageValue)getActionValue(m,"vb", "2");
		

		BeanDataValue b = new BeanDataValue();
		b.setClassname("test.bean.TestBean");
		PageAction pb = m.addAction("bean",b);

		CallDataValue call = new CallDataValue(b, "concat");
		call.addSetterValue("a", "va");
		call.addSetterValue("b", "vb");

		PageAction pc = m.addAction("testcall",call);

		RefDataValue erg = new RefDataValue();
		erg.setSourceName("bean.c");
		erg.setModel(m);

		DataContext data = m.createDataContext();
		try {
			va.setObject(data, "1");
			vb.setObject(data, "2");
			call.perform(data);
			Object obj = erg.getObject(data);
			Assert.assertEquals("12", obj);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void callevents() {
		count = 0;
		ActionDictionary m = new ActionDictionary("test");


		PageValue va = (PageValue) getActionValue(m,"va", "1");
		PageValue vb = (PageValue)getActionValue(m,"vb", "2");
	
		BeanDataValue b = new BeanDataValue();
		b.setClassname("test.bean.TestBean");
		PageAction pb = m.addAction("bean",b);
		pb.setPriority(5);
		
		CallDataValue call = new CallDataValue( b, "concat");
		call.addSetterValue("a", "va");
		call.addSetterValue("b", "vb");

		PageAction pcall = m.addAction("testcall",call);
		va.addActionListener(pcall);
		vb.addActionListener(pcall);
		pcall.setPriority(4);
		pcall.addActionListener(pb);

		RefDataValue ref = new RefDataValue();
		ref.setSourceName("bean.c");
		ref.setModel(m);
		pb.addActionListener(ref);

		PageValue erg = (PageValue)getActionValue(m,"erg", "");
		ref.addActionListener(erg);

		DataContext data = m.createDataContext();
		try {
			pcall.addActionListener(this);
			pcall.setOn(false);
			va.setObject(data, "1");
			vb.setObject(data, "2");
			Assert.assertEquals(0, count);
			pcall.perform(data);
			Assert.assertEquals(0, count);

			pcall.setOn(true);
			va.setObject(data, "2");
			
			m.perform(data);
			
			
			Object obj = ref.getObject(data);
			Assert.assertEquals("22", obj);
			
			obj = erg.getObject(data);
			Assert.assertEquals("22", obj);

			Assert.assertEquals(1, count);

			va.setObject(data, "x");
			m.perform(data);
			obj = erg.getObject(data);
			Assert.assertEquals("x2", obj);
			Assert.assertEquals(2, count);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Override
	public void actionPerformed(Object a, DataContext data)	{
		count++;

	}
}
