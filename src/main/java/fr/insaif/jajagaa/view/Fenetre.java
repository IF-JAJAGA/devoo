package fr.insaif.jajagaa.view;


import fr.insaif.jajagaa.control.Controleur;
import fr.insaif.jajagaa.view.panelDroite.ConteneurDroite;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Classe qui fait l'interface avec le controleur et qui implémente tous les écouteurs.
 * @author alicia
 */
public class Fenetre extends JFrame {
        private static Fenetre fenetre = null;
        public static Fenetre getInstance(){
            if(fenetre==null){
                fenetre=new Fenetre();
            }
            return fenetre;
        }
	
        private static Controleur controleur = new Controleur();
        
        private final VuePlan vuePlan;
	private final ConteneurDroite conteneurDroite;
	private final JSplitPane split;
        
        //Barre de menu
        private final JMenuBar barreMenu;
        private final JMenu fichier, apparence;
        JMenuItem importPlan, importLivr, quit;
        
        //On crée un nouveau sélecteur de fichier
        final JFileChooser fc = new JFileChooser();
        
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
    
        //Initialisation de la barre de menu.
        barreMenu = new JMenuBar();
        
        //Initialisation des différents menus. 
        fichier = new JMenu("Fichier");
        apparence = new JMenu("Apparence");
        
        fichier.setMnemonic(KeyEvent.VK_A);
        fichier.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
        barreMenu.add(fichier);
        
        importPlan = new JMenuItem("Importer un fichier de plan", KeyEvent.VK_T);
        importPlan.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        importPlan.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        fichier.add(importPlan);
        
        importLivr = new JMenuItem("Importer un fichier de livraisons", KeyEvent.VK_T);
        importLivr.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        importLivr.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        fichier.add(importLivr);
        
        
        
        barreMenu.add(apparence);
        
        this.setJMenuBar(barreMenu);
        addListeners();

        pack();
    }
    
    private void addListeners(){
        addComponentListener(new ComponentListener() {

                public void componentShown(ComponentEvent arg0) {}

                public void componentResized(ComponentEvent arg0) {
                        //Maj de l'endroit de la séparation
                    //split.setDividerLocation(getWidth()-200);
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
      
        
        importPlan.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int returnVal = fc.showDialog(fenetre, "Ouvrir le fichier du plan");
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    FileInputStream fipPlan = null;
                    try {
                        fipPlan = new FileInputStream(fc.getSelectedFile());
                        returnVal = fc.showDialog(fenetre, "Ouvrir le fichier de livraison");
                        if(returnVal==JFileChooser.APPROVE_OPTION)
                        {
                            //Si les deux fichiers ont été sélectionnés :
                            FileInputStream fipLivr = new FileInputStream(fc.getSelectedFile());
                            controleur.creerTournee(fipPlan, fipLivr);
                            
                        }
                        else{
                            System.out.println("Opération annulée, pour la livraison.");
                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            fipPlan.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                else{
                    System.out.println("Opération annulée, pour le plan.");
                }
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
