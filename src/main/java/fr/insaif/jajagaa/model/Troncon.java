package fr.insaif.jajagaa.model;

/**
 * Arc du graphe représentant une route, une rue ou un chemin reliant deux points de la carte,
 * qui peuvent être un point de livraison.
 * @author gustavemonod
 */
public class Troncon {
    protected Noeud origine;
    protected Noeud destination;
    protected float longueur;
    protected float vitesse;
}
