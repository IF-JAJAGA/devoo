package fr.insaif.jajagaa.view;

import fr.insaif.jajagaa.model.Noeud;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * Classe Vue qui correspond à un noeud dans le modèle.
 * Cette vue possède plus d'attributs si le noeud est un point de livraison.
 *
 * @author alicia
 */
public class VueNoeud extends JPanel implements VueElement {

    public static final int DIAMETRE = 15;        //Pour l'instant
    public static final int DIAMETRE_LIVRAISON = 20;
    protected static int conv;
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

    //TODO : enum si le noeud est un point de livraison
    /**
     * Ce booléen est vrai si le noeud est un point de livraison.
     */
    protected boolean estPointDeLivraison = false;

    /**
     * Si le noeud est un point de livraison alors cette Date est réglée à
     * l'heure d'arrivée prévue du camion.
     */
    protected Date heureLivraison;


    /**
     * Creates new form VueNoeud
     */

    //TODO : différencier la création d'un noeud et d'un point de livraison).
    public VueNoeud(Noeud unNoeud, Color couleur) {
        this.couleur = couleur;
        noeudModele = unNoeud;
        vueX = unNoeud.getX() * conv;
        vueY = unNoeud.getY() * conv;
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

    public boolean isEstPointDeLivraison() {
        return estPointDeLivraison;
    }

    public void setEstPointDeLivraison(boolean estPointDeLivraison) {
        this.estPointDeLivraison = estPointDeLivraison;
    }


    public boolean changementSelection(Point p) {
        //On calcule la distance de p au centre du noeud et on compare au rayon.
        int d = (int) Math.sqrt(
                Math.pow(Math.abs(p.x - (vueX)), 2) +
                        Math.pow(Math.abs(p.y - (vueY)), 2)
        );
        System.out.println("Inside VN" + estSelectionne);
        if (d < DIAMETRE / 2) {
            //On a cliqué sur le noeud.
            estSelectionne = !(estSelectionne);
            return true;
        }
        //On a cliqué en dehors du noeud.
        else {
            if (!estSelectionne) {
                return false;
            }
            estSelectionne = false;
            return true;

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public Noeud getNoeudClique(Point position) {
        return null;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}