package fr.insaif.jajagaa.model;

import fr.insaif.jajagaa.model.tsp.Graph;
import fr.insaif.jajagaa.model.tsp.SolutionState;
import fr.insaif.jajagaa.model.tsp.TSP;

import java.util.*;

/**
 * Classe permettant de trouver le meilleur chemin localement, ou d'appeler Choco pour calculer la tournée
 * @author gustavemonod
 */
public class Dijkstra {
    /**
     * Nombre maximum de secondes à attendre pour
     */
    public static int MAX_SEC = 200;

    public static Chemin plusCourtChemin(ZoneGeographique zone, LivraisonGraphVertex depart, LivraisonGraphVertex arrivee) {
        final Map<Noeud, Float> distances = new HashMap<Noeud, Float>();
        float maxArcCost = getMaxArcCost(zone);

        Noeud arriveeNoeud = arrivee.getNoeud();
        Noeud departNoeud = depart.getNoeud();
        
        for (Noeud noeud : zone.getNoeuds()) {
            distances.put(noeud, maxArcCost + 1f);
        }
        distances.put(departNoeud, 0f);

        Map<Noeud, Troncon> cheminMap = new HashMap<Noeud, Troncon>();
        Queue<Noeud> q = new PriorityQueue<Noeud>(11, new Comparator<Noeud>() {
            public int compare(Noeud n1, Noeud n2) {
                return -distances.get(n1).compareTo(distances.get(n2));
            }
        });

        q.add(departNoeud);
        while (!q.isEmpty()) {
            Noeud noeud = q.remove();
            if (noeud == arriveeNoeud) {
                List<Troncon> troncons = new LinkedList<Troncon>();
                while (noeud != departNoeud) {
                    troncons.add(0, cheminMap.get(noeud));
                    noeud = zone.getNoeudId(cheminMap.get(noeud).getIdOrigine());
                }
                Chemin chemin = new Chemin(troncons, depart, arrivee);
                depart.getSortants().add(chemin);
                arrivee.getEntrants().add(chemin);
                return chemin;
            }
            for (Troncon t : noeud.getSortants()) {
                Noeud n = zone.getNoeudId(t.getIdDestination());
                if (!cheminMap.containsKey(n)) {
                    q.add(n);
                    cheminMap.put(n, t);
                    distances.put(n, t.getCost() + distances.get(noeud));
                }
            }
        }

        return null;
    }

    public static void cheminComplet(Graph g) {
        final int SEC_TO_MILLIS = 1000;
        int nbVertices = g.getNbVertices();
        int maxCost = g.getMaxArcCost();

        TSP tsp = new TSP(g);
        tsp.solve(MAX_SEC * SEC_TO_MILLIS, nbVertices * maxCost + 1);
        if (tsp.getSolutionState() == SolutionState.SOLUTION_FOUND
                || tsp.getSolutionState() == SolutionState.OPTIMAL_SOLUTION_FOUND) {
            int[] next = tsp.getNext();
            System.out.println(Arrays.toString(next));
        }
    }

    private static float getMaxArcCost(ZoneGeographique zone) {
        float maxArcCost = 0;
        for (Noeud noeud : zone.getNoeuds()) {
            for (Troncon troncon: noeud.getSortants()) {
                float cost = troncon.getCost();
                maxArcCost = cost > maxArcCost ? cost : maxArcCost;
            }
        }
        return maxArcCost;
    }
}
