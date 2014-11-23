/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.view;

import java.awt.Point;

/**
 *
 * @author alicia
 */
public interface VueElement {
	/*
	 * Calcule si l'élément est sélectionné par le clic au point p.
	 * @return : vrai si il y a un changement de sélection.
	 */
	public boolean changementSelection(Point p);
    
}
