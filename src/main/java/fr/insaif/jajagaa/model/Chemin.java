package fr.insaif.jajagaa.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Ensemble ordonné de tronçons joignant deux points de livraison, ou un point de livraison et un entrepôt.
 * @author gustavemonod
 */
public class Chemin {
    
    /**
     * Liste des vueTroncons à emprunter (dans l'ordre) pour aller de l'origine du premier à la destination du dernier.
     */
    protected List<Troncon> troncons;
    
    /**
     * Livraison origine du chemin considéré.
     */
    protected LivraisonGraphVertex origine;
    
    /**
     * Livraison de destination du chemin considéré.
     */
    protected LivraisonGraphVertex destination;

    /**
     * Constructeur de Chemin.
     * @param troncons
     * @param origine
     * @param destination 
     */
    public Chemin(List<Troncon> troncons, LivraisonGraphVertex origine, LivraisonGraphVertex destination) {
        this.setOrigine(origine);
        this.setDestination(destination);
        this.troncons = troncons;
    }
    
    /**
     * Constructeur par copie de Chemin.
     * @param oldChemin 
     */
    public Chemin(Chemin oldChemin){
        troncons = new ArrayList<>();
        for(Troncon Tr : oldChemin.troncons){
            troncons.add(new Troncon(Tr));
        }
        origine = new LivraisonGraphVertex(oldChemin.origine, this, true);
        destination = new LivraisonGraphVertex(oldChemin.destination, this, false);
    }

    /**
     * Méthode renvoyant un booléen indiquant si deux Chemins sont égaux ou pas.
     * @param o Object qu'on compare à this
     * @return Booléen exprimant l'égalité
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Chemin){
            Chemin Ch = (Chemin)o;
            return (origine.equals(Ch.origine) && destination.equals(Ch.origine));
        }
        return super.equals(o);
    }
    
    /**
     * Méthode permettant d'ajouter un Troncon au chemin.
     * @param unTroncon 
     */
    public void addTroncon(Troncon unTroncon){
        this.troncons.add(unTroncon);
    }

    /**
     * Accesseur de la Livraison d'origine du Chemin.
     * @return Le nœud dont part ce chemin (origine du premier troncon)
     */
    public LivraisonGraphVertex getOrigine() {
        return this.origine;
    }
    
    /**
     * Accesseur de la Livraison de destination du Chemin.
     * @return Le nœud où arrive ce chemin (destination du dernier troncon)
     */
    public LivraisonGraphVertex getDestination() {
        return this.destination;
    }

    /**
     * Accesseur de la liste de Troncons composant le Chemin.
     * @return List<Troncon> composant le Chemin
     */
    public List<Troncon> getTroncons() {
        return troncons;
    }

    /**
     * Mutateur de la liste de Troncon du Chemin considéré.
     * @param troncons 
     */
    public void setTroncons(List<Troncon> troncons) {
        this.troncons = troncons;
    }
    
    /**
     * Accesseur du coût du chemin, calculé avec la somme de tous les tronçons.
     * @return integer exprimant le coût du chemin
     */
    public int getCost() {
        int cost = 0;
        for(int i=0, size=troncons.size(); i<size; i++) {
            cost += troncons.get(i).getCost();
        }
        return cost;                 
    }

    /**
     * Mutateur de la Livraison d'origine du Chemin.
     * @param origine
     */
    public void setOrigine(LivraisonGraphVertex origine) {
        this.origine = origine;
    }

    /**
     * Mutateur de la Livraison de destination du Chemin.
     * @param destination 
     */
    public void setDestination(LivraisonGraphVertex destination) {
        this.destination = destination;
    }
}
