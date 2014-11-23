package fr.insaif.jajagaa.view;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

/**
 * Classe qui fait l'interface avec le controleur et qui implémente tous les écouteurs.
 * @author alicia
 */
public class Fenetre {
	private JFrame fenetre;
	private VuePlan vuePlan = new VuePlan();

	
    public Fenetre(){
    	fenetre = new JFrame();
    	fenetre.setSize(new Dimension(600,400));
    	
    	
    	fenetre.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			// TODO Auto-generated method stub
    			super.mouseClicked(e);
    			vuePlan.quiEstClique(e.getLocationOnScreen());
    			System.out.println("Souris cliquée à l'endroit : " + e.getLocationOnScreen());
    		}
		});
    	fenetre.getContentPane().add(vuePlan);
    	fenetre.setVisible(true);
    }
    
    
    
    public static void main(String ... args) throws Exception {
    	Fenetre fen = new Fenetre();
    }
}
