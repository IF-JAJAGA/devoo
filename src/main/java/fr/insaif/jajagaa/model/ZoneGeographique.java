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
     * Noeud de la liste qui est l'entrepôt
     */
    protected Noeud entrepot;

    /**
     * Coût max des arcs
     */
    protected int maxArcCost = 0;

    /**
     * Coût min des arcs
     */
    protected int minArcCost = Integer.MAX_VALUE;

    /**
     * Constructeur de la zone géographique à partir de la liste des noeuds (non vide)
     * @param noeuds La liste des noeuds qui sont dans la zone (contenant l'entrepôt, en première place par défaut)
     */
    public ZoneGeographique(List<Noeud> noeuds) {
        this.setNoeuds(noeuds);
        this.setEntrepot(0);
    }

    /**
     * Coût max des arcs
     * @return Coût max des arcs
     */
    @Override
    public int getMaxArcCost() {
        return this.maxArcCost;
    }

    /**
     * Coût min des arcs
     * @return Coût min des arcs
     */
    @Override
    public int getMinArcCost() {
        return this.minArcCost;
    }

    /**
     * Nombre de sommets du graphe
     * @return Nombre de sommets du graphe
     */
    @Override
    public int getNbVertices() {
        return this.getNoeuds().size();
    }

    @Override
    public int[][] getCost() {
        final int PAS_DE_TRONCON = this.getMaxArcCost() + 1;
        int size = this.getNbVertices();
        int[][] costs = new int[size][size];
        for (int i = 0; i < size; ++i) {
            int successors[] = this.getSucc(i);
            int k = 0;
            Noeud noeud = this.getNoeuds().get(i);
            for (int j = 0; j < size; ++j) {
                if (k < successors.length && j == successors[k]) {
                    costs[i][j] = noeud.getSortants().get(k).getCost();
                    ++k;
                } else {
                    costs[i][j] = PAS_DE_TRONCON;
                }
            }
        }
        return costs;
    }

    /**
     * Renvoie le tableau des ID des noeuds reliés avec un Troncon sortant
     * @param i a vertex such that <code>0 <= i < this.getNbVertices()</code>
     * @return Tableau des ID des noeuds reliés avec un Troncon sortant
     * @throws ArrayIndexOutOfBoundsException
     */
    @Override
    public int[] getSucc(int i) throws ArrayIndexOutOfBoundsException {
        List<Troncon> sortants = this.getNoeuds().get(i).getSortants();
        int size = sortants.size();
        int succ[] = new int[size];
        for (int k = 0; k < size; ++k) {
            succ[k] = sortants.get(k).getDestination().getId();
        }
        return succ;
    }

    @Override
    public int getNbSucc(int i) throws ArrayIndexOutOfBoundsException {
        return this.noeuds.get(i).getSortants().size();
    }

    /**
     * Met à jour les champs calculés
     */
    public void update() {
        for (Noeud noeud : this.getNoeuds()) {
            for (Troncon troncon : noeud.getSortants()) {
                int cost = troncon.getCost();
                this.maxArcCost = cost > this.maxArcCost ? cost : this.maxArcCost;
                this.minArcCost = cost < this.minArcCost ? cost : this.minArcCost;
            }
        }
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
        this.update();
    }
}
