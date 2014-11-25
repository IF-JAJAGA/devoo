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
public class LivraisonGraphVertice {
    
    protected Noeud noeud;
    
    protected List<Chemin> entrants;
    
    protected List<Chemin> sortants;

    public LivraisonGraphVertice(Noeud noeud) {
        this.noeud = noeud;
    }
}
