package fr.insaif.jajagaa.model;

import fr.insaif.jajagaa.model.tsp.SolutionState;
import fr.insaif.jajagaa.model.tsp.TSP;

import java.util.*;

/**
 * Itinéraire d'un livreur dans une seule zone géographique, partant du dépôt et revenant au dépôt,
 * elle est représentée par une liste ordonnée de cheminsResultats
 *
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
    protected List<PlageHoraire> plagesHoraire;

    /**
     * Liste ordonnée des cheminsResultats parcourus au cours de la tournée.
     * Le premier chemin doit partir de l'entrepôt de la zone géographique.
     * Le dernier chemin doit arriver à l'entrepôt.
     * La fin d'un chemin doit être le début du chemin de l'autre.
     */
    protected List<Chemin> cheminsResultats = new LinkedList<Chemin>();

    /**
     * Liste des livraisons à effectuer (non triées, voir {@link fr.insaif.jajagaa.model.Tournee} pour la liste triée)
     */
    protected List<PlageHoraire> plages;

    protected TSP tsp;

    protected LivraisonGraph graph;

    /**
     * Jour au cours duquel se déroule la tournée (non pas l'heure, seulement la date).
     */
    protected Date jour;

    /**
     * Création d'une tournée à partir d'une zone et d'une demande de livraisons (List<PlageHoraire>)
     */
    public Tournee(ZoneGeographique zone) {
        this.zone = zone;
    }

    public void setPlagesHoraire(List<PlageHoraire> plagesHoraire) {
        this.plagesHoraire = plagesHoraire;
        this.graph = new LivraisonGraph(this.getCheminsPossibles());
        this.tsp = new TSP(graph);
    }

    protected List<Chemin> getCheminsPossibles() {
        List<Chemin> cheminsPossibles = new ArrayList<Chemin>();
        LivraisonGraphVertex entrepot = new LivraisonGraphVertex(this.zone.getEntrepot(), true);
        Map<Integer, List<LivraisonGraphVertex>> graphVertexPerPlage = new HashMap<Integer, List<LivraisonGraphVertex>>();
        
        int taille = this.plagesHoraire.size();
        for(int i=0; i<taille; i++) {
            graphVertexPerPlage.put(i, new ArrayList<LivraisonGraphVertex>());
            List<Livraison> livs = this.plagesHoraire.get(i).getLivraisons();
            int nbLivraisons = livs.size();
            for(int j=0; j<nbLivraisons; j++) {
                graphVertexPerPlage.get(i).add(new LivraisonGraphVertex(livs.get(j)));
            }
        }

        // Chemins les plus courts de l'entrepôt vers tous les nœuds de la première plage horaire
        for (LivraisonGraphVertex vertex: graphVertexPerPlage.get(0)) {
            Chemin plusCourt = Dijkstra.plusCourtChemin(this.zone, entrepot, vertex);
            if (plusCourt != null) {
                cheminsPossibles.add(plusCourt);
            }
        }
        
        // Tous les chemins entre les nœuds d'une même plage horaire
        for (int i = 0; i < taille; i++) {
            List<LivraisonGraphVertex> vertices = graphVertexPerPlage.get(i);
            int size = vertices.size();
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    LivraisonGraphVertex vJ = vertices.get(j);
                    LivraisonGraphVertex vK = vertices.get(k);
                    // On ne crée le chemin que pour deux sommets différents
                    if (vJ.getId() != vK.getId()) {
                        Chemin plusCourt = Dijkstra.plusCourtChemin(zone, vJ, vK);
                        if (plusCourt != null) {
                            cheminsPossibles.add(plusCourt);
                        }
                    }
                }
            }
        }

        // Tous les chemins possibles de tous les nœuds de la plage horaire n vers la plage horaire n + 1
        for (int i = 1; i < taille; ++i) {
            List<LivraisonGraphVertex> verticesD = graphVertexPerPlage.get(i-1);
            List<LivraisonGraphVertex> verticesA = graphVertexPerPlage.get(i);
            for (LivraisonGraphVertex depart: verticesD) {
                for (LivraisonGraphVertex arrivee: verticesA) {
                    Chemin plusCourt = Dijkstra.plusCourtChemin(this.zone, depart, arrivee);
                    if (plusCourt != null) {
                        cheminsPossibles.add(plusCourt);
                    }
                }
            }
        }

        // Chemins les plus courts de tous les nœuds de la dernière plage horaire vers l'entrepôt
        for (LivraisonGraphVertex vertex: graphVertexPerPlage.get(taille - 1)) {
            Chemin plusCourt = Dijkstra.plusCourtChemin(this.zone, vertex, entrepot);
            if (plusCourt != null) {
                cheminsPossibles.add(plusCourt);
            }
        }

        return cheminsPossibles;
    }

    public SolutionState solve(int TIME_LIMIT_MS) {
        SolutionState state = this.tsp.solve(TIME_LIMIT_MS, this.graph.getNbVertices() * this.graph.getMaxArcCost() + 1);
        if(state == SolutionState.OPTIMAL_SOLUTION_FOUND || state == SolutionState.SOLUTION_FOUND) {
            this.getCheminsResultats().clear();
            int[] next = this.tsp.getNext();
            int indexEntrepot = this.graph.getIndexNoeudById(this.zone.getEntrepot().getId());
            List<LivraisonGraphVertex> vertices = this.graph.getNoeuds();
            List<LivraisonGraphVertex> orderedVertices = new ArrayList<LivraisonGraphVertex>();
            if(next.length > 0) {
                int i = indexEntrepot;
                orderedVertices.add(vertices.get(i));
                do {
                    i = next[i];
                    orderedVertices.add(vertices.get(i));
                }
                while(i != indexEntrepot);
                this.buildCheminResultat(orderedVertices);
            }
        }
        return state;
    }
    
    protected void buildCheminResultat(List<LivraisonGraphVertex> vertices) {
        int i = 0;
        LivraisonGraphVertex depart, arrivee;
        while(i < (vertices.size()-1)) {
            depart = vertices.get(i);
            arrivee = vertices.get(i+1);
            
            Chemin chemin = depart.getSortantByDest(arrivee);
            if(chemin == null) throw new NullPointerException("Noeud sortant non trouvé");
            this.addCheminResultat(chemin);
            
            i++;
        }
    }
 
    public SolutionState getSolutionState() {
        return this.tsp.getSolutionState();
    }

    public LivraisonGraph getGraph() {
        return this.graph;
    }

    /**
     * Liste ordonnée des cheminsResultats parcourus au cours de la tournée.
     *
     * @return Liste ordonnée des cheminsResultats parcourus au cours de la tournée.
     */
    public List<Chemin> getCheminsResultats() {
        return cheminsResultats;
    }

    /**
     * Ajoute un chemin à la liste ordonnée des cheminsResultats parcourus au cours de la tournée.
     *
     * @param chemin Chemin à ajouter
     */
    public void addCheminResultat(Chemin chemin) {
        this.cheminsResultats.add(chemin);
    }

    /**
     * Modifie la liste ordonnée des cheminsResultats parcourus au cours de la tournée.
     *
     * @param cheminsResultats Liste ordonnée des cheminsResultats parcourus au cours de la tournée.
     */
    public void setCheminsResultats(List<Chemin> cheminsResultats) {
        this.cheminsResultats = cheminsResultats;
    }

    public Chemin calculerPlusCourtChemin(Noeud depart, Noeud arrivee) {
        return null;
    }
    
    /**
     * @param noeudMilieu noeud à ajouter à la tournée
     * @param noeudAvant noeud après lequel on doit ajouter pointMilieu
     * @return la tournée une fois qu'elle a été modifiée.
     */
    public Tournee ajouterPointDeLivraison(Noeud noeudMilieu, Noeud noeudAvant){
        boolean trouveChemin = false;
        int i;
        for (i=0; i<cheminsResultats.size();i++) {
            //TODO : réimplémenter 
            if (cheminsResultats.get(i).getOrigine().equals(noeudAvant)){
                trouveChemin = true;
                break;
            }
        }
        if (trouveChemin){
            //Supprimer le chemin
            cheminsResultats.remove(i);
            //Calculerplus court chemin entre noeud Avant et milieu
            //Chemin cheminAvant = Dijkstra.plusCourtChemin(zone, null, null);
            //Calculer plus court chemin entre noeud Milieu et noeud Après
            //Chemin cheminAprès = Dijkstra.plusCourtChemin(zone, null, null);
            //Ajouter les deux chemins crées à la place de celui supprimé
            cheminsResultats.add(null);
            cheminsResultats.add(null);
        }
        return this;
    }
}
