package org.janus.free;

import java.awt.Container;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

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

		if (obj instanceof Vector) {
			return removeVector((Vector) obj);
		}

		if (obj instanceof Hashtable) {
			return removeHashtable((Hashtable) obj);
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
	public static Vector removeVector(Vector obj) {
		if (obj == null) {
			return null;
		}
		;

		Enumeration e = obj.elements();

		while (e.hasMoreElements()) {
			remove(e.nextElement());
		}

		obj.removeAllElements();

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
	public static Hashtable removeHashtable(Hashtable obj) {
		if (obj == null) {
			return null;
		}

		Enumeration e = obj.elements();

		while (e.hasMoreElements()) {
			remove(e.nextElement());
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
