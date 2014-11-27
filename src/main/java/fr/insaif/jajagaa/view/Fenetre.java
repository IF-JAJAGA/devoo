package fr.insaif.jajagaa.view;

import fr.insaif.jajagaa.control.Controleur;
import fr.insaif.jajagaa.view.panelDroite.ConteneurDroite;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * Classe qui fait l'interface avec le controleur et qui implémente tous les écouteurs.
 * @author alicia
 */
public class Fenetre extends JFrame{
        private static Fenetre fenetre = null;
        public static Fenetre getInstance(){
            if(fenetre==null)   fenetre=new Fenetre();
            return fenetre;
        }
	protected VuePlan vuePlan;
	protected Controleur controleur;
        
	private ConteneurDroite conteneurDroite;
	
	private JSplitPane split;
	
    private Fenetre(){
    	setVisible(true);
    	setSize(new Dimension(600,400));
        vuePlan = new VuePlan();
    	getContentPane().add(vuePlan);
    	
        conteneurDroite = new ConteneurDroite();
    	
    	split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, vuePlan, conteneurDroite);
        split.setDividerLocation(getWidth()-200);
         
        getContentPane().add(split);

        addComponentListener(new ComponentListener() {

                    public void componentShown(ComponentEvent arg0) {}

                    public void componentResized(ComponentEvent arg0) {
                            //Maj de l'endroit de la séparation
                        split.setDividerLocation(getWidth()-200);
                    }

                    public void componentMoved(ComponentEvent arg0) {}

                    public void componentHidden(ComponentEvent arg0) {}
            });
        pack();
    }
    
    
    public static void main(String ... args) throws Exception {
    	Fenetre fen = Fenetre.getInstance();
    }
}
