package fr.insaif.jajagaa.view;

import fr.insaif.jajagaa.model.Chemin;
import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.Tournee;
import fr.insaif.jajagaa.model.Troncon;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe qui implémente la vue d'une tournée, elle même constituée de plusieurs cheminsResultats.
 *
 * @author alicia
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
     *
     * @param uneTournee
     * @param uneCouleur
     */
    public VueTournee(Tournee uneTournee, Color uneCouleur) {
        setTourneeModel(uneTournee);
        System.out.println("uneTournee.getCheminsResultats() : " + uneTournee.getCheminsResultats().size());
        if(uneTournee.getCheminsResultats() !=null){
            for (Chemin ch : uneTournee.getCheminsResultats()) {
                for(Troncon tr : ch.getTroncons()) {
                    vTroncons.add(new VueTroncon(tr));
                }
            }
        }
        couleur = uneCouleur;
    }

    public boolean changementSelection(Point p) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String toString() {
        return "Tournée : " + getTourneeModel().toString() + "; Couleur : " + couleur.toString();
    }

    /**
     * Ajoute un point de livraison à la tournée en recalculant localement
     * @param aAjouter Le noeud qui doit devenir un point de livraison
     * @param pointLivraisonAvant Point de livraison après lequel insérer aAjouter
     * @return La tournée modifiée
     */
    public Tournee ajouterPointLivraison(Noeud aAjouter, Noeud pointLivraisonAvant) {
        // TODO
        throw new UnsupportedOperationException("A IMPLEMENTER");
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
