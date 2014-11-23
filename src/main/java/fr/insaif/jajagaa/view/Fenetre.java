package fr.insaif.jajagaa.view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

/**
 * Classe qui fait l'interface avec le controleur et qui implémente tous les écouteurs.
 * @author alicia
 */
public class Fenetre {
	protected JFrame fenetre;
	protected VuePlan vuePlan = new VuePlan();
	
	protected SpringLayout layout = new SpringLayout();

	
    public Fenetre(){
    	fenetre = new JFrame();
    	fenetre.setVisible(true);
    	fenetre.setSize(new Dimension(600,400));
    	
    	fenetre.add(vuePlan);
    }
    
    
    
    public static void main(String ... args) throws Exception {
    	Fenetre fen = new Fenetre();
    }
}
