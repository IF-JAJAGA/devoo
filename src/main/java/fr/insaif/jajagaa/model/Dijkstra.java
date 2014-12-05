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
     * Méthode permettant de calculer le plus court Chemin entre deux Livraisons.
     * @param zone ZoneGeographique à laquelle appartiennent les deux Livraisons
     * @param depart Livraison de départ
     * @param arrivee Livraison de destination
     * @return Chemin le plus court entre les deux Livraisons
     */
    public static Chemin plusCourtChemin(ZoneGeographique zone, LivraisonGraphVertex depart, LivraisonGraphVertex arrivee) {
        final Map<Noeud, Float> distances = new HashMap<Noeud, Float>();
        float maxArcCost = getMaxArcCost(zone);

        Noeud arriveeNoeud = zone.getNoeudById(arrivee.getIdNoeud());
        Noeud departNoeud = zone.getNoeudById(depart.getIdNoeud());
        
        for (Noeud noeud : zone.getNoeuds()) {
            distances.put(noeud, zone.getNoeuds().size() * (maxArcCost + 1f));
        }
        distances.put(departNoeud, 0f);

        Map<Noeud, Troncon> cheminMap = new HashMap<Noeud, Troncon>();
        Queue<Noeud> q = new PriorityQueue<Noeud>(11, new Comparator<Noeud>() {
            public int compare(Noeud n1, Noeud n2) {
                return distances.get(n1).compareTo(distances.get(n2));
            }
        });

        q.add(departNoeud);
        while (!q.isEmpty()) {
            Noeud plusProche = q.poll();
            if (plusProche == arriveeNoeud) {
                List<Troncon> troncons = new LinkedList<Troncon>();
                while (plusProche != departNoeud) {
                    troncons.add(0, cheminMap.get(plusProche));
                    plusProche = zone.getNoeudId(cheminMap.get(plusProche).getIdOrigine());
                }
                Chemin chemin = new Chemin(troncons, depart, arrivee);
                depart.getSortants().add(chemin);
                return chemin;
            }
            for (Troncon t : plusProche.getSortants()) {
                Noeud voisin = zone.getNoeudId(t.getIdDestination());
                float cost = distances.get(plusProche) + t.getCost();
                if (cost < distances.get(voisin)) {
                    distances.put(voisin, cost);
                    cheminMap.put(voisin, t);
                    q.add(voisin);
                }
            }
        }

        return null;
    }

    /**
     * Recherche le coût maximal possible des Troncons composants la ZoneGeographique
     * zone passée en paramètre
     * @param zone
     * @return le coût maximal
     */
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
