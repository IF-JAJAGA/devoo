package fr.insaif.jajagaa.model;


import java.util.List;

/**
 * Zone regroupant un certain nombre de points de livraison, de noeuds et de tronçons reliants ces noeuds.
 * @author gustavemonod
 */
public class ZoneGeographique {

    /**
     * Noeud de la liste qui est l'entrepôt
     */
    protected Noeud entrepot;
    /**
     * Liste des noeuds qui composent la zone géographique.
     */
    protected List<Noeud> noeuds;

    /**
     * Constructeur de la zone géographique à partir de la liste des noeuds (non vide)
     * @param noeuds La liste des noeuds qui sont dans la zone (contenant l'entrepôt, en première place par défaut)
     */
    public ZoneGeographique(List<Noeud> noeuds) {
        this.setNoeuds(noeuds);
        this.setEntrepot(0);
    }

    /**
     * Entrepôt de la zone
     * @return Entrepôt de la zone
     */
    public Noeud getEntrepot() {
        return this.entrepot;
    }

    /**
     * Modifie la valeur de l'entrepôt (en choisissant un indice la liste)
     * @param indice Indice où se trouve l'entrepôt dans la liste
     */
    public void setEntrepot(int indice) {
        this.setEntrepot(this.getNoeuds().get(indice));
    }

    /**
     * Modifie l'entrepôt de la zone
     * @param entrepot Le nouvel entrepôt de la zone (qui doit déjà être présent dans la liste des noeuds)
     */
    public void setEntrepot(Noeud entrepot) {
        assert this.getNoeuds().contains(entrepot);
        this.entrepot = entrepot;
    }

    /**
     * Liste des noeuds qui forme une zone géographique (carte)
     * @return Liste des noeuds qui forme une zone géographique (carte)
     */
    public List<Noeud> getNoeuds() {
        return noeuds;
    }

    /**
     * Modifie la liste des noeuds qui forme une zone géographique (carte)
     * @param noeuds Liste des noeuds (non vide) qui forme une zone géographique (carte)
     */
    public void setNoeuds(List<Noeud> noeuds) {
        assert !noeuds.isEmpty();
        this.noeuds = noeuds;
    }
}
