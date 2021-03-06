package fr.insaif.jajagaa.view;


import fr.insaif.jajagaa.model.EtatNoeud;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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

import fr.insaif.jajagaa.control.Controleur;
import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.Tournee;
import fr.insaif.jajagaa.view.panelDroite.ConteneurDroite;

/**
 * Classe qui fait l'interface avec le controleur et qui implémente 
 * tous les écouteurs.
 * Contient un JSplitPane permettant de séparer le plan du conteneur de la liste.
 * @author H4201
 */
public class Fenetre extends JFrame {
    private static Fenetre fenetre = null;
    /**
     * Permet de n'avoir qu'une instance de fenêtre (pattern singleton).
     * @return l'instance de Fenetre.
     */
    public static Fenetre getInstance(){
        if(fenetre==null){
            fenetre=new Fenetre();
        }
        return fenetre;
    }

    /**
     * Conteneur du plan
     */
    private final VuePlan vuePlan;
    /**
     * Conteneur de la liste et des boutons
     */
    private final ConteneurDroite conteneurDroite;
    /**
     * Séparateur entre vuePlan et conteneurDroite
     */
    private final JSplitPane split;

    /**
     * Barre de menu
     */
    private final JMenuBar barreMenu;
    private final JMenu fichier, apparence, actions;
    JMenuItem importPlan, importLivr, imprimer, changeBG, annuler, refaire, quit;

    /**
     * Sélectionneur de fichier
     */
    final JFileChooser fc = new JFileChooser("./src/main/resources");

    /**
     * Pointeur sur le noeud sélectionné par l'interface pour l'ajouter à une tournée.
     */
    private VueNoeud vNAAjouter;
    /**
     * Pointeur sur la livraison précédent le noeud à ajouter dans une tournée.
     */
    private VueNoeud vNLivraisonAvant;
        
