package fr.insaif.jajagaa.model;

import fr.insaif.jajagaa.model.tsp.Graph;

import java.util.List;

/**
 * Zone regroupant un certain nombre de points de livraison.
 * @author gustavemonod
 */
public class ZoneGeographique implements Graph {
    /**
     * Liste des noeuds qui forme une zone géographique (carte)
     */
    protected List<Noeud> noeuds;

    /**
     * Liste des noeuds qui forme une zone géographique (carte)
     */
    protected Noeud entrepot;

    /**
     * The max cost of an arc, <code>Integer.MAX_VALUE</code> by default
     */
    protected int maxArcCost = Integer.MAX_VALUE;

    /**
     * The min cost of an arc, <code>0</code> by default
     */
    protected int minArcCost = 0;

    /**
     * Constructeur de la zone géographique à partir de l'entrepôt
     * @param noeuds La liste des noeuds qui sont dans la zone (contenant l'entrepôt)
     * @param entrepot Reference vers l'entrepôt (qui est déjà dans la liste)
     */
    public ZoneGeographique(List<Noeud> noeuds, Noeud entrepot) {
        this.setNoeuds(noeuds);
        this.setEntrepot(entrepot);
    }

    /**
     * The max cost of an arc, <code>Integer.MAX_VALUE</code> by default
     * @return The max cost of an arc, <code>Integer.MAX_VALUE</code> by default
     */
    @Override
    public int getMaxArcCost() {
        return this.maxArcCost;
    }

    /**
     * The min cost of an arc, <code>0</code> by default
     * @return The min cost of an arc, <code>0</code> by default
     */
    @Override
    public int getMinArcCost() {
        return this.minArcCost;
    }

    @Override
    public int getNbVertices() {
        return 0;
    }

    @Override
    public int[][] getCost() {
        return new int[0][];
    }

    @Override
    public int[] getSucc(int i) throws ArrayIndexOutOfBoundsException {
        return new int[0];
    }

    @Override
    public int getNbSucc(int i) throws ArrayIndexOutOfBoundsException {
        return 0;
    }

    /**
     * Entrepôt de la zone
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
        for (Noeud noeud : this.getNoeuds()) {
            for (Troncon troncon : noeud.getSortants()) {

            }
        }
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
     * @param noeuds Liste des noeuds qui forme une zone géographique (carte)
     */
    public void setNoeuds(List<Noeud> noeuds) {
        this.noeuds = noeuds;
    }
}
