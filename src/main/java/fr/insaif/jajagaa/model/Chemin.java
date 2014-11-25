package fr.insaif.jajagaa.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Ensemble ordonné de tronçons joignant deux points de livraison, ou un point de livraison et un entrepôt
 * @author gustavemonod
 */
public class Chemin {
    /**
     * Liste des vueTroncons à emprunter (dans l'ordre) pour aller de l'origine du premier à la destination du dernier
     */
    protected List<Troncon> troncons = new ArrayList<Troncon>();
    
    protected LivraisonGraphVertice depart;
    
    protected LivraisonGraphVertice arrivee;

    public Chemin(List<Troncon> troncons) {
        this.troncons = troncons;
    }

    public void addTroncon(Troncon unTroncon){
        this.troncons.add(unTroncon);
    }

    /**
     * Renvoie le nœud dont part ce chemin (origine du premier troncon)
     * @return Le nœud dont part ce chemin (origine du premier troncon)
     */
    public Noeud getOrigine() {
        return this.getTroncons().get(0).getOrigine();
    }

    public List<Troncon> getTroncons() {
        return troncons;
    }

    public void setTroncons(List<Troncon> troncons) {
        this.troncons = troncons;
    }
    
    /**
     * Coût du chemin, calculé avec la somme de tous les tronçons
     * @return Coût du chemin
     */
    public int getCost() {
        int cost = 0;
        for(int i=0, size=troncons.size(); i<size; i++) {
            cost += troncons.get(i).getCost();
        }
        return cost;                 
    }
}
