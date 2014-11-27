package fr.insaif.jajagaa.control;

import fr.insaif.jajagaa.model.PlageHoraire;
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
}
