/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.control.Commands;

import fr.insaif.jajagaa.control.Controleur;
import fr.insaif.jajagaa.control.Parseur;
import fr.insaif.jajagaa.control.ParseurException;
import fr.insaif.jajagaa.model.Livraison;
import fr.insaif.jajagaa.model.PlageHoraire;
import fr.insaif.jajagaa.model.ZoneGeographique;

import java.util.List;

/**
 *
 * @author Jérôme
 */
public class LireLivraisonsCommand implements Command{
    private ZoneGeographique zoneAvant ;
    private ZoneGeographique zone ;
    private ZoneGeographique zoneApres ;
    private final String fichierLivraison;

    /**
     * La lecture d'un fichier de livraison par le parseur modifie chez la zone la liste des noeuds et les plages.
     * @param zone
     * @param fichierLivraison 
     */
    public LireLivraisonsCommand(ZoneGeographique zone, String fichierLivraison) {
//        this.zone = new ZoneGeographique(Controleur.getInstance().getZoneVierge());
        this.zone = zone;
        this.fichierLivraison = fichierLivraison;
    }

    @Override
    public void execute(){
        if(zoneAvant == null){
            zoneAvant = new ZoneGeographique(zone);
            
            zone = new ZoneGeographique(Controleur.getInstance().getZoneVierge());
            List<PlageHoraire> plages = null;
            try{
                plages = Parseur.lireLivraison(fichierLivraison, zone);
            }
            catch(ParseurException pe) {
                Controleur.getInstance().notifyError(pe);
                return;
            }
            
            zone.getTournee().setPlagesHoraire(plages);
            zoneApres = new ZoneGeographique(zone);
        }
        else {
            zone = new ZoneGeographique(zoneApres);
        }
        
    }

    @Override
    public void undo() {
        zone = new ZoneGeographique(zoneAvant);
    }

    public ZoneGeographique getZone() {
        return zone;
    }
    
    
}
