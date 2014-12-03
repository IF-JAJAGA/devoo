/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insaif.jajagaa.control.Commands;

import fr.insaif.jajagaa.control.Controleur;
import fr.insaif.jajagaa.control.Parseur;
import fr.insaif.jajagaa.control.ParseurException;
import fr.insaif.jajagaa.model.ZoneGeographique;

/**
 *
 * @author Jérôme
 */
public class LirePlanCommand implements Command{
    private ZoneGeographique zone;
    private ZoneGeographique zoneApres;
    private ZoneGeographique zoneAvant;
    private String fichierPlan;
    

    public LirePlanCommand(ZoneGeographique zone, String fichierPlan) {
        this.zone = zone;
        this.fichierPlan = fichierPlan;
    }

    private LirePlanCommand() {
    }
    
    

    @Override
    public void execute() {
        if(zoneAvant == null){
            zoneAvant = new ZoneGeographique(zone);
            
            try { zone = Parseur.lirePlan(fichierPlan); }
            catch ( ParseurException pe) { }
            zoneApres = new ZoneGeographique(zone);
            Controleur.getInstance().setZoneVierge(zoneApres);

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
