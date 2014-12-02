package fr.insaif.jajagaa.control;

import fr.insaif.jajagaa.control.Commands.AjoutLivraisonCommande;
import fr.insaif.jajagaa.control.Commands.Command;
import fr.insaif.jajagaa.control.Commands.LireLivraisonsCommand;
import fr.insaif.jajagaa.control.Commands.LirePlanCommand;
import fr.insaif.jajagaa.model.PlageHoraire;
import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.Tournee;
import fr.insaif.jajagaa.model.ZoneGeographique;
import fr.insaif.jajagaa.view.Fenetre;

import java.io.FileInputStream;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Controleur qui permet de faire l'interface avec le modèle
 * @author gustavemonod
 */
public class Controleur {
    private static Controleur controleur;
    
    /**
     * Singleton pour la création d'un Controleur
     * @return instance du controleur si existant sinon en crée un nouveau
     */
    public static Controleur getInstance(){
        if(controleur==null){  
            controleur = new Controleur();
        }
        return controleur;
    }
    
    private Controleur() {
        Fenetre.getInstance();
    }
    
    /**
     * Équivalent à un pointeur sur le modèle.
     */
    protected ZoneGeographique zone;
    
    
    /**
     * Permet de pointer sur la commande qui est concernée par l'interface.
     * Ce pointeur change lorsqu'on parcours la liste chainée (undo/redo).
     */
    private ElementListeCourante commandeCourante = new ElementListeCourante();
    
    

    /**
     * Getteur de ZoneGeographique 
     * @return ZoneGeographique associée au controleur en question
     */
    public ZoneGeographique getZone() {
        return zone;
    }
    
    /**
     * Méthode permettant de créer une tournée à partir du plan et de la liste de livraison passés en paramètre
     * @param fichierPlan
     * @param fichierLivraison 
     */
    public void creerTournee(FileInputStream fichierPlan, FileInputStream fichierLivraison) {
//        ZoneGeographique zone = Parseur.lirePlan(fichierPlan);
//        List<PlageHoraire> livraisons = Parseur.lireLivraison(fichierLivraison, zone);
    }
    /**
     * Méthode permettant de générer la zone géographique correspondant à la liste de noeuds listés dans le fichier xml passé en paramètre
     * @param fichierPlan
     * @return 
     */
    public void lirePlan(String fichierPlan) {
        creationCommande(new ElementListeCourante(new LirePlanCommand(zone, fichierPlan)));
        
        execute();
        
    }
    
    /**
     * Méthode permettant de générer la liste des plages horaires contenant les ensembles de livraisons à réaliser
     * @param fichierLivraison
     * @return 
     */
    public void lireLivraisons(String fichierLivraison) {
        if(zone == null){
            JOptionPane.showMessageDialog(null, "Impossible d'importer des livraisons sans aucun plan de la ville.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        creationCommande(new ElementListeCourante(new LireLivraisonsCommand(zone, fichierLivraison)));
        
        execute();
    }
    
    /**
     * Permet de créer la commande pour calculer la tournée.
     * @param time 
     */
    public void CalculerTournee(String time) {
        
    }
    
    /**
     * Méthode qui permet d'ajouter un point dans une tournee, juste après un autre point (spécifié)
     * @param tourneeModel tournee que l'on veut modifier
     * @param noeudMilieu noeud que l'on veut ajouter à la tournee
     * @param noeudAvant noeud après lequel on veut ajouter noeudMilieu
     * @return la tournee une fois qu'elle a été modifiée
     */
    public Tournee ajouterPointLivraison (Noeud noeudMilieu, Noeud noeudAvant) {
        zone.getTournee().ajouterPointDeLivraison(noeudMilieu, noeudAvant);
        return null;
    }
    
    /**
     * On doit exécuter la commande puis communiquer les changements au modèle et à la vue.
     */
    public void execute(){
        Command commande = commandeCourante.commande;
        commande.execute();
        
        //Actualisation du modèle puis de la vue.
        if(commande instanceof LirePlanCommand ){
            zone = ((LirePlanCommand)commande).getZone();
            Fenetre.getInstance().actualiserPlan();
        }
        else if(commande instanceof LireLivraisonsCommand){
            zone = ((LireLivraisonsCommand)commande).getZone();
            Fenetre.getInstance().actualiserPlan();
        }
        else if (commande instanceof  AjoutLivraisonCommande){
//            zone = ((AjoutLivraisonCommande)commande).getZone();
            Fenetre.getInstance().actualiserPlan();
        }
        else {
            //Autres types de commande
        }
    }
    
    /**
     * On doit annuler la commande puis communiquer les changements au modèle et à la vue.
     */
    public void undo(){
        Command commande = commandeCourante.commande;
        commande.undo();
        
        //Actualisation du modèle puis de la vue.
        if(commande instanceof LirePlanCommand){
            zone = ((LirePlanCommand)commande).getZone();
            Fenetre.getInstance().actualiserPlan();
        }
        else if(commande instanceof LireLivraisonsCommand){
            zone = ((LireLivraisonsCommand)commande).getZone();
            Fenetre.getInstance().actualiserPlan();
        }
        else if(commande instanceof AjoutLivraisonCommande){
            
        }
        else {
            //Autres types de commande
        }
        
        commandeCourante = commandeCourante.previous;
        
        Fenetre.getInstance().setRedoable(true);
        if(commandeCourante.previous==null){
            Fenetre.getInstance().setUndoable(false);
        }
    }
    
    /**
     * On appelle execute sur la commande pointée sur next dans la liste chainée.
     */
    public void redo(){
        commandeCourante = commandeCourante.next;
        execute();
        
        Fenetre.getInstance().setUndoable(true);
        Fenetre.getInstance().setRedoable(commandeCourante.next != null);
    }
    
    /**
     * Ajout d'un élément à la liste chainée et communication à la fenêtre.
     * Fonction à appeler pour toute création d'une commande.
     * @param nouvelElement l'élément à ajouter à la liste chainée.
     */
    private void creationCommande(ElementListeCourante nouvelElement){
        nouvelElement.previous = commandeCourante;
        commandeCourante.next = nouvelElement;
        commandeCourante = nouvelElement;
        Fenetre.getInstance().setUndoable(true);
        Fenetre.getInstance().setRedoable(false);
    }

    public List<PlageHoraire> getPlagesHoraire() {
        return zone.getTournee().getPlagesHoraire();
    }
    
    /**
     * Classe permettant d'implémenter une liste chainée pour circuler entre les commandes.
     */
    private class ElementListeCourante{
        private Command commande;
        
        private ElementListeCourante next;
        private ElementListeCourante previous;

        private ElementListeCourante(Command commande) {
            this();
            this.commande = commande;
        }
        
        private ElementListeCourante(){
            previous = null;
            next = null;
        }
    }
}
