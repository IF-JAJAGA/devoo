package fr.insaif.jajagaa.model;

/**
 * Zone regroupant un certain nombre de points de livraison.
 * @author gustavemonod
 */
public class ZoneGeographique {
/* TODO Définir comment ces attributs doivent être mis en place
    protected List<Noeud> noeuds;
    protected List<Tournee> tournees;
*/

    /**
     * Entrepôt de la zone
     */
    protected Noeud entrepot;

    /**
     * Constructeur de la zone géographique à partir de l'entrepôt
     */
    public ZoneGeographique(Noeud entrepot) {
        this.setEntrepot(entrepot);
    }

    /**
     * @return Entrepôt de la zone
     */
    public Noeud getEntrepot() {
        return entrepot;
    }

    /**
     * Modifie l'entrepôt de la zone
     * @param entrepot Le nouvel entrepôt de la zone
     */
    public void setEntrepot(Noeud entrepot) {
        this.entrepot = entrepot;
    }
}
