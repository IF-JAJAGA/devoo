/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.control.Commands;

import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.Noeud;
import fr.insaif.jajagaa.model.ZoneGeographique;

/**
 *
 * @author alicia
 */
public class AjoutLivraisonCommande implements Command {
    
    private Noeud noeudAvant;
    private Noeud noeudALivrer;
    private ZoneGeographique zoneAvant ;
    private ZoneGeographique zone ;
    private ZoneGeographique zoneApres ;
    
    public AjoutLivraisonCommande(ZoneGeographique zone, Noeud noeudAvant, Noeud noeudALivrer){
        this.zone = zone;
        this.noeudAvant  = noeudAvant;
        this.noeudALivrer = noeudALivrer;
    }
    
    @Override
    public void execute(){
        if (zoneAvant == null){
            zoneAvant = new ZoneGeographique(zone);
            
            System.out.println("noeudAvant : " + noeudAvant + " ; noeudALivrer : " + noeudALivrer);
            // TODO changer le 5 en vrai idClient
            zone.setTournee(zone.getTournee().ajouterPointDeLivraison(noeudALivrer, 5, (Livraison) noeudAvant));

            zoneApres = new ZoneGeographique(zone);
        }
        else{
            zone = zoneApres;
        }
    }
    
    @Override
    public void undo(){
        zone = zoneAvant;
    }
    
    public ZoneGeographique getZone(){
        return zone;
    }
    
}
