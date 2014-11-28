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
    
    protected LivraisonGraphVertex origine;
    
    protected LivraisonGraphVertex destination;
    
    //C'est la livraison destination
    protected Livraison livraison;

    public Chemin(List<Troncon> troncons, LivraisonGraphVertex origine, LivraisonGraphVertex destination) {
        this.origine = origine;
        this.destination = destination;
        this.troncons = troncons;
    }

    public void addTroncon(Troncon unTroncon){
        this.troncons.add(unTroncon);
    }

    /**
     * Renvoie le nœud dont part ce chemin (origine du premier troncon)
     * @return Le nœud dont part ce chemin (origine du premier troncon)
     */
    public LivraisonGraphVertex getOrigine() {
        return this.origine;
    }
    
    /**
     * Renvoie le nœud où arrive ce chemin (destination du dernier troncon)
     * @return Le nœud où arrive ce chemin (destination du dernier troncon)
     */
    public LivraisonGraphVertex getDestination() {
        return this.destination;
    }

    public List<Troncon> getTroncons() {
        return troncons;
    }

    public void setTroncons(List<Troncon> troncons) {
        this.troncons = troncons;
    }
    
    public Livraison getLivraison() {
        return livraison;
    }

    public void setLivraison(Livraison livraison) {
        this.livraison = livraison;
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
    
    public void recalculerHeuresLivraison(){
        
    }
}
