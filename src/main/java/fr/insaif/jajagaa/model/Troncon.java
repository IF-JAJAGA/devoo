package fr.insaif.jajagaa.model;

/**
 * Arc orienté du graphe représentant une route, une rue ou un chemin reliant deux points de la carte,
 * qui peuvent être un point de livraison.
 * @author gustavemonod
 */
public class Troncon {
    
    /**
     * Noeud d'origine du tronçon.
     */
    protected Noeud origine;
    /**
     * Noeud de destination du tronçon.
     */
    protected Noeud destination;
    /**
     * Vitesse à laquelle on peut se déplacer sur le tronçon.
     */
    protected float vitesse;
    /**
     * Longueur du tronçon.
     */
    protected float longueur;

    
    //GETTERS AND SETTERS  /define which we keep (see useful)
    public Noeud getOrigine() {
        return origine;
    }

    public void setOrigine(Noeud origine) {
        this.origine = origine;
    }

    public Noeud getDestination() {
        return destination;
    }

    public void setDestination(Noeud destination) {
        this.destination = destination;
    }

    public float getVitesse() {
        return vitesse;
    }

    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }

    public float getLongueur() {
        return longueur;
    }

    public void setLongueur(float longueur) {
        this.longueur = longueur;
    }
    
}
