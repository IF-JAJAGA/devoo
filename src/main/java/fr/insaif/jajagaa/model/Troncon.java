package fr.insaif.jajagaa.model;

/**
 * Arc orienté du graphe représentant une route, une rue ou un chemin reliant deux points de la carte,
 * qui peuvent être un point de livraison.
 * @author gustavemonod
 */
public class Troncon {

    /**
     * Noeud origine de ce Troncon
     */
    protected Noeud origine;

    /**
     * Noeud destination de ce Troncon
     */
    protected Noeud destination;

    /**
     * Distance séparant le noeud origine de celui destination (exprimée en mètre?)
     */
    protected float longueurMetre;

    /**
     * Vitesse de parcours du Troncon (exprimée en ???)
     */
    protected float vitesse;
    
    /**
     * Nom de la rue concernée par le Troncon
     */
    protected String nomRue;
    /**
     * Construction d'un Troncon à partir de ces informations de base
     * @param origine Noeud origine de ce Troncon
     * @param destination Noeud destination de ce Troncon
     * @param longueurMetre Distance séparant le noeud origine de celui destination (exprimée en mètre?)
     * @param vitesse Vitesse de parcours du Troncon (exprimée en ???)
     */
    public Troncon(Noeud origine, Noeud destination, float longueurMetre, float vitesse, String rue) {
        this.origine = origine;
        this.destination = destination;
        this.longueurMetre = longueurMetre;
        this.vitesse = vitesse;
        this.nomRue = rue;
    }

    /**
     * Coût d'un Troncon, calculé avec Math.round(longueurMetre / vitesse)
     * @return Coût d'un Troncon, calculé avec Math.round(longueurMetre / vitesse)
     */
    public int getCost() {
        return Math.round(this.getLongueurMetre() / this.getVitesse());
    }

    /**
     * Noeud origine de ce Troncon
     * @return Noeud origine de ce Troncon
     */
    public Noeud getOrigine() {
        return origine;
    }


    /**
     * Modifie le noeud origine de ce Troncon
     * @param origine Noeud origine de ce Troncon
     */
    public void setOrigine(Noeud origine) {
        this.origine = origine;
    }


    /**
     * Noeud destination de ce Troncon
     * @return Noeud destination de ce Troncon
     */
    public Noeud getDestination() {
        return destination;
    }


    /**
     * Modifie le noeud destination de ce Troncon
     * @param destination Noeud destination de ce Troncon
     */
    public void setDestination(Noeud destination) {
        this.destination = destination;
    }


    /**
     * Distance séparant le noeud origine de celui destination (exprimée en mètre?)
     * @return Distance séparant le noeud origine de celui destination (exprimée en mètre?)
     */
    public float getLongueurMetre() {
        return longueurMetre;
    }

    /**
     * Modifie la distance séparant le noeud origine de celui destination (exprimée en mètre?)
     * @param longueurMetre Distance séparant le noeud origine de celui destination (exprimée en mètre?)
     */
    public void setLongueurMetre(float longueurMetre) {
        assert longueurMetre > 0;
        this.longueurMetre = longueurMetre;
    }

    /**
     * Vitesse de parcours du Troncon (exprimée en ???)
     * @return Vitesse de parcours du Troncon (exprimée en ???)
     */
    public float getVitesse() {
        return vitesse;
    }

    /**
     * Modifie la vitesse de parcours du Troncon (exprimée en ???)
     * @param vitesse Vitesse de parcours du Troncon (exprimée en ???)
     */
    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }
    
    public void setNomRue(String nom) {
        this.nomRue = nom;
    }
}
