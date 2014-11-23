package fr.insaif.jajagaa.view;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * Classe qui fait l'interface avec le controleur et qui implémente tous les écouteurs.
 * @author alicia
 */
public class Fenetre {
	private JFrame fenetre;
	private VuePlan vuePlan = new VuePlan();
	private JPanel listeNoeuds = new JPanel();
	
	private JSplitPane split;
	
    public Fenetre(){
    	fenetre = new JFrame();
    	fenetre.setVisible(true);
    	fenetre.setSize(new Dimension(600,400));
    	
    	fenetre.getContentPane().add(vuePlan);
    	
    	listeNoeuds.add(new JLabel("Liste des noeuds ici"));
    	
    	split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, vuePlan, listeNoeuds);
         
	    fenetre.getContentPane().add(split);
	    
	    fenetre.addComponentListener(new ComponentListener() {
			
			public void componentShown(ComponentEvent arg0) {}
			
			public void componentResized(ComponentEvent arg0) {
				//Maj de l'endroit de la séparation
		    	split.setDividerLocation(fenetre.getWidth()-200);
			}
			
			public void componentMoved(ComponentEvent arg0) {}
			
			public void componentHidden(ComponentEvent arg0) {}
		});
    }
    
    
    public static void main(String ... args) throws Exception {
    	Fenetre fen = new Fenetre();
    }
}
