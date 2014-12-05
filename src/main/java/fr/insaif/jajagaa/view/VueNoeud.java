package fr.insaif.jajagaa.view;

import fr.insaif.jajagaa.model.EtatNoeud;
import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.Noeud;

import java.awt.*;
import java.util.Date;

/**
 * Classe Vue qui correspond à un noeud dans le modèle.
 * Cette vue possède plus d'attributs si le noeud est un point de livraison.
 *
 * @author H4201
 */
public class VueNoeud {

    public static final int DIAMETRE = 15;        //Pour l'instant
    public static final int DIAMETRE_LIVRAISON = 20;
    //protected static int conv;
    
    /**
     * Référence vers le noeud correspondant dans le package Modele
     */
    protected Noeud noeudModele;
    /**
     * Coordonnée X du noeud dans la vue.
     */
    protected int vueX;
    /**
     * Coordonnée Y du noeud dans la vue.
     */
    protected int vueY;
    /**
     * Couleur du noeud si c'est un point de livraison.
     */
    protected Color couleur;
    /**
     * Booléen si le noeud est sélectionné par un clic de souris ou pas.
     * Permet de définir la couleur dans l'affichage.
     */
    protected boolean estSelectionne;


    /**
     * Si le noeud est un point de livraison alors cette Date est réglée à
     * l'heure d'arrivée prévue du camion.
     */
    protected Date heureLivraison;


    /**
     * Creates new form VueNoeud
     * @param unNoeud
     * @param couleur
     */

    //TODO : différencier la création d'un noeud et d'un point de livraison).
    public VueNoeud(Noeud unNoeud, Color couleur) {
        this.couleur = couleur;
        noeudModele = unNoeud;
        
        vueX = unNoeud.getX();
        vueY = unNoeud.getY();
    }


    //GETTERS AND SETTERS ------------------------------------

    public Noeud getNoeudModele() {
        return noeudModele;
    }

    public int getVueX() {
        return vueX;
    }

    public void setVueX(int vueX) {
        this.vueX = vueX;
    }

    public int getVueY() {
        return vueY;
    }

    public void setVueY(int vueY) {
        this.vueY = vueY;
    }

    public Color getCouleur() {
        if (estSelectionne) {
            return Color.RED;
        }
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public EtatNoeud getEtatLivraison() {
        return noeudModele.getEtatLivraison();
    }

    public void setEtatLivraison(EtatNoeud etatLivraison) {
        noeudModele.setEtatLivraison(etatLivraison);
    }

    public boolean isEstSelectionne() {
        return estSelectionne;
    }

    public void setEstSelectionne(boolean estSelectionne) {
        this.estSelectionne = estSelectionne;
    }


    /**
     * 
     * @param p  point cliqué par rapport aux origines du plan, en haut à gauche
     * @return  true si le clic est situé dans le noeud.
     */
    public boolean getNoeudClique(Point p) {
        //On calcule la distance de p au centre du noeud et on compare au rayon.
        int d = (int) Math.sqrt(
                Math.pow(Math.abs(p.x - (vueX)), 2) +
                        Math.pow(Math.abs(p.y - (vueY)), 2)
        );
        return d < DIAMETRE / 2;
    }
}
