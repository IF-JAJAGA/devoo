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
    
    
    public enum ACTION{
        CHARGER_PLAN,
        CHARGER_TOURNEE,
        AJOUTER_LIVRAISON
    }

    protected Parseur parseur;
    
    public void creerTournee(String fichierPlan, String fichierLivraison) throws FileNotFoundException {
        ZoneGeographique zone = Parseur.lirePlan(new FileInputStream(fichierPlan));
        List<PlageHoraire> livraisons = Parseur.lireLivraison(new FileInputStream(fichierLivraison), zone);
    }
    
    public void Controle(ACTION action, Object ...obj){
        switch(action) {
            case CHARGER_PLAN :
                break;
            case CHARGER_TOURNEE :
                break;
            case AJOUTER_LIVRAISON :
                System.out.println("[Dans le controleur]    Ajout d'une livraison");
                break;
        }
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
    
      public List<Noeud> LirePlan(String fichierPlan){
          return null;
      }
    
}
