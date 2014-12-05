package fr.insaif.jajagaa.view;

import fr.insaif.jajagaa.model.EtatNoeud;
import fr.insaif.jajagaa.model.Noeud;

import java.awt.*;
import java.util.Date;

/**
 * Classe Vue qui correspond à un noeud ou à une livraison dans le modèle.
 *
 * @author H4201
 */
public class VueNoeud {
    /**
     * Diamètre d'un noeud sur le plan.
     */
    public static final int DIAMETRE = 15;
    /**
     * Diamètre d'une livraison sur le plan.
     */
    public static final int DIAMETRE_LIVRAISON = 20;
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
     * Crée un noeud à afficher
     * @param unNoeud le noeud correspondant dans le modèle
     * @param couleur la couleur attribuée dans l'affichage.
     */
    public VueNoeud(Noeud unNoeud, Color couleur) {
        this.couleur = couleur;
        noeudModele = unNoeud;
        
        vueX = unNoeud.getXMetre();
        vueY = unNoeud.getYMetre();
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
     * Détermine si on a cliqué sur le noeud ou pas en fonction des coordonnées en paramètre.
     * @param p  point cliqué par rapport aux origines du plan, en haut à gauche.
     * @return  true si le clic est situé dans le noeud.
     */
    public boolean getNoeudClique(Point p) {
        int d = (int) Math.sqrt(
                Math.pow(Math.abs(p.x - (vueX)), 2) +
                        Math.pow(Math.abs(p.y - (vueY)), 2)
        );
        return d < DIAMETRE / 2;
    }
}
