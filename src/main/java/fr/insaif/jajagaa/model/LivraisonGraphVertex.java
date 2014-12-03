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
    
    protected int idNoeud;

    protected List<Chemin> sortants = new ArrayList<>();

    public LivraisonGraphVertex(int idNoeud) {
        this.idNoeud = idNoeud;
    }
    
    public LivraisonGraphVertex(int idNoeud, boolean estEntrepot) {
        this.idNoeud = idNoeud;
        this.estEntrepot = estEntrepot;
    }
    
    /**
     * Constructeur par copie.
     * @param old
     * @param cheminAppelant
     * @param isOrigine true si l'objet que l'on construit est à l'origine du chemin cheminAppelant.
     */
    public LivraisonGraphVertex(LivraisonGraphVertex old, Chemin cheminAppelant, boolean isOrigine){
        estEntrepot = old.estEntrepot;
        idNoeud = old.idNoeud;
        sortants = new ArrayList<>();
        for(Chemin Chs : old.sortants){
            
            if(isOrigine && Chs.getOrigine().getIdNoeud() == idNoeud){
                sortants.add(cheminAppelant);
            } 
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof LivraisonGraphVertex){
            LivraisonGraphVertex LGV = (LivraisonGraphVertex)o;
            return idNoeud==LGV.idNoeud;
        }
        return super.equals(o); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    public int getIdNoeud() {
        return idNoeud;
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
}
