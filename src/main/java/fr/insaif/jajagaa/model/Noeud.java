package fr.insaif.jajagaa.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Point défini où il est possible de s'arrêter sur la carte d'une zone géographique,
 * il comporte au moins un tronçon (entrant) le desservant et un tronçon (sortant) desservant un autre point.
 * @author gustavemonod
 */
public class Noeud {
    
    /**
     * Liste des Troncons qui ont ce noeud comme origine
     */
    protected List<Troncon> sortants;

    /**
     * Abscisse du noeud.
     * Exprimée en mètre
     */
    protected int xMetre;

    /**
     * Ordonnée du noeud.
     * Exprimée en mètre
     */
    protected int yMetre;

    /**
     * Identifiant du noeud
     */
    protected int id;
    
    /**
     * Attribut indiquant l'état du noeud
     */
    public EtatNoeud etatLivraison = EtatNoeud.RIEN;
    
    /**
     * Constructeur du noeud à partir de l'adresse
     * @param id Identifiant du noeud
     * @param xMetre Abscisse du noeud (exprimée en mètre)
     * @param yMetre Ordonnée du noeud (exprimée en mètre)
     */
    public Noeud(int id, int xMetre, int yMetre) {
        this.id = id;
        this.setXMetre(xMetre);
        this.setYMetre(yMetre);
        this.sortants = new ArrayList<Troncon>();
    }
    
    /**
     * Constructeur du noeud prenant en compte les Troncons dont il est à l'origine
     * @param id Identifiant du noeud
     * @param xMetre Abscisse du noeud
     * @param yMetre Ordonnée du noeud
     * @param sortants Liste des Troncons sortant du noeud  créer
     */
    public Noeud(int id, int xMetre, int yMetre, List<Troncon> sortants){
        this.id = id;
        this.xMetre = xMetre;
        this.yMetre = yMetre;
        this.sortants = sortants;
    }
    
    /**
     * Constructeur par copie de noeud
     * @param oldNoeud 
     */
    public Noeud(Noeud oldNoeud){
        sortants = new ArrayList<>();
        for(Troncon tr : oldNoeud.sortants){
            sortants.add(new Troncon(tr));
        }
        xMetre = oldNoeud.xMetre;
        yMetre = oldNoeud.yMetre;
        id = oldNoeud.id;
    }

    /**
     * Accesseur de l'état du Noeud
     * @return Etat du noeud
     */
    public EtatNoeud getEtatLivraison() {
        return etatLivraison;
    }

    /**
     * Mutateur de l'état du noeud
     * @param etatLivraison 
     */
    public void setEtatLivraison(EtatNoeud etatLivraison) {
        this.etatLivraison = etatLivraison;
    }
    
    /**
     * Accesseur de l'identifiant du noeud
     * @return Identifiant du noeud
     */
    public int getId() {
        return this.id;
    }

    /**
     * Accesseur de la liste des Troncons qui ont ce noeud comme origine
     * @return liste des vueTroncons qui ont ce noeud comme origine
     */
    public List<Troncon> getSortants() {
        return sortants;
    }

    /**
     * Mutateur de la liste des Troncons qui ont ce noeud comme origine
     * @param sortants Liste des vueTroncons qui ont ce noeud comme origine
     */
    public void setSortants(List<Troncon> sortants) {
        this.sortants = sortants;
    }

    /**
     * Accesseur de l'abscisse du noeud.
     * Exprimée en mètre
     * @return Abscisse du noeud (exprimée en mètre)
     */
    public int getXMetre() {
        return xMetre;
    }

    /**
     * Mutateur de l'abscisse du noeud.
     * Exprimée en mètre
     * @param xMetre Abscisse du noeud (exprimée en mètre)
     */
    public void setXMetre(int xMetre) {
        this.xMetre = xMetre;
    }

    /**
     * Getteur de l'ordonnée du noeud.
     * Exprimée en mètre
     * @return Ordonnée du noeud (exprimée en mètre)
     */
    public int getYMetre() {
        return yMetre;
    }

    /**
     * Mutateur de l'ordonnée du noeud.
     * Exprimée en mètre
     * @param yMetre Ordonnée du noeud (exprimée en mètre)
     */
    public void setYMetre(int yMetre) {
        this.yMetre = yMetre;
    }

    /**
     * Ajoute un Troncon sortant avec la destination en paramètre
     * @param destination Noeud destination du Troncon à rajouter
     * @param longueurMetre Distance séparant le noeud origine de celui destination (exprimée en mètre?)
     * @param vitesse Vitesse de parcours du Troncon (exprimée en ???)
     * @param rue Nom de la Rue du Troncon qu'on ajoute
     */
    public void addSortant(Noeud destination, float longueurMetre, float vitesse, String rue) {
        assert !this.equals(destination);
        this.sortants.add(new Troncon(this.id, destination.getId(), longueurMetre, vitesse,rue));
    }

    /*
    public int getX() {
        return xMetre;
    }

    public void setX(int x) {
        this.xMetre = x;
    }

    public int getY() {
        return yMetre;
    }

    public void setY(int y) {
        this.yMetre = y;
    }
    
    */
    
    /**
     * Teste l'égalité entre le Noeud this et celui passé en paramètre
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Noeud){
            Noeud noeud = (Noeud) obj;
            return (noeud.id == this.id) && (noeud.xMetre==this.xMetre)  && (noeud.yMetre == this.yMetre);
        }
        else { 
            return false;
        }
    }
}
