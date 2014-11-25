package fr.insaif.jajagaa.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Itinéraire d'un livreur dans une seule zone géographique, partant du dépôt et revenant au dépôt,
 * elle est représentée par une liste ordonnée de chemins
 * @author gustavemonod
 */
public class Tournee {
    /**
     * Zone Géographique (unique) dans laquelle se déroule la tournée. 
     */
    protected ZoneGeographique zone;

    /**
     * Liste des plages horaires au cours de laquelle se déroule la tournée. (Peut au maximum contenir 24h)
     */
    protected List <PlageHoraire> plagesHoraire;

    /**
     * Liste ordonnée des chemins parcourus au cours de la tournée.
     * Le premier chemin doit partir de l'entrepôt de la zone géographique.
     * Le dernier chemin doit arriver à l'entrepôt.
     * La fin d'un chemin doit être le début du chemin de l'autre.
     */
    protected List<Chemin> chemins = new ArrayList <Chemin>();

    /**
     * Liste des livraisons à effectuer (non triées, voir {@link fr.insaif.jajagaa.model.Tournee} pour la liste triée)
     */
    protected List<PlageHoraire> plages;

    /**
     * Jour au cours duquel se déroule la tournée (non pas l'heure, seulement la date). 
     */
    protected Date jour;

    /**
     * Liste ordonnée des chemins parcourus au cours de la tournée.
     * @return Liste ordonnée des chemins parcourus au cours de la tournée.
     */
    public List<Chemin> getChemins() {
        return chemins;
    }

    /**
     * Ajoute un chemin à la liste ordonnée des chemins parcourus au cours de la tournée.
     * @param chemin Chemin à ajouter
     */
    public void addChemin(Chemin chemin) {
        chemins.add(chemin);
    }

    /**
     * Modifie la liste ordonnée des chemins parcourus au cours de la tournée.
     * @param chemins Liste ordonnée des chemins parcourus au cours de la tournée.
     */
    public void setChemins(List<Chemin> chemins) {
        this.chemins = chemins;
    }
}