    private Fenetre(){
    	setVisible(true);
        setTitle("OnlyLyon Livreur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vuePlan = VuePlan.getInstance();
    	
        conteneurDroite = ConteneurDroite.getInstance();
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
        
        imprimer = new JMenuItem("Imprimer la tournée", KeyEvent.VK_T);
        imprimer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        imprimer.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        fichier.add(imprimer);
        
        quit = new JMenuItem("Quitter", KeyEvent.VK_T);
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        fichier.add(quit);
        
        changeBG = new JMenuItem("Changer la couleur du fond d'écran", KeyEvent.VK_T);
        changeBG.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        changeBG.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        apparence.add(changeBG);
        
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
        setSize(new Dimension(1366, 768));
        importLivr.setEnabled(false);
        imprimer.setEnabled(false);
    }
    
    /**
     * Ajout de tous les listeners de l'interface. La plupart des listeners appelent des fonctions
     * privées de cette classe et appelent selon les conditions des méthodes du contrôleur.
     */
    private void addListeners(){
        //Redimensionnement
        addComponentListener(new ComponentListener() {

                public void componentShown(ComponentEvent arg0) {}

                public void componentResized(ComponentEvent arg0) {
                        //Mise à jour de l'endroit de la séparation.
                    split.setDividerLocation(getWidth()-250);
                }

                public void componentMoved(ComponentEvent arg0) {}

                public void componentHidden(ComponentEvent arg0) {}
        });
        
        importPlan.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(importPlan.isEnabled()){
                    choisirFichierPlan();
                    if(Controleur.getInstance().getZone().getNoeuds() != null && !Controleur.getInstance().getZone().getNoeuds().isEmpty()){
                        importLivr.setEnabled(true);
                    }
                    else{
                        importLivr.setEnabled(false);
                    }
                }
            }
            
        });
        
        importLivr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(importLivr.isEnabled()){
                    choisirFichierLivraisons();
                    if(Controleur.getInstance().getZone().getTournee().getPlagesHoraire() != null 
                            && !Controleur.getInstance().getZone().getTournee().getPlagesHoraire().isEmpty()
                            /*&& Controleur.getInstance().getZone().getTournee() !=null*/){
                        conteneurDroite.getBtnCalculLivraison().setEnabled(true);
                    }
                    else{
                        conteneurDroite.getBtnCalculLivraison().setEnabled(false);
                    }
                }
            }
            
        });
        
        imprimer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(imprimer.isEnabled()){
                    if(Controleur.getInstance().lancerImpression()){
                        JOptionPane.showMessageDialog(null, "L'impression a été effectuée avec succès.", "Impression terminée", JOptionPane.INFORMATION_MESSAGE);

                    }else{
                        JOptionPane.showMessageDialog(null, "Il y a eu un problème lors de l'impression.", "Erreur", JOptionPane.ERROR_MESSAGE);

                    }
                }
            }
        });
        
        annuler.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(annuler.isEnabled()){
                    Controleur.getInstance().undo();
                    
                    //Grisage BtnCalculLivraison
                    if(Controleur.getInstance().getPlagesHoraire() == null || Controleur.getInstance().getPlagesHoraire().isEmpty() 
                            || !Controleur.getInstance().getZone().getTournee().getCheminsResultats().isEmpty()){
                        conteneurDroite.getBtnCalculLivraison().setEnabled(false);
                    }
                    else{
                        conteneurDroite.getBtnCalculLivraison().setEnabled(true);
                    }
                    
                    //Grisage imprimer
                    if(Controleur.getInstance().getZone().getTournee().getCheminsResultats() == null 
                            || Controleur.getInstance().getZone().getTournee().getCheminsResultats().isEmpty()){
                        imprimer.setEnabled(false);
                    }else{
                        imprimer.setEnabled(true);
                    }
                    
                    //Grisage BtnAjouterLivraison
                    conteneurDroite.getBtnAddNoeud().setEnabled(false); //Toujours le désactiver car quand on fait un undo, pas de noeud sélectionné
                    
                    //Grisage BtnSupprimerLivraison
                    conteneurDroite.getBtnSupNoeud().setEnabled(false); //Toujours le désactiver car quand on fait un undo, pas de noeud sélectionné
                }
            }
        });
        
        refaire.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if(refaire.isEnabled()){
                    Controleur.getInstance().redo();
                    //Grisage BtnCalculLivraison avec redo
                    if(Controleur.getInstance().getPlagesHoraire() == null || Controleur.getInstance().getPlagesHoraire().isEmpty() 
                            || !Controleur.getInstance().getZone().getTournee().getCheminsResultats().isEmpty()){
                        conteneurDroite.getBtnCalculLivraison().setEnabled(false);
                    }
                    else{
                        conteneurDroite.getBtnCalculLivraison().setEnabled(true);
                    }

                    //Grisage imprimer avec redo
                    if(Controleur.getInstance().getZone().getTournee().getCheminsResultats() == null 
                            || Controleur.getInstance().getZone().getTournee().getCheminsResultats().isEmpty()){
                        imprimer.setEnabled(false);
                    }else{
                        imprimer.setEnabled(true);
                    }

                    //Grisage BtnAjouterLivraison
                    conteneurDroite.getBtnAddNoeud().setEnabled(false); //Toujours le désactiver car quand on fait un redo, pas de noeud sélectionné
                    
                    //Grisage BtnSupprimerLivraison
                    conteneurDroite.getBtnSupNoeud().setEnabled(false); //Toujours le désactiver car quand on fait un redo, pas de noeud sélectionné
                }
            }
        });
        
        quit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                close();
            }
        });
        
        changeBG.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                vuePlan.changeBackGround();
            }
        });
        
        //Clic sur un élément de la liste.
        conteneurDroite.getListeNoeuds().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if(!lse.getValueIsAdjusting()){
                    VueNoeud vNListe = (VueNoeud) conteneurDroite.getListeNoeuds().getModel().getElementAt(conteneurDroite.getListeNoeuds().getSelectedIndex());
                    if(vNListe.getEtatLivraison() == EtatNoeud.LIVRAISON){
                        conteneurDroite.setTextFieldText((Livraison)vNListe.getNoeudModele());
                    }
                    else{
                        conteneurDroite.resetTextFieldText();
                    }
                    vuePlan.changerSelection(vNListe);
                    vuePlan.repaint();
                }
            }
        });
        
        conteneurDroite.getBtnCalculLivraison().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(conteneurDroite.getBtnCalculLivraison().isEnabled()){
                    calculerTournee();
                    //TODO Amaury
                    imprimer.setEnabled(true);
                    conteneurDroite.getBtnCalculLivraison().setEnabled(false);
                    conteneurDroite.getBtnAddNoeud().setEnabled(false);
                }
            }
        });
        
        //Ajout d'un point de livraison.
        conteneurDroite.getBtnAddNoeud().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                if(conteneurDroite.getBtnAddNoeud().isEnabled()){
                    traitementAjoutLivraison();
                }
            }

        });
        
        //Suppression d'un point de livraison
        conteneurDroite.getBtnSupNoeud().addMouseListener(new MouseAdapter(){
            
            @Override
            public void mouseClicked(MouseEvent me){
                if(conteneurDroite.getBtnSupNoeud().isEnabled()){
                    traitementSupprLivraison();
                }
            }
        });
        
        vuePlan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                
                VueNoeud vN = vuePlan.noeudEstClique(e.getPoint());
                
                if(((vN == null || vN.getEtatLivraison() == EtatNoeud.LIVRAISON) && vNAAjouter == null) 
                        || (Controleur.getInstance().getPlagesHoraire() == null || Controleur.getInstance().getPlagesHoraire().isEmpty())
                        || Controleur.getInstance().getZone().getTournee().getCheminsResultats().isEmpty()){
                    conteneurDroite.getBtnAddNoeud().setEnabled(false);
                }
                else{
                    conteneurDroite.getBtnAddNoeud().setEnabled(true);
                }
                conteneurDroite.getListeNoeuds().SelectionnerNoeud(vN);
                if(vN != null && vN.getEtatLivraison() == EtatNoeud.LIVRAISON){
                    Tournee t = vuePlan.vueTournee.tourneeModel;
                    if (!t.getCheminsResultats().isEmpty()) {
                        conteneurDroite.setTextFieldText((Livraison)vN.getNoeudModele());
                        if(vNAAjouter == null){
                            conteneurDroite.getBtnSupNoeud().setEnabled(true);
                        }
                    } else {
                        conteneurDroite.resetTextFieldText();
                        conteneurDroite.getBtnSupNoeud().setEnabled(false);
                    }
                }
                else {
                    conteneurDroite.resetTextFieldText();
                    conteneurDroite.getBtnSupNoeud().setEnabled(false);
                }
                
                repaint();
            }
	});
     }
    
    
    /**
     * Appelée pour choisir un fichier xml de plan à charger
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
     * Appelée pour choisir un fichier xml de livraisons à charger
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
     * Appelle le contrôleur afin de calculer la tournée.
     */
    private void calculerTournee(){
        Controleur.getInstance().CalculerTournee();
        conteneurDroite.setEtatBtnAddNoeud(0);
    }
    
    /**
     * Met à jour le bouton d'ajout de livraison et ajoute le noeud vNAAjouter dans la tournée en demandant au du contrôleur.
     */
    private void traitementAjoutLivraison(){
        if(vNAAjouter != null){
            vNLivraisonAvant = vuePlan.getvNSelectionne();
            if(vNLivraisonAvant.getEtatLivraison() == EtatNoeud.LIVRAISON ||
                   vNLivraisonAvant.getEtatLivraison() == EtatNoeud.RETARD){
                conteneurDroite.getBtnAddNoeud().setEnabled(false);
                conteneurDroite.getBtnCalculLivraison().setEnabled(true);
                
                Controleur.getInstance().ajouterPointLivraison(vNAAjouter.getNoeudModele(), vNLivraisonAvant.getNoeudModele());
                
                conteneurDroite.setEtatBtnAddNoeud(0);
                conteneurDroite.getBtnAddNoeud().setEnabled(true);
                vNAAjouter = null;
                vNLivraisonAvant = null;
            }else{
                vNLivraisonAvant = null;
            }
        }
        else{
            vNAAjouter = vuePlan.getvNSelectionne();
            if(vNAAjouter.getEtatLivraison() == EtatNoeud.RIEN && vuePlan.getLivraisonsPresente()){
                conteneurDroite.setEtatBtnAddNoeud(1);
                conteneurDroite.getBtnCalculLivraison().setEnabled(false);
            }else{
                vNAAjouter = null;
            }
        }
    }
    /**
     * Met à jour le bouton de suppression de livraison et supprime la livraison sélectionnée de la tournée en demandant au du contrôleur.
     */
    private void traitementSupprLivraison() {
        VueNoeud vNASupprimer ;
        if((vNASupprimer=vuePlan.getvNSelectionne())!=null){
            Controleur.getInstance().supprimerPointLivraison(vNASupprimer.getNoeudModele());
        }
    }
    
    /**
     * Actualise l'affichage (le plan et la liste) lorsqu'un nouveau plan est chargé dans l'application.
     */
    public void actualiserPlan(){
        vuePlan.actualiserPlan(Controleur.getInstance().getZone());
        conteneurDroite.majListe(vuePlan.getVueNoeuds());
        conteneurDroite.majLegendeCouleurs(vuePlan.getColorsPL());
    }
    
    /**
     * Ferme la fenêtre.
     */
    private void close(){
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Autorise ou pas l'annulation
     * @param b si vrai, on peut annuler la dernière commande
     */
    public void setUndoable(boolean b) {
        if(annuler.isEnabled() != b)    annuler.setEnabled(b);
    }

    /**
     * Autorise ou pas l'action de refaire
     * @param b si vrai, on peut refaire la commande tout juste annulée
     */
    public void setRedoable(boolean b) {
        if(refaire.isEnabled() != b)    refaire.setEnabled(b);
    }
}
