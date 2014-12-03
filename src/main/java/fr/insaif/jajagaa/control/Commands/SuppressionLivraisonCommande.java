/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.control.Commands;

import fr.insaif.jajagaa.control.Commands.Command;
import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.ZoneGeographique;

/**
 *
 * @author alicia
 */
public class SuppressionLivraisonCommande implements Command {
    
    private ZoneGeographique zoneAvant ;
    private ZoneGeographique zone ;
    private ZoneGeographique zoneApres ;
    private Noeud noeudASup;

    public SuppressionLivraisonCommande(ZoneGeographique zone, Noeud noeudASup) {
        this.zone = zone;
        this.noeudASup = noeudASup;
    }
    
    @Override
    public void execute(){
        if (zoneAvant == null){
            zoneAvant = new ZoneGeographique(zone);
            
            zone.setTournee(zone.getTournee().supprimerPointLivraison(noeudASup));
            
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
