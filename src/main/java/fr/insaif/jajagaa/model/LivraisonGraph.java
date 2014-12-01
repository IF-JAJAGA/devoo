/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.model;

import fr.insaif.jajagaa.model.tsp.Graph;

import java.util.*;

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
     * Liste des plus courts cheminsResultats
     */
    protected List<Chemin> chemins;
    
    protected List<LivraisonGraphVertex> noeuds;
    
    protected Map<Integer,Integer> idToIndex;

    public LivraisonGraph(List<Chemin> chemins) {
        this.setChemins(chemins);
    }

    protected List<LivraisonGraphVertex> getNoeuds() {
        return this.noeuds;
    }
    
    public int getIndexNoeudById(int id) {
        return this.idToIndex.get(id);
    }
    
    public LivraisonGraphVertex getVertex(int index) {
        return this.noeuds.get(index);
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
        Set<LivraisonGraphVertex> tree = new HashSet<LivraisonGraphVertex>();
        for(Chemin chemin : chemins) {
            tree.add(chemin.getOrigine());
            tree.add(chemin.getDestination());
        }
        this.noeuds = new ArrayList<LivraisonGraphVertex>(tree);

        this.idToIndex = new HashMap<Integer,Integer>();
        int nbNoeuds = this.getNoeuds().size();
        for (int i=0; i<nbNoeuds; i++) {
            LivraisonGraphVertex noeud = this.getNoeuds().get(i);
            this.idToIndex.put(noeud.getId(), i);
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
            for(int j=0; j<size; j++) {
                costs[i][j] = PAS_DE_TRONCON;
            }
            int[] successors = this.getSucc(i);
            int[] succCosts = this.getSuccCost(i);
            for (int j = 0; j < successors.length; ++j) {
                costs[i][successors[j]] = succCosts[j];
            }
        }
        return costs;
    }

    /**
     * Renvoie le tableau des ID des noeuds reliés avec un Chemin sortant
     * @param i a vertex such that <code>0 <= i < this.getNbVertices()</code>
     * @return Tableau des ID des noeuds reliés avec un Chemin sortant
     * @throws ArrayIndexOutOfBoundsException
     */
    public int[] getSucc(int i) throws ArrayIndexOutOfBoundsException {
        List<Chemin> sortants = this.getNoeuds().get(i).getSortants();
        int size = sortants.size();
        int succ[] = new int[size];
        for (int k = 0; k < size; ++k) {
            int id = sortants.get(k).getDestination().getId();
            succ[k] = this.idToIndex.get(id);
        }
        return succ;
    }

    public int getNbSucc(int i) throws ArrayIndexOutOfBoundsException {
        return this.noeuds.get(i).getSortants().size();
    }
    
    protected int[] getSuccCost(int i) {
        List<Chemin> sortants = this.getNoeuds().get(i).getSortants();
        int size = sortants.size();
        int succCost[] = new int[size];
        for (int k = 0; k < size; ++k) {
            succCost[k] = sortants.get(k).getCost();
        }
        return succCost;
    }
    
}
