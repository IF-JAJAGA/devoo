package fr.insaif.jajagaa.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Ensemble ordonné de tronçons joignant deux points de livraison, ou un point de livraison et un entrepôt
 * @author gustavemonod
 */
public class Chemin {
    /**
     * Liste des troncons à emprunter (dans l'ordre) pour aller de l'origine du premier à la destination du dernier
     */
    protected List<Troncon> troncons = new ArrayList<>();
}
