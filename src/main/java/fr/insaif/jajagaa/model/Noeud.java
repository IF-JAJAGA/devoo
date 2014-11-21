package fr.insaif.jajagaa.model;

import java.util.List;

/**
 * Point défini où il est possible de s'arrêter sur la carte d'une zone géographique,
 * il comporte au moins un tronçon (entrant) le desservant et un tronçon (sortant) desservant un autre point.
 * @author gustavemonod
 */
public class Noeud {
    /**
     * Liste des troncons qui ont ce noeud comme destination
     */
    protected List<Troncon> entrants;

    /**
     * Liste des troncons qui ont ce noeud comme origine
     */
    protected List<Troncon> sortants;

/* TODO Définir comment ces attributs doivent être mis en place
    protected Collection<Livraison> livraisons;
    protected int x;
    protected int y;
*/

    /**
     * L'adresse à laquelle se trouve le noeud
     */
    protected String adresse;

    /**
     * Constructeur du noeud à partir de l'adresse
     * @param adresse Adresse à laquelle se trouve le noeud
     */
    public Noeud(String adresse) {
        this.setAdresse(adresse);
    }

    /**
     * L'adresse à laquelle se trouve le noeud
     * @return L'adresse à laquelle se trouve le noeud
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Modifie l'adresse à laquelle se trouve le noeud
     * @param adresse La nouvelle adresse à laquelle se trouve le noeud
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Liste des troncons qui ont ce noeud comme destination
     */
    public List<Troncon> getEntrants() {
        return entrants;
    }

    /**
     * Modifie la liste des troncons qui ont ce noeud comme destination
     * @param entrants Liste des troncons qui ont ce noeud comme origine
     */
    public void setEntrants(List<Troncon> entrants) {
        this.entrants = entrants;
    }

    /**
     * Liste des troncons qui ont ce noeud comme origine
     * @return liste des troncons qui ont ce noeud comme origine
     */
    public List<Troncon> getSortants() {
        return sortants;
    }

    /**
     * Modifie la liste des troncons qui ont ce noeud comme origine
     * @param sortants Liste des troncons qui ont ce noeud comme origine
     */
    public void setSortants(List<Troncon> sortants) {
        this.sortants = sortants;
    }
}
