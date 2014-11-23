package fr.insaif.jajagaa.view;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

/**
 * Example class of what could be in the {@link fr.insaif.jajagaa.view} package
 */
public class Fenetre {
	private JFrame fenetre;
	private VuePlan vuePlan = VuePlan.getInstance();

	
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
