package fr.insaif.jajagaa.model;

import fr.insaif.jajagaa.model.tsp.Graph;

import java.util.*;

/**
 * LivraisonGraph représente le graph secondaire, utilisé par Choco.
 * Il s'agit de l'implémentation de l'interface Graph.
 * Chaque sommet du graphe est un {@link LivraisonGraphVertex}.
 * Chaque arc est un {@link Chemin}.
 * 
 * @author H4201
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
    
    /**
     * Liste de Livraison composant le graphe
     */
    protected List<LivraisonGraphVertex> noeuds;
    
    /**
     * Map servant à récupérer les IDs
     */
    protected Map<Integer,Integer> idToIndex;

    /**
     * Constructeur de LivraisonGraph
     * @param chemins List<Chemin> composant la Tournee
     */
    public LivraisonGraph(List<Chemin> chemins) {
        this.setChemins(chemins);
    }

    /**
     * Accesseur des Livraisons composant la Tournée 
     * @return  
     */
    protected List<LivraisonGraphVertex> getNoeuds() {
        return this.noeuds;
    }
    
    /**
     * Accesseur de l'indice du noeud dont l'ID est donné
     * @param id
     * @return 
     */
    public int getIndexNoeudById(int id) {
        return this.idToIndex.get(id);
    }
    
    /**
     * Accesseur du LivraisonGrapheVertex
     * @param index
     * @return 
     */
    public LivraisonGraphVertex getVertex(int index) {
        return this.noeuds.get(index);
    }

    /**
     * Mutateur de la liste de Chemins du LivraisonGraphe
     * @param chemins 
     */
    public void setChemins(List<Chemin> chemins) {
        assert !chemins.isEmpty();
        this.chemins = chemins;
        this.update();
    }
    
    /**
     * Met à jour les champs calculés
     */
    public void update() {
        Set<LivraisonGraphVertex> tree = new HashSet<>();
        for(Chemin chemin : chemins) {
            tree.add(chemin.getOrigine());
            tree.add(chemin.getDestination());
        }
        this.noeuds = new ArrayList<>(tree);

        this.idToIndex = new HashMap<>();
        int nbNoeuds = this.getNoeuds().size();
        for (int i = 0; i < nbNoeuds; ++i) {
            LivraisonGraphVertex noeud = this.getNoeuds().get(i);
            this.idToIndex.put(noeud.getIdNoeud(), i);
            for (Chemin chemin : noeud.getSortants()) {
                int cost = chemin.getCost();
                this.maxArcCost = cost > this.maxArcCost ? cost : this.maxArcCost;
                this.minArcCost = cost < this.minArcCost ? cost : this.minArcCost;
            }
        }
    }
    
    /**
     * Coût maximum des arcs
     * @return Coût max des arcs
     */
    @Override
    public int getMaxArcCost() {
        return this.maxArcCost;
    }

    /**
     * Coût minimum des arcs
     * @return Coût min des arcs
     */
    @Override
    public int getMinArcCost() {
        return this.minArcCost;
    }

    /**
     * Accesseur du nombre de sommets du graphe
     * @return Nombre de sommets du graphe
     */
    @Override
    public int getNbVertices() {
        return this.getNoeuds().size();
    }

    /**
     * Accesseur des coûts de tous les Troncons composants le LivraisonGraphe
     * @return 
     */
    @Override
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
    @Override
    public int[] getSucc(int i) throws ArrayIndexOutOfBoundsException {
        List<Chemin> sortants = this.getNoeuds().get(i).getSortants();
        int size = sortants.size();
        int succ[] = new int[size];
        for (int k = 0; k < size; ++k) {
            int id = sortants.get(k).getDestination().getIdNoeud();
            succ[k] = this.idToIndex.get(id);
        }
        return succ;
    }

    /**
     * Récupère le nombre d'arcs sortants du sommet d'indice donné
     * @param i
     * @return
     * @throws ArrayIndexOutOfBoundsException 
     */
    @Override
    public int getNbSucc(int i) throws ArrayIndexOutOfBoundsException {
        return this.noeuds.get(i).getSortants().size();
    }
    
    /**
     * Récupère les coûts de chaque arc sortant d'un sommet dont l'indice est donné
     * @param i
     * @return 
     */
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
