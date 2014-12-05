package fr.insaif.jajagaa.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aurelien
 */
public class LivraisonGraphVertex {
    
    /**
     * Booléen indiquant si l'entrepot 
     */
    protected boolean estEntrepot = false;
    
    /**
     * ID de la Livraison
     */
    protected int idNoeud;
    
    /**
     * Liste de Chemins sortants de la Livraison 
     */
    protected List<Chemin> sortants = new ArrayList<>();

    /**
     * Constructeur de LivraisonGraphVertex à partir de l'ID
     * @param idNoeud 
     */
    public LivraisonGraphVertex(int idNoeud) {
        this.idNoeud = idNoeud;
    }
    
    /**
     * Constructeur de LivraisonGraphVertex en précisant si c'est un entrepot
     * @param idNoeud ID du noeud
     * @param estEntrepot Booléen indiquant si c'est un entrepot
     */
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

    /**
     * Test l'égalité entre deux LivraisonGraphVertex
     * @param o
     * @return 
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof LivraisonGraphVertex){
            LivraisonGraphVertex LGV = (LivraisonGraphVertex)o;
            return idNoeud==LGV.idNoeud;
        }
        return super.equals(o); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Accesseur de la Livraison
     * @return 
     */
    public int getIdNoeud() {
        return idNoeud;
    }

    /**
     * Accesseur des Chemins sortants de la Livraison
     * @return 
     */
    public List<Chemin> getSortants() {
        return sortants;
    }
    
    /**
     * Accesseur du Chemin entre la Livraison this et celui passé en paramètre
     * @param destination
     * @return 
     */
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
