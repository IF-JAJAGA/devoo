package fr.insaif.jajagaa.model;

import fr.insaif.jajagaa.model.tsp.SolutionState;
import fr.insaif.jajagaa.model.tsp.TSP;

import java.awt.Point;
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

        // Chemins les plus courts de l'entrepôt vers tous les nœuds de la première plage horaire
        for (Livraison livraison: this.plagesHoraire.get(0).getLivraisons()) {
            Chemin plusCourt = Dijkstra.plusCourtChemin(this.zone, entrepot, new LivraisonGraphVertex(livraison));
            if (plusCourt != null) {
                cheminsPossibles.add(plusCourt);
            }
        }

        // Tous les chemins possibles de tous les nœuds de la plage horaire n vers la plage horaire n + 1
        int taille = this.plagesHoraire.size();
        for (int i = 1; i < taille; ++i) {
            for (Livraison depart: this.plagesHoraire.get(i - 1).getLivraisons()) {
                for (Livraison arrivee: this.plagesHoraire.get(i).getLivraisons()) {
                    Chemin plusCourt = Dijkstra.plusCourtChemin(this.zone, new LivraisonGraphVertex(depart), new LivraisonGraphVertex(arrivee));
                    if (plusCourt != null) {
                        cheminsPossibles.add(plusCourt);
                    }
                }
            }
        }

        // Chemins les plus courts de tous les nœuds de la dernière plage horaire vers l'entrepôt
        for (Livraison livraison: this.plagesHoraire.get(this.plagesHoraire.size() - 1).getLivraisons()) {
            Chemin plusCourt = Dijkstra.plusCourtChemin(this.zone, entrepot, new LivraisonGraphVertex(livraison));
            if (plusCourt != null) {
                cheminsPossibles.add(plusCourt);
            }
        }

        return cheminsPossibles;
    }

    public SolutionState solve(int TIME_LIMIT_MS) {
        return this.tsp.solve(TIME_LIMIT_MS, this.graph.getNbVertices() * this.graph.getNoeuds().size());
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
     * @param pointMilieu point à ajouter à la tournée
     * @param pointAvant point après lequel on doit ajouter pointMilieu
     * @return la tournée une fois qu'elle a été modifiée.
     */
    public Tournee ajouterPointDeLivraison(Point pointMilieu, Point pointAvant){
        return this;
    }
}
