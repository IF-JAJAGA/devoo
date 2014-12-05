package fr.insaif.jajagaa.view;

import fr.insaif.jajagaa.control.Controleur;
import java.awt.Point;

import fr.insaif.jajagaa.model.Troncon;
import fr.insaif.jajagaa.model.ZoneGeographique;


/**
 * Classe qui implémente la vue d'un tronçon.
 * @author H4201
 */
public class VueTroncon {

    /**
     * Troncon à partir duquel la VueTroncon est implémentée.
     */
    protected Troncon tronconModel;
    /**
     * Coordonnée X du noeud d'origine du tronçon dans la vue.
     */
    protected int origViewX;
    /**
     * Coordonnée Y du noeud d'origine du tronçon dans la vue.
     */
    protected int origViewY;
    /**
     * Coordonnée X du noeud de destination du tronçon dans la vue.
     */
    protected int destViewX;
    /**
     * Coordonnée Y du noeud de destination du tronçon dans la vue.
     */
    protected int destViewY;
    /**
     * Coefficient de conversion entre les coordonnées du modèle et la vue. 
     */
    protected int conv;
    /**
     * Vitesse à laquelle on peut rouler sur le tronconModel.
     */
    protected float vitesse;
    
    
    /**
     * Constructeur de la classe VueTroncon.
     * @param unTroncon 
     */
    public VueTroncon(Troncon unTroncon) {
        ZoneGeographique zg = Controleur.getInstance().getZone();
        
        tronconModel = unTroncon;
        origViewX = zg.getNoeudById(unTroncon.getIdOrigine()).getXMetre()*conv;
        origViewY = zg.getNoeudById(unTroncon.getIdOrigine()).getYMetre()*conv;
        destViewX = zg.getNoeudById(unTroncon.getIdDestination()).getXMetre()*conv;
        destViewY = zg.getNoeudById(unTroncon.getIdDestination()).getYMetre()*conv;
        vitesse = unTroncon.getVitesse();
    }
    
    public Troncon getTronconModel() {
        return tronconModel;
    }

    public void setTronconModel(Troncon tronconModel) {
        this.tronconModel = tronconModel;
    }

    public int getOrigViewX() {
        return origViewX;
    }

    public void setOrigViewX(int origViewX) {
        this.origViewX = origViewX;
    }

    public int getOrigViewY() {
        return origViewY;
    }

    public void setOrigViewY(int origViewY) {
        this.origViewY = origViewY;
    }

    public int getDestViewX() {
        return destViewX;
    }

    public void setDestViewX(int destViewX) {
        this.destViewX = destViewX;
    }

    public int getDestViewY() {
        return destViewY;
    }

    public void setDestViewY(int destViewY) {
        this.destViewY = destViewY;
    }
}
