package fr.insaif.jajagaa.view;


import fr.insaif.jajagaa.control.Controleur;
import fr.insaif.jajagaa.view.panelDroite.ConteneurDroite;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Classe qui fait l'interface avec le controleur et qui implémente 
 * tous les écouteurs.
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
	
        private final VuePlan vuePlan;
	private final ConteneurDroite conteneurDroite;
	private final JSplitPane split;
        
        //Barre de menu
        private final JMenuBar barreMenu;
        private final JMenu fichier, apparence, actions;
        JMenuItem importPlan, importLivr, annuler, refaire, quit;
        
        //On crée un nouveau sélecteur de fichier
        final JFileChooser fc = new JFileChooser();
        
        private VueNoeud vNAAjouter;
        private VueNoeud vNAvant;
        
        private VueNoeud vnASupprimer;
        
        
    private Fenetre(){
    	setVisible(true);
        setTitle("OnlyLyon Livreur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vuePlan = new VuePlan();
    	
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
        actions = new JMenu("Actions");
        
        fichier.setMnemonic(KeyEvent.VK_A);
        fichier.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
        
        
        importPlan = new JMenuItem("Importer un fichier de plan", KeyEvent.VK_T);
        importPlan.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        importPlan.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        fichier.add(importPlan);
        
        importLivr = new JMenuItem("Importer un fichier de livraisons", KeyEvent.VK_T);
        importLivr.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        importLivr.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        fichier.add(importLivr);
        
        quit = new JMenuItem("Quitter", KeyEvent.VK_T);
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        fichier.add(quit);
        
        annuler = new JMenuItem("Annuler", KeyEvent.VK_T);
        annuler.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        annuler.setEnabled(false);
        actions.add(annuler);
        
        refaire = new JMenuItem("Refaire", KeyEvent.VK_T);
        refaire.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        refaire.setEnabled(false);
        actions.add(refaire);
        
        
        barreMenu.add(fichier);
        barreMenu.add(apparence);
        barreMenu.add(actions);
        
        this.setJMenuBar(barreMenu);
        addListeners();
        setSize(new Dimension(1366,768));
    }
    
    private void addListeners(){
        //Redimensionnement
        addComponentListener(new ComponentListener() {

                public void componentShown(ComponentEvent arg0) {}

                public void componentResized(ComponentEvent arg0) {
                        //Maj de l'endroit de la séparation
                    split.setDividerLocation(getWidth()-200);
                }

                public void componentMoved(ComponentEvent arg0) {}

                public void componentHidden(ComponentEvent arg0) {}
        });
        
        importPlan.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                choisirFichierPlan();
            }
            
        });
        
        importLivr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                choisirFichierLivraisons();
            }
            
        });
        
        annuler.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Controleur.getInstance().undo();
            }
        });
        
        refaire.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Controleur.getInstance().redo();
            }
        });
        
        quit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                close();
            }
        });
        
        //Clic sur un élément de la liste.
        conteneurDroite.getListeNoeuds().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if(!lse.getValueIsAdjusting()){
                    VueNoeud vNListe = (VueNoeud) conteneurDroite.getListeNoeuds().getModel().getElementAt(conteneurDroite.getListeNoeuds().getSelectedIndex());
                    vuePlan.changerSelection(vNListe);
                    vuePlan.repaint();
                }
            }
        });
        
        //Ajout d'un point de livraison.
        conteneurDroite.getBtnAddNoeud().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                traitementAjoutLivraison();
            }

        });
        
        //Suppression d'un point de livraison
        conteneurDroite.getBtnSupNoeud().addMouseListener(new MouseAdapter(){
            
            @Override
            public void mouseClicked(MouseEvent me){
                traitementSupprLivraison();
            }
        });
        
        conteneurDroite.getBtnCalculLivraison().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                calculerLivraison(conteneurDroite.getSaisieTemps().getText());
            }

        });
        
        conteneurDroite.getSaisieTemps().addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    calculerLivraison(conteneurDroite.getSaisieTemps().getText());
                }
            }
            
        });
        
        vuePlan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                VueNoeud vN = vuePlan.noeudEstClique(e.getPoint());
                conteneurDroite.getListeNoeuds().SelectionnerNoeud(vN);
                repaint();
            }
	});
     }
    
    
    /**
     * Appelée par un listener sur bouton
     */
    private void choisirFichierPlan(){
        int returnVal = fc.showDialog(fenetre, "Ouvrir le fichier du plan");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String fipPlan = null;
            fipPlan = fc.getSelectedFile().getAbsolutePath();
            Controleur.getInstance().lirePlan(fipPlan);
        }
        else{
            System.out.println("Opération annulée, pour le plan.");
        }
    }
    
    /**
     * Appelée par un listener sur bouton
     */
    private void choisirFichierLivraisons(){
        int returnVal = fc.showDialog(fenetre, "Ouvrir le fichier de la livraison");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String fipLivraison = null;
            fipLivraison = fc.getSelectedFile().getAbsolutePath();
            Controleur.getInstance().lireLivraisons(fipLivraison);
        }
        else{
            System.out.println("Opération annulée, pour la livraison.");
        }
    }
    
    /**
     * 
     * @param time 
     */
    private void calculerLivraison(String time){
        time = time.replace(",", ".");
        try{
            float timeFloat = Float.parseFloat(time);
            Controleur.getInstance().CalculerTournee(((int) (1000*timeFloat)));
        }catch (NumberFormatException | NullPointerException ne){
            JOptionPane.showMessageDialog(null, "Veuillez donner comme temps un nombre décimal ou entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        conteneurDroite.setEtatBtnAddNoeud(0);
    }
    
    /**
     * Met à jour le bouton d'ajout de livraison et appelle le calcul de la tournee si on a saisi les deux noeuds.
     */
    private void traitementAjoutLivraison(){
        if(vNAAjouter != null){
            vNAvant = vuePlan.getvNSelectionne();
            //Doit être une livraison
            if(vNAvant.getPointDeLivraison() == VueNoeud.Etat.LIVRAISON ||
                   vNAvant.getPointDeLivraison() == VueNoeud.Etat.RETARD){
                conteneurDroite.getBtnAddNoeud().setEnabled(false);
                //Appel
                System.out.println("Ajouter point de livraison");
                Controleur.getInstance().ajouterPointLivraison(vNAAjouter.getNoeudModele(), vNAvant.getNoeudModele());
                conteneurDroite.setEtatBtnAddNoeud(0);
                conteneurDroite.getBtnAddNoeud().setEnabled(true);
                vNAAjouter = null;
                vNAvant = null;
            }else{
                vNAvant = null;
            }
        }
        else{
            vNAAjouter = vuePlan.getvNSelectionne();
            //Ne doit pas être une livraison + au moins un livraison doit être présente.
            if(vNAAjouter.getPointDeLivraison() == VueNoeud.Etat.RIEN && vuePlan.getLivraisonsPresente()){
                conteneurDroite.setEtatBtnAddNoeud(1);
            }else{
                vNAAjouter = null;
            }
        }
    }
    /**
     * Met à jour le bouton de suppression de livraison et appelle le calcul de la tournée si on valide la suppression. 
     */
    private void traitementSupprLivraison() {
        //TODO
        Controleur.getInstance().supprimerPointLivraison(vnASupprimer.getNoeudModele());
        
    }
    
    /**
     * Actualise l'affichage (le plan et la liste) lorsqu'un nouveau plan est chargé dans l'application.
     */
    public void actualiserPlan(){
        vuePlan.actualiserPlan(Controleur.getInstance().getZone());
        conteneurDroite.majListe(vuePlan.getVueNoeuds());
    };
    
    /**
     * Ferme la fenêtre.
     */
    private void close(){
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public void setUndoable(boolean b) {
        if(annuler.isEnabled() != b)    annuler.setEnabled(b);
    }

    public void setRedoable(boolean b) {
        if(refaire.isEnabled() != b)    refaire.setEnabled(b);
    }
}
