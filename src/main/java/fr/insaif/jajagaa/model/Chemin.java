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
    protected List<Troncon> troncons;
    
    protected LivraisonGraphVertex origine;
    
    protected LivraisonGraphVertex destination;

    public Chemin(List<Troncon> troncons, LivraisonGraphVertex origine, LivraisonGraphVertex destination) {
        this.setOrigine(origine);
        this.setDestination(destination);
        this.troncons = troncons;
    }
    
    public Chemin(Chemin oldChemin){
        troncons = new ArrayList<>();
        for(Troncon Tr : oldChemin.troncons){
            troncons.add(new Troncon(Tr));
        }
        origine = new LivraisonGraphVertex(oldChemin.origine, this, true);
        destination = new LivraisonGraphVertex(oldChemin.destination, this, false);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Chemin){
            Chemin Ch = (Chemin)o;
            return (origine.equals(Ch.origine) && destination.equals(Ch.origine));
        }
        return super.equals(o); //To change body of generated methods, choose Tools | Templates.
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
    
    public void recalculerHeuresLivraison() {
        // TODO implémentation
        throw new UnsupportedOperationException("TODO");
    }

    public void setOrigine(LivraisonGraphVertex origine) {
        this.origine = origine;
    }

    public void setDestination(LivraisonGraphVertex destination) {
        this.destination = destination;
    }
}
