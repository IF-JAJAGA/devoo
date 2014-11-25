package fr.insaif.jajagaa.control;

import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.ZoneGeographique;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Controleur qui permet de faire l'interface avec le modèle
 * @author gustavemonod
 */
public class Controleur {
    public void creerTournee(String fichierPlan, String fichierLivraison) throws FileNotFoundException {
        ZoneGeographique zone = Parseur.lirePlan(new FileInputStream(fichierPlan));
        List<Livraison> livraisons = Parseur.lireLivraison(new FileInputStream(fichierLivraison), zone);
    }
}
