/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aurelien
 */
public class LivraisonGraphVertex {
    
    protected boolean estEntrepot = false;
    
    protected Noeud noeud;
    
    protected List<Chemin> entrants = new ArrayList<Chemin>();
    
    protected List<Chemin> sortants = new ArrayList<Chemin>();

    public LivraisonGraphVertex(Livraison noeud) {
        this.noeud = noeud;
    }
    
    public LivraisonGraphVertex(Noeud noeud, boolean estEntrepot) {
        this.noeud = noeud;
        this.estEntrepot = estEntrepot;
    }
    
    public int getId() {
        return this.getNoeud().getId();
    }

    public List<Chemin> getEntrants() {
        return entrants;
    }

    public List<Chemin> getSortants() {
        return sortants;
    }
    
    public Chemin getSortantByDest(LivraisonGraphVertex destination) {
        List<Chemin> chemins = this.getSortants();
        for(Chemin c : chemins) {
            if(c.getDestination().equals(destination)) {
                return c;
            }
        }
        return null;
    } 

    public Noeud getNoeud() {
        return noeud;
    }
}
