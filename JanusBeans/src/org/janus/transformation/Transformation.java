package org.janus.transformation;

import java.io.Serializable;

/**
 * Transformation = Aufruf einer Funktion
 * 
 * @author javaman
 *
 */
public interface Transformation {
	Serializable calculate(Serializable values[]);
}
