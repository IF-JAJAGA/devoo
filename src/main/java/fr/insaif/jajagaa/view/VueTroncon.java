package fr.insaif.jajagaa.view;

import fr.insaif.jajagaa.control.Controleur;
import java.awt.Point;

import javax.swing.JPanel;

import fr.insaif.jajagaa.model.Troncon;
import fr.insaif.jajagaa.model.ZoneGeographique;


/**
 * Classe qui implémente la vue d'un tronçon.
 * @author alicia
 */
public class VueTroncon {

    /**
     * Troncon à partir duquel la VueTroncon est implémentée.
     */
    protected Troncon tronconModel;
    /**
     * Coordonnée X du noeud d'origine du tronçon.
     */
    protected int origViewX;
    /**
     * Coordonnée Y du noeud d'origine du tronçon.
     */
    protected int origViewY;
    /**
     * Coordonnée X du noeud de destination du tronçon.
     */
    protected int destViewX;
    /**
     * Coordonnée Y du noeud de destination du tronçon.
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
        origViewX = zg.getNoeudId(unTroncon.getIdOrigine()).getX()*conv;
        origViewY = zg.getNoeudId(unTroncon.getIdOrigine()).getY()*conv;
        destViewX = zg.getNoeudId(unTroncon.getIdDestination()).getX()*conv;
        destViewY = zg.getNoeudId(unTroncon.getIdDestination()).getY()*conv;
        vitesse = unTroncon.getVitesse();
    }
    
    public boolean changementSelection(Point p) {
		// TODO Auto-generated method stub
		return false;
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
