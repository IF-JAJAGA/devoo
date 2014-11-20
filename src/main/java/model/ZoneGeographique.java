package model;

import java.util.List;

/**
 * Zone regroupant un certain nombre de points de livraison.
 * @author gustavemonod
 */
public class ZoneGeographique {
    protected Noeud entrepot;
    protected List<Noeud> noeuds;
    protected List<Tournee> tournees;
}
