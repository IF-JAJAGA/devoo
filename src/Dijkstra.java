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
        final Map<Noeud, Float> distances = new HashMap<>();
        Map<Noeud, Troncon> predecesseurs = new HashMap<>();
        Queue<Noeud> file = new PriorityQueue<>(11, new Comparator<Noeud>() {
            public int compare(Noeud n1, Noeud n2) {
                return distances.get(n1).compareTo(distances.get(n2));
            }
        });

        Noeud arriveeNoeud = zone.getNoeudById(arrivee.getIdNoeud());
        Noeud departNoeud = zone.getNoeudById(depart.getIdNoeud());

        float maxArcCost = getMaxArcCost(zone);
        for (Noeud noeud : zone.getNoeuds()) {
            // Infinity:
            distances.put(noeud, maxArcCost + 1f);
        }
        distances.put(departNoeud, 0f);

        file.add(departNoeud);

        for (Troncon t : departNoeud.getSortants()) {
            Noeud v = zone.getNoeudId(t.getIdDestination());
        }

        while (!file.isEmpty()) {
            Noeud plusProche = file.remove();
            if (plusProche == arriveeNoeud) {
                return null;
            }
            for (Troncon sortant : plusProche.getSortants()) {
                Noeud voisin = zone.getNoeudId(sortant.getIdDestination());

                float alt = distances.get(plusProche) + sortant.getCost();
                if (alt < distances.get(voisin)) {
                    distances.put(voisin, alt);
                    predecesseurs.put(voisin, sortant);
                    file.add(voisin);
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
