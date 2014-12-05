package fr.insaif.jajagaa.view;

import fr.insaif.jajagaa.model.Chemin;
import fr.insaif.jajagaa.model.Tournee;
import fr.insaif.jajagaa.model.Troncon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui implémente la vue d'une tournée, elle-même constituée de plusieurs cheminsResultats.
 *
 * @author H4201
 */
public class VueTournee {

    /**
     * Tournée du modèle que VueTournee implémente.
     */
    protected Tournee tourneeModel;
    /**
     * Couleur avec laquelle sera colorée la tournée sur la carte.
     */
    protected Color couleur;
    /**
     * Liste des tronçons qui composent la tournée
     */
    protected List<VueTroncon> vTroncons = new ArrayList<VueTroncon>();
    /**
     * Constructeur de la classe tournée
     * @param uneTournee la tournée dans le modèle.
     * @param uneCouleur
     */
    public VueTournee(Tournee uneTournee, Color uneCouleur) {
        setTourneeModel(uneTournee);
        if(uneTournee.getCheminsResultats() !=null){
            for (Chemin ch : uneTournee.getCheminsResultats()) {
                for(Troncon tr : ch.getTroncons()) {
                    vTroncons.add(new VueTroncon(tr));
                }
            }
        }
        couleur = uneCouleur;
    }

    @Override
    public String toString() {
        return "Tournée : " + getTourneeModel().toString() + "; Couleur : " + couleur.toString();
    }

    /**
     * Tournée du modèle que VueTournee implémente.
     * @return Tournée du modèle que VueTournee implémente.
     */
    public Tournee getTourneeModel() {
        return tourneeModel;
    }

    /**
     * Modifie la tournée du modèle que VueTournee implémente.
     * @param tourneeModel La tournée du modèle que VueTournee implémente.
     */
    public void setTourneeModel(Tournee tourneeModel) {
        this.tourneeModel = tourneeModel;
    }
}
