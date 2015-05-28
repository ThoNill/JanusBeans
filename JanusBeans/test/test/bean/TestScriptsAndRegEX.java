package test.bean;

import org.janus.actions.DefaultValue;
import org.janus.data.DataContext;
import org.janus.datatype.DefaultData;
import org.janus.dict.actions.ActionDictionary;
import org.janus.dict.actions.PageValue;
import org.janus.value.BooleanScriptValue;
import org.janus.value.RegExpValue;
import org.junit.Assert;
import org.junit.Test;

public class TestScriptsAndRegEX {



	@Test
	public void pattern() {
		ActionDictionary m = new ActionDictionary("test");
		
		DefaultData d = new DefaultData("1");
		DefaultValue a = new DefaultValue(d);
		PageValue va = m.addAction("va", a);
		

		RegExpValue re = new RegExpValue();
		re.setPatternString("[1,2]");
		re.setValueName("va");

		DataContext data = m.createDataContext();
		try {
			m.getAction("va").perform(data);
			m.perform(data);
			boolean ok = re.isOk(data);
			Assert.assertTrue(ok);
			va.setObject(data, "3");
			ok = re.isOk(data);
			Assert.assertFalse(ok);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void script() {
		ActionDictionary m = new ActionDictionary("test");
		
		DefaultData d = new DefaultData("1");
		DefaultValue a = new DefaultValue(d);
		PageValue va = m.addAction("va", a);
		
		
		BooleanScriptValue re = new BooleanScriptValue(" ${va} > 0 ");
	
		DataContext data = m.createDataContext();
		try {
			m.getAction("va").perform(data);
			m.perform(data);
			boolean ok = re.isOk(data);
			Assert.assertTrue(ok);
			va.setObject(data, "0");
			ok = re.isOk(data);
			Assert.assertFalse(ok);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
