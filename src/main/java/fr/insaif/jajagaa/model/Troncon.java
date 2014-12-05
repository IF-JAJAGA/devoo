package fr.insaif.jajagaa.model;

/**
 * Arc orienté du graphe représentant une route, une rue ou un chemin reliant deux points de la carte,
 * qui peuvent être un point de livraison.
 * @author H4201
 */
public class Troncon {

    /**
     * id du noeud origine de ce Troncon
     */
    protected int idOrigine;

    /**
     * id du noeud destination de ce Troncon
     */
    protected int idDestination;

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
     * @param idOrigine Noeud origine de ce Troncon
     * @param idDestination Noeud destination de ce Troncon
     * @param longueurMetre Distance séparant le noeud origine de celui destination (exprimée en mètre?)
     * @param vitesse Vitesse de parcours du Troncon (exprimée en ???)
     * @param rue Nom de la rue associée au Troncon à créer
     * Renvoie des erreurs lorsque des valeurs sont repérées comme aberrantes
     */
    public Troncon(int idOrigine, int idDestination, float longueurMetre, float vitesse, String rue) {
        this.idOrigine = idOrigine;
        this.idDestination = idDestination;
        if (longueurMetre < 0 || longueurMetre > 1500) {
            System.err.println("Longueur du troncon d'origine " + idOrigine +" et de destination " + idDestination + " incorrecte");
        } else {
            this.longueurMetre = longueurMetre;
        }
        if (vitesse < 0 || vitesse > 6) {
            System.err.println("Vitesse du troncon d'origine " + idOrigine +" et de destination " + idDestination + " incorrecte");
        } else {
            this.vitesse = vitesse;
        }
        this.nomRue = rue;
    }
    
    /**
     * Constructeur par copie.
     * @param oldTroncon 
     */
    public Troncon(Troncon oldTroncon){
        idOrigine = oldTroncon.idOrigine;
        idDestination = oldTroncon.idDestination;
        longueurMetre = oldTroncon.longueurMetre;
        vitesse = oldTroncon.vitesse;
        nomRue = new String(oldTroncon.nomRue);
    }

    /**
     * Coût d'un Troncon, calculé avec longueurMetre / vitesse
     * @return Coût d'un Troncon, calculé avec longueurMetre / vitesse
     */
    public float getCost() {
        return this.getLongueurMetre() / this.getVitesse();
    }

    /**
     * Coût arrondi d'un Troncon Math.round(this.getCost())
     * @return Coût arrondi d'un Troncon Math.round(this.getCost())
     */
    public int getCostInt() {
        return Math.round(this.getCost());
    }

    /**
     * Accesseur de l'ID du Noeud origine de ce Troncon
     * @return ID du noeud origine de ce Troncon
     */
    public int getIdOrigine() {
        return idOrigine;
    }

    /**
     * Mutateur du noeud origine de ce Troncon
     * @param idOrigine ID du Noeud origine de ce Troncon
     */
    public void setIdOrigine(int idOrigine) {
        this.idOrigine = idOrigine;
    }

    /**
     * Accesseur de l'ID du Noeud destination de ce Troncon
     * @return ID du Noeud destination de ce Troncon
     */
    public int getIdDestination() {
        return idDestination;
    }

    /**
     * Mutateurde l'ID du Noeud destination de ce Troncon
     * @param idDestination ID du Noeud destination de ce Troncon
     */
    public void setIdDestination(int idDestination) {
        this.idDestination = idDestination;
    }

    /**
     * Accesseur de la distance séparant le noeud origine de celui destination.
     * Exprimée en mètre
     * @return Distance séparant le noeud origine de celui destination (exprimée en mètre?)
     */
    public float getLongueurMetre() {
        return longueurMetre;
    }

    /**
     * Mutateur de la distance séparant le noeud origine de celui destination.
     * Exprimée en mètre
     * @param longueurMetre Distance séparant le noeud origine de celui destination (exprimée en mètre?)
     */
    public void setLongueurMetre(float longueurMetre) {
        assert longueurMetre > 0;
        this.longueurMetre = longueurMetre;
    }

    /**
     * Accesseur de la vitesse de parcours du Troncon.
     * Exprimée en 10km/h
     * @return Vitesse de parcours du Troncon (exprimée en 10km/h)
     */
    public float getVitesse() {
        return vitesse;
    }

    /**
     * Mutateur de la vitesse de parcours du Troncon.
     * Exprimée en 10km/h
     * @param vitesse Vitesse de parcours du Troncon (exprimée en 10km/h)
     */
    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }
    
    /**
     * Accesseur du nom de la rue concernée par le Troncon désigné
     * @return Nom de la rue sous forme de String
     */
    public String getNomRue() {
        return nomRue;
    }
    
    /**
     * Mutateur du nom de la rue associée au tronçon
     * @param nom Nom de la rue du Troncon
     */
    public void setNomRue(String nom) {
        this.nomRue = nom;
    }
}
