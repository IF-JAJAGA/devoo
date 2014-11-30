package fr.insaif.jajagaa.control;

import fr.insaif.jajagaa.control.Commands.Command;
import fr.insaif.jajagaa.control.Commands.LirePlanCommand;
import fr.insaif.jajagaa.model.PlageHoraire;
import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.Tournee;
import fr.insaif.jajagaa.model.ZoneGeographique;
import fr.insaif.jajagaa.view.Fenetre;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    
    protected ZoneGeographique zone;
    
    protected List<PlageHoraire> plagesHoraire;
    
    private ElementListeCourante commandeCourante = new ElementListeCourante();
    
    private Controleur() {
        Fenetre.getInstance();
    }

    /**
     * Getteur de ZoneGeographique 
     * @return ZoneGeographique associée au controleur en question
     */
    public ZoneGeographique getZone() {
        return zone;
    }

    /**
     * Getteur de la liste de PlageHoraire
     * @return List<PlageHoraire> associée au controleur en question
     */
    public List<PlageHoraire> getPlagesHoraire() {
        return plagesHoraire;
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
     * @param zone
     * @return 
     */
    public List<PlageHoraire> lireLivraisons(String fichierLivraison, ZoneGeographique zone) {
        this.plagesHoraire = Parseur.lireLivraison(fichierLivraison, zone);
        return this.plagesHoraire;
    }
    
    /**
     * Méthode qui permet d'ajouter un point dans une tournee, juste après un autre point (spécifié)
     * @param tourneeModel tournee que l'on veut modifier
     * @param noeudMilieu noeud que l'on veut ajouter à la tournee
     * @param noeudAvant noeud après lequel on veut ajouter noeudMilieu
     * @return la tournee une fois qu'elle a été modifiée
     */
    public Tournee ajouterPointLivraison (Tournee tourneeModel,Noeud noeudMilieu, Noeud noeudAvant) {
        return null;
    }
    
    /**
     * On doit exécuter la commande puis communiquer les changements au modèle et à la vue.
     */
    public void execute(){
        Command commande = commandeCourante.commande;
        commande.execute();
        
        if(commande instanceof LirePlanCommand){
            zone = ((LirePlanCommand)commande).getZone();
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
        
        if(commande instanceof LirePlanCommand){
            zone = ((LirePlanCommand)commande).getZone();
            Fenetre.getInstance().actualiserPlan();
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
