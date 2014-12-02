/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.control.Commands;

import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.Tournee;

/**
 *
 * @author alicia
 */
public class AjoutLivraisonCommande implements Command {
    
    private Noeud noeudAvant;
    private Noeud noeudMilieu;
    private Tournee tournee;
    private Tournee tourneeAvant;
    private Tournee tourneeApres;
    //TODO : compléter.
    
    public AjoutLivraisonCommande(Tournee tournee, Noeud noeudAvant, Noeud noeudMilieu){
        this.tournee = tournee;
        this.noeudAvant  = noeudAvant;
        this.noeudMilieu = noeudMilieu;
    }
    
    @Override
    public void execute(){
        if (tourneeAvant == null){
            tourneeAvant = tournee;
            tournee = tournee.ajouterPointDeLivraison(noeudAvant, noeudMilieu);
            tourneeApres = tournee;
        }
        else{
            tournee = tourneeApres;
        }
    }
    
    @Override
    public void undo(){
        tournee = tourneeAvant;
    }
    
    public Tournee getTournee(){
        return tournee;
    }
    
}
