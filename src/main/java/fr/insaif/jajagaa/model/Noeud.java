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
     * Liste des vueTroncons qui ont ce noeud comme origine
     */
    protected List<Troncon> sortants;

    /**

     * Abscisse du noeud (exprimée en mètre)
     */
    protected int xMetre;

    /**
     * Ordonnée du noeud (exprimée en mètre)
     */
    protected int yMetre;

    /**
     * Identifiant du noeud
     */
    protected int id;

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
    
    public Noeud(int id, int xMetre, int yMetre, List<Troncon> sortants){
        this.id = id;
        this.xMetre = xMetre;
        this.yMetre = yMetre;
        this.sortants = sortants;
    }
    
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
     * Identifiant du noeud
     * @return Identifiant du noeud
     */
    public int getId() {
        return this.id;
    }

    /**
     * Liste des vueTroncons qui ont ce noeud comme origine
     * @return liste des vueTroncons qui ont ce noeud comme origine
     */
    public List<Troncon> getSortants() {
        return sortants;
    }

    /**
     * Modifie la liste des vueTroncons qui ont ce noeud comme origine
     * @param sortants Liste des vueTroncons qui ont ce noeud comme origine
     */
    public void setSortants(List<Troncon> sortants) {
        this.sortants = sortants;
    }

    /**
     * Abscisse du noeud (exprimée en mètre)
     * @return Abscisse du noeud (exprimée en mètre)
     */
    public int getXMetre() {
        return xMetre;
    }

    /**
     * Modifie l'abscisse du noeud (exprimée en mètre)
     * @param xMetre Abscisse du noeud (exprimée en mètre)
     */
    public void setXMetre(int xMetre) {
        this.xMetre = xMetre;
    }

    /**
     * Ordonnée du noeud (exprimée en mètre)
     * @return Ordonnée du noeud (exprimée en mètre)
     */
    public int getYMetre() {
        return yMetre;
    }

    /**
     * Modifie l'ordonnée du noeud (exprimée en mètre)
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
     */
    public void addSortant(Noeud destination, float longueurMetre, float vitesse, String rue) {
        assert !this.equals(destination);
        this.sortants.add(new Troncon(this.id, destination.getId(), longueurMetre, vitesse,rue));
    }

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
