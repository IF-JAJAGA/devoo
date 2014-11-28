package fr.insaif.jajagaa.control;

import fr.insaif.jajagaa.model.PlageHoraire;
import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.Tournee;
import fr.insaif.jajagaa.model.ZoneGeographique;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Controleur qui permet de faire l'interface avec le modèle
 * @author gustavemonod
 */
public class Controleur {
    private static Controleur controleur;
    public static Controleur getInstance(){
        //TODO : mieu 
        if(controleur==null)    controleur = new Controleur();
        return controleur;
    }
    
    protected ZoneGeographique zone;
    protected List<PlageHoraire> plagesHoraire;

    public ZoneGeographique getZone() {
        return zone;
    }

    public List<PlageHoraire> getPlagesHoraire() {
        return plagesHoraire;
    }
    
    
    public ZoneGeographique lirePlan(String fichierPlan) throws FileNotFoundException {
        this.zone = Parseur.lirePlan(new FileInputStream(fichierPlan));
        return this.zone;
    }
    
    public List<PlageHoraire> lireLivraisons(String fichierLivraison, ZoneGeographique zone) throws FileNotFoundException {
        this.plagesHoraire = Parseur.lireLivraison(new FileInputStream(fichierLivraison), zone);
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
}
