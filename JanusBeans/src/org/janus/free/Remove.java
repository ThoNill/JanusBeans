package org.janus.free;

import java.awt.Container;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * Class declaration
 * 
 * Hilfsfunktionen zur Freigabe von Objekten. Java besitzt zwar eine
 * Speicherverwaltung, aber wenn zuvor Referenzen aufgelöst werden, 
 * schadet dies nicht.
 * 
 * @author Thomas Nill
 * @version %I%, %G%
 */
public class Remove {

	/**
	 * Method declaration
	 * 
	 * 
	 * @param obj
	 * 
	 * @return
	 * 
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public static Object remove(Object obj) {
		if (obj == null) {
			return null;
		}

		if (obj instanceof Removable) {
			((Removable) obj).remove();

			return null;
		}

		if (obj instanceof Container) {
			return removeContainer((Container) obj);
		}

		if (obj.getClass().isArray()) {
			return removeArray((Object[]) obj);
		}

		if (obj instanceof List) {
			return removeList((List) obj);
		}

		if (obj instanceof HashMap) {
			return removeHashMap((HashMap) obj);
		}

		return null;
	}

	/**
	 * Method declaration
	 * 
	 * 
	 * @param obj
	 * 
	 * @return
	 * 
	 * @see
	 */
	public static Object[] removeArray(Object obj[]) {
		if (obj == null) {
			return null;
		}

		int anz = obj.length;

		for (int i = 0; i < anz; i++) {
			obj[i] = remove(obj[i]);
		}

		return null;
	}

	/**
	 * Method declaration
	 * 
	 * 
	 * @param obj
	 * 
	 * @return
	 * 
	 * @see
	 */
	public static List removeList(List<?> obj) {
		if (obj == null) {
			return null;
		}

		for(Object o : obj) {
			remove(o);
		}

		obj.clear();

		return null;
	}

	/**
	 * Method declaration
	 * 
	 * 
	 * @param obj
	 * 
	 * @return
	 * 
	 * @see
	 */
	public static HashMap removeHashMap(HashMap obj) {
		if (obj == null) {
			return null;
		}

		for(Object o : obj.values()) {
			remove(o);
		}

		obj.clear();

		return null;
	}

	/**
	 * Method declaration
	 * 
	 * 
	 * @param obj
	 * 
	 * @return
	 * 
	 * @see
	 */
	public static Container removeContainer(Container obj) {
		if (obj == null) {
			return null;
		}

		java.awt.Component[] comp = obj.getComponents();

		removeArray(comp);
		obj.removeAll();

		return null;
	}

}
