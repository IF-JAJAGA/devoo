/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.model;

import java.util.List;

/**
 *
 * @author aurelien
 */
public class LivraisonGraphVertex {
    
    protected boolean estEntrepot = false;
    
    protected Noeud noeud;
    
    protected List<Chemin> entrants;
    
    protected List<Chemin> sortants;

    public LivraisonGraphVertex(Noeud noeud) {
        this.noeud = noeud;
    }
    
    public LivraisonGraphVertex(Noeud noeud, boolean estEntrepot) {
        this.noeud = noeud;
        this.estEntrepot = estEntrepot;
    }
    
    public int getId() {
        return this.noeud.getId();
    }

    public List<Chemin> getEntrants() {
        return entrants;
    }

    public List<Chemin> getSortants() {
        return sortants;
    }
    
}
