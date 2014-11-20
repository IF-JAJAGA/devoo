package model;

import java.util.Collection;
import java.util.List;

/**
 * Point défini où il est possible de s'arrêter sur la carte d'une zone géographique,
 * il comporte au moins un tronçon (entrant) le desservant et un tronçon (sortant) desservant un autre point.
 * @author gustavemonod
 */
public class Noeud {
    protected List<Troncon> entrants;
    protected List<Troncon> sortants;

    protected Collection<Livraison> livraisons;
}
