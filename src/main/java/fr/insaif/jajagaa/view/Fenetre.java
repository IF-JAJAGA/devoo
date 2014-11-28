package fr.insaif.jajagaa.view;

import fr.insaif.jajagaa.control.Controleur;
import fr.insaif.jajagaa.view.panelDroite.ConteneurDroite;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Classe qui fait l'interface avec le controleur et qui implémente tous les écouteurs.
 * @author alicia
 */
public class Fenetre extends JFrame {
        private static Fenetre fenetre = null;
        public static Fenetre getInstance(){
            if(fenetre==null)   fenetre=new Fenetre();
            return fenetre;
        }
	
        private final VuePlan vuePlan;
	private final ConteneurDroite conteneurDroite;
	private final JSplitPane split;
        
        private VueNoeud vNAAjouter;
        private VueNoeud vNAvant;
	
    private Fenetre(){
    	setVisible(true);
    	setSize(new Dimension(600,400));
        setTitle("OnlyLyon Livreur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vuePlan = new VuePlan();
    	getContentPane().add(vuePlan);
    	
        conteneurDroite = new ConteneurDroite();
        conteneurDroite.majListe(vuePlan.getVueNoeuds());
    	
    	split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, vuePlan, conteneurDroite);
        split.setDividerLocation(getWidth()-200);
         
        getContentPane().add(split);
    
        addListeners();

        pack();
    }
    
    private void addListeners(){
        addComponentListener(new ComponentListener() {

                public void componentShown(ComponentEvent arg0) {}

                public void componentResized(ComponentEvent arg0) {
                        //Maj de l'endroit de la séparation
                    split.setDividerLocation(getWidth()-200);
                }

                public void componentMoved(ComponentEvent arg0) {}

                public void componentHidden(ComponentEvent arg0) {}
        });
        
        conteneurDroite.getBtnAddNoeud().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                //TODO
                Controleur.getInstance().ajouterPointLivraison(null, null, null);
            }

        });
        
        conteneurDroite.getListeNoeuds().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if(!lse.getValueIsAdjusting()){
                    VueNoeud vNListe = (VueNoeud) conteneurDroite.getListeNoeuds().getModel().getElementAt(conteneurDroite.getListeNoeuds().getSelectedIndex());
                    vuePlan.repaint();
                }
            }
        });
    }
    
    
    /**
     * Appelée par un listener sur bouton
     */
    private void ChoisirZoneGeographique(){
        //TODO : appel de JFileChooser
    }
    
    /**
     * Appelée par un listener sur bouton
     */
    private void ChoisirTournee(){
        //TODO : appel de JFileChooser
    }
    
    
    public static void main(String ... args) throws Exception {
    	Fenetre fen = Fenetre.getInstance();
    }
}
