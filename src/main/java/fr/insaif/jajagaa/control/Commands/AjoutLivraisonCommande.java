/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.control.Commands;

import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.Tournee;
import fr.insaif.jajagaa.model.ZoneGeographique;

/**
 *
 * @author alicia
 */
public class AjoutLivraisonCommande implements Command {
    
    private Noeud noeudAvant;
    private Noeud noeudMilieu;
    private ZoneGeographique zoneAvant ;
    private ZoneGeographique zone ;
    private ZoneGeographique zoneApres ;
    
    public AjoutLivraisonCommande(ZoneGeographique zone, Noeud noeudAvant, Noeud noeudMilieu){
        this.zone = zone;
        this.noeudAvant  = noeudAvant;
        this.noeudMilieu = noeudMilieu;
    }
    
    @Override
    public void execute(){
        if (zoneAvant == null){
            zoneAvant = new ZoneGeographique(zone);
            
            System.out.println("noeudAvant : " + noeudAvant + " ; noeudMilieu : " + noeudMilieu);
            zone.setTournee(zone.getTournee().ajouterPointDeLivraison(noeudAvant, noeudMilieu));
            
            zoneApres = new ZoneGeographique(zone);
        }
        else{
            zone = new ZoneGeographique(zoneApres);
        }
    }
    
    @Override
    public void undo(){
        zone = new ZoneGeographique(zoneAvant);
    }
    
    public ZoneGeographique getZone(){
        return zone;
    }
    
}
