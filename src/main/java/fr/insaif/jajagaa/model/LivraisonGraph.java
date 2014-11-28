/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.model;

import fr.insaif.jajagaa.model.tsp.Graph;
import java.util.List;

/**
 *
 * @author aurelien
 */
public class LivraisonGraph implements Graph {

     /**
     * Coût max des arcs
     */
    protected int maxArcCost = 0;

    /**
     * Coût min des arcs
     */
    protected int minArcCost = Integer.MAX_VALUE;
    
    /**
     * Liste des plus courts chemins
     */
    protected List<Chemin> chemins;
    
    protected List<LivraisonGraphVertex> noeuds;

    public LivraisonGraph(List<Chemin> chemins) {
        this.chemins = chemins;
    }

    public List<LivraisonGraphVertex> getNoeuds() {
        return this.noeuds;
    }

    public void setChemins(List<Chemin> chemins) {
        assert !chemins.isEmpty();
        this.chemins = chemins;
        this.update();
    }
    
    /**
     * Met à jour les champs calculés
     */
    public void update() {
        for (LivraisonGraphVertex noeud : this.getNoeuds()) {
            for (Chemin chemin : noeud.getSortants()) {
                int cost = chemin.getCost();
                this.maxArcCost = cost > this.maxArcCost ? cost : this.maxArcCost;
                this.minArcCost = cost < this.minArcCost ? cost : this.minArcCost;
            }
        }
    }
    
    /**
     * Coût max des arcs
     * @return Coût max des arcs
     */
    public int getMaxArcCost() {
        return this.maxArcCost;
    }

    /**
     * Coût min des arcs
     * @return Coût min des arcs
     */
    public int getMinArcCost() {
        return this.minArcCost;
    }

    /**
     * Nombre de sommets du graphe
     * @return Nombre de sommets du graphe
     */
    public int getNbVertices() {
        return this.getNoeuds().size();
    }

    public int[][] getCost() {
        final int PAS_DE_TRONCON = this.getMaxArcCost() + 1;
        int size = this.getNbVertices();
        int[][] costs = new int[size][size];
        for (int i = 0; i < size; ++i) {
            int successors[] = this.getSucc(i);
            int k = 0;
            LivraisonGraphVertex noeud = this.getNoeuds().get(i);
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
    public int[] getSucc(int i) throws ArrayIndexOutOfBoundsException {
        List<Chemin> sortants = this.getNoeuds().get(i).getSortants();
        int size = sortants.size();
        int succ[] = new int[size];
        for (int k = 0; k < size; ++k) {
            succ[k] = sortants.get(k).getDestination().getId();
        }
        return succ;
    }

    public int getNbSucc(int i) throws ArrayIndexOutOfBoundsException {
        return this.noeuds.get(i).getSortants().size();
    }
    
}
