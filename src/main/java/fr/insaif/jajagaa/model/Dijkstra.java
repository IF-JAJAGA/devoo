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

    public static Chemin plusCourtChemin(ZoneGeographique zone, Noeud depart, Noeud arrivee) {
        final Map<Noeud, Float> distances = new HashMap<Noeud, Float>();

        for (Noeud noeud : zone.getNoeuds()) {
            distances.put(noeud, zone.getMaxArcCost() + 1f);
        }
        distances.put(depart, 0f);

        Map<Noeud, Troncon> chemin = new HashMap<Noeud, Troncon>();
        Queue<Noeud> q = new PriorityQueue<Noeud>(11, new Comparator<Noeud>() {
            public int compare(Noeud n1, Noeud n2) {
                return -distances.get(n1).compareTo(distances.get(n2));
            }
        });

        q.add(depart);
        while (!q.isEmpty()) {
            Noeud noeud = q.remove();
            if (noeud == arrivee) {
                List<Troncon> troncons = new LinkedList<Troncon>();
                while (noeud != depart) {
                    troncons.add(0, chemin.get(noeud));
                    noeud = chemin.get(noeud).getOrigine();
                }
                return new Chemin(troncons);
            }
            for (Troncon t : noeud.getSortants()) {
                Noeud n = t.getDestination();
                if (!chemin.containsKey(n)) {
                    q.add(n);
                    chemin.put(n, t);
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
}
